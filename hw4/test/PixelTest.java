import org.junit.Before;
import org.junit.Test;

import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class PixelTest {

  Pixel pixel1;
  Pixel pixel2;
  Pixel pixel3;
  Pixel pixel4;
  Pixel pixel5;

  @Before
  public void init() {
    this.pixel1 = new Pixel(0, 0, 0, 1);
    this.pixel2 = new Pixel(120, 72, 99);
    this.pixel3 = new Pixel(21, 50, 68, 100);
    this.pixel4 = new Pixel(12, 11, 10);
    this.pixel5 = new Pixel(1, 1, 1);
  }

  @Test
  public void testInvalidConstructor() {
    try {
      Pixel pixel1 = new Pixel(-1, 0, 0);
      fail("the given component can't be negative");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      Pixel pixel1 = new Pixel(0, -1, 0);
      fail("the given component can't be negative");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      Pixel pixel1 = new Pixel(0, 0, -1);
      fail("the given component can't be negative");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      Pixel pixel1 = new Pixel(0, 0, 0, -1);
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
    assertTrue(this.pixel3.value() == 68);
    assertTrue(this.pixel3.value() != 21);
    assertFalse(this.pixel2.value() == 10);
  }

  @Test
  public void testIntensity() {
    this.init();
    assertEquals(0, this.pixel1.intensity());
    assertEquals(1, this.pixel5.intensity());
  }

  @Test
  public void testLuma() {
    this.init();
    assertEquals(11, this.pixel4.luma());
    assertEquals(1, this.pixel5.luma());
    assertEquals(0, this.pixel1.luma());
    assertEquals(45, this.pixel3.luma());

  }

  @Test
  public void invalidModifyComponentByBrightness() {
    this.init();

    try {
      this.pixel1.modifyComponentByBrightness(null, true);
    } catch (IllegalArgumentException e) {
      //do nothing
    }
  }

  @Test
  public void testModifyComponentByBrightnessBrightening() {
    this.init();

    this.pixel1 = new Pixel(0, 0, 0, 1);
    this.pixel2 = new Pixel(120, 72, 99);
    this.pixel3 = new Pixel(21, 50, 68, 100);
    this.pixel4 = new Pixel(12, 11, 10);
    this.pixel5 = new Pixel(1, 1, 1);

    this.pixel1.modifyComponentByBrightness("brighten-luma", true);
    this.pixel2.modifyComponentByBrightness("brighten-luma", true);
    this.pixel3.modifyComponentByBrightness("brighten-value", true);
    this.pixel4.modifyComponentByBrightness("brighten-intensity", true);

    assertEquals(0, this.pixel1.getRedComponent());
    assertEquals(0, this.pixel1.getGreenComponent());
    assertEquals(0, this.pixel1.getBlueComponent());

    assertEquals(205, this.pixel2.getRedComponent());
    assertEquals(157, this.pixel2.getGreenComponent());
    assertEquals(184, this.pixel2.getBlueComponent());

    assertEquals(89, this.pixel3.getRedComponent());
    assertEquals(118, this.pixel3.getGreenComponent());
    assertEquals(136, this.pixel3.getBlueComponent());

    assertEquals(23, this.pixel4.getRedComponent());
    assertEquals(22, this.pixel4.getGreenComponent());
    assertEquals(21, this.pixel4.getBlueComponent());

    this.pixel2.modifyComponentByBrightness("brighten-intensity", true);
    assertEquals(255, this.pixel2.getRedComponent());
    assertEquals(255, this.pixel2.getGreenComponent());
    assertEquals(255, this.pixel2.getBlueComponent());

    this.pixel2.modifyComponentByBrightness("brighten-value", true);
    assertEquals(255, this.pixel2.getRedComponent());
    assertEquals(255, this.pixel2.getGreenComponent());
    assertEquals(255, this.pixel2.getBlueComponent());
  }

  @Test
  public void testModifyComponentByBrightnessDarkening() {
    this.init();

    this.pixel1 = new Pixel(0, 0, 0, 1);
    this.pixel2 = new Pixel(120, 72, 99);
    this.pixel3 = new Pixel(21, 50, 68, 100);
    this.pixel4 = new Pixel(12, 11, 10);
    this.pixel5 = new Pixel(1, 1, 1);

    this.pixel1.modifyComponentByBrightness("darken-luma", false);
    this.pixel2.modifyComponentByBrightness("darken-luma", false);
    this.pixel3.modifyComponentByBrightness("darken-value", false);
    this.pixel4.modifyComponentByBrightness("darken-intensity", false);

    assertEquals(0, this.pixel1.getRedComponent());
    assertEquals(0, this.pixel1.getGreenComponent());
    assertEquals(0, this.pixel1.getBlueComponent());

    assertEquals(35, this.pixel2.getRedComponent());
    assertEquals(0, this.pixel2.getGreenComponent());
    assertEquals(14, this.pixel2.getBlueComponent());

    assertEquals(0, this.pixel3.getRedComponent());
    assertEquals(0, this.pixel3.getGreenComponent());
    assertEquals(0, this.pixel3.getBlueComponent());

    assertEquals(1, this.pixel4.getRedComponent());
    assertEquals(0, this.pixel4.getGreenComponent());
    assertEquals(0, this.pixel4.getBlueComponent());

    this.pixel2.modifyComponentByBrightness("darken-intensity", false);
    assertEquals(19, this.pixel2.getRedComponent());
    assertEquals(0, this.pixel2.getGreenComponent());
    assertEquals(0, this.pixel2.getBlueComponent());

    this.pixel2.modifyComponentByBrightness("darken-value", false);
    assertEquals(0, this.pixel2.getRedComponent());
    assertEquals(0, this.pixel2.getGreenComponent());
    assertEquals(0, this.pixel2.getBlueComponent());
  }

  @Test
  public void testSetFilter() {
    this.init();

    this.pixel2.setFilter("red-component");
    assertEquals(0, this.pixel2.getBlueComponent());
    assertEquals(0, this.pixel2.getGreenComponent());

    this.pixel3.setFilter("green-component");
    assertEquals(0, this.pixel3.getRedComponent());
    assertEquals(0, this.pixel3.getBlueComponent());

    this.pixel4.setFilter("blue-component");
    assertEquals(10, this.pixel4.getBlueComponent());
    assertEquals(0, this.pixel4.getRedComponent());
    assertEquals(0, this.pixel4.getGreenComponent());


    try {
      this.pixel2.setFilter("yellow-component");
      fail("the option must be red, green, or blue");
    } catch (IllegalArgumentException iae) {
      //does nothing because we want it to fail.
    }

    try {
      this.pixel3.setFilter("alpha-component");
      fail("the option must be red, green, or blue");
    } catch (IllegalArgumentException iae) {
      //does nothing because we want it to fail.
    }

    try {
      this.pixel4.setFilter("purple-component");
      fail("the option must be red, green, or blue");
    } catch (IllegalArgumentException iae) {
      //does nothing because we want it to fail.
    }

  }


}
