package controller;


import model.CollageProject;
import view.GUIView;

public class CollageGUIController implements Features {

  private final CollageProject model;
  private CollageController textUIController;
  private GUIView view;
  private boolean hasAlpha;

  public CollageGUIController(CollageProject model) {
    this.model = model;
    this.textUIController = new CollageControllerImpl(this.model, false);
  }

  @Override
  public void setView(GUIView view) {
    this.view = view;
    this.view.addFeatures(this);
  }

  @Override
  public void exitProgram() {
    System.exit(0);
  }

  @Override
  public void newProject(String typed, String height, String width, String hasAlpha) {

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

      this.model.newProject(typed, height2, width2);
      this.showImage();

      this.textUIController = new CollageControllerImpl(this.model, true);

    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Can't use strings for the ");
    }
  }

  @Override
  public void loadProject(String filePath) {
    this.textUIController.loadProject(filePath);
    this.view.updateLayers(this.model.getLayers().size());
    this.showImage();
  }

  @Override
  public void addLayer(String layerName) {
    this.model.addLayer(layerName);
    this.showImage();
  }

  @Override
  public void addImageToLayer(String layerName, String filePath, String xPos, String yPos) {
    try {
      int xPosition = Integer.parseInt(xPos);
      int yPosition = Integer.parseInt(yPos);

      String extension = filePath.substring(filePath.lastIndexOf(".") + 1);

      String imageToken = null;

      if (extension.equalsIgnoreCase("ppm")) {
        imageToken = "P3";
      }

      this.model.addImageToLayer(layerName,
          this.textUIController.readImage(filePath, this.hasAlpha, imageToken), xPosition, yPosition);

      this.showImage();
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Can't use strings for the ");
    }
  }

  @Override
  public void saveProject(String filePath, String projectType) {
    this.textUIController.saveProject(filePath, projectType);
  }

  @Override
  public void saveImage(String filePath) {
    this.textUIController.saveImage(filePath);
  }

  @Override
  public void setFilter(String layerName, String filterOption) {
    this.model.setFilter(layerName, filterOption);
    this.showImage();
    this.view.refresh();
  }

  private void showImage() {
    this.view.getImageToPutOnScreen(this.model.getHeight(), this.model.getWidth(),
        this.model.makeFinalImage(this.hasAlpha).getPixelsOnLayer());
  }

}
