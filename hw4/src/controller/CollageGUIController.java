package controller;


import java.awt.image.BufferedImage;
import java.util.List;
import model.CollageProject;
import model.IPixel;
import view.GUIView;

/**
 * This class handles the asynchronous interactions that the user inputs and delegates action to
 * the model using the CollageController to handle the processing part and send information to the
 * GUI to update it. It also uses the CollageController to let it handle the File IO.
 */
public class CollageGUIController implements Features {

  private final CollageProject model;
  private CollageController textUIController;
  private GUIView view;
  private boolean projectMade;

  /**
   * The Controller that handles the delegation.
   * @param model the CollageProject that will be used
   * @throws IllegalArgumentException if the given argument is null
   */
  public CollageGUIController(CollageProject model) throws IllegalArgumentException {

    if (model == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    this.model = model;
    this.projectMade = false;
    this.textUIController = new CollageControllerImpl(this.model, false);
  }

  @Override
  public void setView(GUIView view) throws IllegalArgumentException {

    if (view == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    this.view = view;
    this.view.addFeatures(this);
  }

  @Override
  public void newProject(String projectName, String height, String width)
      throws IllegalArgumentException {

    try {
      int height2 = Integer.parseInt(height);
      int width2 = Integer.parseInt(width);

      this.model.newProject(projectName, height2, width2);
      this.showImage();

      this.projectMade = true;
      this.textUIController = new CollageControllerImpl(this.model, true);

    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Can't use strings as inputs for height and width");
    }
  }

  @Override
  public void loadProject(String filePath) throws IllegalArgumentException {
    this.textUIController.loadProject(filePath);
    this.view.resetLayers();
    this.view.updateLayers(this.model.getLayers().size());

    this.projectMade = true;
    this.textUIController = new CollageControllerImpl(this.model, true);
    this.showImage();
  }

  @Override
  public void addLayer(String layerName) throws IllegalArgumentException {
    this.model.addLayer(layerName);
    this.showImage();
  }

  @Override
  public void addImageToLayer(String layerName, String filePath, String xPos, String yPos)
      throws IllegalArgumentException {
    try {
      int xPosition = Integer.parseInt(xPos);
      int yPosition = Integer.parseInt(yPos);

      String extension = filePath.substring(filePath.lastIndexOf(".") + 1);

      String imageToken = null;
      List<List<IPixel>> image = null;

      if (extension.equalsIgnoreCase("ppm")) {
        imageToken = "P3";
        image = this.textUIController.readImagePPM(filePath, imageToken);
      } else {
        image = this.textUIController.readImage(filePath);
      }

      this.model.addImageToLayer(layerName, image, xPosition, yPosition);

      this.showImage();
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("x and y position needs to be integers");
    }
  }

  @Override
  public void saveProject(String filePath) throws IllegalArgumentException {
    this.textUIController.saveProject(filePath);
  }

  @Override
  public void saveImage(String filePath) throws IllegalArgumentException {
    this.textUIController.saveImage(filePath);
  }

  @Override
  public void setFilter(String layerName, String filterOption) throws IllegalArgumentException {
    this.model.setFilter(layerName, filterOption);
    this.showImage();
    this.view.refresh();
  }

  @Override
  public boolean projectMade() {
    return this.projectMade;
  }

  /**
   * Delegates to the view to display the current image onto the screen.
   */
  private void showImage() {

    List<List<IPixel>> imageToAdd = this.model.makeFinalImage(true).getPixelsOnLayer();

    BufferedImage image = new BufferedImage(this.model.getHeight(),
        this.model.getWidth(), BufferedImage.TYPE_INT_ARGB);

    for (int x = 0; x < image.getHeight(); x++) {
      for (int y = 0; y < image.getWidth(); y++) {
        int r = imageToAdd.get(x).get(y).getRedComponent();
        int g = imageToAdd.get(x).get(y).getGreenComponent();
        int b = imageToAdd.get(x).get(y).getBlueComponent();

        int a = imageToAdd.get(x).get(y).getAlphaComponent();

        int argb = a << 24;
        argb |= r << 16;
        argb |= g << 8;
        argb |= b;
        image.setRGB(y, x, argb);
      }
    }

    this.view.getImageToPutOnScreen(image);
  }
}
