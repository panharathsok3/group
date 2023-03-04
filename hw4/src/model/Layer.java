package model;

import java.util.ArrayList;

/**
 * Represents a single layer.
 */
public class Layer {
  private final String layerName;


  private ArrayList<ArrayList<Pixel>> layers;


  public Layer(String layerName) {
    this.layerName = layerName;
  }

  public String getName() {
    return this.layerName;
  }


}
