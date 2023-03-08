import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import model.CollageProject;
import model.Layer;
import model.Pixel;

import static org.junit.Assert.assertEquals;

public class LayerTest {


  Layer layer1;
  Layer layer2;

  Layer layer3;

  Pixel pixel1;
  Pixel pixel2;
  Pixel pixel3;
  CollageProject project1;


  @Before
  public void init() {
    this.layer1 = new Layer("L1",10,10,0);
    this.layer2 = new Layer("L2",10,10,0);
    this.layer3 = new Layer("L3",10,10,0);
    this.pixel1 = new Pixel(0,0,0, 1);
    this.pixel2 = new Pixel(120,72,99);
    this.pixel3 = new Pixel(21,50,68,100);
  }

  @Test
  public void testLayerName() {
    assertEquals("L1",layer1.getName());
  }

  @Test
  public void testGetPixelsOnALayer() {


    ArrayList<ArrayList<Pixel>> blankLayer = layer1.getPixelsOnLayer();



   assertEquals(255,blankLayer.get(1).get(1));

  }





}
