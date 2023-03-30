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
    try {
      log.append("refreshed\n");
    } catch (IOException e) {
      //
    }
  }

  @Override
  public void addFeatures(Features features) {
    try {
      log.append("Added feature");
    } catch (IOException e) {
      //
    }

  }

  @Override
  public void updateLayers(int layerNumber) {

  }

  @Override
  public void errorMessage(String message) {

  }

  @Override
  public void resetLayers() {

  }

  @Override
  public void getImageToPutOnScreen(int height, int width, List<List<IPixel>> imageToAdd) {

  }

}
