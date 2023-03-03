import org.junit.Before;
import org.junit.Test;

import model.Pixel;

import static org.junit.Assert.assertEquals;

public class PixelTest {

  Pixel pixel1;
  Pixel pixel2;
  Pixel pixel3;
  Pixel pixel4;
  Pixel pixel5;

  @Before
  public void init() {
    this.pixel1 = new Pixel(0,0,0);
    this.pixel2 = new Pixel(120,72,99);
    this.pixel3 = new Pixel(21,50,68,100);
    this.pixel4 = new Pixel(12,11,10);
    this.pixel5 = new Pixel(1,1,1);
  }

  @Test
  public void testGetRedComponent() {
    this.init();
    assertEquals(0,this.pixel1.getRedComponent());
    assertEquals(120,this.pixel2.getRedComponent());
    assertEquals(21,this.pixel3.getRedComponent());
    assertEquals(12,this.pixel4.getRedComponent());
    assertEquals(1,this.pixel5.getRedComponent());


    this.pixel1.setRedComponent(20);
    assertEquals(20,this.pixel1.getRedComponent());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetRedException() {
    this.init();
    this.pixel1.setRedComponent(300);
  }

  @Test
  public void testGetGreenComponent() {

  }

  @Test
  public void testGetBlueComponent() {

  }



}
