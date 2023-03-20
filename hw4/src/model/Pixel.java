package model;

/**
 * This class is used to make a pixel of an image.
 * The pixel stores the red, green, blue, and alpha components of the pixel.
 * If the pixel being red from an image originally doesn't have an alpha component,
 * it will be set to 255.
 */
public class Pixel implements IPixel {
  private int redComponent;
  private int greenComponent;
  private int blueComponent;
  private int alphaComponent;

  /**
   * Creates a color using rgb values.
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


  @Override
  public int getRedComponent() {
    return this.redComponent;
  }

  @Override
  public int getGreenComponent() {
    return this.greenComponent;
  }

  @Override
  public int getBlueComponent() {
    return this.blueComponent;
  }

  @Override
  public int getAlphaComponent() {
    return this.alphaComponent;
  }

  @Override
  public int value() {
    return Math.max(Math.max(this.redComponent, this.greenComponent), this.blueComponent);
  }

  @Override
  public int intensity() {
    return (this.redComponent + this.greenComponent + this.blueComponent) / 3;
  }

  @Override
  public int luma() {
    return (int) Math.round((0.216 * this.redComponent) + (0.7152 * this.greenComponent)
            + (0.0722 * this.blueComponent));
  }

//  @Override
//  public void modifyComponentByBrightness(String brightnessOptions, boolean add)
//          throws IllegalArgumentException {
//    if (brightnessOptions == null) {
//      throw new IllegalArgumentException("the component cannot be null.");
//    }
//    int brightness;
//
//    switch (brightnessOptions) {
//      case "brighten-luma":
//      case "darken-luma":
//        brightness = this.luma();
//        break;
//      case "brighten-value":
//      case "darken-value":
//        brightness = this.value();
//        break;
//      case "brighten-intensity":
//      case "darken-intensity":
//        brightness = this.intensity();
//        break;
//      default:
//        throw new IllegalArgumentException("The brightnessOptions must be brighten-luma, "
//            + "brighten-value, or brighten-intensity");
//    }
//
//    if (add) {
//      this.add(brightness);
//    } else {
//      this.subtract(brightness);
//    }
//  }
//
//  @Override
//  public void setFilter(String option) throws IllegalArgumentException {
//    if (option == null) {
//      throw new IllegalArgumentException("The option cannot be null");
//    }
//
//    switch (option) {
//      case "red-component":
//        this.greenComponent = 0;
//        this.blueComponent = 0;
//        break;
//      case "green-component":
//        this.redComponent = 0;
//        this.blueComponent = 0;
//        break;
//      case "blue-component":
//        this.redComponent = 0;
//        this.greenComponent = 0;
//        break;
//      default:
//        throw new IllegalArgumentException("the option must be red, green, or blue");
//    }
//  }
//
//  /**
//   * Adds the value to the given component.
//   *
//   * @param value the value to add to the component
//   */
//  private void add(int value) {
//    this.redComponent += value;
//    this.blueComponent += value;
//    this.greenComponent += value;
//    this.checkBounds();
//  }
//
//  /**
//   * Subtracts the value to the given component.
//   *
//   * @param value the value to add to the component
//   */
//  private void subtract(int value) {
//    this.redComponent -= value;
//    this.blueComponent -= value;
//    this.greenComponent -= value;
//    this.checkBounds();
//  }
//
//  /**
//   * Checks if the given component is higher than the max value or if it's lower than zero and set
//   * it to either the max value if it exceeds it or set it to zero if it goes below zero.
//   */
//  private void checkBounds() {
//    if (this.redComponent > 255) {
//      this.redComponent = 255;
//    }
//    if (this.redComponent < 0) {
//      this.redComponent = 0;
//    }
//
//    if (this.blueComponent > 255) {
//      this.blueComponent = 255;
//    }
//    if (this.blueComponent < 0) {
//      this.blueComponent = 0;
//    }
//
//    if (this.greenComponent > 255) {
//      this.greenComponent = 255;
//    }
//    if (this.greenComponent < 0) {
//      this.greenComponent = 0;
//    }
//  }

  @Override
  public void changeTransparency(boolean hasAlpha, int dR, int dG, int dB, int dA) {
    if (!hasAlpha) {
      this.redComponent = (int) (this.getRedComponent() * this.alphaComponent / 255f);
      this.greenComponent = (int) (this.getGreenComponent() * this.alphaComponent / 255f);
      this.blueComponent = (int) (this.getBlueComponent() * this.alphaComponent / 255f);
    } else {
      int originalAlpha = this.alphaComponent;
      double alphaPrime = (originalAlpha / 255f) + (dA / 255f) * (1 - (originalAlpha / 255f));

      this.redComponent = (int) (((originalAlpha / 255f * this.redComponent) + (dR * (dA / 255f)
              * (1 - (originalAlpha / 255f)))) * (1f / alphaPrime));
      this.greenComponent = (int) (((originalAlpha / 255f * this.greenComponent) + (dG * (dA / 255f)
              * (1 - (originalAlpha / 255f)))) * (1f / alphaPrime));
      this.blueComponent = (int) (((originalAlpha / 255f * this.blueComponent) + (dB * (dA / 255f)
              * (1 - (originalAlpha / 255f)))) * (1f / alphaPrime));
      this.alphaComponent = (int) (alphaPrime * 255);
    }
  }
}
