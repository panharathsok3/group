package model;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CollageProjectModelImpl implements CollageProjectModel {

  //Hashmap that internally matches a layer to its pixels.
  //the directory of layers stored in the Collage program.
  //the directory of layers maps a name String to a list of pixels.
  private final Map<String, ArrayList<ArrayList<Pixel>>> collageDirectory;
  private final int canvasHeight;
  private final int canvasWidth;

  private Image image;

  private final Layer layer;


  public CollageProjectModelImpl(int canvasHeight, int canvasWidth) {
    this.canvasHeight = canvasHeight;
    this.canvasWidth = canvasWidth;
    this.collageDirectory = new HashMap<>();
    this.layer = new Layer(this.canvasHeight, this.canvasWidth, " ");
  }

  @Override
  public int getHeight(String name) {
    return this.canvasHeight;
  }


  @Override
  public int getWidth(String name) {
    return this.canvasWidth;
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
  public void addLayerToProject(String layerName, ArrayList<ArrayList<Pixel>> defaultLayer) {
    this.collageDirectory.put(layerName, defaultLayer);
  }


  //places an image on the layer such that the top left corner
  // of the image is at (x-pos, y-pos)
  @Override
  public void addImageToLayer(String layerName, ArrayList<Pixel> imageToAdd, int xPos, int yPos) {
    //this.collageDirectory.get(layer).add((xPos * yPos), imageToAdd);
    this.collageDirectory.get(xPos).set(yPos,imageToAdd);

  }


  @Override
  public void saveProject(String fileName, String filePath, Color[][] loadedImages) throws IllegalArgumentException {
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

  @Override
  public void savePPMProject(String filePath, Color[][] loadedImages) throws IOException, FileNotFoundException {
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
        Color c = loadedImages[i][j];
        fileWriter.write(c.getRed() + "\n");
        fileWriter.write(c.getGreen() + "\n");
        fileWriter.write(c.getBlue() + "\n");
      }
    }
    fileWriter.close();
  }


  @Override
  public void saveImage(String filePath, Color[][] imagePixels) throws IllegalArgumentException {

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


  //key value pairs
  private void UpdateCollageDirectory(String layerName, ArrayList<ArrayList<Pixel>> layer) {
    this.collageDirectory.put(layerName, layer);
  }


  //set-filter layer-name filter-option:
  // sets the filter of the given layer where filter-option is one of the following at the moment


  @Override
  public void setFilter(String layerName, String filterOption, double[] filterValue) {

    //get the layer that we want to add a filter to
    ArrayList<ArrayList<Pixel>> layer = collageDirectory.get(layerName);

    for (int row = 0; row < layer.size(); row++) {
      for (int col = 0; col < layer.get(0).size(); col++) {

        //getting the rgb values on the current layer
        Pixel currentLayer = layer.get(row).get(col);

        int red = currentLayer.getRedComponent();
        int green = currentLayer.getGreenComponent();
        int blue = currentLayer.getBlueComponent();


        int newRedColor = (int) Math.round((filterValue[0] * red) + (filterValue[1] * green) + (filterValue[2] * blue));
        int newGreenColor = (int) Math.round((filterValue[3] * red) + (filterValue[4] * green) + (filterValue[5] * blue));
        int newBlueColor = (int) Math.round((filterValue[6] * red) + (filterValue[7] * green) + (filterValue[8] * blue));


        //setting the rgb values on the current layer
        currentLayer.setRedComponent(Math.max(Math.min(newRedColor, 255), 0));
        currentLayer.setGreenComponent(Math.max(Math.min(newGreenColor, 255), 0));
        currentLayer.setBlueComponent(Math.max(Math.min(newBlueColor, 255), 0));
      }
    }
    UpdateCollageDirectory(layerName, layer);
  }
}
