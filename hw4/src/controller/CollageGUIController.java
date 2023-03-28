package controller;


import model.CollageProject;
import view.GUIView;

/**
 * This class handles the asynchronous interactions that the user inputs and delegates action to
 * the model to handle the processing part and send information to the GUI to update it.
 */
public class CollageGUIController implements Features {

  private final CollageProject model;
  private CollageController textUIController;
  private GUIView view;
  private boolean hasAlpha;
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
  public void newProject(String projectName, String height, String width, String hasAlpha)
      throws IllegalArgumentException {

    try {
      int height2 = Integer.parseInt(height);
      int width2 = Integer.parseInt(width);

      if (hasAlpha.equalsIgnoreCase("yes")) {
        this.hasAlpha = true;
      }
      else if (hasAlpha.equalsIgnoreCase("no")) {
        this.hasAlpha = false;
      }
      else {
        throw new IllegalArgumentException("you need to enter either yes or no when asked for the "
            + "alpha value");
      }

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
    this.showImage();
  }

  @Override
  public void addLayer(String layerName) throws IllegalArgumentException {
    this.model.addLayer(layerName);
    this.showImage();
  }

  @Override
  public void addImageToLayer(String layerName, String filePath, String xPos, String yPos)
      throws IllegalArgumentException{
    try {
      int xPosition = Integer.parseInt(xPos);
      int yPosition = Integer.parseInt(yPos);

      String extension = filePath.substring(filePath.lastIndexOf(".") + 1);

      String imageToken = null;

      if (extension.equalsIgnoreCase("ppm")) {
        imageToken = "P3";
      }

      this.model.addImageToLayer(layerName,
          this.textUIController.readImage(filePath, this.hasAlpha, imageToken),
          xPosition, yPosition);



      this.showImage();
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("x and y position needs to be integers");
    }
  }

  @Override
  public void saveProject(String filePath, String projectType) throws IllegalArgumentException {
    this.textUIController.saveProject(filePath, projectType);
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
    this.view.getImageToPutOnScreen(this.model.getHeight(), this.model.getWidth(),
        this.model.makeFinalImage(this.hasAlpha).getPixelsOnLayer());
  }

}
