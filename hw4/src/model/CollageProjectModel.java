package model;


import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * An interface to represent the operations that can be done with our program.
 */
public interface CollageProjectModel {

  /**
   * Produces the project's height.
   * @param name the name of the project.
   * @return the integer value of the project's height.
   */
  int getHeight(String name);

  /**
   * Produces the project's height.
   * @param name the name of the project.
   * @return the integer value of the project's width.
   */
  int getWidth(String name);

  /**
   * Creates a new Project with a name and a set of dimensions.
   * DEFAULT: every project has a white background layer by default.
   * @param projectName the name of the new project that user is creating.
   * @param canvasHeight the height of the project desired by the user.
   * @param canvasWidth the width of the project desired by the user.
   */
  void newProject(String projectName, int canvasHeight,int canvasWidth);

  /**
   * Adds a layer with a given name to the top of the whole project.
   * DEFAULT: a fully transparent white image and the normal filter.

   * @throws IllegalArgumentException if there already exists a layer with the name
   * that the user is trying to give. Program should continue running.
   */
  void addLayerToProject();


  /**
   * Places an image on a layer at given dimensions.
   * @param imageToAdd the image the user wants to add to the layer.
   * @param xPos the position of the x-coordinate.
   * @param yPos the position of the y-coordinate.
   */
  void addImageToLayer(ArrayList<Pixel> imageToAdd, int xPos, int yPos);


  /**
   * Sets the filter of the given layer based on the filter options.
   * A filter can be normal,red-component,green-component,blue-component,
   * brighten-value,brighten-intensity,brighten-luma, etc.
   * @param layerName the name of the layer we want to apply this filter on.
   * @param filterOption the option of filter that the client desires.
   */
  void setFilter(String layerName, String filterOption, double[] filterValue);


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
   * Allows the user to save a project as a PPM file.
   * <p>
   * //  * @param fileName     the name of the file they want to give to their project.
   *
   * @param filePath     the location where the file will be stored.
   * @param loadedImages the images in the project at the time they saved it.
   * @throws IllegalArgumentException if the file has no contents/images.
   * @throws FileNotFoundException    if the file
   */
  void savePPMProject(String filePath, Pixel[][] loadedImages) throws IOException, FileNotFoundException;

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
