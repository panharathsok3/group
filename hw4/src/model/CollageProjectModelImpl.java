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

  private final Layer layer;


  public CollageProjectModelImpl(int canvasHeight, int canvasWidth) {
    this.canvasHeight = canvasHeight;
    this.canvasWidth = canvasWidth;
    this.collageDirectory = new HashMap<>();
    this.layer = new Layer(this.canvasHeight,this.canvasWidth," ");
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
  public void addLayerToProject(String layerName,ArrayList<ArrayList<Pixel>> defaultLayer) {
   this.collageDirectory.put(layerName,defaultLayer);
  }


  //places an image on the layer such that the top left corner
  // of the image is at (x-pos, y-pos)
  @Override
  public void addImageToLayer(String layerName, ArrayList<Pixel> imageToAdd, int xPos, int yPos) {
    this.collageDirectory.get(layer).add((xPos * yPos),imageToAdd);
  }


  @Override
  public void saveProject(String fileName, String filePath, Image[][] loadedImages) throws IllegalArgumentException {

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
  public void savePPMProject(String filePath, Image[][] loadedImages) throws IOException, FileNotFoundException {
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

    for (int i = 0; i < height; i += 1) {
      for (int j = 0; j < width; j += 1) {

        ArrayList<Image> layer;
        Map<String, ArrayList<ArrayList<Image>>> layers;
        ArrayList<ArrayList<Image> pixelsOnALayer =


//        Color[][] projectContents =  loadedImages[i][j].getGraphics().getColor();
//        Color c = loadedImages[i][j].getGraphics().getColor();
//
//        int red = c.getRed();
//        int green = c.getGreen();
//        int blue = c.getBlue();
//        fileWriter.write(red + "\n");
//        fileWriter.write(green + "\n");
//        fileWriter.write(blue + "\n");
      }
    }

//    try{
//      byte[] bytes = fileWriter.toString().getBytes();
//      fileWriter.write(Arrays.toString(bytes));
//      fileWriter.close();
//      fileWriter.flush();
//      System.out.println("Project has been saved!");
//    } catch(IOException e) {
//      throw new IllegalStateException("Was not able to save project!");
//    }


  }


  @Override
  public void saveImage(String fileName, String filePath) throws IllegalArgumentException {

    //list of pixels make an image
    //List of , list of pixels make images

    Color[][] images3;
    ArrayList<ArrayList<Pixel>> images = new ArrayList<ArrayList<Pixel>>();
    ArrayList<ArrayList<Color>> images2 = new ArrayList<ArrayList<Color>>();

    try {
      if (fileName.endsWith(".ppm")) {
        savePPMProject(fileName, filePath);
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
    Image[][] projectContents;
    Map<String, ArrayList<ArrayList<Image>>> fileContents;

    try {
      if (!fileName.endsWith(".ppm")) {
        //read other format of image
        projectContents = ImageUtil.readPPM(fileName);
      } else {

        //read the file/project as a ppm
        projectContents = ImageUtil.readPPM(fileName);
      }
    } catch (IOException e) {
      throw new IllegalArgumentException("not able to read");
    }

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
