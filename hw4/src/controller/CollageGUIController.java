package controller;

import model.CollageProject;
import model.CollageProjectModelImpl;
import view.GUIView;

public class CollageGUIController implements Features {

  private final CollageProject model;
  private GUIView view;

  public CollageGUIController(CollageProject model) {
    this.model = model;
  }

  public void setGUIView(GUIView view) {
    this.view = view;
    view.addFeatures(this);
  }

  @Override
  public void exitProgram() {

  }

  @Override
  public void setView(GUIView v) {

  }
}
