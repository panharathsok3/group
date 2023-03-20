package controller;

/**
 * A controller interface for the CollageProjectModelImpl.
 */
public interface CollageController {

  /**
   * The controller for the CollageProjectModelImpl. This is used to take user input and give it
   * to the model to process information and sends the information to the view to display something
   * to the user.
   * @throws IllegalStateException if and only if the controller is unable to successfully read
   *                              input or transmit output
   */
  void runProgram() throws IllegalStateException;
}
