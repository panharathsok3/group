package model;

import java.awt.*;

/**
 * Represents a single layer.
 */
public class Layer {
  private final int height;
  private final int width;
  private final String layerName;
  private Color color;



  public Layer(int height, int width, String name) {
    this.height = height;
    this.width = width;
    this.layerName = name;


  }


  public String getName() {
    return this.layerName;
  }



}
