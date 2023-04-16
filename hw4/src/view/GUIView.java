package view;

import controller.Features;
import java.awt.image.BufferedImage;

/**
 * The GUI that will display information onto the screen and make events occur based on user inputs.
 * It adds features that the user will interact with. It also updates the layers, resets the layers,
 * and also displays image to put on the screen.
 */
public interface GUIView {

  /**
   * Refreshes the screen to update after the user has done something.
   */
  void refresh();

  /**
   * Add features onto the screen that listens to user input.
   * @param features the features that can be seen
   */
  void addFeatures(Features features);

  /**
   * Updates the layers on the screen.
   * @param layerNumber the number of layers added
   */
  void updateLayers(int layerNumber);

  /**
   * Displays an error message onto the screen.
   * @param message the message to be displayed
   */
  void errorMessage(String message);

  /**
   * Resets the layers to display back to the user.
   */
  void resetLayers();

  /**
   * Displays the current image that the user is working on.
   * @param image the image to be added onto the screen
   */
  void getImageToPutOnScreen(BufferedImage image);
}

