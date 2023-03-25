package view;

import controller.Features;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.util.List;
import model.IPixel;


public interface GUIView {

  void refresh();
  void addFeatures(Features features);

  void displayImage(Image image);


  void displayMessage(String message);

  int getImageBorderHeight();

  int getImageBorderWidth();

  Image getImageToPutOnScreen(int height, int width, List<List<IPixel>> imageToAdd);
}
