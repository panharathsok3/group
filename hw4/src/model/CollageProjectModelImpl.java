package model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import model.Effects.BulkAssignFilter;
import model.Effects.MacroCollageEffects;


public class CollageProjectModelImpl implements CollageProject {

  private final ArrayList<Layer> project;

  //maps a list of layer names as Strings to their layers
  private final LinkedHashMap<String, ArrayList<Layer>> collageDirectory;

  private final int canvasHeight;
  private final int canvasWidth;


  public CollageProjectModelImpl(int canvasHeight, int canvasWidth) {
    this.canvasHeight = canvasHeight;
    this.canvasWidth = canvasWidth;
    this.project = new ArrayList<>();
    this.collageDirectory = new LinkedHashMap<>();
  }




  //new-project canvas-height canvas-width:
  @Override
  public void newProject(int canvasHeight, int canvasWidth) {
    CollageProjectModelImpl projectModel = new CollageProjectModelImpl(canvasHeight, canvasWidth);

  }


  /**
   * Checks if there is already a layer with the same name as the user is trying to give.
   *
   * @param layerName the name of the new layer that the user wants to add.
   */
  private void checkLayerName(String layerName) {
    if (collageDirectory.containsKey(layerName)) {
      throw new IllegalArgumentException("There is already a layer" +
              "with the name you are trying to use");
    }
  }

  //add-layer layer-name: adds a new layer with the given name

  // to the top of the whole project.
  //should throw an exception if there is any attempt at
  // creating another layer with the same name
  @Override
  public void addLayerToProject(String layerName) {
    Layer layer = new Layer(layerName, this.canvasHeight, this.canvasWidth);
    checkLayerName(layerName);
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
  public void saveProject(String fileName, String filePath, Pixel[][] loadedImages) throws IllegalArgumentException {
    String[] cd = filePath.split("\\.");
    String fileFormat = cd[1];

    if (fileFormat.equals("ppm")) {
      try {
        savePPMProject(fileName, loadedImages);
      } catch (IOException e) {
        throw new IllegalArgumentException("Was not able to save");
      }
    }
  }

  /**
   * Allows the user to save a project as a PPM file.
   *
   * @param filePath     the location where the file will be stored.
   * @param loadedImages the images in the project at the time they saved it.
   * @throws IllegalArgumentException if the file has no contents/images.
   * @throws FileNotFoundException    if the file
   */
  private void savePPMProject(String filePath, Pixel[][] loadedImages) throws IOException {
    if (loadedImages == null || loadedImages.length == 0) {
      throw new IllegalArgumentException("File cannot be empty");
    }
    //write the new file to this path
    FileWriter fileWriter = new FileWriter(filePath);

    //following the format on the specification
    int height = loadedImages.length;
    int width = loadedImages[0].length;

    fileWriter.write("C1\n");
    fileWriter.write(String.format("%d %d\n", width, height));
    //depends on what ever the colors in the project are
    fileWriter.write("256\n"); //because the max value of each pixel can be 256

    //get each value the number of height times width times.
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Pixel c = loadedImages[i][j];
        fileWriter.write(c.getRedComponent() + " " + c.getGreenComponent() + " " +
                c.getBlueComponent() + " " + c.getAlphaComponent() + "\n");
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

  private CollageProjectModelImpl loadProject(String filePath) throws IOException {

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
      if (layer.getName().equals(layerName)) {
        currentLayer = layer;
      }
      else {
        throw new IllegalArgumentException("Layer doesn't exist");
      }
    }

    switch (filterOption) {
      case "normal":
        break;
      case "red-component":
        //get the pixels in that layer and set blue and green comps to 0
        bulkAssign = new BulkAssignFilter(this.canvasWidth, this.canvasHeight, filterOption);
        bulkAssign.executeMacro(currentLayer);
        break;
      case "green-component":
        //setting reg and blue comps to 0
        bulkAssign = new BulkAssignFilter(this.canvasWidth, this.canvasHeight, filterOption);
        bulkAssign.executeMacro(currentLayer);
        break;
      case "blue-component":
        //setting red and green comps to 0
        bulkAssign = new BulkAssignFilter(this.canvasWidth, this.canvasHeight, filterOption);
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
