package view;

import controller.Features;
import java.util.List;
import model.IPixel;

/**
 *
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

  void displayMessage(String message);

  /**
   * Displays the current image that the user is working on.
   * @param height the height of the project
   * @param width the width of the project
   * @param imageToAdd the image to be added onto the screen
   */
  void getImageToPutOnScreen(int height, int width, List<List<IPixel>> imageToAdd);
}

