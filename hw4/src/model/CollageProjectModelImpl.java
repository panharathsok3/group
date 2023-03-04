package model;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import model.Effects.MacroCollageEffects;


public class CollageProjectModelImpl implements CollageProject {

  private ArrayList<Layer> project;

  private final int canvasHeight;
  private final int canvasWidth;

  private Image image;


  public CollageProjectModelImpl(int canvasHeight, int canvasWidth) {
    this.canvasHeight = canvasHeight;
    this.canvasWidth = canvasWidth;
  }

  //new-project canvas-height canvas-width:
  @Override
  public void newProject(String projectName, int canvasHeight, int canvasWidth) {
    CollageProjectModelImpl projectModel = new CollageProjectModelImpl(canvasHeight, canvasWidth);
  }


  //add-layer layer-name: adds a new layer with the given name

  // to the top of the whole project.
  //should throw an exception if there is any attempt at
  // creating another layer with the same name
  @Override
  public void addLayerToProject(String layerName) {
    Layer layer = new Layer(layerName);
    project.add(layer);
  }


  //places an image on the layer such that the top left corner
  // of the image is at (x-pos, y-pos)
  @Override
  public void addImageToLayer(String layerName, ArrayList<Pixel> imageToAdd, int xPos, int yPos) {
    for (Layer layer : this.project) {
      if (layer.getName().equals(layerName)) {
        //imageToAdd.add()
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
   * <p>
   * //  * @param fileName     the name of the file they want to give to their project.
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

  private CollageProjectModelImpl loadProject(String filePath, String fileName) throws IOException {

    FileReader loader = new FileReader(filePath);

    ArrayList<ArrayList<Layer>> projectContents;

    // projectContents = ImageUtil.readPPM(fileName);

    return new CollageProjectModelImpl(this.canvasHeight, this.canvasWidth);
  }

  //set-filter layer-name filter-option:
  // sets the filter of the given layer where filter-option is one of the following at the moment


  @Override
  public void setFilter(String layerName, String filterOption, double[] filterValue) {

    switch (filterOption) {
      case "red-component":
        //get the pixels in that layer and set blue and green comps to 0
        MacroCollageEffects bulkAssign;
        break;
      case "green-component":
        //setting reg and blue comps to 0
        break;
      case "blue-component":
        //setting red and green comps to 0
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


    //get the layer that we want to add a filter to
    //ArrayList<ArrayList<Pixel>> layer = collageDirectory.get(layerName);
    Layer layer = new Layer("layer1");

//    for (int row = 0; row < layer.size(); row++) {
//      for (int col = 0; col < layer.get(0).size(); col++) {
//
//        //getting the rgb values on the current layer
//        Pixel currentLayer = layer.get(row).get(col);
//
//        int red = currentLayer.getRedComponent();
//        int green = currentLayer.getGreenComponent();
//        int blue = currentLayer.getBlueComponent();
//
//
//        int newRedColor = (int) Math.round((filterValue[0] * red) + (filterValue[1] * green) + (filterValue[2] * blue));
//        int newGreenColor = (int) Math.round((filterValue[3] * red) + (filterValue[4] * green) + (filterValue[5] * blue));
//        int newBlueColor = (int) Math.round((filterValue[6] * red) + (filterValue[7] * green) + (filterValue[8] * blue));
//
//      }
//    }
//    UpdateCollageDirectory(layerName, layer);
  }
}
