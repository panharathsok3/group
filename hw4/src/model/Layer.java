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

  public ArrayList<Pixel> layer;



  public Layer(String layerName, int height, int width) {
    this.layerName = layerName;
    this.height = height;
    this.width = width;
  }

  public ArrayList<ArrayList<Pixel>> getPixelsOnLayer() {
    return this.pixelsOnLayer;
  }

  public String getName() {
    return this.layerName;
  }


}
