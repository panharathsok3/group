package controller;


import view.GUIView;

/**
 * The features that will be used for a GUI to delegate information between the model and the view.
 */
public interface Features {

  /**
   * Sets the view of the controller.
   * @param view the view that will used to display information
   * @throws IllegalArgumentException when the given argument is null
   */
  void setView(GUIView view) throws IllegalArgumentException;

  /**
   * Creates a new project using the model and display it to the view.
   * @param projectName the name of the project
   * @param height the height of the project
   * @param width the width of the project
   * @param hasAlpha true if and only if the project uses an alpha value
   * @throws IllegalArgumentException when the given argument is null
   *                                  or if height or width is not an integer as a String
   */
  void newProject(String projectName, String height, String width, String hasAlpha)
      throws IllegalArgumentException;

  /**
   * Loads the project onto the program to resume the process.
   * @param filePath the file path to the file to be loaded
   * @throws IllegalArgumentException when the given argument is null
   */
  void loadProject(String filePath) throws IllegalArgumentException;

  /**
   * Adds a layer onto the project and display it to the view.
   * @param layerName the name of the layer to be added
   * @throws IllegalArgumentException when the given argument is null
   */
  void addLayer(String layerName) throws IllegalArgumentException;

  /**
   * Adds an image from a file path onto a specified layer on a given offset.
   * @param layerName the name of the layer that the image will be added to
   * @param filePath the file path to the layer
   * @param xPos the x offset of the image
   * @param yPos the y offset of the image
   * @throws IllegalArgumentException when the given argument is null
   *                                  or if xPos or yPos is not an integer as a String
   */
  void addImageToLayer(String layerName, String filePath, String xPos, String yPos)
      throws IllegalArgumentException;

  /**
   * Saves the project onto a file with a given file path. The projectType refers to the file that
   * is being worked on this can be a PNG, JPEG, PPM, etc.
   * @param filePath the file path that the project will be saved to
   * @param projectType the project type of the project
   * @throws IllegalArgumentException when the given argument is null
   */
  void saveProject(String filePath, String projectType) throws IllegalArgumentException;

  /**
   * Saves the current image on the project to a file.
   * @param filePath the file path that the image will be saved to
   * @throws IllegalArgumentException when the given argument is null
   */
  void saveImage(String filePath) throws IllegalArgumentException;

  /**
   * Sets the filter on a layer using a given filterOption.
   * @param layerName the layer the filter will be set on
   * @param filterOption the filter that will be applied
   * @throws IllegalArgumentException when the given argument is null
   */
  void setFilter(String layerName, String filterOption) throws IllegalArgumentException;

  /**
   * Returns true if and only if the project has been made.
   * @return true if and only if the project has been made
   */
  boolean projectMade();
}
