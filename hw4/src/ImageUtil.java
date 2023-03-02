import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

import model.CollageProjectModelImpl;
import model.Pixel;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents. Feel free to change this method
 * as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file
   */

  //changed return value from void to CollageModelImpl
  //changed name from readPPM to loadPPM
  public static Image[][] readPPM(String filename) {

    if (filename == null || filename.equals("")) {
      throw new IllegalArgumentException("No directory to the file has been provided. Unable to load");
    }

    Scanner sc = null;


    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    //System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

    Image[][] collageContents = new Image[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        System.out.println("Color of pixel (" + j + "," + i + "): " + r + ", " + g + "," + b);
      }
    }
    return collageContents;
  }

  //a collage is set of images on top of each other
  //add images to the collage and save it
  //every image is a new layer on top of each other
  //when a new project us created there is always a background of size height and width


  //demo main
  public static void main(String[] args) {
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "sample.ppm";
    }
    ImageUtil.readPPM(filename);
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


}

