import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import model.CollageProject;
import model.Layer;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LayerTest {


  Layer layer1;
  Layer layer2;
  Layer layer3;
  Layer layer4;

  Pixel pixel1;
  Pixel pixel2;
  Pixel pixel3;

  Pixel pixel4;
  Pixel pixel5;
  Pixel pixel6;



  @Before
  public void init() {
    this.layer1 = new Layer("L1",10,20,255);
    this.layer2 = new Layer("L2",10,15,0);
    this.layer3 = new Layer("L3",10,10,0);
    this.layer4 = new Layer("L4", 2, 2, 255);
    this.pixel1 = new Pixel(0,0,0, 1);
    this.pixel2 = new Pixel(120,72,99);
    this.pixel3 = new Pixel(21,50,68,100);
    this.pixel5 = new Pixel(1, 1, 1);
    this.pixel6 = new Pixel(33, 55, 99);
  }

  @Test
  public void testInvalidConstructor() {
    try {
      this.layer1 = new Layer(null,10,20,255);
      fail("String can't be null");
    } catch (IllegalArgumentException e) {
      //do nothing
    }

    try {
      this.layer1 = new Layer("L1",-1,20,255);
      fail("Integer can't be negative");
    } catch (IllegalArgumentException e) {
      //do nothing
    }

    try {
      this.layer1 = new Layer("L1",10,-1,255);
      fail("Integer can't be negative");
    } catch (IllegalArgumentException e) {
      //do nothing
    }

    try {
      this.layer1 = new Layer("L1",10,20,-1);
      fail("Integer can't be negative");
    } catch (IllegalArgumentException e) {
      //do nothing
    }

  }

  @Test
  public void testLayerName() {
    assertEquals("L1",layer1.getName());
    assertEquals("L2",layer2.getName());
    assertEquals("L3",layer3.getName());
  }

  @Test
  public void testInvalidAddImage() {
    try {
      this.layer1.addImage(-1, 0, new ArrayList<>(3));
    } catch (IllegalArgumentException e) {
      //do nothing
    }

    try {
      this.layer1.addImage(0, -1, new ArrayList<>(3));
    } catch (IllegalArgumentException e) {
      //do nothing
    }

    try {
      this.layer1.addImage(0, 0, null);
    } catch (IllegalArgumentException e) {
      //do nothing
    }
  }

  @Test
  public void testAddImage() {
    ArrayList<ArrayList<Pixel>> pixels = new ArrayList<>();
    Pixel pixel = new Pixel(5, 5, 5);

    for (int i = 0; i < 2; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        pixels.get(i).add(pixel);
      }
    }

    this.layer4.addImage(0, 0, pixels);

    for (ArrayList<Pixel> list : this.layer4.getPixelsOnLayer()) {
      for (Pixel p : list) {
        assertEquals(p.getRedComponent(), pixel.getRedComponent());
        assertEquals(p.getGreenComponent(), pixel.getGreenComponent());
        assertEquals(p.getBlueComponent(), pixel.getBlueComponent());
        assertEquals(p.getAlphaComponent(), pixel.getAlphaComponent());
      }
    }
  }

  @Test
  public void testGetPixelsOnALayer() {
    this.init();
    ArrayList<ArrayList<Pixel>> whiteLayer = this.layer1.getPixelsOnLayer();
    Pixel whiteBackgroundPixel = new Pixel(255, 255, 255, 255);


    for (int i = 0; i < whiteLayer.size(); i++) {
      for (int j = 0; j < whiteLayer.get(0).size(); j ++) {
        int redComponent = whiteLayer.get(i).get(j).getRedComponent();
        int greenComponent = whiteLayer.get(i).get(j).getGreenComponent();
        int blueComponent = whiteLayer.get(i).get(j).getBlueComponent();
        int alphaComponent = whiteLayer.get(i).get(j).getAlphaComponent();

        assertEquals(redComponent, whiteBackgroundPixel.getRedComponent());
        assertEquals(greenComponent, whiteBackgroundPixel.getGreenComponent());
        assertEquals(blueComponent, whiteBackgroundPixel.getBlueComponent());
        assertEquals(alphaComponent, whiteBackgroundPixel.getAlphaComponent());
      }
    }

    ArrayList<ArrayList<Pixel>> blankLayer = this.layer2.getPixelsOnLayer();
    Pixel whiteBlankPixel = new Pixel(255, 255, 255, 0);

    for (int i = 0; i < blankLayer.size(); i++) {
      for (int j = 0; j < blankLayer.get(0).size(); j ++) {
        int redComponent = blankLayer.get(i).get(j).getRedComponent();
        int greenComponent = blankLayer.get(i).get(j).getGreenComponent();
        int blueComponent = blankLayer.get(i).get(j).getBlueComponent();
        int alphaComponent = blankLayer.get(i).get(j).getAlphaComponent();

        assertEquals(redComponent, whiteBlankPixel.getRedComponent());
        assertEquals(greenComponent, whiteBlankPixel.getGreenComponent());
        assertEquals(blueComponent, whiteBlankPixel.getBlueComponent());
        assertEquals(alphaComponent, whiteBlankPixel.getAlphaComponent());
      }
    }

  }


  @Test
  public void testBrightenLayerByIntensity() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(this.pixel3);

    for (int i = 0; i < currentLayer.size(); i++) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        currentLayer.get(i).get(j).modifyComponentByBrightness("brighten-intensity", true);

        int newRed = currentLayer.get(i).get(j).getRedComponent();
        int newGreen = currentLayer.get(i).get(j).getGreenComponent();
        int newBlue = currentLayer.get(i).get(j).getBlueComponent();
        int newAlpha = currentLayer.get(i).get(j).getAlphaComponent();


        assertEquals(newRed, this.pixel3.getRedComponent());
        assertEquals(newGreen, this.pixel3.getGreenComponent());
        assertEquals(newBlue, this.pixel3.getBlueComponent());
        assertEquals(newAlpha, this.pixel3.getAlphaComponent());

        assertEquals(67,this.pixel3.getRedComponent());
        assertEquals(96,this.pixel3.getGreenComponent());
        assertEquals(114,this.pixel3.getBlueComponent());
        assertEquals(100, this.pixel3.getAlphaComponent());

      }
    }
  }

  @Test
  public void testBrightenLayerByValue() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(this.pixel3);

    for (int i = 0; i < currentLayer.size(); i++) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        currentLayer.get(i).get(j).modifyComponentByBrightness("brighten-value", true);

        int newRed = currentLayer.get(i).get(j).getRedComponent();
        int newGreen = currentLayer.get(i).get(j).getGreenComponent();
        int newBlue = currentLayer.get(i).get(j).getBlueComponent();
        int newAlpha = currentLayer.get(i).get(j).getAlphaComponent();


        assertEquals(newRed, this.pixel3.getRedComponent());
        assertEquals(newGreen, this.pixel3.getGreenComponent());
        assertEquals(newBlue, this.pixel3.getBlueComponent());
        assertEquals(newAlpha, this.pixel3.getAlphaComponent());

        assertEquals(89,this.pixel3.getRedComponent());
        assertEquals(118,this.pixel3.getGreenComponent());
        assertEquals(136,this.pixel3.getBlueComponent());
        assertEquals(100, this.pixel3.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBrightenLayerByLuma() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(this.pixel3);

    for (int i = 0; i < currentLayer.size(); i++) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        currentLayer.get(i).get(j).modifyComponentByBrightness("brighten-luma", true);

        int newRed = currentLayer.get(i).get(j).getRedComponent();
        int newGreen = currentLayer.get(i).get(j).getGreenComponent();
        int newBlue = currentLayer.get(i).get(j).getBlueComponent();
        int newAlpha = currentLayer.get(i).get(j).getAlphaComponent();


        assertEquals(newRed, this.pixel3.getRedComponent());
        assertEquals(newGreen, this.pixel3.getGreenComponent());
        assertEquals(newBlue, this.pixel3.getBlueComponent());
        assertEquals(newAlpha, this.pixel3.getAlphaComponent());

        assertEquals(66,this.pixel3.getRedComponent());
        assertEquals(95,this.pixel3.getGreenComponent());
        assertEquals(113,this.pixel3.getBlueComponent());
        assertEquals(100, this.pixel3.getAlphaComponent());
      }
    }
  }

  @Test
  public void testDarkenByValue() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(this.pixel5);



    for (int i = 0; i < currentLayer.size(); i++) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        currentLayer.get(i).get(j).modifyComponentByBrightness("darken-value", false);

        int newRed = currentLayer.get(i).get(j).getRedComponent();
        int newGreen = currentLayer.get(i).get(j).getGreenComponent();
        int newBlue = currentLayer.get(i).get(j).getBlueComponent();
        int newAlpha = currentLayer.get(i).get(j).getAlphaComponent();

        assertEquals(newRed, this.pixel5.getRedComponent());
        assertEquals(newGreen, this.pixel5.getGreenComponent());
        assertEquals(newBlue, this.pixel5.getBlueComponent());
        assertEquals(newAlpha, this.pixel5.getAlphaComponent());
      }
    }
  }

  @Test
  public void testDarkenLayerByIntensity() {
    this.init();
    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(this.pixel5);

    for (ArrayList<Pixel> pixels : currentLayer) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        pixels.get(j).modifyComponentByBrightness("darken-intensity", false);

        int newRed = pixels.get(j).getRedComponent();
        int newGreen = pixels.get(j).getGreenComponent();
        int newBlue = pixels.get(j).getBlueComponent();
        int newAlpha = pixels.get(j).getAlphaComponent();

        assertEquals(newRed, this.pixel5.getRedComponent());
        assertEquals(newGreen, this.pixel5.getGreenComponent());
        assertEquals(newBlue, this.pixel5.getBlueComponent());
        assertEquals(newAlpha, this.pixel5.getAlphaComponent());

        assertEquals(0, this.pixel5.getRedComponent());
        assertEquals(0, this.pixel5.getGreenComponent());
        assertEquals(0, this.pixel5.getBlueComponent());

      }
    }
  }

  @Test
  public void testDarkenByLuma() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();

    currentLayer.add(lop);
    lop.add(this.pixel2);

    for (ArrayList<Pixel> pixels : currentLayer) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        pixels.get(j).modifyComponentByBrightness("darken-luma", false);

        int newRed = pixels.get(j).getRedComponent();
        int newGreen = pixels.get(j).getGreenComponent();
        int newBlue = pixels.get(j).getBlueComponent();
        int newAlpha = pixels.get(j).getAlphaComponent();

        assertEquals(newRed, this.pixel2.getRedComponent());
        assertEquals(newGreen, this.pixel2.getGreenComponent());
        assertEquals(newBlue, this.pixel2.getBlueComponent());
        assertEquals(newAlpha, this.pixel2.getAlphaComponent());

        assertEquals(35, this.pixel2.getRedComponent());
        assertEquals(0, this.pixel2.getGreenComponent());
        assertEquals(14, this.pixel2.getBlueComponent());

      }
    }
  }


}
