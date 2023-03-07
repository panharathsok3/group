package model;

import java.util.ArrayList;

/**
 * Represents a single layer.
 */
public class Layer {
  private final String layerName;


  private ArrayList<ArrayList<Pixel>> layers;


  public ArrayList<Pixel> layer;



  public Layer(String layerName, int height, int width) {
    this.layerName = layerName;
  }



  public String getName() {
    return this.layerName;
  }


}
