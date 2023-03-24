package controller;

import model.CollageProject;
import view.GUIView;

public class CollageGUIController implements Features {

  private final CollageProject model;
  private final CollageController textUIController;
  private GUIView view;

  public CollageGUIController(CollageProject model, CollageController textUIController) {
    this.model = model;
    this.textUIController = textUIController;
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
  public void newProject(String typed, String height, String width) {
    int height2 = Integer.parseInt(height);
    int width2 = Integer.parseInt(width);

    this.model.newProject(typed, height2, width2);
    this.view.displayImage(this.view.getImageToPutOnScreen(height2, width2,
            this.model.getLayers().get(0).getPixelsOnLayer()));
  }

  @Override
  public void loadProject(String filePath) {
    this.model.loadProject(filePath);
  }

  @Override
  public void addLayer(String layerName) {
    this.model.addLayer(layerName);
    this.view.refresh();
  }

  @Override
  public void addImageToLayer(String layerName, String filePath, String xPos, String yPos) {
    int xPosition = Integer.parseInt(xPos);
    int yPosition = Integer.parseInt(yPos);

    this.model.addImageToLayer(layerName, filePath, xPosition, yPosition);

    this.view.displayImage(this.view.getImageToPutOnScreen(xPosition, yPosition,
            this.model.getLayers().get(0).getPixelsOnLayer()));

  }

  @Override
  public void saveProject(String filePath, String projectType) {
    this.model.saveProject(filePath, projectType);
  }

  @Override
  public void saveImage(String filePath) {
    this.model.saveImage(filePath);
  }

  @Override
  public void setFilter(String layerName, String filterOption) {
    this.model.setFilter(layerName, filterOption);
    this.view.refresh();
  }


}
