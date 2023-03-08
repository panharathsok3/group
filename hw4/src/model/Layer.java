package model;

import java.util.ArrayList;

/**
 * Represents a single layer.
 */
public class Layer {
  private final String layerName;
  private ArrayList<ArrayList<Pixel>> pixelsOnLayer;
  private final int height;
  private final int width;
  private final int alpha;

  /**
   * Creates a layer for the collage.
   * @param layerName the name of the layer
   * @param height the height of the layer
   * @param width the width of the layer
   */
  public Layer(String layerName, int height, int width, int alpha) {
    this.layerName = layerName;
    this.height = height;
    this.width = width;
    this.alpha = alpha;
    this.pixelsOnLayer = new ArrayList<>();
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

}
