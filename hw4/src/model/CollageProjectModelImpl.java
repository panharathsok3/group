package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import java.util.Map;
import model.Effects.BulkAssignFilter;
import model.Effects.MacroCollageEffects;

/**
 * Creates a collage to work on.
 */
public class CollageProjectModelImpl implements CollageProject {

  private final List<Layer> project;
  private final int canvasHeight;
  private final int canvasWidth;
  private boolean backgroundMade;

  /**
   * Creates a CollageProjectModelImpl to work on
   * @param canvasHeight the height of the canvas
   * @param canvasWidth the width of the canvas
   */
  public CollageProjectModelImpl(int canvasHeight, int canvasWidth) {
    this.canvasHeight = canvasHeight;
    this.canvasWidth = canvasWidth;
    this.project = new LinkedList<>();
    this.backgroundMade = false;
  }


  //new-project canvas-height canvas-width:
  @Override
  public void newProject(int canvasHeight, int canvasWidth) {
    CollageProjectModelImpl projectModel = new CollageProjectModelImpl(canvasHeight, canvasWidth);
    this.addLayerToProject("Background");
    this.backgroundMade = true;
  }

  @Override
  public void addLayerToProject(String layerName) throws IllegalStateException {
    Layer layer;
    if (!this.backgroundMade) {
      layer = new Layer(layerName, this.canvasHeight, this.canvasWidth, 0);
    }
    else {
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


    /*
  add-image-to-layer layer-name image-name x-pos y-pos:
  places an image on the layer such that the top left corner of the image is at (x-pos, y-pos)
   */


  private boolean isPositionOccupied(String layerName, int row, int col) {
    if (row < this.canvasHeight || row > this.canvasHeight || col < this.canvasWidth || col > this.canvasWidth) {
      throw new IllegalArgumentException("The Row and column that you specified " +
              "are out of the bounds of this layer. Please Try again");
    }

    Pixel pixels = new Pixel(0, 0, 0);
    List<Integer> possibleCoordinates = new ArrayList<>();
    List<List<Integer>> occupiedPixels = new ArrayList<>();

    //get every position on a layer
    // check if they have an image(pixels)
    // return false if they do else return true

    for (int i = 0; i < this.canvasHeight; i++) {
      for (int j = 0; j < this.canvasWidth; j++) {
        possibleCoordinates.add(i, j);
      }
    }

    //These are pixels that are on the grid currently
    List<Integer> listOfPixels = new ArrayList<>() {{
      int r = possibleCoordinates.get(pixels.getRedComponent());
      int g = possibleCoordinates.get(pixels.getGreenComponent());
      int b = possibleCoordinates.get(pixels.getGreenComponent());
    }};

    occupiedPixels.add(listOfPixels);

    if ((occupiedPixels.contains(row) && (occupiedPixels.contains(col)))) {
      return true;
    }
    return false;
  }

  //places an image on the layer such that the top left corner
  // of the image is at (x-pos, y-pos)
  @Override
  public void addImageToLayer(String layerName, ArrayList<Pixel> imageToAdd, int xPos, int yPos) {
//before we add an image to a layer, we want to check if x and y are occupied or not,
    if (!isPositionOccupied(layerName, xPos, yPos)) {
      for (Layer layer : this.project) {
        if (layer.getName().equals(layerName)) {
          //imageToAdd.add()

        }
      }
    }
    //take the pixels from the image I want to add,
  }


  @Override
  public void saveProject(String filePath) throws IllegalArgumentException {
    String[] cd = filePath.split("\\.");
    String fileFormat = cd[1];

//    if (fileFormat.equals("ppm")) {
//      try {
//        savePPMProject(fileName, loadedImages);
//      } catch (IOException e) {
//        throw new IllegalArgumentException("Was not able to save");
//      }
//    }
  }

  /**
   * Allows the user to save a project as a PPM file.
   *
   * @param filePath the location where the file will be stored.
   * @throws IllegalArgumentException if the file has no contents/images.
   * @throws FileNotFoundException    if the file
   */
  private void savePPMProject(String filePath) throws IOException {

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
  public void saveImage(String filePath, Pixel[][] imagePixels) throws IllegalArgumentException {
    try {
      if (filePath.endsWith(".ppm")) {
        savePPMProject(filePath, imagePixels);
      }
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("The image you are trying to save cannot be found");
    } catch (IOException e) {
      throw new IllegalArgumentException(e);
    }
  }

  //load-project path-to-project-file: loads a project into the program
  @Override
  public CollageProjectModelImpl loadProject(String filePath) throws IOException {

    FileReader loader = new FileReader(filePath);
    ArrayList<Layer> projectContents;

    if (filePath.endsWith(".ppm")) {
      //projectContents =  ImageUtil.readPPM(filePath);
    }
    return new CollageProjectModelImpl(canvasHeight, canvasWidth);
  }

  //set-filter layer-name filter-option:
  // sets the filter of the given layer where filter-option is one of the following at the moment


  @Override
  public void setFilter(String layerName, String filterOption) {
    MacroCollageEffects bulkAssign;
    Layer currentLayer = null;

    for (Layer layer : project) {
      if (layerName.equals(layer.getName())) {
        currentLayer = layer;
      }
    }

    switch (filterOption) {
      case "normal":
        break;
      case "red-component":
        bulkAssign = new BulkAssignFilter(this.canvasHeight, this.canvasWidth, filterOption);
        bulkAssign.executeMacro(currentLayer);
        break;
      case "green-component":
        bulkAssign = new BulkAssignFilter(this.canvasHeight, this.canvasWidth, filterOption);
        bulkAssign.executeMacro(currentLayer);
        break;
      case "blue-component":
        bulkAssign = new BulkAssignFilter(this.canvasHeight, this.canvasWidth, filterOption);
        bulkAssign.executeMacro(currentLayer);
        break;
      case "brighten-value":
        break;
      case "brighten-luma":
        break;
      case "brighten-intensity":
        break;
      case "darken-intensity":
        break;
      case "darken-luma":
        break;
      case "darken-value":
        break;
      default:
        //normal-does nothing to the image
    }
  }
}
