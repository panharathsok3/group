package model;



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
   * Produces a given component
   * @param name the name of the project we want to get the
   * @return
   */
  Color[][] getIndividualComponent(String name);

  /**
   * Creates a new Project with a name and a set of dimensions.
   * DEFAULT: every project has a white background layer by default.
   * @param name the name of the new project that user is creating.
   * @param height the height of the project desired by the user.
   * @param width the width of the project desired by the user.
   */
  void newProject(String name, int height,int width);

  /**
   * Adds a layer with a given name to the top of the whole project.
   * DEFAULT: a fully transparent white image and the normal filter.
   * @param name the name of layer that would be added to the project.
   * @throws IllegalArgumentException if there already exists a layer with the name
   * that the user is trying to give. Program should continue running.
   */
  void addLayerToProject(String name);


  /**
   * Places an image on a layer at given dimensions.
   * @param layerName the name of the layer that the image would be placed on.
   * @param imageName the image the user wants to add to the layer.
   * @param xPos the position of the x-coordinate.
   * @param yPos the position of the y-coordinate.
   */
  void addImageToLayer(String layerName, String imageName, int xPos,int yPos);


  /**
   * Sets the filter of the given layer based on the filter options.
   * A filter can be normal,red-component,green-component,blue-component,
   * brighten-value,brighten-intensity,brighten-luma, etc.
   * @param layerName the name of the layer we want to apply this filter on.
   * @param filterOption the option of filter that the client desires.
   */
  void setFilter(String layerName, String filterOption, double filterValue);


}
