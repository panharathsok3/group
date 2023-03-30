
import model.IPixel;
import org.junit.Before;
import org.junit.Test;

import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * This is a test class for Pixel.
 */


public class PixelTest {

  IPixel pixel1;
  IPixel pixel2;
  IPixel pixel3;
  IPixel pixel4;
  IPixel pixel5;
  IPixel pixel6;
  IPixel pixel7;
  IPixel pixel8;
  IPixel pixel9;
  IPixel pixel10;
  IPixel pixel11;
  IPixel pixel12;


  @Before
  public void init() {
    this.pixel1 = new Pixel(0, 0, 0, 1);
    this.pixel2 = new Pixel(120, 72, 99);
    this.pixel3 = new Pixel(21, 50, 68, 100);
    this.pixel4 = new Pixel(12, 11, 10);
    this.pixel5 = new Pixel(1, 1, 1);
    this.pixel6 = new Pixel(100, 60, 33, 220);
    this.pixel7 = new Pixel(0.0, 0.0, 0.0, 255);
    this.pixel8 = new Pixel(120.0, 0.72, 0.99, 255);
    this.pixel9 = new Pixel(21.0, 0.50, 0.68, 255);
    this.pixel10 = new Pixel(12.0, 0.11, 0.10, 255);
    this.pixel11 = new Pixel(1.0, 0.10, 0.10, 255);
    this.pixel12 = new Pixel(100.0, 0.60, 0.33, 255);
  }

  @Test
  public void testInvalidConstructor() {

    try {
      IPixel pixel1 = new Pixel(-1, 0, 0);
      fail("the given component can't be negative");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      IPixel pixel1 = new Pixel(0, -1, 0);
      fail("the given component can't be negative");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      IPixel pixel1 = new Pixel(0, 0, -1);
      fail("the given component can't be negative");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      IPixel pixel1 = new Pixel(0, 0, 0, -1);
      fail("the given component can't be negative");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      IPixel pixel1 = new Pixel(-1.0, 0.0, 0.0, 255);
      fail("the given component can't be negative");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      IPixel pixel1 = new Pixel(370, 0.0, 0.0, 255);
      fail("the given component can't be negative");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      IPixel pixel1 = new Pixel(0.0, 120.0, 0.0, 255);
      fail("the given component can't be negative");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      IPixel pixel1 = new Pixel(0.0, 120.0, 0.0, -255);
      fail("the given component can't be negative");
    } catch (IllegalArgumentException e) {
      // do nothing
    }


  }

  @Test
  public void testGetRedComponent() {
    this.init();
    assertEquals(0, this.pixel1.getRedComponent());
    assertEquals(120, this.pixel2.getRedComponent());
    assertEquals(21, this.pixel3.getRedComponent());
    assertEquals(12, this.pixel4.getRedComponent());
    assertEquals(1, this.pixel5.getRedComponent());
  }


  @Test
  public void testGetGreenComponent() {
    this.init();
    assertEquals(0, this.pixel1.getGreenComponent());
    assertEquals(72, this.pixel2.getGreenComponent());
    assertEquals(50, this.pixel3.getGreenComponent());
    assertEquals(11, this.pixel4.getGreenComponent());
    assertEquals(1, this.pixel5.getGreenComponent());
  }

  @Test
  public void testGetBlueComponent() {
    this.init();
    assertEquals(0, this.pixel1.getBlueComponent());
    assertEquals(99, this.pixel2.getBlueComponent());
    assertEquals(68, this.pixel3.getBlueComponent());
    assertEquals(10, this.pixel4.getBlueComponent());
    assertEquals(1, this.pixel5.getBlueComponent());
  }

  @Test
  public void testGetHueComponent() {
    this.init();
    assertEquals(0.0, this.pixel7.getHueComponent(), 0.01);
    assertEquals(120.0, this.pixel8.getHueComponent(), 0.01);
    assertEquals(21.0, this.pixel9.getHueComponent(), 0.01);
    assertEquals(12.0, this.pixel10.getHueComponent(), 0.01);
    assertEquals(1.0, this.pixel11.getHueComponent(), 0.01);
  }


  @Test
  public void testGetSaturationComponent() {
    this.init();
    assertEquals(0.0, this.pixel7.getSaturationComponent(), 0.01);
    assertEquals(0.72, this.pixel8.getSaturationComponent(), 0.01);
    assertEquals(0.50, this.pixel9.getSaturationComponent(), 0.01);
    assertEquals(0.11, this.pixel10.getSaturationComponent(), 0.01);
    assertEquals(0.1, this.pixel11.getSaturationComponent(), 0.01);
  }

