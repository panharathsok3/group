package model.effects;

import model.ILayer;
import model.Pixel;

/**
 * This class handles the operation to brighten an image
 * by doing arithmetic on its colors.
 */
public class BrightenDarkenMacro implements MacroCollageEffects {

  private final int row;
  private final int col;
  private final String optionFilter;
  private final boolean brighten;

  /**
   * Creates a Macro for brightening and darkening a pixel.
   *
   * @param row          the row of the pixel that will be darkened/brightened
   * @param col          the column of the pixel that will be darkened/brightened
   * @param optionFilter the option the pixel can be changed by using its brightness
   * @param brighten     true if and only if the pixel is getting brightened and false if and only
   *                     if the pixel is getting darkened
   * @throws IllegalArgumentException if the given optionFilter is null
   *                                  or if the row or column is negative.
   */
  public BrightenDarkenMacro(int row, int col, String optionFilter, boolean brighten) {

    if (row < 0 || col < 0 || optionFilter == null || optionFilter.equals("")) {
      throw new IllegalArgumentException("The arguments cannot be null or less than 0");
    }

    this.row = row;
    this.col = col;
    this.optionFilter = optionFilter;
    this.brighten = brighten;
  }

  @Override
  public void executeMacro(ILayer layer) throws IllegalArgumentException {
    if (layer == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    int brightness;
    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.col; j++) {
        switch (this.optionFilter) {
          case "brighten-luma":
          case "darken-luma":
            brightness = layer.getPixelsOnLayer().get(i).get(j).luma();
            break;
          case "brighten-value":
          case "darken-value":
            brightness = layer.getPixelsOnLayer().get(i).get(j).value();
            break;
          case "brighten-intensity":
          case "darken-intensity":
            brightness = layer.getPixelsOnLayer().get(i).get(j).intensity();
            break;
          default:
            throw new IllegalArgumentException("The optionFilter must be brighten-luma, "
                + "brighten-value, or brighten-intensity");
        }

        int red = layer.getPixelsOnLayer().get(i).get(j).getRedComponent();
        int green = layer.getPixelsOnLayer().get(i).get(j).getGreenComponent();
        int blue = layer.getPixelsOnLayer().get(i).get(j).getBlueComponent();
        int alpha = layer.getPixelsOnLayer().get(i).get(j).getAlphaComponent();

        if (this.brighten) {
          red += brightness;
          green += brightness;
          blue += brightness;

          if (red > 255) {
            red = 255;
          }
          if (green > 255) {
            green = 255;
          }
          if (blue > 255) {
            blue = 255;
          }

          layer.getPixelsOnLayer().get(i).set(j, new Pixel(red, green, blue, alpha));
        } else {
          red -= brightness;
          green -= brightness;
          blue -= brightness;

          if (red < 0) {
            red = 0;
          }
          if (green < 0) {
            green = 0;
          }
          if (blue < 0) {
            blue = 0;
          }
          layer.getPixelsOnLayer().get(i).set(j, new Pixel(red, green, blue, alpha));
        }
      }
    }
  }
}


