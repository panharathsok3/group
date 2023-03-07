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
   */
  public BulkAssignFilter(int row, int col, String optionFilter) {
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
