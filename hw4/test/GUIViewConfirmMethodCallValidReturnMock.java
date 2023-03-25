import java.awt.*;
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
  }

  @Override
  public void addFeatures(Features features) {
  }

  @Override
  public void displayImage(Image image) {

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
  public Image getImageToPutOnScreen(int height, int width, List<List<IPixel>> imageToAdd) {
    return null;
  }
}
