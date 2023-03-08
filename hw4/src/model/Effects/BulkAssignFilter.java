package model.Effects;

import model.Layer;

/**
 * A command that assigns a specified filter to a layer.
 */
public class BulkAssignFilter implements MacroCollageEffects {

  private final int row;
  private final int col;
  private final String optionFilter;

  /**
   * Creates a macroFilter.
   * @param row the row of the layer to filter
   * @param col the column of the layer to filter
   * @param optionFilter the filter option
   * @throws IllegalArgumentException if the optionFilter is null
   *                                  or if the row and column is negative
   */
  public BulkAssignFilter(int row, int col, String optionFilter) throws IllegalArgumentException {
    if (optionFilter == null || row < 0 || col < 0) {
      throw new IllegalArgumentException("Arguments can't be null or negative");
    }
    this.row = row;
    this.col = col;
    this.optionFilter = optionFilter;
  }

  @Override
  public void executeMacro(Layer layer) {
    for (int i = 0; i < this.row; i++) {
      for (int j = 0; j < this.col; j++) {
        layer.getPixelsOnLayer().get(i).get(j).setFilter(this.optionFilter);
      }
    }
  }
}
