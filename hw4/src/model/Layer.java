package model;

/**
 * Represents a single layer.
 */
public class Layer {
  private final int height;
  private final int width;
  private final String name;


  public Layer(int height, int width, String name) {
    this.height = height;
    this.width = width;
    this.name = name;
  }

  public String getName() {
    return this.name;
  }
}
