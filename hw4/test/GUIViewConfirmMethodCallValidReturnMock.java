import java.awt.*;
import java.io.IOException;
import java.util.List;

import controller.Features;
import model.IPixel;
import view.GUIView;

public class GUIViewConfirmMethodCallValidReturnMock implements GUIView {

  private final Appendable log;

  public GUIViewConfirmMethodCallValidReturnMock(Appendable log) {
    this.log = log;
  }

  @Override
  public void refresh() {
    try{
      log.append("refreshed\n");
    } catch (IOException e) {
      //
    }
  }

  @Override
  public void addFeatures(Features features) {
    try {
      log.append("Added feature");
    } catch(IOException e) {
      //
    }

  }

  @Override
  public void displayImage(Image image) {
    try {
      this.log.append(String.format("Displayed Image:%s\n", image));
    } catch(IOException e) {
      //
    }
  }

  @Override
  public void displayMessage(String message) {
    try {
      this.log.append(String.format("Message : = %s\n ",message));
    } catch(IOException e) {
      //
    }
  }

  @Override
  public int getImageBorderHeight() {
    return 0;
  }

  @Override
  public int getImageBorderWidth() {
    return 0;
  }

  @Override
  public Image getImageToPutOnScreen
          (int height, int width, List<List<IPixel>> imageToAdd) {
    try {
       this.log.append(String.format("Displayed the Image with %height" +
                      "% width\n",
              height,width));
    } catch(IOException e) {
      //
    }

    return null;
  }



}
