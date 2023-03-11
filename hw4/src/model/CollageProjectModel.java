package model;

/**
 * An interface to represent the file operation that can be done with our program.
 */
public interface CollageProjectModel {

  /**
   * Creates a new Project with a name and a set of dimensions.
   * DEFAULT: every project has a white background layer by default.
   * @param name the name of the project
   * @param canvasHeight the height of the project desired by the user.
   * @param canvasWidth the width of the project desired by the user.
   * @throws IllegalArgumentException if the name is null
   *                                  or if the canvas height or width is not positive
   */
  void newProject(String name, int canvasHeight,int canvasWidth) throws IllegalArgumentException;

  /**
   * Loads the project onto the program to resume the process.
   * @param filePath the file path to the file to be loaded
   * @throws IllegalArgumentException if the filePath is null
   * @throws IllegalStateException if the content of the file is empty
   *                               or if a new project can't be made from the content inside
   *                               or if the file doesn't exist or can't be open
   */
  void loadProject(String filePath) throws IllegalArgumentException,
      IllegalStateException;


  /**
   * Allows the user to save their project to a file with all the loaded images included.
   * This is to accommodate workflow of users making incremental progress.
   * @param filePath the path/directory where the user saves their image
   * @throws IllegalArgumentException if the given filePath is null
   * @throws IllegalStateException if the user is not able to save their project
   *                               or if the project has not been made yet
   */
  void saveProject(String filePath, String projectType) throws IllegalArgumentException,
      IllegalStateException;


  /**
   * Allows the user to save an image that they have applied a filter(s) to.
   * @param filePath the directory or location of the new image.
   * @throws IllegalArgumentException if the user is not able to save their new image.
   * @throws IllegalStateException if the project has not been made yet
   */
  void saveImage(String filePath) throws IllegalArgumentException, IllegalStateException;



}
