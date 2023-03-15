import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import model.Layer;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class LayerTest {


  Layer layer1;
  Layer layer2;
  Layer layer3;
  Layer layer4;
  Layer layer5;
  Layer layer6;

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
    this.layer5 = new Layer("L5", 10, 20, 255);
    this.layer6 = new Layer("L6", 2, 2, 0);

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
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();

    for (int i = 0; i < 2; i++) {
      currentLayer.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        currentLayer.get(i).add(new Pixel(5, 5, 5));
      }
    }

    this.layer4.addImage(0, 0, currentLayer);

    for (int i = 0; i < currentLayer.size(); i++) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {
        int redComponent = this.layer4.getPixelsOnLayer().get(i).get(j).getRedComponent();
        int greenComponent = this.layer4.getPixelsOnLayer().get(i).get(j).getGreenComponent();
        int blueComponent = this.layer4.getPixelsOnLayer().get(i).get(j).getBlueComponent();
        int alphaComponent = this.layer4.getPixelsOnLayer().get(i).get(j).getAlphaComponent();

        assertEquals(redComponent, currentLayer.get(i).get(j).getRedComponent());
        assertEquals(greenComponent, currentLayer.get(i).get(j).getGreenComponent());
        assertEquals(blueComponent, currentLayer.get(i).get(j).getBlueComponent());
        assertEquals(alphaComponent, currentLayer.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testGetPixelsOnALayer() {
    this.init();
    ArrayList<ArrayList<Pixel>> whiteLayer = this.layer1.getPixelsOnLayer();
    Pixel whiteBackgroundPixel = new Pixel(255, 255, 255, 255);

    for (ArrayList<Pixel> pixels : whiteLayer) {
      for (int j = 0; j < whiteLayer.get(0).size(); j++) {
        int redComponent = pixels.get(j).getRedComponent();
        int greenComponent = pixels.get(j).getGreenComponent();
        int blueComponent = pixels.get(j).getBlueComponent();
        int alphaComponent = pixels.get(j).getAlphaComponent();

        assertEquals(redComponent, whiteBackgroundPixel.getRedComponent());
        assertEquals(greenComponent, whiteBackgroundPixel.getGreenComponent());
        assertEquals(blueComponent, whiteBackgroundPixel.getBlueComponent());
        assertEquals(alphaComponent, whiteBackgroundPixel.getAlphaComponent());
      }
    }

    ArrayList<ArrayList<Pixel>> blankLayer = this.layer2.getPixelsOnLayer();
    Pixel whiteBlankPixel = new Pixel(255, 255, 255, 0);

    for (ArrayList<Pixel> pixels : blankLayer) {
      for (int j = 0; j < blankLayer.get(0).size(); j++) {
        int redComponent = pixels.get(j).getRedComponent();
        int greenComponent = pixels.get(j).getGreenComponent();
        int blueComponent = pixels.get(j).getBlueComponent();
        int alphaComponent = pixels.get(j).getAlphaComponent();

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
    lop.add(new Pixel(21,50,68,100));

    for (ArrayList<Pixel> pixels : currentLayer) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        pixels.get(j).modifyComponentByBrightness("brighten-intensity", true);

        assertEquals(67, pixels.get(j).getRedComponent());
        assertEquals(96, pixels.get(j).getGreenComponent());
        assertEquals(114, pixels.get(j).getBlueComponent());
        assertEquals(100, pixels.get(j).getAlphaComponent());

      }
    }
  }

  @Test
  public void testBrightenLayerByValue() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(new Pixel(21,50,68,100));

    for (ArrayList<Pixel> pixels : currentLayer) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        pixels.get(j).modifyComponentByBrightness("brighten-value", true);

        assertEquals(89, pixels.get(j).getRedComponent());
        assertEquals(118, pixels.get(j).getGreenComponent());
        assertEquals(136, pixels.get(j).getBlueComponent());
        assertEquals(100, pixels.get(j).getAlphaComponent());

      }
    }
  }

  @Test
  public void testBrightenLayerByLuma() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(new Pixel(21,50,68,100));

    for (ArrayList<Pixel> pixels : currentLayer) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        pixels.get(j).modifyComponentByBrightness("brighten-luma", true);

        assertEquals(66, pixels.get(j).getRedComponent());
        assertEquals(95, pixels.get(j).getGreenComponent());
        assertEquals(113, pixels.get(j).getBlueComponent());
        assertEquals(100, pixels.get(j).getAlphaComponent());

      }
    }
  }

  @Test
  public void testDarkenByValue() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(new Pixel(1, 1, 1));

    for (ArrayList<Pixel> pixels : currentLayer) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        pixels.get(j).modifyComponentByBrightness("darken-value", false);

        assertEquals(0, pixels.get(j).getRedComponent());
        assertEquals(0, pixels.get(j).getGreenComponent());
        assertEquals(0, pixels.get(j).getBlueComponent());
        assertEquals(255, pixels.get(j).getAlphaComponent());

      }
    }
  }

  @Test
  public void testDarkenLayerByIntensity() {
    this.init();
    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(new Pixel(1, 1, 1));

    for (ArrayList<Pixel> pixels : currentLayer) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        pixels.get(j).modifyComponentByBrightness("darken-intensity", false);

        assertEquals(0, pixels.get(j).getRedComponent());
        assertEquals(0, pixels.get(j).getGreenComponent());
        assertEquals(0, pixels.get(j).getBlueComponent());
        assertEquals(255, pixels.get(j).getAlphaComponent());

      }
    }
  }

  @Test
  public void testDarkenByLuma() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();

    currentLayer.add(lop);
    lop.add(new Pixel(120,72,99));

    for (ArrayList<Pixel> pixels : currentLayer) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        pixels.get(j).modifyComponentByBrightness("darken-luma", false);

        assertEquals(35, pixels.get(j).getRedComponent());
        assertEquals(0, pixels.get(j).getGreenComponent());
        assertEquals(14, pixels.get(j).getBlueComponent());
        assertEquals(255, pixels.get(j).getAlphaComponent());

      }
    }
  }

  @Test
  public void testModifyTransparencyWithWhiteBackGround() {
    this.init();

    ArrayList<ArrayList<Pixel>> modifiedList =
        this.layer1.modifyTransparency(this.layer5.getPixelsOnLayer(), true);

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();

    currentLayer.add(lop);
    lop.add(new Pixel(255, 255, 255));

    for (int i = 0; i < currentLayer.size(); i++) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        int newRed = currentLayer.get(i).get(j).getRedComponent();
        int newGreen =  currentLayer.get(i).get(j).getGreenComponent();
        int newBlue =  currentLayer.get(i).get(j).getBlueComponent();
        int newAlpha =  currentLayer.get(i).get(j).getAlphaComponent();

        assertEquals(newRed, modifiedList.get(i).get(j).getRedComponent());
        assertEquals(newGreen, modifiedList.get(i).get(j).getGreenComponent());
        assertEquals(newBlue, modifiedList.get(i).get(j).getBlueComponent());
        assertEquals(newAlpha, modifiedList.get(i).get(j).getAlphaComponent());

      }
    }

    modifiedList = this.layer1.modifyTransparency(this.layer5.getPixelsOnLayer(), false);

    lop = new ArrayList<>();
    currentLayer = new ArrayList<>();

    currentLayer.add(lop);
    lop.add(new Pixel(255, 255, 255));

    for (int i = 0; i < currentLayer.size(); i++) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        int newRed = currentLayer.get(i).get(j).getRedComponent();
        int newGreen =  currentLayer.get(i).get(j).getGreenComponent();
        int newBlue =  currentLayer.get(i).get(j).getBlueComponent();
        int newAlpha =  currentLayer.get(i).get(j).getAlphaComponent();

        assertEquals(newRed, modifiedList.get(i).get(j).getRedComponent());
        assertEquals(newGreen, modifiedList.get(i).get(j).getGreenComponent());
        assertEquals(newBlue, modifiedList.get(i).get(j).getBlueComponent());
        assertEquals(newAlpha, modifiedList.get(i).get(j).getAlphaComponent());

      }
    }
  }

  @Test
  public void testModifyTransparencyWithOpaqueImageOnTop() {
    this.init();

    ArrayList<ArrayList<Pixel>> pixels = new ArrayList<>();

    for (int i = 0; i < 2; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        pixels.get(i).add(new Pixel(5, 5, 5));
      }
    }

    this.layer4.addImage(0, 0, pixels);

    ArrayList<ArrayList<Pixel>> modifiedList =
        this.layer4.modifyTransparency(this.layer6.getPixelsOnLayer(), false);

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(5, modifiedList.get(i).get(j).getRedComponent());
        assertEquals(5, modifiedList.get(i).get(j).getGreenComponent());
        assertEquals(5, modifiedList.get(i).get(j).getBlueComponent());
        assertEquals(255, modifiedList.get(i).get(j).getAlphaComponent());

      }
    }
  }

  @Test
  public void testModifyTransparencyWithOpaqueImageOnTopAndTransparentBottom() {
    this.init();

    ArrayList<ArrayList<Pixel>> pixels1 = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      pixels1.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        pixels1.get(i).add(new Pixel(10, 10, 10, 255));
      }
    }

    this.layer4.addImage(0, 0, pixels1);

    ArrayList<ArrayList<Pixel>> pixels2 = new ArrayList<>();

    for (int i = 0; i < 2; i++) {
      pixels2.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        pixels2.get(i).add(new Pixel(10, 10, 10, 0));
      }
    }

    this.layer6.addImage(0, 0, pixels2);

    ArrayList<ArrayList<Pixel>> modifiedList =
        this.layer4.modifyTransparency(this.layer6.getPixelsOnLayer(), true);

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(10, modifiedList.get(i).get(j).getRedComponent());
        assertEquals(10, modifiedList.get(i).get(j).getGreenComponent());
        assertEquals(10, modifiedList.get(i).get(j).getBlueComponent());
        assertEquals(255, modifiedList.get(i).get(j).getAlphaComponent());

      }
    }
  }

  @Test
  public void testModifyTransparencyWithDifferentTransparencies() {
    this.init();

    ArrayList<ArrayList<Pixel>> pixels1 = new ArrayList<>();

    for (int i = 0; i < 2; i++) {
      pixels1.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        pixels1.get(i).add(new Pixel(10, 10, 10, 20));
      }
    }

    this.layer4.addImage(0, 0, pixels1);

    ArrayList<ArrayList<Pixel>> pixels2 = new ArrayList<>();

    for (int i = 0; i < 2; i++) {
      pixels2.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        pixels2.get(i).add(new Pixel(15, 30, 50, 30));
      }
    }

    this.layer6.addImage(0, 0, pixels2);

    ArrayList<ArrayList<Pixel>> modifiedList =
        this.layer4.modifyTransparency(this.layer6.getPixelsOnLayer(), true);

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j ++) {
        assertEquals(12, modifiedList.get(i).get(j).getRedComponent());
        assertEquals(21, modifiedList.get(i).get(j).getGreenComponent());
        assertEquals(33, modifiedList.get(i).get(j).getBlueComponent());
        assertEquals(47, modifiedList.get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testInvalidChangeTransparency() {
    try {
      this.layer1.modifyTransparency(null, true);
      fail("Arguments can't be null");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
  }

}
