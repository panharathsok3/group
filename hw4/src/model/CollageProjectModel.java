package model;

/**
 * An interface to represent the file operation that can be done with our program.
 */
public interface CollageProjectModel {

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
   * @throws IllegalArgumentException if the user is not able to save their new image
   *                                  or if the given argument is null
   * @throws IllegalStateException if the project has not been made yet
   */
  void saveImage(String filePath) throws IllegalArgumentException, IllegalStateException;



}
