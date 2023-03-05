package model;

/**
 * An interface to represent the operations that can be done with our program.
 */
public interface CollageProjectModel {

  /**
   * Creates a new Project with a name and a set of dimensions.
   * DEFAULT: every project has a white background layer by default.
   //* @param projectName the name of the new project that user is creating.
   * @param canvasHeight the height of the project desired by the user.
   * @param canvasWidth the width of the project desired by the user.
   */
  void newProject(int canvasHeight,int canvasWidth);


  /**
   * Allows the user to save their project to a file with all the loaded images included.
   * This is to accommodate workflow of users making incremental progress.
   *
   * @param fileName     the name of the new file.
   * @param filePath     the path/directory where the user saves their image.
   * @param loadedImages the images that were already in the project.
   * @throws IllegalArgumentException is the user is not able to save their project.
   */
  void saveProject(String fileName, String filePath, Pixel[][] loadedImages);


  /**
   * Allows the user to save an image that they have applied a filter(s) to.
   *
   * @param imagePixels the pixels in the new image.
   * @param filePath the directory or location of the new image.
   *                 //* @param imageComponents the rgb values in that image after the filter(s) have been applied.
   * @throws IllegalArgumentException is the user is not able to save their new image.
   */
  void saveImage(String filePath,Pixel[][] imagePixels) throws IllegalArgumentException;



}
