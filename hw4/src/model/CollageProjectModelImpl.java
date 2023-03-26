package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import java.util.Scanner;
import model.effects.BrightenDarkenMacro;
import model.effects.BulkAssignFilter;
import model.effects.ChangeTransparencyMacro;
import model.effects.DarkenMultiplyBrightenScreenMacro;
import model.effects.InversionDifferenceMacro;
import model.effects.MacroCollageEffects;

/**
 * This class is used to make a collage to work on.
 * The Layers on the project is stored as a List so that as users add Layers to the project,
 * they know the order in which they added the layers.
 * The filters that are being applied is stored as a Map where the key is the layer name as a string
 * and the filter type is the value as a string. If no filter was applied, it will be put to normal.
 * When reading in an image, we convert it to a base of 256.
 */
public class CollageProjectModelImpl implements CollageProject {

  private List<ILayer> project;
  private int canvasHeight;
  private int canvasWidth;
  private String projectName;
  private boolean backgroundMade;
  private boolean createdProject;
  private Map<String, String> layerFilter;
  private final int maxValue;

  /**
   * Creates a CollageProjectModelImpl to work on.
   */
  public CollageProjectModelImpl() {
    this.createdProject = false;
    this.maxValue = 255;
  }

  @Override
  public void newProject(String name, int canvasHeight, int canvasWidth)
          throws IllegalArgumentException {
    if (name == null || name.equals("") || canvasHeight < 1 || canvasWidth < 1) {
      throw new IllegalArgumentException("the name of the project can't be null and the "
              + "canvas height and width can't be less than 1");
    }
    this.backgroundMade = false;
    this.projectName = name;
    this.createdProject = true;
    this.canvasHeight = canvasHeight;
    this.canvasWidth = canvasWidth;
    this.project = new ArrayList<>();
    this.layerFilter = new HashMap<>();
    this.addLayer("Background");
    this.backgroundMade = true;
  }

