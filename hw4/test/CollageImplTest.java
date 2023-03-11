import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import model.ImageUtil;
import org.junit.Test;

import model.CollageProject;
import model.CollageProjectModelImpl;
import model.Layer;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CollageImplTest {
  Pixel pixel1;
  Pixel pixel2;
  Pixel pixel3;
  Pixel pixel4;
  Pixel pixel5;
  Layer layer1;
  Layer layer2;
  Layer layer3;
  CollageProject collage1;
  CollageProject collage2;
  CollageProject collage3;
  CollageProject collage4;


  public void init() {
    this.pixel1 = new Pixel(0, 0, 0, 1);
    this.pixel2 = new Pixel(120, 72, 99);
    this.pixel3 = new Pixel(21, 50, 68, 100);
    this.pixel4 = new Pixel(12, 11, 10);
    this.pixel5 = new Pixel(1, 1, 1);

    this.layer1 = new Layer("L1", 10, 10, 0);
    this.layer2 = new Layer("L2", 10, 10, 0);
    this.layer3 = new Layer("L3", 10, 10, 0);

    this.collage1 = new CollageProjectModelImpl();
    this.collage2 = new CollageProjectModelImpl();
    this.collage3 = new CollageProjectModelImpl();
    this.collage4 = new CollageProjectModelImpl();

    this.collage1.newProject("C1", 2, 2);
    this.collage1.saveProject("src/saveProjectAndLoadImmediately", "PPM");
  }

  @Test
  public void testValidCreateNewProject() {
    this.init();
    this.collage1.newProject("C1", 100, 100);
    this.collage2.newProject("C2", 20, 20);
    this.collage3.newProject("C3", 10, 10);
    this.collage4.newProject("C4", 2, 2);
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
    } catch (IllegalStateException e) {
      //do nothing
    }
  }


  @Test
  public void testAddLayerToProject() {
    this.init();

    this.collage1.newProject("C1", 5, 5);

    this.collage1.addLayer("L2");
    this.collage1.addLayer("L3");
    this.collage1.addLayer("L1");

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
    this.collage1.addImageToLayer("L1", "src/tako.ppm", 0, 0);

    ArrayList<ArrayList<Pixel>> pixelsOnLayers = new ImageUtil().readPPM("src/tako.ppm");

    for (int i = 0; i < 100; i++) {
      for (int j = 0; j < 100; j++) {
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
  }

  @Test
  public void testInvalidAddImageToLayer() {
    this.collage1 = new CollageProjectModelImpl();
    try {
      this.collage1.addImageToLayer("L1", "src/tako.ppm",0,0);
      fail("Didn't start a new project yet");
    } catch (IllegalStateException e) {
      //do nothing
    }

    this.init();

    try {
      this.collage4.newProject("C4",100,100);
      this.collage4.addImageToLayer(null,"src/tako.ppm",0,0);
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
      this.collage3.addImageToLayer(layer1.getName(),"src/tako.ppm",-200,0);
      fail("arguments are invalid");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.collage4 = new CollageProjectModelImpl();
      this.collage4.newProject("C4",100,100);
      this.collage4.addImageToLayer(layer1.getName(),"src/tako.ppm",0,120);
      fail("arguments are invalid");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.collage1.newProject("C1",100,100);
      this.collage1.addImageToLayer("L3", "src/tako.ppm", 10, 10);
      fail("Layer doesn't exist");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

  }

  @Test
  public void testSaveProjectImmediately() {
    this.init();

    this.collage1.newProject("C1", 100, 100);
    this.collage1.saveProject("src/saveImmediately", "PPM");

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("src/saveImmediately"));
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

    assertEquals("C1", sc.next());
    assertEquals("100", sc.next());
    assertEquals("100", sc.next());
    assertEquals("255", sc.next());
    assertEquals("Background", sc.next());
    assertEquals("normal", sc.next());

    while(sc.hasNext()) {
      assertEquals("255", sc.next());
      assertEquals("255", sc.next());
      assertEquals("255", sc.next());
      assertEquals("255", sc.next());
    }

  }

  @Test
  public void testSaveProjectWithOneLayer() {
    this.init();

    this.collage1.newProject("C1", 200, 200);
    this.collage1.addLayer("L1");
    this.collage1.saveProject("src/saveOneLayer", "PPM");

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("src/saveOneLayer"));
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

    assertEquals("C1", sc.next());
    assertEquals("200", sc.next());
    assertEquals("200", sc.next());
    assertEquals("255", sc.next());
    assertEquals("Background", sc.next());
    assertEquals("normal", sc.next());

    for (int i = 0; i < 200; i++) {
      for (int j = 0; j < 200; j++) {
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
      }
    }

    assertEquals("L1", sc.next());
    assertEquals("normal", sc.next());

    while(sc.hasNext()) {
      assertEquals("255", sc.next());
      assertEquals("255", sc.next());
      assertEquals("255", sc.next());
      assertEquals("0", sc.next());
    }
  }

  @Test
  public void testSaveProjectAfterModification() {
    this.init();

    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", "src/tako.ppm", 0, 0);
    this.collage1.saveProject("src/saveAfterModification", "PPM");

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("src/saveAfterModification"));
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

    assertEquals("C1", sc.next());
    assertEquals("2", sc.next());
    assertEquals("2", sc.next());
    assertEquals("255", sc.next());
    assertEquals("Background", sc.next());
    assertEquals("normal", sc.next());

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
      }
    }

    assertEquals("L1", sc.next());
    assertEquals("normal", sc.next());

    assertEquals("255", sc.next());
    assertEquals("173", sc.next());
    assertEquals("179", sc.next());
    assertEquals("255", sc.next());

    while(sc.hasNext()) {
      assertEquals("151", sc.next());
      assertEquals("173", sc.next());
      assertEquals("179", sc.next());
      assertEquals("255", sc.next());
    }

    this.collage1.setFilter("L1", "darken-intensity");
    this.collage1.saveProject("src/saveAfterModification", "PPM");

    try {
      sc = new Scanner(new FileInputStream("src/saveAfterModification"));
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

    assertEquals("C1", sc.next());
    assertEquals("2", sc.next());
    assertEquals("2", sc.next());
    assertEquals("255", sc.next());
    assertEquals("Background", sc.next());
    assertEquals("normal", sc.next());

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
      }
    }

    assertEquals("L1", sc.next());
    assertEquals("darken-intensity", sc.next());

    assertEquals("53", sc.next());
    assertEquals("0", sc.next());
    assertEquals("0", sc.next());
    assertEquals("255", sc.next());

    while(sc.hasNext()) {
      assertEquals("0", sc.next());
      assertEquals("6", sc.next());
      assertEquals("12", sc.next());
      assertEquals("255", sc.next());
    }

  }

  @Test
  public void testInvalidSaveProjects() {
    this.collage1 = new CollageProjectModelImpl();
    try {
      this.collage1.saveProject("src/new", "ppm");
      fail("The project hasn't been made yet");
    } catch (IllegalStateException e) {
      // do nothing
    }

    this.init();

    this.collage1.newProject("C1", 1, 1);

    try {
      this.collage1.saveProject(null, "ppm");
      fail("Arguments can't be null");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.collage1.setFilter("src/fileName", null);
      fail("Arguments can't be null");
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

    ArrayList<ArrayList<Pixel>> pixelsOnLayer = this.collage1.getLayers().get(1).getPixelsOnLayer();

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

    ArrayList<ArrayList<Pixel>> pixelsOnLayer = this.collage1.getLayers().get(1).getPixelsOnLayer();

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255, pixelsOnLayer.get(i).get(j).getRedComponent());
        assertEquals(0, pixelsOnLayer.get(i).get(j).getGreenComponent());
        assertEquals(0, pixelsOnLayer.get(i).get(j).getBlueComponent());
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

    ArrayList<ArrayList<Pixel>> pixelsOnLayer = this.collage1.getLayers().get(1).getPixelsOnLayer();

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, pixelsOnLayer.get(i).get(j).getRedComponent());
        assertEquals(255, pixelsOnLayer.get(i).get(j).getGreenComponent());
        assertEquals(0, pixelsOnLayer.get(i).get(j).getBlueComponent());
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

    ArrayList<ArrayList<Pixel>> pixelsOnLayer = this.collage1.getLayers().get(1).getPixelsOnLayer();

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, pixelsOnLayer.get(i).get(j).getRedComponent());
        assertEquals(0, pixelsOnLayer.get(i).get(j).getGreenComponent());
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
    this.collage1.addImageToLayer("L1", "src/tako.ppm", 0, 0);

    ArrayList<ArrayList<Pixel>> pixelsOnLayerBefore = this.collage1.getLayers().get(1).getPixelsOnLayer();

    assertEquals(255, pixelsOnLayerBefore.get(0).get(0).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(0).get(0).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(0).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(0).get(0).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(0).get(1).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(0).get(1).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(0).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(0).get(1).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(1).get(0).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(1).get(0).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(1).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(1).get(0).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(1).get(1).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(1).get(1).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(1).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(1).get(1).getAlphaComponent());

    this.collage1.setFilter("L1", "brighten-value");
    ArrayList<ArrayList<Pixel>> pixelsOnLayerAfter = this.collage1.getLayers().get(1).getPixelsOnLayer();


    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getRedComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getGreenComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidSetFilterBrightenLuma() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", "src/tako.ppm", 0, 0);

    ArrayList<ArrayList<Pixel>> pixelsOnLayerBefore = this.collage1.getLayers().get(1).getPixelsOnLayer();

    assertEquals(255, pixelsOnLayerBefore.get(0).get(0).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(0).get(0).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(0).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(0).get(0).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(0).get(1).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(0).get(1).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(0).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(0).get(1).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(1).get(0).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(1).get(0).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(1).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(1).get(0).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(1).get(1).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(1).get(1).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(1).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(1).get(1).getAlphaComponent());

    this.collage1.setFilter("L1", "brighten-luma");
    ArrayList<ArrayList<Pixel>> pixelsOnLayerAfter = this.collage1.getLayers().get(1).getPixelsOnLayer();


    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getRedComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getGreenComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidSetFilterBrightenIntensity() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", "src/tako.ppm", 0, 0);

    ArrayList<ArrayList<Pixel>> pixelsOnLayerBefore = this.collage1.getLayers().get(1).getPixelsOnLayer();

    assertEquals(255, pixelsOnLayerBefore.get(0).get(0).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(0).get(0).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(0).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(0).get(0).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(0).get(1).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(0).get(1).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(0).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(0).get(1).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(1).get(0).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(1).get(0).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(1).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(1).get(0).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(1).get(1).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(1).get(1).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(1).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(1).get(1).getAlphaComponent());

    this.collage1.setFilter("L1", "brighten-intensity");
    ArrayList<ArrayList<Pixel>> pixelsOnLayerAfter = this.collage1.getLayers().get(1).getPixelsOnLayer();


    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getRedComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getGreenComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidSetFilterDarkenValue() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", "src/tako.ppm", 0, 0);

    ArrayList<ArrayList<Pixel>> pixelsOnLayerBefore = this.collage1.getLayers().get(1).getPixelsOnLayer();

    assertEquals(255, pixelsOnLayerBefore.get(0).get(0).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(0).get(0).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(0).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(0).get(0).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(0).get(1).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(0).get(1).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(0).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(0).get(1).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(1).get(0).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(1).get(0).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(1).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(1).get(0).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(1).get(1).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(1).get(1).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(1).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(1).get(1).getAlphaComponent());

    this.collage1.setFilter("L1", "darken-value");
    ArrayList<ArrayList<Pixel>> pixelsOnLayerAfter = this.collage1.getLayers().get(1).getPixelsOnLayer();


    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, pixelsOnLayerAfter.get(i).get(j).getRedComponent());
        assertEquals(0, pixelsOnLayerAfter.get(i).get(j).getGreenComponent());
        assertEquals(0, pixelsOnLayerAfter.get(i).get(j).getBlueComponent());
        assertEquals(255, pixelsOnLayerAfter.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidSetFilterDarkenLuma() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", "src/tako.ppm", 0, 0);

    ArrayList<ArrayList<Pixel>> pixelsOnLayerBefore = this.collage1.getLayers().get(1).getPixelsOnLayer();

    assertEquals(255, pixelsOnLayerBefore.get(0).get(0).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(0).get(0).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(0).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(0).get(0).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(0).get(1).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(0).get(1).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(0).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(0).get(1).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(1).get(0).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(1).get(0).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(1).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(1).get(0).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(1).get(1).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(1).get(1).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(1).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(1).get(1).getAlphaComponent());

    this.collage1.setFilter("L1", "darken-luma");
    ArrayList<ArrayList<Pixel>> pixelsOnLayerAfter = this.collage1.getLayers().get(1).getPixelsOnLayer();

    assertEquals(63, pixelsOnLayerAfter.get(0).get(0).getRedComponent());
    assertEquals(0, pixelsOnLayerAfter.get(0).get(0).getGreenComponent());
    assertEquals(0, pixelsOnLayerAfter.get(0).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerAfter.get(0).get(0).getAlphaComponent());

    assertEquals(0, pixelsOnLayerAfter.get(0).get(1).getRedComponent());
    assertEquals(4, pixelsOnLayerAfter.get(0).get(1).getGreenComponent());
    assertEquals(10, pixelsOnLayerAfter.get(0).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerAfter.get(0).get(1).getAlphaComponent());

    assertEquals(0, pixelsOnLayerAfter.get(1).get(0).getRedComponent());
    assertEquals(4, pixelsOnLayerAfter.get(1).get(0).getGreenComponent());
    assertEquals(10, pixelsOnLayerAfter.get(1).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerAfter.get(1).get(0).getAlphaComponent());

    assertEquals(0, pixelsOnLayerAfter.get(1).get(1).getRedComponent());
    assertEquals(4, pixelsOnLayerAfter.get(1).get(1).getGreenComponent());
    assertEquals(10, pixelsOnLayerAfter.get(1).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerAfter.get(1).get(1).getAlphaComponent());

  }

  @Test
  public void testValidSetFilterDarkenIntensity() {
    this.init();
    this.collage1.newProject("C1", 2, 2);
    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", "src/tako.ppm", 0, 0);

    ArrayList<ArrayList<Pixel>> pixelsOnLayerBefore = this.collage1.getLayers().get(1).getPixelsOnLayer();

    assertEquals(255, pixelsOnLayerBefore.get(0).get(0).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(0).get(0).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(0).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(0).get(0).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(0).get(1).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(0).get(1).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(0).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(0).get(1).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(1).get(0).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(1).get(0).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(1).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(1).get(0).getAlphaComponent());

    assertEquals(151, pixelsOnLayerBefore.get(1).get(1).getRedComponent());
    assertEquals(173, pixelsOnLayerBefore.get(1).get(1).getGreenComponent());
    assertEquals(179, pixelsOnLayerBefore.get(1).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerBefore.get(1).get(1).getAlphaComponent());

    this.collage1.setFilter("L1", "darken-intensity");
    ArrayList<ArrayList<Pixel>> pixelsOnLayerAfter = this.collage1.getLayers().get(1).getPixelsOnLayer();


    assertEquals(53, pixelsOnLayerAfter.get(0).get(0).getRedComponent());
    assertEquals(0, pixelsOnLayerAfter.get(0).get(0).getGreenComponent());
    assertEquals(0, pixelsOnLayerAfter.get(0).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerAfter.get(0).get(0).getAlphaComponent());

    assertEquals(0, pixelsOnLayerAfter.get(0).get(1).getRedComponent());
    assertEquals(6, pixelsOnLayerAfter.get(0).get(1).getGreenComponent());
    assertEquals(12, pixelsOnLayerAfter.get(0).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerAfter.get(0).get(1).getAlphaComponent());

    assertEquals(0, pixelsOnLayerAfter.get(1).get(0).getRedComponent());
    assertEquals(6, pixelsOnLayerAfter.get(1).get(0).getGreenComponent());
    assertEquals(12, pixelsOnLayerAfter.get(1).get(0).getBlueComponent());
    assertEquals(255, pixelsOnLayerAfter.get(1).get(0).getAlphaComponent());

    assertEquals(0, pixelsOnLayerAfter.get(1).get(1).getRedComponent());
    assertEquals(6, pixelsOnLayerAfter.get(1).get(1).getGreenComponent());
    assertEquals(12, pixelsOnLayerAfter.get(1).get(1).getBlueComponent());
    assertEquals(255, pixelsOnLayerAfter.get(1).get(1).getAlphaComponent());
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
    ArrayList<Layer> list = this.collage1.getLayers();
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
  public void testValidLoadProjectImmediately() {
    this.init();

    this.collage1.loadProject("src/saveProjectAndLoadImmediately");
    assertEquals("C1", this.collage1.getProjectName());
    assertEquals(2, this.collage1.getHeight());
    assertEquals(2, this.collage1.getWidth());
    assertEquals(255, this.collage1.getMaxValue());

    Layer backgroundLayer = this.collage1.getLayers().get(0);
    assertEquals("Background", backgroundLayer.getName());

    Map<String, String> layerWithFilter = this.collage1.getFiltersOnProject();
    assertEquals("normal", layerWithFilter.get("Background"));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidLoadProjectAfterAddingLayer() {
    this.init();

    this.collage1.addLayer("L1");
    this.collage1.saveProject("src/saveProjectAndLoadAfterAddingLayer", "PPM");
    this.collage1.loadProject("src/saveProjectAndLoadAfterAddingLayer");
    assertEquals("C1", this.collage1.getProjectName());
    assertEquals(2, this.collage1.getHeight());
    assertEquals(2, this.collage1.getWidth());
    assertEquals(255, this.collage1.getMaxValue());

    Layer backgroundLayer = this.collage1.getLayers().get(0);
    assertEquals("Background", backgroundLayer.getName());

    Map<String, String> layerWithFilter = this.collage1.getFiltersOnProject();
    assertEquals("normal", layerWithFilter.get("Background"));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }

    backgroundLayer = this.collage1.getLayers().get(1);
    assertEquals("L1", backgroundLayer.getName());

    layerWithFilter = this.collage1.getFiltersOnProject();
    assertEquals("normal", layerWithFilter.get("L1"));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(0,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidLoadProjectAfterAddingLayerAndModifying() {
    this.init();

    this.collage1.addLayer("L1");
    this.collage1.addImageToLayer("L1", "src/tako.ppm", 0, 0);
    this.collage1.setFilter("L1", "darken-intensity");
    this.collage1.saveProject("src/saveProjectAndLoadAfterAddingLayerAndModifying",
        "PPM");
    this.collage1.loadProject("src/saveProjectAndLoadAfterAddingLayerAndModifying");
    assertEquals("C1", this.collage1.getProjectName());
    assertEquals(2, this.collage1.getHeight());
    assertEquals(2, this.collage1.getWidth());
    assertEquals(255, this.collage1.getMaxValue());

    Layer backgroundLayer = this.collage1.getLayers().get(0);
    assertEquals("Background", backgroundLayer.getName());

    Map<String, String> layerWithFilter = this.collage1.getFiltersOnProject();
    assertEquals("normal", layerWithFilter.get("Background"));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }

    backgroundLayer = this.collage1.getLayers().get(1);
    assertEquals("L1", backgroundLayer.getName());

    layerWithFilter = this.collage1.getFiltersOnProject();
    assertEquals("darken-intensity", layerWithFilter.get("L1"));

    assertEquals(53, backgroundLayer.getPixelsOnLayer().get(0).get(0).getRedComponent());
    assertEquals(0, backgroundLayer.getPixelsOnLayer().get(0).get(0).getGreenComponent());
    assertEquals(0, backgroundLayer.getPixelsOnLayer().get(0).get(0).getBlueComponent());
    assertEquals(255, backgroundLayer.getPixelsOnLayer().get(0).get(0).getAlphaComponent());

    assertEquals(0, backgroundLayer.getPixelsOnLayer().get(0).get(1).getRedComponent());
    assertEquals(6, backgroundLayer.getPixelsOnLayer().get(0).get(1).getGreenComponent());
    assertEquals(12, backgroundLayer.getPixelsOnLayer().get(0).get(1).getBlueComponent());
    assertEquals(255,
        backgroundLayer.getPixelsOnLayer().get(0).get(1).getAlphaComponent());

    assertEquals(0, backgroundLayer.getPixelsOnLayer().get(1).get(0).getRedComponent());
    assertEquals(6, backgroundLayer.getPixelsOnLayer().get(1).get(0).getGreenComponent());
    assertEquals(12, backgroundLayer.getPixelsOnLayer().get(1).get(0).getBlueComponent());
    assertEquals(255,
        backgroundLayer.getPixelsOnLayer().get(1).get(0).getAlphaComponent());

    assertEquals(0, backgroundLayer.getPixelsOnLayer().get(1).get(1).getRedComponent());
    assertEquals(6, backgroundLayer.getPixelsOnLayer().get(1).get(1).getGreenComponent());
    assertEquals(12, backgroundLayer.getPixelsOnLayer().get(1).get(1).getBlueComponent());
    assertEquals(255,
        backgroundLayer.getPixelsOnLayer().get(1).get(1).getAlphaComponent());

  }

  @Test
  public void testInvalidLoadProject() {
    this.init();
    try {
      this.collage1.loadProject(null);
      fail("Arguments can't be null");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.collage1.loadProject("a");
      fail("File not found");
    } catch (IllegalStateException e) {
      //do nothing
    }

    try {
      FileWriter fileWriter = new FileWriter("src/nothingInside");
      fileWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    try {
      this.collage1.loadProject("src/nothingInside");
      fail("Nothing is inside the file");
    } catch (IllegalStateException e) {
      //do nothing
    }

    try {
      FileWriter fileWriter = new FileWriter("src/nothingInside");
      fileWriter.write("C1" + "\n");
      fileWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    try {
      this.collage1.loadProject("src/nothingInside");
      fail("Not enough to make a project");
    } catch (IllegalStateException e) {
      //do nothing
    }

    try {
      FileWriter fileWriter = new FileWriter("src/nothingInside");
      fileWriter.write("C1" + "\n");
      fileWriter.write("100 100" + "\n");
      fileWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    try {
      this.collage1.loadProject("src/nothingInside");
      fail("Not enough to make a project");
    } catch (IllegalStateException e) {
      //do nothing
    }

    try {
      FileWriter fileWriter = new FileWriter("src/nothingInside");
      fileWriter.write("C1" + "\n");
      fileWriter.write("a 100" + "\n");
      fileWriter.write("255" + "\n");
      fileWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    try {
      this.collage1.loadProject("src/nothingInside");
      fail("Not enough to make a project");
    } catch (IllegalStateException e) {
      //do nothing
    }

    try {
      FileWriter fileWriter = new FileWriter("src/nothingInside");
      fileWriter.write("C1" + "\n");
      fileWriter.write("100 a" + "\n");
      fileWriter.write("255" + "\n");
      fileWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    try {
      this.collage1.loadProject("src/nothingInside");
      fail("Not enough to make a project");
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

  //TODO: Need to test save image

}
