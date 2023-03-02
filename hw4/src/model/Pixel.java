package model;

/**
 * Represents a pixel of an image
 */
public class Pixel {
  //pixel doesn't need to have a row or colum  because it can exist without a Collage Class
  private  int row;
  private  int col;
  private  Color color;

  private int red;
  private int green;
  private int blue;

  static  final int maxValue = 255;
  private int alpha;

  /**
   * Represents a pixel of an image using the row and column of the image and its color.
   * @param row the row of the pixel
   * @param col the column of the pixel
   * @param color the color of the pixel
   * @throws IllegalArgumentException when the row or column is negative or Color is NULL
   */
  public Pixel(int row, int col, Color color) throws IllegalArgumentException {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("The row and column cannot be negative");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }

    this.row = row;
    this.col = col;
    this.color = color;
  }

  /**
   * Represents a pixel of an image using the row and column of the image and its color.
   * @param row the row of the pixel
   * @param col the column of the pixel
   * @param color the color of the pixel
   * @param alpha the
   * @throws IllegalArgumentException when the row or column is negative or Color is NULL
   */
  public Pixel(int row, int col, Color color, int alpha) {
    if (row < 0 || col < 0) {
      throw new IllegalArgumentException("The row and column cannot be negative");
    }
    if (color == null) {
      throw new IllegalArgumentException("Color cannot be null");
    }
    this.row = row;
    this.col = col;
    this.color = color;
    this.alpha = alpha;
  }


  public Pixel(int red, int green, int blue) {
    if(red <= maxValue && red >= 0) {
      this.red = red;
    } else {
    throw new IllegalArgumentException("value should not be bigger or smaller than the max ");
  }
    if (green <= maxValue && green >= 0) {
    this.green = green;
  } else {
    throw new IllegalArgumentException("value should not be bigger or smaller than the max");
  }
    if (blue <= maxValue && blue >= 0) {
    this.blue = blue;
  } else {
    throw new IllegalArgumentException("value should not be bigger or smaller than the max");
  }
  }



  /**
   * Returns the maximum value of the three components of this pixel.
   * @return the maximum value of the three components of this pixel
   */
  public int value() {
    return this.color.value();
  }

  /**
   * Returns the average of the three components of this pixel.
   * @return the average of the three components of this pixel
   */
  public int intensity() {
    return this.color.intensity();
  }

  /**
   * Returns the weighted sum of the three components.
   * @return the weighted sum of the three components
   */
  public int luma() {
    return this.color.luma();
  }



}
