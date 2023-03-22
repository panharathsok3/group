package controller;


import view.GUIView;

public interface Features {

  void exitProgram();

  void setView(GUIView v);

  void newProject(String typed, String height, String width);

  /**
   * Loads the project onto the program to resume the process.
   * @param filePath the file path to the file to be loaded
   * @throws IllegalArgumentException if the filePath is null
   * @throws IllegalStateException if the content of the file is empty
   *                               or if a new project can't be made from the content inside
   *                               or if the file doesn't exist or can't be open
   */
  void loadProject(String filePath) throws IllegalArgumentException, IllegalStateException;

}
