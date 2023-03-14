package model;

import java.util.ArrayList;

/**
 * Represents a single layer that can hold images.
 */
public class Layer {
  private final String layerName;
  private final ArrayList<ArrayList<Pixel>> pixelsOnLayer;
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
    this.addPixels();
  }

  public Layer(Layer layer) {
    this.layerName = layer.layerName;
    this.height = layer.height;
    this.width = layer.width;
    this.alpha = layer.alpha;
    this.pixelsOnLayer = new ArrayList<>(layer.pixelsOnLayer);
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
   * Adds a given image to this layer. It doesn't resize the image, so if the image is bigger than
   * the layer, it will only get the top left portion of the image and not the entire image.
   * @param xPos the x position of the pixel on this layer
   * @param yPos the y position of the pixel on this layer
   * @param image the image that will be placed on this layer
   * @throws IllegalArgumentException if the given image is null
   *                                  or if the xPos or yPos is not in the bounds of the Layer
   */
  public void addImage(int xPos, int yPos, ArrayList<ArrayList<Pixel>> image)
      throws IllegalArgumentException {
    if (image == null || xPos < 0 || xPos > this.height || yPos < 0 || yPos > this.width) {
      throw new IllegalArgumentException("Arguments can't be null and they can't be negative");
    }

    int height = image.size();
    int width = image.get(0).size();
    if (image.size() > this.height) {
      height = this.height;
    }
    if (image.get(0).size() > this.width) {
      width = this.width;
    }

    /*
    ArrayList<ArrayList<Pixel>> pixelsOnPrevLayer = prevLayer.getPixelsOnLayer();

    Pixel pixel = image.get(i).get(j);
    pixel.changeTransparency(flag, pixelOnPrevLayer.get(i).get(j).getRedComponent(),
      pixelOnPrevLayer.get(i).get(j).getGreenComponent(), pixelOnPrevLayer.get(i).get(j)
        .getBlueComponent(), pixelOnPrevLayer.get(i).get(j).getAlphaComponent());
     */

    for (int i = xPos; i < height; i++) {
      for (int j = yPos; j < width; j++) {
        this.pixelsOnLayer.get(i).set(j, image.get(i).get(j));
      }
    }
  }

  public ArrayList<ArrayList<Pixel>> modifyTransparency(ArrayList<ArrayList<Pixel>> image) {

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
