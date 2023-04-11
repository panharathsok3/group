package controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.CollageProject;
import model.ILayer;
import model.IPixel;
import model.Pixel;
import view.CollageView;

/**
 * A CollageControllerImpl is the controller for the CollageProjectModelImpl. This class is used to
 * read user input and perform actions accordingly.
 */
public class CollageControllerImpl implements CollageController {
  private Readable in;
  private final CollageProject collage;
  private CollageView view;
  private boolean projectMade;

  /**
   * Represents the controller for this CollageProjectModelImpl.
   *
   * @param in      used to parse input from the user
   * @param collage the collage that will be worked on
   * @param view    the view of the CollageProjectModelImpl
   * @throws IllegalArgumentException if any of the given parameters is null
   */
  public CollageControllerImpl(Readable in, CollageProject collage, CollageView view)
          throws IllegalArgumentException {
    if (in == null || collage == null || view == null) {
      throw new IllegalArgumentException("the given arguments cannot be null");
    }
    this.in = in;
    this.collage = collage;
    this.view = view;
    this.projectMade = false;
  }

  /**
   * Represents a Controller who's only job is to handle File IO.
   * @param collage the CollageProject that will be used
   * @param projectMade true if and only if the project has been made
   */
  public CollageControllerImpl(CollageProject collage, boolean projectMade) {
    this.collage = collage;
    this.projectMade = projectMade;
  }

