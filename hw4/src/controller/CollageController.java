package controller;

import java.util.List;
import model.IPixel;

/**
 * A controller interface for the CollageProjectModelImpl. This interface deals with handling file
 * IO and running the program for the CollageProjectModelImpl.
 */
public interface CollageController {

  /**
   * The controller for the CollageProjectModelImpl. This is used to take user input and give it
   * to the model to process information and sends the information to the view to display something
   * to the user.
   * @throws IllegalStateException if and only if the controller is unable to successfully read
   *                              input or transmit output
   */
  void runProgram() throws IllegalStateException;

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

  /**
   * Read an image file and returns the pixels on the image as a 2D array.
   * Converts color value to the base of 256.
   * @param filename the path of the file
   * @param hasAlpha true if and only if the original image has an alpha value
   * @param fileType the type of file that is being read from
   * @return the pixels on the image as a 2D array
   * @throws IllegalStateException when the file could not be retrieved
   *                               or the file is not a PPM file
   */
  List<List<IPixel>> readImage(String filename, boolean hasAlpha, String fileType)
      throws IllegalStateException;
}
