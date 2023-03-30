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
  private double hue;
  private double saturation;
  private double lightness;
  private boolean isRGB;

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
    this.isRGB = true;
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
    this.isRGB = true;
  }

  /**
   * Creates a Pixel using the hue, saturation and lightness.
   * @param hue the hue of this Pixel
   * @param saturation the saturation of this Pixel
   * @param lightness the lightness of this Pixel
   * @param alphaComponent the alphaComponent of this Pixel
   * @throws IllegalArgumentException if 0 > hue >= 360
   *                                  or 0 > saturation > 1
   *                                  or 0 > lightness > 1
   */
  public Pixel(double hue, double saturation, double lightness, int alphaComponent)
      throws IllegalArgumentException {

    if (hue < 0 || hue >= 360 || saturation < 0 || saturation > 1 || lightness < 0
        || lightness > 1) {
      throw new IllegalArgumentException("hue must be: 0 <= hue < 360, saturation must be: "
          + "0 <= saturation <= 1, and lightness must be: 0 <= lightness <= 1\n");
    }

    this.hue = hue;
    this.saturation = saturation;
    this.lightness = lightness;
    this.isRGB = false;
    this.alphaComponent = alphaComponent;
  }


  @Override
  public int getRedComponent() throws IllegalStateException {
    this.throwErrorIfNotCorrectRepresentation(!this.isRGB);
    return this.redComponent;
  }

  @Override
  public int getGreenComponent() throws IllegalStateException {
    this.throwErrorIfNotCorrectRepresentation(!this.isRGB);
    return this.greenComponent;
  }

  @Override
  public int getBlueComponent() throws IllegalStateException {
    this.throwErrorIfNotCorrectRepresentation(!this.isRGB);
    return this.blueComponent;
  }

  @Override
  public int getAlphaComponent() throws IllegalStateException {
    return this.alphaComponent;
  }

  @Override
  public double getHueComponent() throws IllegalStateException {
    this.throwErrorIfNotCorrectRepresentation(this.isRGB);
    return this.hue;
  }

  @Override
  public double getSaturationComponent() throws IllegalStateException {
    this.throwErrorIfNotCorrectRepresentation(this.isRGB);
    return this.saturation;
  }

  @Override
  public double getLightnessComponent() throws IllegalStateException {
    this.throwErrorIfNotCorrectRepresentation(this.isRGB);
    return this.lightness;
  }

  @Override
  public int value() throws IllegalStateException {
    this.throwErrorIfNotCorrectRepresentation(!this.isRGB);
    return Math.max(Math.max(this.redComponent, this.greenComponent), this.blueComponent);
  }

  @Override
  public int intensity() throws IllegalStateException {
    this.throwErrorIfNotCorrectRepresentation(!this.isRGB);
    return (this.redComponent + this.greenComponent + this.blueComponent) / 3;
  }

  @Override
  public int luma() throws IllegalStateException {
    this.throwErrorIfNotCorrectRepresentation(!this.isRGB);
    return (int) Math.round((0.216 * this.redComponent) + (0.7152 * this.greenComponent)
            + (0.0722 * this.blueComponent));
  }

  @Override
  public IPixel convertRGBtoHSL() throws IllegalStateException {

    if (!isRGB) {
      throw new IllegalStateException("This is used to convert RBG to HSL only!");
    }

    double red = this.redComponent / 255f;
    double green = this.greenComponent / 255f;
    double blue = this.blueComponent / 255f;

    double componentMax = Math.max(red, Math.max(green, blue));
    double componentMin = Math.min(red, Math.min(green, blue));

    double delta = componentMax - componentMin;

    double lightness = (componentMax + componentMin) / 2;
    double hue, saturation;
    if (delta == 0) {
      hue = 0;
      saturation = 0;
    } else {
      saturation = delta / (1 - Math.abs(2 * lightness - 1));
      hue = 0;
      if (componentMax == red) {
        hue = (green - blue) / delta;
        while (hue < 0) {
          hue += 6; //hue must be positive to find the appropriate modulus
        }
        hue = hue % 6;
      } else if (componentMax == green) {
        hue = (blue - red) / delta;
        hue += 2;
      } else if (componentMax == blue) {
        hue = (red - green) / delta;
        hue += 4;
      }

      hue = hue * 60;
    }

    return new Pixel(hue, saturation, lightness, this.alphaComponent);
  }


  @Override
  public IPixel convertHSLtoRGB() throws IllegalStateException {

    if (isRGB) {
      throw new IllegalStateException("This is used to convert HSL to RBG only!");
    }

    double r = convertFn(this.hue, this.saturation, this.lightness, 0) * 255;
    double g = convertFn(this.hue, this.saturation, this.lightness, 8) * 255;
    double b = convertFn(this.hue, this.saturation, this.lightness, 4) * 255;

    //convert double to int
    int rInt = (int)Math.round(r);
    int gInt = (int)Math.round(g);
    int bInt = (int)Math.round(b);

    return new Pixel(rInt, gInt, bInt, this.alphaComponent);
  }

  /**
   * Helper method that performs the translation from the HSL polygonal
   * model to the more familiar RGB model
   */
  private double convertFn(double hue, double saturation, double lightness, int n) {
    double k = (n + (hue/30)) % 12;
    double a  = saturation * Math.min(lightness, 1 - lightness);

    return lightness - a * Math.max(-1, Math.min(k - 3, Math.min(9 - k, 1)));
  }

  /**
   * Helper method to throw error if a HSL pixel is trying to access methods from a RGBA pixel or
   * vice versa.
   * @param wrongRepresentation true if a HSL pixel is trying to access methods from a RGBA pixel or
   *                            vice versa
   */
  private void throwErrorIfNotCorrectRepresentation(boolean wrongRepresentation)
      throws IllegalArgumentException {
    if (wrongRepresentation) {
      throw new IllegalArgumentException("This is wrong!");
    }
  }
}
