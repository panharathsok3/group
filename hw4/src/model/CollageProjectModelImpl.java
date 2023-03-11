package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import java.util.Map;
import model.Effects.BrightenDarkenMacro;
import model.Effects.BulkAssignFilter;
import model.Effects.MacroCollageEffects;

/**
 * Creates a collage to work on.
 */
public class CollageProjectModelImpl implements CollageProject {

  private final List<Layer> project;
  private int canvasHeight;
  private int canvasWidth;
  private String projectName;
  private boolean backgroundMade;
  private boolean createdProject;
  Map<String, String> layerFilter;

  /**
   * Creates a CollageProjectModelImpl to work on.
   */
  public CollageProjectModelImpl() {
    this.project = new LinkedList<>();
    this.backgroundMade = false;
    this.createdProject = false;
    this.layerFilter = new HashMap<>();
  }

  @Override
  public void newProject(String name, int canvasHeight, int canvasWidth)
          throws IllegalArgumentException {

    if (name == null || name.equals("") || canvasHeight < 1 || canvasWidth < 1) {
      throw new IllegalArgumentException("the name of the project can't be null and the"
              + "canvas height and width can't be less than 1");
    }

    this.projectName = name;
    this.createdProject = true;
    this.canvasHeight = canvasHeight;
    this.canvasWidth = canvasWidth;
    this.addLayer("Background");
    this.backgroundMade = true;
  }

  @Override
  public void addLayer(String layerName) throws IllegalStateException, IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    if (!createdProject) {
      this.throwExceptionProjectNotMade();
    }

    Layer layer;
    if (this.backgroundMade) {
      layer = new Layer(layerName, this.canvasHeight, this.canvasWidth, 0);
    } else {
      layer = new Layer(layerName, this.canvasHeight, this.canvasWidth, 255);
    }
    for (Layer currentLayer : project) {
      if (currentLayer.getName().equals(layer.getName())) {
        throw new IllegalStateException("There is already a layer with "
            + "the name you are trying to use");
      }
    }

    this.project.add(layer);
    this.setFilter(layerName, "normal");
  }

  @Override
  public void addImageToLayer(String layerName, String filePath, int xPos, int yPos)
          throws IllegalArgumentException {

    if (layerName == null || layerName.equals("") || filePath == null || filePath.equals("")
            || xPos < 0 || xPos > this.canvasHeight || yPos < 0 || yPos > this.canvasWidth) {
      throw new IllegalArgumentException("layer name and file path cannot be null, x and y positions have to be" +
              "within the boundaries of the canvas");
    }

    if (!createdProject) {
      this.throwExceptionProjectNotMade();
    }

    ArrayList<ArrayList<Pixel>> image = new ImageUtil().readPPM(filePath);

    for (Layer layer : this.project) {
      if (layerName.equals(layer.getName())) {
        layer.addImage(xPos, yPos, image);
        return;
      }
    }

    throw new IllegalArgumentException("Layer not found");
  }

  @Override
  public void saveProject(String filePath, String projectType) throws IllegalArgumentException, IllegalStateException {
    if (filePath == null) {
      throw new IllegalArgumentException("Cannot give null as an argument");
    }
    if (!createdProject) {
      this.throwExceptionProjectNotMade();
    }

    if (projectType.equals("PPM")) {
      try {
        savePPMProject(filePath);
      } catch (IOException e) {
        throw new IllegalArgumentException("Was not able to save");
      }
    }
  }

  /**
   * Allows the user to save a project as a PPM file.
   *
   * @param filePath the location where the file will be stored.
   * @throws IllegalArgumentException if the file has no contents/images
   *                                  or if the given filePath is null
   */
  private void savePPMProject(String filePath) throws IOException {
    if (filePath == null) {
      throw new IllegalArgumentException("Cannot give null as an argument");
    }

    //write the new file to this path
    FileWriter fileWriter = new FileWriter(filePath);

    fileWriter.write(this.projectName + "\n");
    fileWriter.write(this.canvasWidth + " " + this.canvasHeight + "\n");
    //depends on what ever the colors in the project are
    fileWriter.write("255\n"); //because the max value of each pixel can be 255

    for (int i = 0; i < this.project.size(); i++) {
      Layer layer = this.project.get(i);
      String filter = this.layerFilter.get(layer.getName());
      fileWriter.write(layer.getName() + " " + filter + "\n");

      ArrayList<ArrayList<Pixel>> pixels = layer.getPixelsOnLayer();

      for (int j = 0; j < this.canvasHeight; j++) {
        for (int k = 0; k < this.canvasWidth; k++) {
          int redComponent = pixels.get(j).get(k).getRedComponent();
          int greenComponent = pixels.get(j).get(k).getGreenComponent();
          int blueComponent = pixels.get(j).get(k).getBlueComponent();
          int alphaComponent = pixels.get(j).get(k).getAlphaComponent();
          fileWriter.write(redComponent + " " + greenComponent + " " + blueComponent + " "
                  + alphaComponent + "\n");
        }
      }
    }

    fileWriter.close();
  }

  @Override
  public void saveImage(String filePath) throws IllegalArgumentException {
    if (!createdProject) {
      this.throwExceptionProjectNotMade();
    }

    try {
      if (filePath.endsWith(".ppm")) {
        savePPMProject(filePath);
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("The image you are trying to save cannot be found");
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  //load-project path-to-project-file: loads a project into the program TODO
  @Override
  public CollageProjectModelImpl loadProject(String filePath) throws FileNotFoundException {
    FileReader loader = new FileReader(filePath);

    this.createdProject = true;
    this.backgroundMade = true;

    ArrayList<Layer> projectContents;

    if (filePath.endsWith(".ppm")) {
      //projectContents =  ImageUtil.readPPM(filePath);
    }


    return new CollageProjectModelImpl();
  }

  @Override
  public void setFilter(String layerName, String filterOption) throws IllegalArgumentException {
    if (!createdProject) {
      this.throwExceptionProjectNotMade();
    }

    if (layerName == null || filterOption == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    MacroCollageEffects macro;
    Layer currentLayer = null;
    boolean layerFound = false;

    for (Layer layer : this.project) {
      if (layerName.equals(layer.getName())) {
        currentLayer = layer;
        layerFound = true;
        this.layerFilter.put(layerName, filterOption);
      }
    }

    if (!layerFound) {
      throw new IllegalArgumentException("Layer not found");
    }



    switch (filterOption) {
      case "normal":
        break;
      case "red-component":
      case "green-component":
      case "blue-component":
        macro = new BulkAssignFilter(this.canvasHeight, this.canvasWidth, filterOption);
        macro.executeMacro(currentLayer);
        break;
      case "brighten-value":
      case "brighten-luma":
      case "brighten-intensity":
        macro = new BrightenDarkenMacro(this.canvasHeight, this.canvasWidth, filterOption, true);
        macro.executeMacro(currentLayer);
        break;
      case "darken-intensity":
      case "darken-luma":
      case "darken-value":
        macro = new BrightenDarkenMacro(this.canvasHeight, this.canvasWidth, filterOption, false);
        macro.executeMacro(currentLayer);
        break;
      default:
        throw new IllegalArgumentException("Filter not found");
    }
  }

  @Override
  public ArrayList<Layer> getLayers() {
    return new ArrayList<>(this.project);
  }

  /**
   * Helper method for throwing an exception when the project has not been created.
   */
  private void throwExceptionProjectNotMade() {
    throw new IllegalStateException("The project has not been created");
  }

}
