package model;

/**
 * An interface to represent the operations that can be done with our program.
 */
public interface CollageProjectModel {

  /**
   * Creates a new Project with a name and a set of dimensions.
   * DEFAULT: every project has a white background layer by default.
   * @param canvasHeight the height of the project desired by the user.
   * @param canvasWidth the width of the project desired by the user.
   */
  void newProject(int canvasHeight,int canvasWidth);

  void loadProject(String filePath)


  /**
   * Allows the user to save their project to a file with all the loaded images included.
   * This is to accommodate workflow of users making incremental progress.
   * @param filePath     the path/directory where the user saves their image
   * @throws IllegalArgumentException if the user is not able to save their project
   */
  void saveProject(String filePath);


  /**
   * Allows the user to save an image that they have applied a filter(s) to.
   *
   * @param imagePixels the pixels in the new image.
   * @param filePath the directory or location of the new image.
   * @throws IllegalArgumentException if the user is not able to save their new image.
   */
  void saveImage(String filePath, Pixel[][] imagePixels) throws IllegalArgumentException;



}
