package model;

import java.util.ArrayList;

/**
 * An interface to represent the canvas operations that can be done with our program.
 */
public interface CollageProject extends CollageProjectModel {

  /**
   * Adds a layer with a given name to the top of the whole project.
   * DEFAULT: a fully transparent white image and the normal filter.
   * @param layerName the name of the layer
   * @throws IllegalStateException if there already exists a layer with the name
   * that the user is trying to give. Program should continue running.
   * @throws IllegalArgumentException if the given layerName is null
   */
  void addLayer(String layerName) throws IllegalStateException, IllegalArgumentException;

  /**
   * Places an image on a layer at given dimensions where the top left is 0, 0.
   * The x values increases to the right and the y values increases downwards.
   * @param layerName the layer that is being added to
   * @param filePath the image the user wants to add to the layer
   * @param xPos the position of the x-coordinate
   * @param yPos the position of the y-coordinate
   * @throws IllegalArgumentException if the layer doesn't exist or is null
   *                                  or if the filePath doesn't exist
   *                                  or if the x or y position is not on the canvas
   */
  void addImageToLayer(String layerName, String filePath, int xPos, int yPos)
      throws IllegalArgumentException;


  /**
   * Sets the filter of the given layer based on the filter options.
   * A filter can be normal,red-component,green-component,blue-component,
   * brighten-value,brighten-intensity,brighten-luma, etc.
   * @param layerName the name of the layer we want to apply this filter on.
   * @param filterOption the option of filter that the client desires.
   * @throws IllegalArgumentException if the given layerName is null or doesn't exist
   *                                  or if the filterOptions is null or doesn't exist
   */
  void setFilter(String layerName, String filterOption) throws IllegalArgumentException;

  /**
   * Returns a copy of the layers on the collage.
   * @return a copy of the layers on the collage
   */
  ArrayList<Layer> getLayers();

}