  @Test
  public void testGetLightnessComponent() {
    this.init();
    assertEquals(0.0, this.pixel7.getLightnessComponent(), 0.01);
    assertEquals(0.99, this.pixel8.getLightnessComponent(), 0.01);
    assertEquals(0.68, this.pixel9.getLightnessComponent(), 0.01);
    assertEquals(0.10, this.pixel10.getLightnessComponent(), 0.01);
    assertEquals(0.10, this.pixel11.getLightnessComponent(), 0.01);
  }

  @Test
  public void testValue() {
    this.init();
    assertEquals(0, this.pixel1.value());
    assertEquals(120, this.pixel2.value());
    assertEquals(68, this.pixel3.value());
    assertEquals(12, this.pixel4.value());
    assertEquals(1, this.pixel5.value());
  }

  @Test
  public void testIntensity() {
    this.init();
    assertEquals(0, this.pixel1.intensity());
    assertEquals(97, this.pixel2.intensity());
    assertEquals(46, this.pixel3.intensity());
    assertEquals(11, this.pixel4.intensity());
    assertEquals(1, this.pixel5.intensity());

  }

  @Test
  public void testLuma() {
    this.init();
    assertEquals(0, this.pixel1.luma());
    assertEquals(85, this.pixel2.luma());
    assertEquals(45, this.pixel3.luma());
    assertEquals(11, this.pixel4.luma());
    assertEquals(1, this.pixel5.luma());
  }

  @Test
  public void testConvertRGBtoHSL() {
    this.init();

    System.out.println(this.pixel4.convertRGBtoHSL().getHueComponent());
    System.out.println(this.pixel4.convertRGBtoHSL().getSaturationComponent());
    System.out.println(this.pixel4.convertRGBtoHSL().getLightnessComponent());

    assertEquals(0.0, this.pixel1.convertRGBtoHSL().getHueComponent(), 0.01);
    assertEquals(0.0, this.pixel1.convertRGBtoHSL().getSaturationComponent(), 0.01);
    assertEquals(0.0, this.pixel1.convertRGBtoHSL().getLightnessComponent(), 0.01);

    assertEquals(326.25, this.pixel2.convertRGBtoHSL().getHueComponent(), 0.01);
    assertEquals(0.25, this.pixel2.convertRGBtoHSL().getSaturationComponent(), 0.01);
    assertEquals(0.38, this.pixel2.convertRGBtoHSL().getLightnessComponent(), 0.01);

    assertEquals(202.98, this.pixel3.convertRGBtoHSL().getHueComponent(), 0.01);
    assertEquals(0.52, this.pixel3.convertRGBtoHSL().getSaturationComponent(), 0.01);
    assertEquals(0.17, this.pixel3.convertRGBtoHSL().getLightnessComponent(), 0.01);

    assertEquals(30.0, this.pixel4.convertRGBtoHSL().getHueComponent(), 0.01);
    assertEquals(0.09, this.pixel4.convertRGBtoHSL().getSaturationComponent(), 0.01);
    assertEquals(0.04, this.pixel4.convertRGBtoHSL().getLightnessComponent(), 0.01);


  }

  @Test
  public void testConvertHSLtoRGB() {
    this.init();

    assertEquals(0, this.pixel7.convertHSLtoRGB().getRedComponent(), 0.01);
    assertEquals(0, this.pixel7.convertHSLtoRGB().getGreenComponent(), 0.01);
    assertEquals(0, this.pixel7.convertHSLtoRGB().getBlueComponent(), 0.01);

    assertEquals(251, this.pixel8.convertHSLtoRGB().getRedComponent(), 0.01);
    assertEquals(254, this.pixel8.convertHSLtoRGB().getGreenComponent(), 0.01);
    assertEquals(251, this.pixel8.convertHSLtoRGB().getBlueComponent(), 0.01);

    assertEquals(214, this.pixel9.convertHSLtoRGB().getRedComponent(), 0.01);
    assertEquals(161, this.pixel9.convertHSLtoRGB().getGreenComponent(), 0.01);
    assertEquals(133, this.pixel9.convertHSLtoRGB().getBlueComponent(), 0.01);

    assertEquals(28, this.pixel10.convertHSLtoRGB().getRedComponent(), 0.01);
    assertEquals(24, this.pixel10.convertHSLtoRGB().getGreenComponent(), 0.01);
    assertEquals(23, this.pixel10.convertHSLtoRGB().getBlueComponent(), 0.01);

  }


}
