package model.effects;

import model.ILayer;
import model.Pixel;

/**
 * A command that assigns a specified filter to a layer.
 */
public class BulkAssignFilter implements MacroCollageEffects {

  private final int row;
  private final int col;
  private final String optionFilter;

  /**
   * Creates a macroFilter.
   *
   * @param row          the row of the layer to filter
   * @param col          the column of the layer to filter
   * @param optionFilter the filter option
   * @throws IllegalArgumentException if the optionFilter is null
   *                                  or if the row and column is negative
   */
  public BulkAssignFilter(int row, int col, String optionFilter) throws IllegalArgumentException {
    if (optionFilter == null || optionFilter.equals("") || row < 0 || col < 0) {
      throw new IllegalArgumentException("Arguments can't be null or negative");
    }
    this.row = row;
    this.col = col;
    this.optionFilter = optionFilter;
  }

  @Override
  public void executeMacro(ILayer layer) throws IllegalArgumentException {
    if (layer == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    switch (optionFilter) {
      case "red-component":
        for (int i = 0; i < this.row; i++) {
          for (int j = 0; j < this.col; j++) {
            int red = layer.getPixelsOnLayer().get(i).get(j).getRedComponent();
            int alpha = layer.getPixelsOnLayer().get(i).get(j).getAlphaComponent();
            layer.getPixelsOnLayer().get(i).set(j, new Pixel(red, 0, 0, alpha));
          }
        }
        break;
      case "green-component":
        for (int i = 0; i < this.row; i++) {
          for (int j = 0; j < this.col; j++) {
            int green = layer.getPixelsOnLayer().get(i).get(j).getGreenComponent();
            int alpha = layer.getPixelsOnLayer().get(i).get(j).getAlphaComponent();
            layer.getPixelsOnLayer().get(i).set(j, new Pixel(0, green, 0, alpha));
          }
        }
        break;
      case "blue-component":
        for (int i = 0; i < this.row; i++) {
          for (int j = 0; j < this.col; j++) {
            int blue = layer.getPixelsOnLayer().get(i).get(j).getBlueComponent();
            int alpha = layer.getPixelsOnLayer().get(i).get(j).getAlphaComponent();
            layer.getPixelsOnLayer().get(i).set(j, new Pixel(0, 0, blue, alpha));
          }
        }
        break;
      default:
        throw new IllegalArgumentException("the option must be red, green, or blue");
    }

//    for (int i = 0; i < this.row; i++) {
//      for (int j = 0; j < this.col; j++) {
//
//        layer.getPixelsOnLayer().get(i).get(j).setFilter(this.optionFilter);
//      }
//    }
  }
}