  @Override
  public void runProgram() throws IllegalStateException {
    Scanner sc = new Scanner(this.in);
    boolean running = true;
    // display();

    if (!sc.hasNext()) {
      throw new IllegalStateException("Ran out of inputs.");
    }

    while (running) {
      String command = this.readValueString(sc);


      switch (command) {
        case "quit":
          running = false;
          this.renderMessage("The program has ended");
          break;
        case "new-project":
          String name = this.readValueString(sc);
          int height = this.readValueInteger(sc);
          int width = this.readValueInteger(sc);
          try {
            this.collage.newProject(name, height, width);
            this.projectMade = true;
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null");
            this.projectMade = false;
          }
          break;
        case "load-project":
          String filename = this.readValueString(sc);
          try {
            this.loadProject(filename);
            this.projectMade = true;
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null");
          } catch (IllegalStateException e) {
            this.renderMessage("File can't be open or file is not enough to start a load a "
                + "project");
            this.projectMade = false;
          }
          break;
        case "save-project":
          String filePath = this.readValueString(sc);
          try {
            this.saveProject(filePath);
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null");
          } catch (IllegalStateException e) {
            this.renderMessage("The project hasn't been made yet");
          }
          break;
        case "add-layer":
          String layerName = this.readValueString(sc);
          try {
            this.collage.addLayer(layerName);
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null or the layer already exist");
          } catch (IllegalStateException e) {
            this.renderMessage("The project hasn't been made yet");
          }
          break;
        case "add-image-to-layer":
          String layerName1 = this.readValueString(sc);
          String imageName = this.readValueString(sc);
          int x = this.readValueInteger(sc);
          int y = this.readValueInteger(sc);

          String extension = imageName.substring(imageName.lastIndexOf(".") + 1);
          String imageToken = null;
          List<List<IPixel>> image = new ArrayList<>();

          boolean hasAlpha = true;
          if (extension.equalsIgnoreCase("ppm")) {
            imageToken = "P3";
            image = this.readImagePPM(imageName, imageToken);
          }
          else if (extension.equalsIgnoreCase("png")) {
            imageToken = "png";
          }
          else if (extension.equalsIgnoreCase("jpg")){
            imageToken = "jpg";
          }
          else {
            throw new IllegalStateException("We only support ppm, png, and jpg");
          }

          try {
            this.collage.addImageToLayer(layerName1,
                image, x, y);
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null or negative or the layer doesn't exist");
          } catch (IllegalStateException e) {
            this.renderMessage("The layer already exists or the project hasn't been made yet");
          }
          break;
        case "set-filter":
          String layerName2 = this.readValueString(sc);
          String filterOption = this.readValueString(sc);
          try {
            this.collage.setFilter(layerName2, filterOption);
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null or the layer doesn't exist or filter "
                + "doesn't exist");
          } catch (IllegalStateException e) {
            this.renderMessage("The project hasn't been made yet");
          }
          break;
        case "save-image":
          String fileName = this.readValueString(sc);
          try {
            this.saveImage(fileName);
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null");
          } catch (IllegalStateException e) {
            this.renderMessage("The project hasn't been made yet");
          }
          break;
        default:
          this.renderMessage("Command doesn't exist");
      }
    }
  }


  @Override
  public void saveProject(String filePath) throws IllegalArgumentException,
      IllegalStateException {
    if (filePath == null) {
      throw new IllegalArgumentException("Cannot give null as an argument");
    }
    this.throwExceptionProjectNotMade();

    try {
      saveProjectHelper(this.collage.getProjectName(), this.collage.getHeight(),
          this.collage.getWidth(), this.collage.getMaxValue(), filePath, this.collage.getLayers(),
          this.collage.getFiltersOnProject(), false);
    } catch (IOException e) {
      throw new IllegalArgumentException("Was not able to save");
    }
  }

  /**
   * Helper method for saving a project to a file.
   * @param projectName the name of the project
   * @param height the height of the image/project
   * @param width the width of the image/project
   * @param maxValue the max value of a pixel component
   * @param filePath the location where the file will be stored.
   * @param layers the layers of the entire project
   * @param filters the map of that has the layer name as the key and its filter as the value
   * @param isSaveImage true if and only if this method is used to save an image
   * @throws IllegalArgumentException if the given filePath is null
   * @throws IOException if there is issue writing to the file
   */
  private void saveProjectHelper(String projectName, int height, int width, int maxValue,
      String filePath, List<ILayer> layers, Map<String, String> filters, boolean isSaveImage)
      throws IOException {

    //write the new file to this path
    FileWriter fileWriter = new FileWriter(filePath);

    fileWriter.write(projectName + "\n");
    fileWriter.write(width + " " + height + "\n");
    //depends on what ever the colors in the project are
    fileWriter.write(maxValue + "\n"); //because the max value of each pixel can be 255

    for (ILayer layer : layers) {
      if (!isSaveImage) {
        String filter = filters.get(layer.getName());
        fileWriter.write(layer.getName() + " " + filter + "\n");

        List<List<IPixel>> pixels = layer.getPixelsOnLayer();

        for (int j = 0; j < height; j++) {
          for (int k = 0; k < width; k++) {
            int redComponent = pixels.get(j).get(k).getRedComponent();
            int greenComponent = pixels.get(j).get(k).getGreenComponent();
            int blueComponent = pixels.get(j).get(k).getBlueComponent();
            int alphaComponent = pixels.get(j).get(k).getAlphaComponent();
            fileWriter.write(redComponent + " " + greenComponent + " " + blueComponent + " "
                + alphaComponent + "\n");
          }
        }
      }
      else {
        List<List<IPixel>> pixels = layer.getPixelsOnLayer();
        for (int j = 0; j < height; j++) {
          for (int k = 0; k < width; k++) {
            int redComponent = pixels.get(j).get(k).getRedComponent();
            int greenComponent = pixels.get(j).get(k).getGreenComponent();
            int blueComponent = pixels.get(j).get(k).getBlueComponent();
            fileWriter.write(redComponent + " " + greenComponent + " " + blueComponent + "\n");
          }
        }
      }
    }

    fileWriter.close();
  }

  @Override
  public void saveImage(String filePath) throws IllegalArgumentException, IllegalStateException {
    this.throwExceptionProjectNotMade();

    if (filePath == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    ILayer finalImage;
    if (filePath.endsWith(".ppm")) {
      finalImage = this.collage.makeFinalImage(false);
      List<ILayer> listLayer = new ArrayList<>();
      listLayer.add(finalImage);

      try {
        this.saveProjectHelper("P3", this.collage.getHeight(), this.collage.getWidth(),
            this.collage.getMaxValue(), filePath, listLayer, this.collage.getFiltersOnProject(),
            true);
      } catch (IOException e) {
        throw new IllegalArgumentException("Was not able to save");
      }
    }
  }


  @Override
  public void loadProject(String filePath) throws IllegalArgumentException,
      IllegalStateException {

    if (filePath == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found!");
    }

    if (!sc.hasNextLine()) {
      throw new IllegalStateException("Nothing inside the file to load");
    }

    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    String projectName = sc.next();

    if (!sc.hasNextInt()) {
      throw new IllegalStateException("this file cannot create a new project");
    }
    int canvasWidth = sc.nextInt();

    if (!sc.hasNextInt()) {
      throw new IllegalStateException("this file cannot create a new project");
    }
    int canvasHeight = sc.nextInt();

    if (!sc.hasNext()) {
      throw new IllegalStateException("this file cannot create a new project");
    }

    this.collage.newProject(projectName, canvasHeight, canvasWidth);

    try {
      sc.next(); // max color
      sc.next(); // background
      sc.next(); // normal

      int layerNum = 0;
      this.addImageToLayerFromFile(sc, layerNum, this.collage.getHeight(), this.collage.getWidth());

      while (sc.hasNext()) {
        String currentLayer = sc.next();
        String filterType = sc.next();
        this.collage.addLayer(currentLayer);
        this.collage.setFilter(currentLayer, filterType);

        layerNum++;
        this.addImageToLayerFromFile(sc, layerNum,
            this.collage.getHeight(), this.collage.getWidth());
      }
    } catch (IllegalStateException e) {
      throw new IllegalStateException("Not enough information to add/create a new layer to add to "
          + "the collage project");
    }
  }

  /**
   * Adds the content of the image from the file and place it on the Layer.
   * @param sc the scanner to read from the file
   * @param layerNum the number of the layer
   */
  private void addImageToLayerFromFile(Scanner sc, int layerNum, int height, int width) {
    List<List<IPixel>> image = new ArrayList<>();

    for (int i = 0; i < height; i++) {
      image.add(new ArrayList<>());
      for (int j = 0; j < width; j++) {
        image.get(i).add(new Pixel(sc.nextInt(), sc.nextInt(), sc.nextInt(), sc.nextInt()));
      }
    }

    this.collage.getLayers().get(layerNum).addImage(0, 0, image);
  }


  @Override
  public List<List<IPixel>> readImagePPM(String filename, String fileType)
      throws IllegalStateException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File " + filename + " not found!");
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
    if (!token.equals(fileType)) {
      throw new IllegalStateException("Invalid file type: plain RAW file should begin with "
          + fileType);
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    List<List<IPixel>> pixelsOnImage = new ArrayList<>();

    for (int i = 0; i < height; i++) {
      pixelsOnImage.add(new ArrayList<>());
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        r = r * 255 / maxValue;
        g = g * 255 / maxValue;
        b = b * 255 / maxValue;

        pixelsOnImage.get(i).add(new Pixel(r, g, b));
      }
    }
    return pixelsOnImage;
  }

  /**
   * Helper method that throws an exception when the project has not been made yet.
   * @throws IllegalStateException when the project has not been made yet
   */
  private void throwExceptionProjectNotMade() throws IllegalStateException {
    if (!projectMade) {
      throw new IllegalStateException("project has not been made");
    }
  }

  /**
   * Reads the next user input as a String and returns it.
   *
   * @param scan the scanner to read in the next user input
   * @return the user input as a String
   * @throws IllegalStateException if and only if the controller is unable to successfully read
   *                               input
   */
  private String readValueString(Scanner scan) throws IllegalStateException {

    String data;

    try {
      data = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("No more inputs");
    }

    return data;
  }

  /**
   * Reads the next user input as an Integer and returns it.
   *
   * @param scan the scanner to read in the next user input
   * @return the user input as an integer
   * @throws IllegalStateException if and only if the controller is unable to successfully read
   *                               input
   */
  private int readValueInteger(Scanner scan) throws IllegalStateException {

    String data = this.readValueString(scan);

    int dataInt = 0;

    try {
      dataInt = Integer.parseInt(data);
    } catch (NumberFormatException e) {
      // do nothing
    }

    return dataInt;
  }

  /**
   * Renders a message to the user.
   * @param message the message to print out
   */
  private void renderMessage(String message) {
    try {
      this.view.renderMessage(message + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException\n");
    }
  }


}
