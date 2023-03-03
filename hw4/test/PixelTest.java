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
    this.pixel1 = new Pixel(0,0,0, 1);
    this.pixel2 = new Pixel(120,72,99);
    this.pixel3 = new Pixel(21,50,68,100);
    this.pixel4 = new Pixel(12,11,10);
    this.pixel5 = new Pixel(1,1,1);
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
    assertEquals(0,this.pixel1.value());
    assertEquals(120,this.pixel2.value());
    assertEquals(68,this.pixel3.value());
    assertTrue(this.pixel3.value() == 68);
    assertTrue(this.pixel3.value() != 21);
    assertFalse(this.pixel2.value() == 10);
  }

  @Test
  public void testIntensity() {
    this.init();
    assertEquals(0,this.pixel1.intensity());
    assertEquals(1,this.pixel5.intensity());
  }

  @Test
  public void testLuma() {
    this.init();
    assertEquals(11,this.pixel4.luma());
    assertEquals(1,this.pixel5.luma());
    assertEquals(0,this.pixel1.luma());
    assertEquals(45,this.pixel3.luma());
  }


}
