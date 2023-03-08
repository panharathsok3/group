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

  Pixel pixel1;
  Pixel pixel2;
  Pixel pixel3;


  @Before
  public void init() {
    this.layer1 = new Layer("L1",10,20,255);
    this.layer2 = new Layer("L2",10,15,0);
    this.layer3 = new Layer("L3",10,10,0);
    this.pixel1 = new Pixel(0,0,0, 1);
    this.pixel2 = new Pixel(120,72,99);
    this.pixel3 = new Pixel(21,50,68,100);
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
}
