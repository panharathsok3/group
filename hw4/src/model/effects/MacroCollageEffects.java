package model.effects;

import model.ILayer;

/**
 * A Macro for the adding effects to the Layer of the CollageProjectModelImpl.
 * Represents the command interface.
 */
public interface MacroCollageEffects {
  /**
   * A macro method for the Layer of the CollageProjectModelImpl.
   * @param layer the layer the marco will be used in
   * @throws IllegalArgumentException if the given argument is null
   */
  void executeMacro(ILayer layer) throws IllegalArgumentException;
}
