package model.Effects;

import model.Layer;

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
   * @param row the row of the pixel that will be darkened/brightened
   * @param col the column of the pixel that will be darkened/brightened
   * @param optionFilter the option the pixel can be changed by using its brightness
   * @param brighten true if and only if the pixel is getting brightened and false if and only if
   *                 the pixel is getting darkened
   * @throws IllegalArgumentException if the given optionFilter is null
   *                                  or ...
   */
  public BrightenDarkenMacro(int row, int col, String optionFilter, boolean brighten) {
    this.row = row;
    this.col = col;
    this.optionFilter = optionFilter;
    this.brighten = brighten;
  }

  @Override
  public void executeMacro(Layer layer) {
    if (this.brighten) {
      for (int i = 0; i < this.row; i++) {
        for (int j = 0; j < this.col; j++) {
          layer.getPixelsOnLayer().get(i).get(j).modifyComponentByBrightness(this.optionFilter, true);
        }
      }
    }
    else {
      for (int i = 0; i < this.row; i++) {
        for (int j = 0; j < this.col; j++) {
          layer.getPixelsOnLayer().get(i).get(j).modifyComponentByBrightness(this.optionFilter, false);
        }
      }
    }
  }
}


