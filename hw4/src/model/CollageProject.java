package model;

import java.util.ArrayList;

/**
 *
 */
public interface CollageProject extends CollageProjectModel {

  /**
   * Adds a layer with a given name to the top of the whole project.
   * DEFAULT: a fully transparent white image and the normal filter.
   * @param layerName the name of the layer
   * @throws IllegalArgumentException if there already exists a layer with the name
   * that the user is trying to give. Program should continue running.
   */
  void addLayerToProject(String layerName) throws IllegalArgumentException;

  /**
   * Places an image on a layer at given dimensions.
   * @param layerName the layer that is being added to
   * @param imageToAdd the image the user wants to add to the layer.
   * @param xPos the position of the x-coordinate.
   * @param yPos the position of the y-coordinate.
   */
  void addImageToLayer(String layerName, ArrayList<Pixel> imageToAdd, int xPos, int yPos);


  /**
   * Sets the filter of the given layer based on the filter options.
   * A filter can be normal,red-component,green-component,blue-component,
   * brighten-value,brighten-intensity,brighten-luma, etc.
   * @param layerName the name of the layer we want to apply this filter on.
   * @param filterOption the option of filter that the client desires.
   */
  void setFilter(String layerName, String filterOption, double[] filterValue);

}
