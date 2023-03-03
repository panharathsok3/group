package view;

import java.io.IOException;

/**
 * The interface for the client's perspective of the model of the collage.
 */
public interface CollageView {

  /**
   * Renders a given message to the data output in the implementation.
   * @param message the message to be printed
   * @throws IOException if the transmission of the grid to the data output fails
   */
  void renderMessage(String message) throws IOException;
}
