package model;

/**
 * Represents a Pixel interface.
 */
public interface IPixel {

  /**
   * Returns the red component of this color.
   *
   * @return the red component of this color
   */
  int getRedComponent();


  /**
   * Returns the green component of this color.
   *
   * @return the green component of this color
   */
  int getGreenComponent();


  /**
   * Returns the blue component of this color.
   *
   * @return the blue component of this color
   */
  int getBlueComponent();

  /**
   * Returns the alpha component of this color.
   *
   * @return the alpha component of this color
   */

  int getAlphaComponent();


  /**
   * Returns the maximum value of the rgb component.
   *
   * @return the maximum value of the rgb component
   */
  int value();


  /**
   * Returns the average of the rgb components.
   *
   * @return the average of the rgb components
   */

  int intensity();


  /**
   * Returns the weighted sum of the rgb components.
   *
   * @return the weighted sum of the rgb components
   */

  int luma();


  /**
   * Modifies the component by adding or subtracting it by a given value.
   *
   * @param brightnessOptions a string of values that can be applied to the component
   * @param add               true if and only if the value is being added to and false otherwise which makes it
   *                          subtract instead
   * @throws IllegalArgumentException if the given component is null
   *                                  or if the brightnessOptions is not brighten-luma,
   *                                  brighten-value, or brighten-intensity
   */
  void modifyComponentByBrightness(String brightnessOptions, boolean add);

  /**
   * Filters for a specific color component by setting the other 2 to zero.
   *
   * @param option the color to filter to
   * @throws IllegalArgumentException when the option is not red-component, green-component,
   *                                  or blue-component or if the option is null
   */

  void setFilter(String option);


  /**
   * Changes the transparency of this Pixel by modifying the four components if the image originally
   * has an alpha value and modifies only the rgb if the image doesn't have an alpha component.
   *
   * @param hasAlpha true if and only if this pixel doesn't require an alpha value
   * @param dR       the default red value which is the value of the background's red value
   * @param dG       the default green value which is the value of the background's green value
   * @param dB       the default blue value which is the value of the background's blue value
   * @param dA       the default alpha value which is the value of the background's alpha value
   */
  void changeTransparency(boolean hasAlpha, int dR, int dG, int dB, int dA);

}

