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

  //Hashmap that internally matches a layers to its pixels.
  private final Map<String, ArrayList<ArrayList<Image>>> collageDirectory;

 private final int canvasHeight;

 private final int canvasWidth;


  public CollageProjectModelImpl(int canvasHeight, int canvasWidth) {
    this.canvasHeight = canvasHeight;
    this.canvasWidth = canvasWidth;
    this.collageDirectory = new HashMap<>();
  }

  @Override
  public int getHeight(String name) {
    return this.canvasHeight;
  }

  //the name of each layer
  @Override
  public int getWidth(String name) {
    return this.canvasWidth;
  }

  @Override
  public void newProject(String name, int height, int width) {

  }

  @Override
  public void addLayerToProject(String name) {
  }

  @Override
  public void addImageToLayer(String layerName, String imageName, int xPos, int yPos) {
  }


  /**
   * Allows the user to save their project to a file with all the loaded images included.
   * This is to accommodate workflow of users making incremental progress.
   *
   * @param fileName     the name of the new file.
   * @param filePath     the path/directory where the user saves their image.
   * @param loadedImages the images that were already in the project.
   * @throws IllegalArgumentException is the user is not able to save their project.
   */
  public static void saveProject(String fileName, String filePath, Image[][] loadedImages) throws IllegalArgumentException {

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
   //  * @param fileName     the name of the file they want to give to their project.
   * @param filePath     the location where the file will be stored.
   * @param loadedImages the images in the project at the time they saved it.
   * @throws IllegalArgumentException if the file has no contents/images.
   * @throws FileNotFoundException    if the file
   */
  public static void savePPMProject(String filePath, Image[][] loadedImages) throws IOException, FileNotFoundException {
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
        Map<String,ArrayList<ArrayList<Image>>> layers ;
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



  /**
   * Allows the user to save an image that they have applied a filter(s) to.
   *
   * @param fileName the name of the new image after the filter(s) have been applied.
   * @param filePath the directory or location of the new image.
   *                 //* @param imageComponents the rgb values in that image after the filter(s) have been applied.
   * @throws IllegalArgumentException is the user is not able to save their new image.
   */
  public static void saveImage(String fileName, String filePath) throws IllegalArgumentException {

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

  private static CollageProjectModelImpl read(String filePath,String fileName) throws IOException {
    FileReader loader = new FileReader(filePath);
    Image[][] projectContents;
    Map<String,ArrayList<ArrayList<Image>>> fileContents ;

    try {
      if (!fileName.endsWith(".ppm")) {
        //read other format of image
        projectContents = ImageUtil.readPPM(fileName);
      } else {
        //read the file/project as a ppm
        projectContents = ImageUtil.readPPM(fileName);
      }
    } catch(IOException e){
      throw new IllegalArgumentException("not able to read");
    }

    return projectContents;
  }

  //new-project canvas-height canvas-width:
  private static CollageProjectModelImpl newProject(String projectName,int canvasHeight, int canvasWidth) {


    CollageProjectModelImpl projectModel = new CollageProjectModelImpl( canvasHeight, canvasWidth);


  }









  //key value pairs
  private void UpdateCollageDirectory(String layerName,ArrayList<ArrayList<Image>> image ) {
    this.collageDirectory.put(layerName,image);
  }

  @Override
  public void setFilter(String layerName, String filterOption, double filterValue) {

    ArrayList<ArrayList<Image>> project = collageDirectory.get(layerName);

    float r = 0;
    float g = 0;
    float b = 0;

    for (int row = 0; row < project.size(); row++) {
      for (int col = 0; col < project.get(0).size(); col++) {

     Image currentLayer = project.get(row).get(col);
        int red = currentLayer.getGraphics().getColor().getRed();
        int green = currentLayer.getGraphics().getColor().getGreen();
        int blue = currentLayer.getGraphics().getColor().getBlue();



        r += filterValue * currentLayer.getGraphics().getColor().getRed();
        g += filterValue * currentLayer.getGraphics().getColor().getRed();
        b += filterValue * currentLayer.getGraphics().getColor().getRed();



        int newRedColor = (int) Math.round((filterValue[0] * red) + (filterValue[1] * green) + (filterValue[2] * blue));
        int newGreenColor = (int) Math.round((filterValue[3] * red) + (filterValue[4] * green) + (filterValue[5] * blue));
        int newBlueColor = (int) Math.round((filterValue[6] * red) + (filterValue[7] * green) + (filterValue[8] * blue));

       return new Color(Math.max(0,Math.min(255,Math.round(r)))),

        Math.max(Math.min(newRedColor, 255), 0));
        (Math.max(Math.min(newGreenColor, 255), 0));
        (Math.max(Math.min(newBlueColor, 255), 0));
      }
    }

  }

}
