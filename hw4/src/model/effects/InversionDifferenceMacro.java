package model.effects;

import java.util.List;
import model.ILayer;
import model.IPixel;

/**
 *
 */
public class InversionDifferenceMacro implements MacroCollageEffects {

  private final List<List<IPixel>> layerBelow;

  /**
   *
   * @param layerBelow
   */
  public InversionDifferenceMacro(List<List<IPixel>> layerBelow) {
    this.layerBelow = layerBelow;
  }

  @Override
  public void executeMacro(ILayer layer) throws IllegalArgumentException {

  }
}
