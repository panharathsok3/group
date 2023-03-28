package model;

import java.util.List;

/**
 * Represents the interface for a single layer that can hold images and modify it.
 */
public interface ILayer {

  /**
   * Returns a copy of the pixels on this layer in a 2D array.
   * @return a copy of the pixels on this layer in a 2D array
   */
  List<List<IPixel>> getPixelsOnLayer();

  /**
   * Returns the name of this layer.
   * @return the name of this layer
   */
  String getName();

  /**
   * Adds a given image to this layer. It doesn't resize the image, so if the image is bigger than
   * the layer, it will only get the top left portion of the image and not the entire image.
   * @param xPos  the x position of the pixel on this layer
   * @param yPos  the y position of the pixel on this layer
   * @param image the image that will be placed on this layer
   * @throws IllegalArgumentException if the given image is null
   *                                  or if the xPos or yPos is not in the bounds of the Layer
   */
  void addImage(int xPos, int yPos, List<List<IPixel>> image);
}
