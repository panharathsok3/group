package model;

/**
 * Represents a pixel of an image.
 */
public class Pixel {
  private int redComponent;
  private int greenComponent;
  private int blueComponent;
  private int alphaComponent;

  /**
   * Creates a color using rgb values.
   *
   * @param redComponent   the red pixel value
   * @param greenComponent the greenComponent pixel value
   * @param blueComponent  the blue pixel value
   * @throws IllegalArgumentException if the components are negative
   */
  public Pixel(int redComponent, int greenComponent, int blueComponent)
          throws IllegalArgumentException {
    if (redComponent < 0 || greenComponent < 0 || blueComponent < 0) {
      throw new IllegalArgumentException("The components can't be negative");
    }
    this.redComponent = redComponent;
    this.greenComponent = greenComponent;
    this.blueComponent = blueComponent;
    this.alphaComponent = 255;
  }

  /**
   * Creates a color using rgb values and an alpha component for transparency.
   *
   * @param redComponent   the red pixel value
   * @param greenComponent the green pixel value
   * @param blueComponent  the blue pixel value
   * @throws IllegalArgumentException if the components are negative
   */
  public Pixel(int redComponent, int greenComponent, int blueComponent, int alphaComponent)
          throws IllegalArgumentException {
    if (redComponent < 0 || greenComponent < 0 || blueComponent < 0 || alphaComponent < 0) {
      throw new IllegalArgumentException("The components can't be negative");
    }
    this.redComponent = redComponent;
    this.greenComponent = greenComponent;
    this.blueComponent = blueComponent;
    this.alphaComponent = alphaComponent;
  }


  /**
   * Returns the red component of this color.
   *
   * @return the red component of this color
   */
  public int getRedComponent() {
    return this.redComponent;
  }

  /**
   * Returns the green component of this color.
   *
   * @return the green component of this color
   */
  public int getGreenComponent() {
    return this.greenComponent;
  }

  /**
   * Returns the blue component of this color.
   *
   * @return the blue component of this color
   */
  public int getBlueComponent() {
    return this.blueComponent;
  }

  /**
   * Returns the alpha component of this color.
   *
   * @return the alpha component of this color
   */
  public int getAlphaComponent() {
    return this.alphaComponent;
  }

  /**
   * Returns the maximum value of the rgb component.
   *
   * @return the maximum value of the rgb component
   */
  public int value() {
    return Math.max(Math.max(this.redComponent, this.greenComponent), this.blueComponent);
  }


  /**
   * Returns the average of the rgb components.
   *
   * @return the average of the rgb components
   */
  public int intensity() {
    return (this.redComponent + this.greenComponent + this.blueComponent) / 3;
  }

  /**
   * Returns the weighted sum of the rgb components.
   *
   * @return the weighted sum of the rgb components
   */
  public int luma() {
    return (int) Math.round((0.216 * this.redComponent) + (0.7152 * this.greenComponent)
            + (0.0722 * this.blueComponent));
  }

  /**
   * Modifies the component by adding or subtracting it by a given value.
   *
   * @param brightnessOptions a string of values that can be applied to the component
   * @param add   true if and only if the value is being added to and false otherwise which makes it
   *              subtract instead
   * @throws IllegalArgumentException if the given component is null
   *                                  or if the brightnessOptions is not brighten-luma,
   *                                  brighten-value, or brighten-intensity
   */
  public void modifyComponentByBrightness(String brightnessOptions, boolean add)
          throws IllegalArgumentException {
    if (brightnessOptions == null) {
      throw new IllegalArgumentException("the component cannot be null.");
    }
    int brightness;

    if (brightnessOptions.equals("brighten-luma") || brightnessOptions.equals("darken-luma")) {
      brightness = this.luma();
    }
    else if (brightnessOptions.equals("brighten-value")
        || brightnessOptions.equals("darken-value")) {
      brightness = this.value();
    }
    else if (brightnessOptions.equals("brighten-intensity")
        || brightnessOptions.equals("darken-intensity")) {
      brightness = this.intensity();
    }
    else {
      throw new IllegalArgumentException("The brightnessOptions must be brighten-luma, "
          + "brighten-value, or brighten-intensity");
    }

    if (add) {
      this.add(brightness);
    } else {
      this.subtract(brightness);
    }
  }

  /**
   * Filters for a specific color component by setting the other 2 to zero.
   * @param option the color to filter to
   * @throws IllegalArgumentException when the option is not red-component, green-component,
   *                                  or blue-component or if the option is null
   */
  public void setFilter(String option) throws IllegalArgumentException {
    if (option == null) {
      throw new IllegalArgumentException("The option cannot be null");
    }

    switch (option) {
      case "red-component":
        this.greenComponent = 0;
        this.blueComponent = 0;
        break;
      case "green-component":
        this.redComponent = 0;
        this.blueComponent = 0;
        break;
      case "blue-component":
        this.redComponent = 0;
        this.greenComponent = 0;
        break;
      default:
        throw new IllegalArgumentException("the option must be red, green, or blue");
    }
  }

  /**
   * Adds the value to the given component.
   *
   * @param value the value to add to the component
   */
  private void add(int value) {
    this.redComponent += value;
    this.blueComponent += value;
    this.greenComponent += value;
    this.checkBounds();
  }

  /**
   * Subtracts the value to the given component.
   *
   * @param value the value to add to the component
   */
  private void subtract(int value) {
    this.redComponent -= value;
    this.blueComponent -= value;
    this.greenComponent -= value;
    this.checkBounds();
  }

  /**
   * Checks if the given component is higher than the max value or if it's lower than zero and set
   * it to either the max value if it exceeds it or set it to zero if it goes below zero.
   *
   */
  private void checkBounds() {
    if (this.redComponent > 255) {
      this.redComponent = 255;
    }
    if (this.redComponent < 0) {
      this.redComponent = 0;
    }

    if (this.blueComponent > 255) {
      this.blueComponent = 255;
    }
    if (this.blueComponent < 0) {
      this.blueComponent = 0;
    }

    if (this.greenComponent > 255) {
      this.greenComponent = 255;
    }
    if (this.greenComponent < 0) {
      this.greenComponent = 0;
    }
  }




}
