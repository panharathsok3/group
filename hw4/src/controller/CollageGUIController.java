package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

import model.CollageProject;
import model.CollageProjectModelImpl;
import view.GUIView;

public class CollageGUIController implements Features {

  private final CollageProject model;
  private GUIView view;

  public CollageGUIController(CollageProject model) {
    this.model = model;
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
  public void newProject(String typed) {
    int height = this.view.getImageBorderHeight();
    int width = this.view.getImageBorderWidth();
    this.model.newProject(typed, height, width);
    this.view.displayImage(this.view.getImageToPutOnScreen(height, width,
        this.model.getLayers().get(0).getPixelsOnLayer()));
  }

  @Override
  public void loadProject(String filePath) throws IllegalArgumentException, IllegalStateException {

  }
}
