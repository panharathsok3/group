import controller.Features;
import java.io.IOException;
import view.GUIView;

public class GUIControllerConfirmMethodCallValidReturnMock implements Features {

  private final Appendable log;

  public GUIControllerConfirmMethodCallValidReturnMock(Appendable log) {
    this.log = log;
  }

  @Override
  public void setView(GUIView view) throws IllegalArgumentException {
    // do nothing
  }

  @Override
  public void newProject(String projectName, String height, String width)
      throws IllegalArgumentException {
    try {
      this.log.append(String.format("newProject: project name = %s, height = %s, width = %s\n",
          projectName, height, width));
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void loadProject(String filePath) throws IllegalArgumentException {
    try {
      this.log.append(String.format("loadProject: filePath = %s\n", filePath));
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void addLayer(String layerName) throws IllegalArgumentException {
    try {
      this.log.append(String.format("addLayer: layerName = %s\n", layerName));
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void addImageToLayer(String layerName, String filePath, String xPos, String yPos)
      throws IllegalArgumentException {
    try {
      this.log.append(String.format("addImageToLayer: layerName = %s, filePath = %s, xPos = %s, "
          + "yPos = %s\n", layerName, filePath, xPos, yPos));
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void saveProject(String filePath) throws IllegalArgumentException {
    try {
      this.log.append(String.format("saveProject: filePath = %s\n", filePath));
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void saveImage(String filePath) throws IllegalArgumentException {
    try {
      this.log.append(String.format("saveImage: filePath = %s\n", filePath));
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public void setFilter(String layerName, String filterOption) throws IllegalArgumentException {
    try {
      this.log.append(String.format("setFilter: layerName = %s, filterOption = %s\n", layerName,
          filterOption));
    } catch (IOException e) {
      // do nothing
    }
  }

  @Override
  public boolean projectMade() {
    return false;
  }
}
