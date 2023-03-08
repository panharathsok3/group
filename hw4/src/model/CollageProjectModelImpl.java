package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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
  private boolean backgroundMade;
  private boolean createdProject;

  /**
   * Creates a CollageProjectModelImpl to work on.
   */
  public CollageProjectModelImpl() {
    this.project = new LinkedList<>();
    this.backgroundMade = false;
    this.createdProject = false;
  }

  @Override
  public void newProject(int canvasHeight, int canvasWidth) {
    CollageProjectModelImpl projectModel = new CollageProjectModelImpl();
    this.addLayer("Background");
    this.canvasHeight = canvasHeight;
    this.canvasWidth = canvasWidth;
    this.backgroundMade = true;
    this.createdProject = true;
  }

  @Override
  public void addLayer(String layerName) throws IllegalStateException {
    if (!createdProject) {
      this.throwExceptionProjectNotMade();
    }

    Layer layer;
    if (!this.backgroundMade) {
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
  }

  //places an image on the layer such that the top left corner TODO
  // of the image is at (x-pos, y-pos)
  @Override
  public void addImageToLayer(String layerName, String filePath, int xPos, int yPos) {
    if (!createdProject) {
      this.throwExceptionProjectNotMade();
    }


    ArrayList<ArrayList<Pixel>> pixels = new ArrayList<>();

    Pixel newImage = new Pixel(1, 1, 1);


    for (Layer layer : this.project) {
      if (getPixelAtCoordinate(xPos, yPos).contains(pixels)) {
        ArrayList<Pixel> row = layer.getPixelsOnLayer().get(xPos);
        row.set(yPos, newImage);
      } else {
        Layer row = project.get(xPos);
        row.getPixelsOnLayer().get(yPos).set(yPos, newImage);
      }
    }


    //before we add an image to a layer, we want to check if x and y are occupied or not,
//    if (!isPositionOccupied(layerName, xPos, yPos)) {
//      for (Layer layer : this.project) {
//        if (layer.getName().equals(layerName)) {
//          //imageToAdd.add()
//
//        }
//      }
//    }
    //take the pixels from the image I want to add,
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

    fileWriter.write("C1\n");
    fileWriter.write(this.canvasWidth + " " + this.canvasHeight + "\n");
    //depends on what ever the colors in the project are
    fileWriter.write("256\n"); //because the max value of each pixel can be 256

    for (int i = 0; i < this.project.size(); i++) {
      Layer layer = this.project.get(i);
      fileWriter.write(layer.getName() + "\n");

      ArrayList<ArrayList<Pixel>> pixels = layer.getPixelsOnLayer();

      for (int j = 0; j < this.canvasHeight; j++) {
        for (int k = 0; k < this.canvasWidth; k++) {
          int redComponent = pixels.get(i).get(j).getRedComponent();
          int greenComponent = pixels.get(i).get(j).getGreenComponent();
          int blueComponent = pixels.get(i).get(j).getBlueComponent();
          int alphaComponent = pixels.get(i).get(j).getAlphaComponent();
          fileWriter.write(redComponent + " " + greenComponent + " " + blueComponent + " "
                  + " " + alphaComponent + "\n");
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
    this.createdProject = true;
    this.backgroundMade = true;

    FileReader loader = new FileReader(filePath);

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

    for (Layer layer : project) {
      if (layerName.equals(layer.getName())) {
        currentLayer = layer;
      } else {
        throw new IllegalArgumentException("Layer not found");
      }
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

  /**
   * Helper method for throwing an exception when the project has not been created.
   */
  private void throwExceptionProjectNotMade() {
    throw new IllegalStateException("The project has not been created");
  }

  private ArrayList<ArrayList<Pixel>> getPixelAtCoordinate(int row, int col) {
    if (row < 0 || row > this.canvasHeight || col < 0 || col > this.canvasWidth) {
      throw new IllegalArgumentException("coordinates provided are out of bounds");
    }
    return this.project.get(canvasHeight).getPixelsOnLayer();
  }

}
