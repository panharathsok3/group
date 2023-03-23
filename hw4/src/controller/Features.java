package controller;


import view.GUIView;

public interface Features {

  void exitProgram();

  void setView(GUIView v);

  void newProject(String typed, String height, String width);

  /**
   * Loads the project onto the program to resume the process.
   * @param filePath the file path to the file to be loaded
   */

  void loadProject(String filePath);

  void addLayer(String layerName);

  void addImageToLayer(String layerName, String filePath, String xPos, String yPos);

  void saveProject(String filePath, String projectType);

  void saveImage(String filePath);

  void setFilter(String layerName, String filterOption);





}
