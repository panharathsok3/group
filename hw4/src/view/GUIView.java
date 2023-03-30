package view;

import controller.Features;
import java.util.List;
import model.IPixel;

/**
 * The GUI that will display information onto the screen and make events occur based on user inputs.
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
   * @param height the height of the project
   * @param width the width of the project
   * @param imageToAdd the image to be added onto the screen
   */
  void getImageToPutOnScreen(int height, int width, List<List<IPixel>> imageToAdd);
}

