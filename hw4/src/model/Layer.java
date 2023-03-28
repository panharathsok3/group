package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single layer that can hold images.
 * A layer is represented as a 2D ArrayList of Pixels.
 * It stores the layer name, its height and width as well as the alpha value.
 */
public class Layer implements ILayer {
  private final String layerName;
  private final List<List<IPixel>> pixelsOnLayer;
  private final int height;
  private final int width;
  private int alpha;

  /**
   * Creates a layer for the collage.
   * @param layerName the name of the layer
   * @param height the height of the layer
   * @param width the width of the layer
   * @param alpha the alpha value of the layer
   * @throws IllegalArgumentException if the given String is null
   *                                  or if the height, width, or alpha is negative
   */
  public Layer(String layerName, int height, int width, int alpha) throws IllegalArgumentException {
    if (layerName == null || height < 0 || width < 0 || alpha < 0) {
      throw new IllegalArgumentException("String can't be null and integers can't be negative");
    }

    this.layerName = layerName;
    this.height = height;
    this.width = width;
    this.alpha = alpha;
    this.pixelsOnLayer = new ArrayList<>();
    this.addPixels();
  }

  /**
   * Creates a Layer with a given 2D array of IPixels.
   * @param layerName the name of this layer
   * @param height the height of this Layer
   * @param width the width of this Layer
   * @param pixelsOnLayer the IPixels on this Layer
   * @throws IllegalArgumentException when the given arguments are null
   */
  public Layer(String layerName, int height, int width, List<List<IPixel>> pixelsOnLayer)
      throws IllegalArgumentException {

    if (layerName == null || pixelsOnLayer == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    this.layerName = layerName;
    this.height = height;
    this.width = width;
    this.pixelsOnLayer = pixelsOnLayer;
  }

  @Override
  public List<List<IPixel>> getPixelsOnLayer() {
    return new ArrayList<>(this.pixelsOnLayer);
  }

  @Override
  public String getName() {
    return this.layerName;
  }

  @Override
  public void addImage(int xPos, int yPos, List<List<IPixel>> image)
      throws IllegalArgumentException {
    if (image == null || xPos < 0 || xPos > this.height || yPos < 0 || yPos > this.width) {
      throw new IllegalArgumentException("Arguments can't be null and they can't be negative");
    }

    int height = image.size() + yPos;
    int width = image.get(0).size() + xPos;
    if (image.size() > this.height) {
      height = this.height;
    }
    if (image.get(0).size() > this.width) {
      width = this.width;
    }

    int imageHeight = 0;
    int imageWidth;

    for (int i = yPos; i < height; i++) {
      imageWidth = 0;
      for (int j = xPos; j < width; j++) {
        this.pixelsOnLayer.get(i).set(j, image.get(imageHeight).get(imageWidth));
        imageWidth++;
      }
      imageHeight++;
    }
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
