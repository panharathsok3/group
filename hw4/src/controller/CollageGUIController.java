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
    int hieght2 = Integer.parseInt(height);
    int width2 = Integer.parseInt(width);

    this.model.newProject(typed, hieght2, width2);
    this.view.displayImage(this.view.getImageToPutOnScreen(hieght2, width2,
        this.model.getLayers().get(0).getPixelsOnLayer()));
  }

  @Override
  public void loadProject(String filePath) throws IllegalArgumentException, IllegalStateException {

  }
}
