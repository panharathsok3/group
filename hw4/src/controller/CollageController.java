package controller;

/**
 * A controller interface the for the CollageProjectModelImpl.
 */
public interface CollageController {

  /**
   * The controller for the CollageProjectModelImpl.
   * @throws IllegalStateException if and only if the controller is unable to successfully read
   *                              input or transmit output
   */
  void runProgram() throws IllegalStateException;
}
