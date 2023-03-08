package model;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * Represents a single layer.
 */
public class Layer {
  private final String layerName;
  private ArrayList<ArrayList<Pixel>> pixelsOnLayer;
  private Set<Integer> occupiedPixels;
  private final int height;
  private final int width;
  private final int alpha;

  /**
   * Creates a layer for the collage.
   * @param layerName the name of the layer
   * @param height the height of the layer
   * @param width the width of the layer
   * @param alpha the alpha value of the layer
   * @throws IllegalArgumentException if the given String is null
   *                                  or if the height, width, or alpha is negative
   */
  public Layer(String layerName, int height, int width, int alpha) throws IllegalArgumentException{
    if (layerName == null || height < 0 || width < 0 || alpha < 0) {
      throw new IllegalArgumentException("String can't be null and integers can't be negative");
    }

    this.layerName = layerName;
    this.height = height;
    this.width = width;
    this.alpha = alpha;
    this.pixelsOnLayer = new ArrayList<>();
    this.occupiedPixels = new TreeSet<>();
    this.addPixels();
  }

  /**
   * Returns pixels in a 2D array.
   * @return pixels in a 2D array
   */
  public ArrayList<ArrayList<Pixel>> getPixelsOnLayer() {
    return this.pixelsOnLayer;
  }

  /**
   * Returns the name of this layer.
   * @return the name of this layer
   */
  public String getName() {
    return this.layerName;
  }

  /**
   * Creates a 2d array of pixels in this layer.
   */
  private void addPixels() {
    for (int i = 0; i < this.height; i++) {
      this.pixelsOnLayer.add(new ArrayList<>());
      for (int j = 0; j < this.width; j++) {
        this.pixelsOnLayer.get(i).add(new Pixel(255, 255, 255, this.alpha));
      }
    }
  }



  /**
   * Adds the coordinate that has been occupied by pixels.
   * @param x the x position on the layer that's being occupied
   * @param y the y position on the layer that's being occupied
   */
  private void occupy(int x, int y) {
    for (int i = x; i < this.height; i++) {
      for (int j = y; j < this.width; j++) {
        this.occupiedPixels.add(i + j);
      }
    }
  }

}
