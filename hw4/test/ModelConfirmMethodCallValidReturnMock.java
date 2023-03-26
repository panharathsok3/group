import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import model.CollageProject;
import model.ILayer;
import model.Layer;

/**
 * A mock class for the model that tests and logs communication between the
 * model and the controller.
 */
public class ModelConfirmMethodCallValidReturnMock implements CollageProject {

  private final Appendable log;

  /**
   * Creates a mock keeps track of the method calls.
   */
  public ModelConfirmMethodCallValidReturnMock(Appendable log) {
    this.log = log;
  }

  @Override
  public void addLayer(String layerName) throws IllegalStateException, IllegalArgumentException {
    try {
      this.log.append(String.format("Added a Layer to the project with the given name = %s\n",
          layerName));
    } catch (IOException ioe) {
      //
    }
  }

  @Override
  public void addImageToLayer(String layerName, String filePath, int xPos, int yPos)
      throws IllegalArgumentException {
    try {
      this.log.append(String.format("Added an Image to a layer with the given arguments = %s, "
          + "%s, %d, %d\n", layerName, filePath, xPos, yPos));
    } catch (IOException ioe) {
      //
    }
  }

  @Override
  public void setFilter(String layerName, String filterOption) throws IllegalArgumentException,
      IllegalStateException {
    try {
      this.log.append(String.format("Applied a filter with the given arguments = %s, %s\n",
              layerName, filterOption));
    } catch (IOException ioe) {
      //
    }
  }

  @Override
  public Layer makeFinalImage(boolean hasAlpha) {
    return null;
  }

  @Override
  public ArrayList<ILayer> getLayers() throws IllegalStateException {
    return null;
  }

  @Override
  public String getProjectName() throws IllegalStateException {
    return null;
  }

  @Override
  public int getWidth() throws IllegalStateException {
    return 0;
  }

  @Override
  public int getHeight() throws IllegalStateException {
    return 0;
  }

  @Override
  public int getMaxValue() throws IllegalStateException {
    return 0;
  }

  @Override
  public Map<String, String> getFiltersOnProject() throws IllegalStateException {
    return null;
  }

  @Override
  public void newProject(String name, int canvasHeight, int canvasWidth)
      throws IllegalArgumentException {
    try {
      this.log.append(String.format("Created a new project with the given arguments = %s, %d, %d\n",
              name, canvasHeight, canvasWidth));
    } catch (IOException ioe) {
      //
    }
  }


  public String getLog() {
    return this.log.toString();
  }


}
