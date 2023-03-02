package model;

/**
 * Represents a color where the rgb values are represented in the redComponent, greenComponent, and
 * blue component respectively.
 */
public class Color {
  private int redComponent;
  private int greenComponent;
  private int blueComponent;

  /**
   * Creates a color using rgb values.
   * @param redComponent the red pixel value
   * @param greenComponent the greenComponent pixel value
   * @param blueComponent the blue pixel value
   */
  public Color(int redComponent, int greenComponent, int blueComponent) {
    this.redComponent = redComponent;
    this.greenComponent = greenComponent;
    this.blueComponent = blueComponent;
  }

  /**
   * Creates a color using rgb values for png format.
   * @param redComponent the red pixel value
   * @param greenComponent the green pixel value
   * @param blueComponent the blue pixel value
   */
  public Color(int redComponent, int greenComponent, int blueComponent, int alphaComponent) {
    this.redComponent = (alphaComponent / 255 * redComponent) +
            (redComponent * (1 - (alphaComponent / 255)));
    this.greenComponent = (alphaComponent / 255 * greenComponent) +
            (greenComponent * (1 - (alphaComponent / 255)));
    this.blueComponent = (alphaComponent / 255 * blueComponent) +
            (blueComponent * (1 - (alphaComponent / 255)));
  }




  /**
   * Returns the maximum value of the three component.
   * @return the maximum value of the three component
   */
  public int value() {
    return Math.max(Math.max(this.redComponent, this.greenComponent), this.blueComponent);
  }

  /**
   * the average of the three components.
   * @return the average of the three components
   */
  public int intensity() {
    return (this.redComponent + this.greenComponent + this.blueComponent) / 3;
  }

  /**
   * Returns the weighted sum of the three components.
   * @return the weighted sum of the three components
   */
  public int luma() {
    return (int)Math.round((0.216 * this.redComponent) + (0.7152 * this.greenComponent)
            + (0.0722 * this.blueComponent));
  }

}
