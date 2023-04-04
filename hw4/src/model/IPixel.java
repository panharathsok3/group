package model;

/**
 * Represents a IPixel interface for Modifying and accessing a IPixel. This interface exposes the
 * red, green, blue, and alpha component for a RGBA and RGB IPixel. It also exposes the hue,
 * saturation, and lightness if a HSL IPixel.
 */
public interface IPixel {

  /**
   * Returns the red component of this IPixel.
   * @return the red component of this IPixel
   * @throws IllegalStateException when a HSL pixel is trying to access this method
   */
  int getRedComponent() throws IllegalStateException;

  /**
   * Returns the green component of this IPixel.
   * @return the green component of this IPixel
   * @throws IllegalStateException when a HSL pixel is trying to access this method
   */
  int getGreenComponent() throws IllegalStateException;

  /**
   * Returns the blue component of this IPixel.
   * @return the blue component of this IPixel
   * @throws IllegalStateException when a HSL pixel is trying to access this method
   */
  int getBlueComponent() throws IllegalStateException;

  /**
   * Returns the alpha component of this IPixel.
   * @return the alpha component of this IPixel
   */
  int getAlphaComponent();

  /**
   * Returns the hue of this IPixel.
   * @return the hue of this IPixel
   * @throws IllegalStateException when a RGB pixel is trying to access this method
   */
  double getHueComponent() throws IllegalStateException;

  /**
   * Returns the saturation of this IPixel.
   * @return the saturation of this IPixel
   * @throws IllegalStateException when a RGB pixel is trying to access this method
   */
  double getSaturationComponent() throws IllegalStateException;

  /**
   * Returns the lightness of this IPixel.
   * @return the lightness of this IPixel
   * @throws IllegalStateException when a RGB pixel is trying to access this method
   */
  double getLightnessComponent() throws IllegalStateException;

  /**
   * Returns the maximum value of the rgb component.
   * @return the maximum value of the rgb component
   * @throws IllegalStateException when a HSL pixel is trying to access this method
   */
  int value() throws IllegalStateException;

  /**
   * Returns the average of the rgb components.
   * @return the average of the rgb components
   * @throws IllegalStateException when a HSL pixel is trying to access this method
   */
  int intensity() throws IllegalStateException;

  /**
   * Returns the weighted sum of the rgb components.
   * @return the weighted sum of the rgb components
   * @throws IllegalStateException when a HSL pixel is trying to access this method
   */
  int luma() throws IllegalStateException;

  /**
   * Converts an RGB representation of an IPixel into an HSL representation of an IPixel and returns
   * it.
   * @return a new IPixel that using a HSL representation
   * @throws IllegalStateException when trying to convert anything other than from RGB
   *                               representation to HSL
   */
  IPixel convertRGBtoHSL() throws IllegalStateException;

  /**
   * Converts an HSL representation of an IPixel into an RGB representation of an IPixel and returns
   * it.
   * @return a new IPixel that using an RGB representation
   * @throws IllegalStateException when trying to convert anything other than from HSL
   *                               representation to RGB
   */
  IPixel convertHSLtoRGB() throws IllegalStateException;
}

