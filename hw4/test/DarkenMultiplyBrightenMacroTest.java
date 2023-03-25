
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.ILayer;
import model.IPixel;
import model.Layer;
import model.Pixel;
import model.effects.DarkenMultiplyBrightenScreenMacro;
import model.effects.MacroCollageEffects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class DarkenMultiplyBrightenMacroTest {

  ILayer layer1;
  ILayer layer2;
  ILayer layer3;
  ILayer layer4;
  ILayer layer5;

  @Before
  public void init() {
    this.layer1 = new Layer("L1", 10, 10, 255);
    this.layer2 = new Layer("L2", 10, 10, 0);
    this.layer3 = new Layer("L3", 10, 10, 0);
    this.layer4 = new Layer("L4", 10, 10, 255);
    this.layer5 = new Layer("L5", 10, 10, 255);
  }

  @Test
  public void testInvalidDarkenMultiplyBrightenMacro() {

    try {
      new DarkenMultiplyBrightenScreenMacro(2, 2,null, true);
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }

    try {
      new DarkenMultiplyBrightenScreenMacro(-10, 10,
              new ArrayList<List<IPixel>>(), true);
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }


    try {
      new DarkenMultiplyBrightenScreenMacro(10, -10,
              new ArrayList<List<IPixel>>(), true);
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }

  }


  @Test
  public void testInvalidExecuteMacro() {
    this.init();

    MacroCollageEffects darkenMultiplyBrightenMacro = new DarkenMultiplyBrightenScreenMacro(10, 10,
            new ArrayList<List<IPixel>>(), true);
    try {
      darkenMultiplyBrightenMacro.executeMacro(null);
      fail("Arguments can't be null");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }


  @Test
  public void testDarken() {
    this.init();

    List<List<IPixel>> pixelsOnCurrentLayer = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      pixelsOnCurrentLayer.add(new ArrayList<>());
      for (int j = 0; j < 10; j++) {
        pixelsOnCurrentLayer.get(i).add(new Pixel(21, 50,
                68, 1));
      }
    }

    this.layer2.addImage(0, 0, pixelsOnCurrentLayer);


    List<List<IPixel>> pixelsOnCurrentLayer2 = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      pixelsOnCurrentLayer2.add(new ArrayList<>());
      for (int j = 0; j < 10; j++) {
        pixelsOnCurrentLayer2.get(i).add(new Pixel(150, 25,
                18, 150));
      }
    }

    this.layer3.addImage(0, 0, pixelsOnCurrentLayer);

    MacroCollageEffects macro = new DarkenMultiplyBrightenScreenMacro(10, 10,
            pixelsOnCurrentLayer, true);
    macro.executeMacro(this.layer3);

    for (List<IPixel> lop : this.layer3.getPixelsOnLayer()) {
      for (IPixel p : lop) {
        assertEquals(4, p.getRedComponent());
        assertEquals(9, p.getGreenComponent());
        assertEquals(12, p.getBlueComponent());
        assertEquals(189.66, p.getAlphaComponent(), 0.1);
      }
    }

  }


  @Test
  public void testBrighten() {
    this.init();

    List<List<IPixel>> pixelsOnCurrentLayer = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      pixelsOnCurrentLayer.add(new ArrayList<>());
      for (int j = 0; j < 10; j++) {
        pixelsOnCurrentLayer.get(i).add(new Pixel(21, 50,
                68, 100));
      }
    }

    this.layer2.addImage(0, 0, pixelsOnCurrentLayer);


    List<List<IPixel>> pixelsOnCurrentLayer2 = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      pixelsOnCurrentLayer2.add(new ArrayList<>());
      for (int j = 0; j < 10; j++) {
        pixelsOnCurrentLayer2.get(i).add(new Pixel(150, 25,
                18, 150));
      }
    }

    this.layer3.addImage(0, 0, pixelsOnCurrentLayer);

    MacroCollageEffects macro = new DarkenMultiplyBrightenScreenMacro(10, 10,
            pixelsOnCurrentLayer, false);
    macro.executeMacro(this.layer3);

    for (List<IPixel> lop : this.layer3.getPixelsOnLayer()) {
      for (IPixel p : lop) {
        assertEquals(38, p.getRedComponent());
        assertEquals(91, p.getGreenComponent());
        assertEquals(124, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }

  }




}
