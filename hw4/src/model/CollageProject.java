package model;

import java.util.List;
import java.util.Map;

/**
 * An interface to represent the collage operations that can be done with our program.
 */
public interface CollageProject {

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
   * Adds a layer with a given name to the top of the whole project.
   * DEFAULT: a fully transparent white image and the normal filter.
   * @param layerName the name of the layer
   * @throws IllegalStateException if the project has not been made yet
   * @throws IllegalArgumentException if the given layerName is null
   *                                  or if there already exists a layer with the name
   */
  void addLayer(String layerName) throws IllegalStateException, IllegalArgumentException;

  /**
   * Places an image on a layer at given dimensions where the top left is 0, 0.
   * The x values increases to the right and the y values increases downwards.
   * @param layerName the layer that is being added to
   * @param image the image to put on the layer
   * @param xPos the position of the x-coordinate
   * @param yPos the position of the y-coordinate
   * @throws IllegalArgumentException if the layer doesn't exist or is null
   *                                  or if the filePath doesn't exist
   *                                  or if the x or y position is not on the canvas
   * @throws IllegalStateException if the project has not been made yet
   */
  void addImageToLayer(String layerName, List<List<IPixel>> image, int xPos, int yPos)
      throws IllegalArgumentException, IllegalStateException;

  /**
   * Sets the filter of the given layer based on the filter options.
   * A filter can be normal,red-component,green-component,blue-component,
   * brighten-value,brighten-intensity,brighten-luma, etc.
   * @param layerName the name of the layer we want to apply this filter on.
   * @param filterOption the option of filter that the client desires.
   * @throws IllegalArgumentException if the given layerName is null
   *                                  or if the filterOptions is null or doesn't exist
   * @throws IllegalStateException if the project has not been made yet
   *                               or if the given layerName doesn't exist
   */
  void setFilter(String layerName, String filterOption) throws IllegalArgumentException,
      IllegalStateException;

  /**
   * Returns a layer that is made from putting all the layers in this CollageProject together.
   * @param hasAlpha true if and only if the original images has an alpha component
   * @return a layer that is made from putting all the layers in this CollageProject together
   * @throws IllegalStateException if the project has not been made yet
   */
  ILayer makeFinalImage(boolean hasAlpha) throws IllegalStateException;


  /**
   * Returns a copy of the layers on the collage.
   * @return a copy of the layers on the collage
   * @throws IllegalStateException if the project has not been made yet
   */
  List<ILayer> getLayers() throws IllegalStateException;

  /**
   * Returns the name of this project.
   * @return the name of this project
   * @throws IllegalStateException if the project has not been made yet
   */
  String getProjectName() throws IllegalStateException;

  /**
   * Returns the width of this collageProject.
   * @return the width of this collageProject
   * @throws IllegalStateException if the project has not been made yet
   */
  int getWidth() throws IllegalStateException;

  /**
   * Returns the height of this collageProject.
   * @return the height of this collageProject
   * @throws IllegalStateException if the project has not been made yet
   */
  int getHeight() throws IllegalStateException;

  /**
   * Returns the maximum value of a color of this collageProject.
   * @return the maximum value of a color of this collageProject
   * @throws IllegalStateException if the project has not been made yet
   */
  int getMaxValue() throws IllegalStateException;

  /**
   * Returns a map of a Layer name and its filter of this collageProject.
   * @return a map of a Layer name and its filter of this collageProject
   * @throws IllegalStateException if the project has not been made yet
   */
  Map<String, String> getFiltersOnProject() throws IllegalStateException;

}
