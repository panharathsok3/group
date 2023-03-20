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

  @Before
  public void init() {
    this.pixel1 = new Pixel(0, 0, 0, 1);
    this.pixel2 = new Pixel(120, 72, 99);
    this.pixel3 = new Pixel(21, 50, 68, 100);
    this.pixel4 = new Pixel(12, 11, 10);
    this.pixel5 = new Pixel(1, 1, 1);
    this.pixel6 = new Pixel(100, 60, 33, 220);
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
  public void testChangeTransparency() {
    this.init();

    this.pixel1.changeTransparency(false, 10, 10, 10, 255);
    assertEquals(0, this.pixel1.getRedComponent());
    assertEquals(0, this.pixel1.getGreenComponent());
    assertEquals(0, this.pixel1.getBlueComponent());
    assertEquals(1, this.pixel1.getAlphaComponent());

    this.pixel2.changeTransparency(true, 20, 30, 40, 20);
    assertEquals(120, this.pixel2.getRedComponent());
    assertEquals(72, this.pixel2.getGreenComponent());
    assertEquals(99, this.pixel2.getBlueComponent());
    assertEquals(255, this.pixel2.getAlphaComponent());

    this.pixel4.changeTransparency(false, 10, 10, 10,20);
    assertEquals(12, this.pixel4.getRedComponent());
    assertEquals(11, this.pixel4.getGreenComponent());
    assertEquals(10, this.pixel4.getBlueComponent());
    assertEquals(255, this.pixel4.getAlphaComponent());

    this.pixel4.changeTransparency(false, 10, 10, 10,20);
    assertEquals(12, this.pixel4.getRedComponent());
    assertEquals(11, this.pixel4.getGreenComponent());
    assertEquals(10, this.pixel4.getBlueComponent());
    assertEquals(255, this.pixel4.getAlphaComponent());


    this.pixel6.changeTransparency(true, 10, 20, 30, 30);
    assertEquals(98, this.pixel6.getRedComponent());
    assertEquals(59, this.pixel6.getGreenComponent());
    assertEquals(32, this.pixel6.getBlueComponent());
    assertEquals(224, this.pixel6.getAlphaComponent());
  }
}