  @Override
  public void addLayer(String layerName) throws IllegalStateException, IllegalArgumentException {
    if (layerName == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    this.throwExceptionProjectNotMade();

    Layer layer;
    if (this.backgroundMade) {
      layer = new Layer(layerName, this.canvasHeight, this.canvasWidth, 0);
    } else {
      layer = new Layer(layerName, this.canvasHeight, this.canvasWidth, 255);
    }

    for (ILayer currentLayer : project) {
      if (currentLayer.getName().equals(layer.getName())) {
        throw new IllegalArgumentException("There is already a layer with the name you are trying "
            + "to use");
      }
    }

    this.project.add(layer);
    this.setFilter(layerName, "normal");
  }

  @Override
  public void addImageToLayer(String layerName, String filePath, int xPos, int yPos)
          throws IllegalArgumentException {

    this.throwExceptionProjectNotMade();

    if (layerName == null || layerName.equals("") || filePath == null || filePath.equals("")
            || xPos < 0 || xPos > this.canvasHeight || yPos < 0 || yPos > this.canvasWidth) {
      throw new IllegalArgumentException("layer name and file path cannot be null, x and y "
          + "positions have to be within the boundaries of the canvas");
    }

    List<List<IPixel>> image = this.readImage(filePath, false, "P3");

    for (ILayer layer : this.project) {
      if (layerName.equals(layer.getName())) {

        layer.addImage(xPos, yPos, image);
        return;
      }
    }

    throw new IllegalArgumentException("Layer not found");
  }

  /**
   * Read an image file and returns the pixels on the image as a 2D array.
   * Converts color value to the base of 256.
   * @param filename the path of the file
   * @param hasAlpha true if and only if the original image has an alpha value
   * @param fileType the type of file that is being read from
   * @return the pixels on the image as a 2D array
   * @throws IllegalStateException when the file could not be retrieved
   *                               or the file is not a PPM file
   */
  private List<List<IPixel>> readImage(String filename, boolean hasAlpha, String fileType)
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

        if (!hasAlpha) {
          pixelsOnImage.get(i).add(new Pixel(r, g, b));
        }
        else {
          int a = sc.nextInt();
          pixelsOnImage.get(i).add(new Pixel(r, g, b, a));
        }

      }
    }
    return pixelsOnImage;
  }


  @Override
  public ILayer makeFinalImage(boolean hasAlpha) {
    this.throwExceptionProjectNotMade();

    MacroCollageEffects macro;

    List<ILayer> layers = new ArrayList<>(this.project);
    List<List<IPixel>> finalImage = new ArrayList<>();
    boolean isBackground = true;

    for (ILayer layer: layers) {
      String filter = this.getFiltersOnProject().get(layer.getName());

      switch (filter) {
        case "normal":
          break;
        case "red-component":
        case "green-component":
        case "blue-component":
          macro = new BulkAssignFilter(this.canvasHeight, this.canvasWidth, filter);
          macro.executeMacro(layer);
          break;
        case "brighten-value":
        case "brighten-luma":
        case "brighten-intensity":
          macro = new BrightenDarkenMacro(this.canvasHeight, this.canvasWidth, filter, true);
          macro.executeMacro(layer);
          break;
        case "darken-intensity":
        case "darken-luma":
        case "darken-value":
          macro = new BrightenDarkenMacro(this.canvasHeight, this.canvasWidth, filter, false);
          macro.executeMacro(layer);
          break;
        case "inversion-difference":
          macro = new InversionDifferenceMacro(this.canvasHeight, this.canvasWidth, finalImage);
          macro.executeMacro(layer);
          break;
        case "darken-multiply":
          macro = new DarkenMultiplyBrightenScreenMacro(this.canvasHeight, this.canvasWidth,
              finalImage, false);
          macro.executeMacro(layer);
          break;
        case "brighten-screen":
          macro = new DarkenMultiplyBrightenScreenMacro(this.canvasHeight, this.canvasWidth,
              finalImage, true);
          macro.executeMacro(layer);
           break;
        default:
          //do nothing
      }

      if (isBackground) {
        finalImage = new ArrayList<>(layer.getPixelsOnLayer());
        isBackground = false;
      }
      else {
        macro = new ChangeTransparencyMacro(this.canvasHeight, this.canvasWidth, hasAlpha,
            finalImage);
        macro.executeMacro(layer);
        finalImage = layer.getPixelsOnLayer();
      }
    }

    return new Layer("Final Image", this.canvasHeight, this.canvasWidth, finalImage);
  }

  @Override
  public void setFilter(String layerName, String filterOption) throws IllegalArgumentException,
      IllegalStateException {
    this.throwExceptionProjectNotMade();

    if (layerName == null || filterOption == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }
    boolean layerFound = false;

    switch (filterOption) {
      case "normal":
      case "red-component":
      case "green-component":
      case "blue-component":
      case "brighten-value":
      case "brighten-luma":
      case "brighten-intensity":
      case "darken-intensity":
      case "darken-luma":
      case "darken-value":
      case "inversion-difference":
      case "darken-multiply":
      case "brighten-screen":
        break;
      default:
        throw new IllegalArgumentException("Filter doesn't exist");
    }

    for (ILayer layer : this.project) {
      if (layerName.equals(layer.getName())) {
        layerFound = true;
        this.layerFilter.put(layerName, filterOption);
      }
    }

    if (!layerFound) {
      throw new IllegalArgumentException("Layer not found");
    }


  }

  @Override
  public List<ILayer> getLayers() throws IllegalStateException {
    this.throwExceptionProjectNotMade();
    return new ArrayList<>(this.project);
  }

  @Override
  public String getProjectName() throws IllegalStateException {
    this.throwExceptionProjectNotMade();
    return this.projectName;
  }

  @Override
  public int getHeight() throws IllegalStateException {
    this.throwExceptionProjectNotMade();
    return this.canvasHeight;
  }

  @Override
  public int getWidth() throws IllegalStateException {
    this.throwExceptionProjectNotMade();
    return this.canvasWidth;
  }

  @Override
  public int getMaxValue() throws IllegalStateException {
    this.throwExceptionProjectNotMade();
    return this.maxValue;
  }

  @Override
  public Map<String, String> getFiltersOnProject() {
    this.throwExceptionProjectNotMade();
    return new HashMap<>(this.layerFilter);
  }

  /**
   * Helper method for throwing an exception when the project has not been created.
   */
  private void throwExceptionProjectNotMade() {
    if (!createdProject) {
      throw new IllegalStateException("The project has not been created");
    }
  }

}
