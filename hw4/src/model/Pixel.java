package model;

/**
 * Represents a pixel of an image.
 */
public class Pixel {
  private int redComponent;
  private int greenComponent;
  private int blueComponent;
  private int alphaComponent;
  static final int maxValue = 255;


  /**
   * Creates a color using rgb values.
   * @param redComponent the red pixel value
   * @param greenComponent the greenComponent pixel value
   * @param blueComponent the blue pixel value
   */
  public Pixel(int redComponent, int greenComponent, int blueComponent) {
    this.redComponent = redComponent;
    this.greenComponent = greenComponent;
    this.blueComponent = blueComponent;
    this.alphaComponent = 255;
  }

  /**
   * Creates a color using rgb values and an alpha component for transparency.
   * @param redComponent the red pixel value
   * @param greenComponent the green pixel value
   * @param blueComponent the blue pixel value
   */
  public Pixel(int redComponent, int greenComponent, int blueComponent, int alphaComponent) {
    this.redComponent = redComponent;
    this.greenComponent = greenComponent;
    this.blueComponent = blueComponent;
    this.alphaComponent = alphaComponent;
  }


  /**
   * Returns the red component of this color.
   * @return the red component of this color
   */
  public int getRedComponent() {
    return this.redComponent;
  }

  /**
   * Returns the green component of this color.
   * @return the green component of this color
   */
  public int getGreenComponent() {
    return this.greenComponent;
  }

  /**
   * Returns the blue component of this color.
   * @return the blue component of this color
   */
  public int getBlueComponent() {
    return this.blueComponent;
  }

  /**
   * Returns the alpha component of this color.
   * @return the alpha component of this color
   */
  public int getAlphaComponent() {
    return this.alphaComponent;
  }

  /**
   * Returns the maximum value of the rgb component.
   * @return the maximum value of the rgb component
   */
  public int value() {
    return Math.max(Math.max(this.redComponent, this.greenComponent), this.blueComponent);
  }


  /**
   * Returns the average of the rgb components.
   * @return the average of the rgb components
   */
  public int intensity() {
    return (this.redComponent + this.greenComponent + this.blueComponent) / 3;
  }

  /**
   * Returns the weighted sum of the rgb components.
   * @return the weighted sum of the rgb components
   */
  public int luma() {
    return (int)Math.round((0.216 * this.redComponent) + (0.7152 * this.greenComponent)
        + (0.0722 * this.blueComponent));
  }

  //added setters to the pixel class
  public void setRedComponent(int newComponent) {
    this.redComponent = newComponent;
  }

  public void setGreenComponent(int newComponent) {
    this.greenComponent = newComponent;
  }

  public void setBlueComponent(int newComponent) {
    this.blueComponent = newComponent;
  }


}
