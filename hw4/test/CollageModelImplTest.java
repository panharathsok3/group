import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import model.ILayer;
import model.IPixel;
import org.junit.Test;

import model.CollageProject;
import model.CollageProjectModelImpl;
import model.Layer;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * This is a test class for CollageModelImpl.
 */
public class CollageModelImplTest {
  ILayer layer1;
  ILayer layer2;
  ILayer layer3;
  CollageProject collage1;
  CollageProject collage2;
  CollageProject collage3;
  CollageProject collage4;

  /**
   * Initializes the values.
   */
  private void init() {

    this.layer1 = new Layer("L1", 10, 10, 0);
    this.layer2 = new Layer("L2", 10, 10, 0);
    this.layer3 = new Layer("L3", 10, 10, 0);

    this.collage1 = new CollageProjectModelImpl();
    this.collage2 = new CollageProjectModelImpl();
    this.collage3 = new CollageProjectModelImpl();
    this.collage4 = new CollageProjectModelImpl();

    this.collage1.newProject("C1", 2, 2);
  }

  @Test
  public void testRunningEntireProgram() {
    this.init();

    //NEW PROJECT
    this.collage1.newProject("C1", 2, 2);

    assertEquals("C1", this.collage1.getProjectName());
    assertEquals(2, this.collage1.getHeight());
    assertEquals(2, this.collage1.getWidth());

    assertEquals("Background", this.collage1.getLayers().get(0).getName());
    assertEquals("normal", this.collage1.getFiltersOnProject().get("Background"));

    //ADD LAYER
    this.collage1.addLayer("L1");

    assertEquals("L1", this.collage1.getLayers().get(1).getName());
    assertEquals("normal", this.collage1.getFiltersOnProject().get("L1"));

    //ADD IMAGE TO LAYER
    this.collage1.addImageToLayer("L1", this.getImageFromTakoPPM(), 0, 0);

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("src/tako.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found!");
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
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    List<List<Pixel>> pixelsOnLayers = new ArrayList<>();

    for (int i = 0; i < height; i++) {
      pixelsOnLayers.add(new ArrayList<>());
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        pixelsOnLayers.get(i).add(new Pixel(r, g, b));

      }
    }

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(pixelsOnLayers.get(i).get(j).getGreenComponent(),
            this.collage1.getLayers().get(1).getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(pixelsOnLayers.get(i).get(j).getBlueComponent(),
            this.collage1.getLayers().get(1).getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(pixelsOnLayers.get(i).get(j).getRedComponent(),
            this.collage1.getLayers().get(1).getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(pixelsOnLayers.get(i).get(j).getAlphaComponent(),
            this.collage1.getLayers().get(1).getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }

    //ADD LAYER
    this.collage1.addLayer("L2");
    assertEquals(3, this.collage1.getLayers().size());
    assertEquals("L2", this.collage1.getLayers().get(2).getName());
    assertEquals("normal", this.collage1.getFiltersOnProject().get("L2"));

    //ADD IMAGE TO LAYER
    this.collage1.addImageToLayer("L2", this.getImageFromTakoPPM(), 0, 0);

    try {
      sc = new Scanner(new FileInputStream("src/tako.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found!");
    }

    builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }

    width = sc.nextInt();
    height = sc.nextInt();
    maxValue = sc.nextInt();

    pixelsOnLayers = new ArrayList<>();

    for (int i = 0; i < height; i++) {
      pixelsOnLayers.add(new ArrayList<>());
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        pixelsOnLayers.get(i).add(new Pixel(r, g, b));

      }
    }

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(pixelsOnLayers.get(i).get(j).getGreenComponent(),
            this.collage1.getLayers().get(1).getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(pixelsOnLayers.get(i).get(j).getBlueComponent(),
            this.collage1.getLayers().get(1).getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(pixelsOnLayers.get(i).get(j).getRedComponent(),
            this.collage1.getLayers().get(1).getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(pixelsOnLayers.get(i).get(j).getAlphaComponent(),
            this.collage1.getLayers().get(1).getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }

    //SET FILTER
    this.collage1.setFilter("L2", "darken-intensity");
    List<List<IPixel>> pixelsOnLayerBefore = this.collage1.getLayers().get(2)
        .getPixelsOnLayer();

    assertEquals("darken-intensity", this.collage1.getFiltersOnProject().get("L2"));
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, pixelsOnLayerBefore.get(i).get(j).getRedComponent());
        assertEquals(179, pixelsOnLayerBefore.get(i).get(j).getGreenComponent());
        assertEquals(151, pixelsOnLayerBefore.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerBefore.get(i).get(j).getAlphaComponent());
      }
    }

  }

  @Test
  public void testValidCreateNewProject() {
    this.init();
    this.collage1.newProject("C1", 100, 100);

    assertEquals("C1", this.collage1.getProjectName());
    assertEquals(100, this.collage1.getHeight());
    assertEquals(100, this.collage1.getWidth());
    assertEquals("Background", this.collage1.getLayers().get(0).getName());
    assertEquals("normal", this.collage1.getFiltersOnProject().get("Background"));

    this.collage2.newProject("C2", 20, 20);

    assertEquals("C2", this.collage2.getProjectName());
    assertEquals(20, this.collage2.getHeight());
    assertEquals(20, this.collage2.getWidth());
    assertEquals("Background", this.collage2.getLayers().get(0).getName());
    assertEquals("normal", this.collage2.getFiltersOnProject().get("Background"));
  }

  @Test
  public void testInvalidCreateNewProject() {
    this.init();

    try {
      this.collage2.newProject(null, 20, 20);
      fail("Project name cannot be null");
    } catch (IllegalArgumentException iae) {
      // do nothing
    }

    try {
      this.collage2.newProject("C1", 0, 20);
      fail("Canvas height cannot be less than 1");
    } catch (IllegalArgumentException iae) {
      // do nothing
    }

    try {
      this.collage2.newProject("C1", 20, -2);
      fail("Canvas width cannot be less than 1");
    } catch (IllegalArgumentException iae) {
      // do nothing
    }

    try {
      this.collage2.newProject(null, -2000, 0);
      fail("Arguments name cannot be null, width and height cannot be less than 1");
    } catch (IllegalArgumentException iae) {
      // do nothing
    }

    try {
      this.collage2.newProject("", 2, 2);
      fail("Project name cannot be empty");
    } catch (IllegalArgumentException iae) {
      // do nothing
    }

  }

  @Test
  public void testInvalidAddLayer() {

    this.collage2 = new CollageProjectModelImpl();
    try {
      this.collage2.addLayer("L0");
      fail("A  project must be created first");
    } catch (IllegalStateException e) {
      //do nothing
    }

    this.init();
    try {
      this.collage1.newProject("C1", 5, 5);
      this.collage1.addLayer(null);
      fail("layer name cannot be null");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.collage1.newProject("C1", 5, 5);
      this.collage1.addLayer("L1");
      this.collage1.addLayer("L1");

      this.collage2.newProject("C2",10,10);
      this.collage2.addLayer("L1");
      this.collage2.addLayer("L2");
      this.collage2.addLayer("L1");
      this.collage2.addLayer("L2");
      fail("Cant make a layer with an already used name");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
  }


  @Test
  public void testValidAddLayer() {
    this.init();

    this.collage1.newProject("C1", 5, 5);

    this.collage1.addLayer("L2");
    this.collage1.addLayer("L3");
    this.collage1.addLayer("L1");

    assertEquals(4, this.collage1.getLayers().size());
    assertEquals("Background", this.collage1.getLayers().get(0).getName());
    assertEquals("L1", this.collage1.getLayers().get(3).getName());
    assertEquals("L3", this.collage1.getLayers().get(2).getName());
    assertEquals("L2", this.collage1.getLayers().get(1).getName());
  }

  @Test
  public void testValidAddImageToLayer() {
    this.init();

    this.collage1.newProject("C1", 100, 100);
    this.collage1.addLayer("L1");

    List<List<IPixel>> pixelsOnImage = this.getImageFromTakoPPM();

    this.collage1.addImageToLayer("L1", pixelsOnImage, 0, 0);

    for (int i = 0; i < 100; i++) {
      for (int j = 0; j < 100; j++) {
        assertEquals(pixelsOnImage.get(i).get(j).getGreenComponent(),
            this.collage1.getLayers().get(1).getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(pixelsOnImage.get(i).get(j).getBlueComponent(),
            this.collage1.getLayers().get(1).getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(pixelsOnImage.get(i).get(j).getRedComponent(),
            this.collage1.getLayers().get(1).getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(pixelsOnImage.get(i).get(j).getAlphaComponent(),
            this.collage1.getLayers().get(1).getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testInvalidAddImageToLayer() {
    this.collage1 = new CollageProjectModelImpl();
    try {
      this.collage1.addImageToLayer("L1", new ArrayList<>(1),0,0);
      fail("Didn't start a new project yet");
    } catch (IllegalStateException e) {
      //do nothing
    }

    this.init();

    try {
      this.collage4.newProject("C4",100,100);
      this.collage4.addImageToLayer(null, new ArrayList<>(1),0,0);
      fail("arguments are invalid");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.collage2.newProject("C2",100,100);
      this.collage2.addImageToLayer("L9", null,0,0);
      fail("arguments are invalid");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.collage3.newProject("C3",100,100);
      this.collage3.addImageToLayer(layer1.getName(), new ArrayList<>(1),
          -200,0);
      fail("arguments are invalid");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.collage4 = new CollageProjectModelImpl();
      this.collage4.newProject("C4",100,100);
      this.collage4.addImageToLayer(layer1.getName(), new ArrayList<>(1),
          0,120);
      fail("arguments are invalid");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.collage1.newProject("C1",100,100);
      this.collage1.addImageToLayer("L3", new ArrayList<>(1),
          10, 10);
      fail("Layer doesn't exist");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

  }

  @Test
  public void testValidSetFilterNormal() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.setFilter("L1", "normal");

    List<List<IPixel>> pixelsOnLayer = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();
    assertEquals("normal", this.collage1.getFiltersOnProject().get("L1"));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255, pixelsOnLayer.get(i).get(j).getRedComponent());
        assertEquals(255, pixelsOnLayer.get(i).get(j).getGreenComponent());
        assertEquals(255, pixelsOnLayer.get(i).get(j).getBlueComponent());
        assertEquals(0, pixelsOnLayer.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidSetFilterRedComponent() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.setFilter("L1", "red-component");

    List<List<IPixel>> pixelsOnLayer = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    assertEquals("red-component", this.collage1.getFiltersOnProject().get("L1"));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255, pixelsOnLayer.get(i).get(j).getRedComponent());
        assertEquals(255, pixelsOnLayer.get(i).get(j).getGreenComponent());
        assertEquals(255, pixelsOnLayer.get(i).get(j).getBlueComponent());
        assertEquals(0, pixelsOnLayer.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidSetFilterGreenComponent() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.setFilter("L1", "green-component");

    List<List<IPixel>> pixelsOnLayer = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    assertEquals("green-component", this.collage1.getFiltersOnProject().get("L1"));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255, pixelsOnLayer.get(i).get(j).getRedComponent());
        assertEquals(255, pixelsOnLayer.get(i).get(j).getGreenComponent());
        assertEquals(255, pixelsOnLayer.get(i).get(j).getBlueComponent());
        assertEquals(0, pixelsOnLayer.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidSetFilterBlueComponent() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.setFilter("L1", "blue-component");

    List<List<IPixel>> pixelsOnLayer = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();
    assertEquals("blue-component", this.collage1.getFiltersOnProject().get("L1"));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255, pixelsOnLayer.get(i).get(j).getRedComponent());
        assertEquals(255, pixelsOnLayer.get(i).get(j).getGreenComponent());
        assertEquals(255, pixelsOnLayer.get(i).get(j).getBlueComponent());
        assertEquals(0, pixelsOnLayer.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidSetFilterBrightenValue() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", this.getImageFromTakoPPM(), 0, 0);

    List<List<IPixel>> pixelsOnLayerBefore = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    for (int i = 0; i < 2; i ++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, pixelsOnLayerBefore.get(i).get(j).getRedComponent());
        assertEquals(179, pixelsOnLayerBefore.get(i).get(j).getGreenComponent());
        assertEquals(151, pixelsOnLayerBefore.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerBefore.get(i).get(j).getAlphaComponent());
      }
    }

    this.collage1.setFilter("L1", "brighten-value");
    List<List<IPixel>> pixelsOnLayerAfter = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    for (int i = 0; i < 2; i ++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, pixelsOnLayerAfter.get(i).get(j).getRedComponent());
        assertEquals(179, pixelsOnLayerAfter.get(i).get(j).getGreenComponent());
        assertEquals(151, pixelsOnLayerAfter.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidSetFilterBrightenLuma() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", this.getImageFromTakoPPM(), 0, 0);

    List<List<IPixel>> pixelsOnLayerBefore = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    for (int i = 0; i < 2; i ++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, pixelsOnLayerBefore.get(i).get(j).getRedComponent());
        assertEquals(179, pixelsOnLayerBefore.get(i).get(j).getGreenComponent());
        assertEquals(151, pixelsOnLayerBefore.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerBefore.get(i).get(j).getAlphaComponent());
      }
    }

    this.collage1.setFilter("L1", "brighten-luma");
    List<List<IPixel>> pixelsOnLayerAfter = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    assertEquals("brighten-luma", this.collage1.getFiltersOnProject().get("L1"));
    for (int i = 0; i < 2; i ++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, pixelsOnLayerAfter.get(i).get(j).getRedComponent());
        assertEquals(179, pixelsOnLayerAfter.get(i).get(j).getGreenComponent());
        assertEquals(151, pixelsOnLayerAfter.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidSetFilterBrightenIntensity() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", this.getImageFromTakoPPM(), 0, 0);

    List<List<IPixel>> pixelsOnLayerBefore = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    for (int i = 0; i < 2; i ++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, pixelsOnLayerBefore.get(i).get(j).getRedComponent());
        assertEquals(179, pixelsOnLayerBefore.get(i).get(j).getGreenComponent());
        assertEquals(151, pixelsOnLayerBefore.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerBefore.get(i).get(j).getAlphaComponent());
      }
    }

    this.collage1.setFilter("L1", "brighten-intensity");
    List<List<IPixel>> pixelsOnLayerAfter = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    assertEquals("brighten-intensity", this.collage1.getFiltersOnProject().get("L1"));
    for (int i = 0; i < 2; i ++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, pixelsOnLayerAfter.get(i).get(j).getRedComponent());
        assertEquals(179, pixelsOnLayerAfter.get(i).get(j).getGreenComponent());
        assertEquals(151, pixelsOnLayerAfter.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidSetFilterDarkenValue() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", this.getImageFromTakoPPM(), 0, 0);

    List<List<IPixel>> pixelsOnLayerBefore = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    for (int i = 0; i < 2; i ++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, pixelsOnLayerBefore.get(i).get(j).getRedComponent());
        assertEquals(179, pixelsOnLayerBefore.get(i).get(j).getGreenComponent());
        assertEquals(151, pixelsOnLayerBefore.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerBefore.get(i).get(j).getAlphaComponent());
      }
    }

    this.collage1.setFilter("L1", "darken-value");
    List<List<IPixel>> pixelsOnLayerAfter = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    for (int i = 0; i < 2; i ++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, pixelsOnLayerAfter.get(i).get(j).getRedComponent());
        assertEquals(179, pixelsOnLayerAfter.get(i).get(j).getGreenComponent());
        assertEquals(151, pixelsOnLayerAfter.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidSetFilterDarkenLuma() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", this.getImageFromTakoPPM(), 0, 0);

    List<List<IPixel>> pixelsOnLayerBefore = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    for (int i = 0; i < 2; i ++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, pixelsOnLayerBefore.get(i).get(j).getRedComponent());
        assertEquals(179, pixelsOnLayerBefore.get(i).get(j).getGreenComponent());
        assertEquals(151, pixelsOnLayerBefore.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerBefore.get(i).get(j).getAlphaComponent());
      }
    }

    this.collage1.setFilter("L1", "darken-luma");
    List<List<IPixel>> pixelsOnLayerAfter = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    assertEquals("darken-luma", this.collage1.getFiltersOnProject().get("L1"));
    for (int i = 0; i < 2; i ++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, pixelsOnLayerAfter.get(i).get(j).getRedComponent());
        assertEquals(179, pixelsOnLayerAfter.get(i).get(j).getGreenComponent());
        assertEquals(151, pixelsOnLayerAfter.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getAlphaComponent());
      }
    }

  }

  @Test
  public void testValidSetFilterDarkenIntensity() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", this.getImageFromTakoPPM(), 0, 0);

    List<List<IPixel>> pixelsOnLayerBefore = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    for (int i = 0; i < 2; i ++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, pixelsOnLayerBefore.get(i).get(j).getRedComponent());
        assertEquals(179, pixelsOnLayerBefore.get(i).get(j).getGreenComponent());
        assertEquals(151, pixelsOnLayerBefore.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerBefore.get(i).get(j).getAlphaComponent());
      }
    }

    this.collage1.setFilter("L1", "darken-intensity");
    List<List<IPixel>> pixelsOnLayerAfter = this.collage1.getLayers().get(1)
        .getPixelsOnLayer();

    assertEquals("darken-intensity", this.collage1.getFiltersOnProject().get("L1"));
    for (int i = 0; i < 2; i ++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, pixelsOnLayerAfter.get(i).get(j).getRedComponent());
        assertEquals(179, pixelsOnLayerAfter.get(i).get(j).getGreenComponent());
        assertEquals(151, pixelsOnLayerAfter.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testInvalidSetFilter() {

    this.collage1 = new CollageProjectModelImpl();
    try {
      this.collage1.setFilter("L1", "Brighten-Luma");
      fail("Project hasn't been made yet");
    } catch (IllegalStateException e) {
      //do thing
    }

    this.init();
    this.collage1.newProject("C1", 1, 1);

    try {
      this.collage1.setFilter(null, "brighten-intensity");
      fail("Arguments can't be null");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    this.collage1.addLayer("L1");

    try {
      this.collage1.setFilter("L1", null);
      fail("Arguments can't be null");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.collage1.setFilter("L2", "brighten-intensity");
      fail("Layer doesn't exist");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.collage1.setFilter("L1", "a");
      fail("Filter doesn't exist");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }

  @Test
  public void testGetLayers() {
    this.init();
    this.collage1.newProject("C1", 1, 1);
    List<ILayer> list = this.collage1.getLayers();
    assertEquals(new Layer("Background", 1, 1, 0).getName(),
        list.get(0).getName());

    this.collage1.addLayer("L1");
    list = this.collage1.getLayers();
    assertEquals(new Layer("Background", 1, 1, 0).getName(),
        list.get(0).getName());
    assertEquals(new Layer("L1", 1, 1, 0).getName(),
        list.get(1).getName());
  }

  @Test
  public void testInvalidGetLayers() {
    this.collage1 = new CollageProjectModelImpl();
    try {
      this.collage1.getLayers();
      fail("project hasn't been made yet");
    } catch (IllegalStateException e) {
      //do nothing
    }
  }

  @Test
  public void testValidGetProjectNames() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    assertEquals("C1", this.collage1.getProjectName());

    this.collage2.newProject("C2", 2, 2);
    assertEquals("C2", this.collage2.getProjectName());
  }

  @Test
  public void testInvalidGetProjectNames() {
    this.collage1 = new CollageProjectModelImpl();
    try {
      this.collage1.getProjectName();
      fail("project hasn't been made yet");
    } catch (IllegalStateException e) {
      //do nothing
    }
  }

  @Test
  public void testValidGetHeight() {
    this.init();
    this.collage1.newProject("C1", 2, 3);
    assertEquals(2, this.collage1.getHeight());

    this.collage2.newProject("C2", 4, 5);
    assertEquals(4, this.collage2.getHeight());
  }

  @Test
  public void testInvalidGetHeight() {
    this.collage1 = new CollageProjectModelImpl();
    try {
      this.collage1.getHeight();
      fail("project hasn't been made yet");
    } catch (IllegalStateException e) {
      //do nothing
    }
  }

  @Test
  public void testValidGetWidth() {
    this.init();
    this.collage1.newProject("C1", 2, 3);
    assertEquals(3, this.collage1.getWidth());

    this.collage2.newProject("C2", 4, 5);
    assertEquals(5, this.collage2.getWidth());
  }

  @Test
  public void testInvalidGetWidth() {
    this.collage1 = new CollageProjectModelImpl();
    try {
      this.collage1.getWidth();
      fail("project hasn't been made yet");
    } catch (IllegalStateException e) {
      //do nothing
    }
  }

  @Test
  public void testValidGetMaxValue() {
    this.init();
    this.collage1.newProject("C1", 2, 3);
    assertEquals(255, this.collage1.getMaxValue());

    this.collage2.newProject("C2", 4, 5);
    assertEquals(255, this.collage2.getMaxValue());
  }

  @Test
  public void testInvalidGetMaxValue() {
    this.collage1 = new CollageProjectModelImpl();
    try {
      this.collage1.getMaxValue();
      fail("project hasn't been made yet");
    } catch (IllegalStateException e) {
      //do nothing
    }
  }

  @Test
  public void testValidGetFiltersOnProject() {
    this.init();
    this.collage1.newProject("C1", 2, 3);
    assertEquals("normal", this.collage1.getFiltersOnProject().get("Background"));

    this.collage2.newProject("C2", 4, 5);
    assertEquals("normal", this.collage2.getFiltersOnProject().get("Background"));
  }

  @Test
  public void testInvalidGetFiltersOnProject() {
    this.collage1 = new CollageProjectModelImpl();
    try {
      this.collage1.getFiltersOnProject();
      fail("project hasn't been made yet");
    } catch (IllegalStateException e) {
      //do nothing
    }
  }

  @Test
  public void testSaveBackground() {
    this.init();
    ILayer layer = this.collage1.makeFinalImage(false);

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255, layer.getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(255, layer.getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(255, layer.getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(255, layer.getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testMakeFinalImageAfterAddingALayer() {
    this.init();
    this.collage1.addLayer("L1");
    ILayer layer = this.collage1.makeFinalImage(false);

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255, layer.getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(255, layer.getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(255, layer.getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(255, layer.getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testMakeFinalImageWithNoChanges() {
    this.init();
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", this.getImageFromTakoPPM(), 0, 0);
    ILayer layer = this.collage1.makeFinalImage(false);

    List<List<IPixel>> pixelsOnLayer = layer.getPixelsOnLayer();

    assertEquals(173, pixelsOnLayer.get(0).get(0).getRedComponent());
    assertEquals(179, pixelsOnLayer.get(0).get(0).getGreenComponent());
    assertEquals(151, pixelsOnLayer.get(0).get(0).getBlueComponent());

    assertEquals(173, pixelsOnLayer.get(0).get(1).getRedComponent());
    assertEquals(179, pixelsOnLayer.get(0).get(1).getGreenComponent());
    assertEquals(151, pixelsOnLayer.get(0).get(1).getBlueComponent());

    assertEquals(173, pixelsOnLayer.get(1).get(0).getRedComponent());
    assertEquals(179, pixelsOnLayer.get(1).get(0).getGreenComponent());
    assertEquals(151, pixelsOnLayer.get(1).get(0).getBlueComponent());

    assertEquals(173, pixelsOnLayer.get(1).get(1).getRedComponent());
    assertEquals(179, pixelsOnLayer.get(1).get(1).getGreenComponent());
    assertEquals(151, pixelsOnLayer.get(1).get(1).getBlueComponent());
  }

  @Test
  public void testMakeFinalImageWithChanges() {
    this.init();
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", this.getImageFromTakoPPM(), 0, 0);
    this.collage1.setFilter("L1", "darken-intensity");
    ILayer layer = this.collage1.makeFinalImage(false);

    List<List<IPixel>> pixelsOnLayer = layer.getPixelsOnLayer();

    assertEquals(6, pixelsOnLayer.get(0).get(0).getRedComponent());
    assertEquals(12, pixelsOnLayer.get(0).get(0).getGreenComponent());
    assertEquals(0, pixelsOnLayer.get(0).get(0).getBlueComponent());

    assertEquals(6, pixelsOnLayer.get(0).get(1).getRedComponent());
    assertEquals(12, pixelsOnLayer.get(0).get(1).getGreenComponent());
    assertEquals(0, pixelsOnLayer.get(0).get(1).getBlueComponent());

    assertEquals(6, pixelsOnLayer.get(1).get(0).getRedComponent());
    assertEquals(12, pixelsOnLayer.get(1).get(0).getGreenComponent());
    assertEquals(0, pixelsOnLayer.get(1).get(0).getBlueComponent());

    assertEquals(6, pixelsOnLayer.get(1).get(1).getRedComponent());
    assertEquals(12, pixelsOnLayer.get(1).get(1).getGreenComponent());
    assertEquals(0, pixelsOnLayer.get(1).get(1).getBlueComponent());
  }

  /**
   * Returns a 2d Array of Pixels from tako.ppm.
   * @return a 2d Array of Pixels from tako.ppm
   */
  private List<List<IPixel>> getImageFromTakoPPM() {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("src/tako.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found!");
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
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
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

        pixelsOnImage.get(i).add(new Pixel(r, g, b));

      }
    }

    return pixelsOnImage;
  }
}
