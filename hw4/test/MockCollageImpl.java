import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import model.CollageProject;
import model.Layer;

/**
 * A mock class for the model that tests and logs communication between the
 * model and the controller.
 */
public class MockCollageImpl implements CollageProject {

  private final Appendable log;

  /**
   *
   */
  public MockCollageImpl(Appendable log) {
    this.log = Objects.requireNonNull(log);
  }

  /**
   * Helps us see what the model sent to the controller.
   *
   * @return a complete transcript of what the communication
   * between the model and controller.
   */
  public String getLog() {
    return this.log.toString();
  }

  @Override
  public void addLayer(String layerName) throws IllegalStateException, IllegalArgumentException {
    try {
      this.log.append(String.format(" Added a Layer to the project with the given name = %s", layerName));
    } catch (IOException ioe) {
      //
    }
  }

  @Override
  public void addImageToLayer(String layerName, String filePath, int xPos, int yPos) throws IllegalArgumentException {
    try {
      this.log.append(String.format(" Added an Image to a layer with the given arguments = %s, %s, %d," +
              " %d", layerName, filePath, xPos, yPos));
    } catch (IOException ioe) {
      //
    }
  }

  @Override
  public void setFilter(String layerName, String filterOption) throws IllegalArgumentException, IllegalStateException {
    try {
      this.log.append(String.format(" Applied a filter with the given arguments = %s, %s",
              layerName, filterOption));
    } catch (IOException ioe) {
      //
    }
  }

  @Override
  public ArrayList<Layer> getLayers() throws IllegalStateException {
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
  public void newProject(String name, int canvasHeight, int canvasWidth) throws IllegalArgumentException {
    try {
      this.log.append(String.format(" Created a new project with the given arguments = %s, %d, %d ",
              name, canvasHeight, canvasWidth));
    } catch (IOException ioe) {
      //
    }
  }

  @Override
  public void loadProject(String filePath) throws IllegalArgumentException, IllegalStateException {
    try {
      this.log.append(String.format(" Loaded a project with the given argument = %s", filePath));
    } catch (IOException ioe) {
      //
    }
  }

  @Override
  public void saveProject(String filePath, String projectType) throws IllegalArgumentException, IllegalStateException {
    try {
      this.log.append(String.format(" Saved a project with the given arguments = %s, %s", filePath, projectType));
    } catch (IOException ioe) {
      //
    }
  }

  @Override
  public void saveImage(String filePath) throws IllegalArgumentException, IllegalStateException {
    try{
    this.log.append(String.format(" Saved an image with the given argument = %s", filePath));} catch (IOException ioe) {
      //
    }
  }


}
