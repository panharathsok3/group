
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.ILayer;
import model.IPixel;
import model.Layer;
import model.Pixel;
import model.effects.BrightenDarkenMacro;
import model.effects.BulkAssignFilter;
import model.effects.ChangeTransparencyMacro;
import model.effects.MacroCollageEffects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ChangeTransparencyMacroTest  {
  ILayer layer1;
  ILayer layer2;
  ILayer layer3;
  ILayer layer4;

  @Before
  public void init() {
    this.layer1 = new Layer("L1", 10, 15, 255);
    this.layer2 = new Layer("L2", 10, 15, 0);
    this.layer3 = new Layer("L3", 10, 15, 0);
    this.layer4 = new Layer("L4", 10, 15, 255);
  }

  @Test
  public void testInvalidChangeTransparencyMacro() {

    try {
      new ChangeTransparencyMacro(2, 2, true, null);
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }

    try {
      new ChangeTransparencyMacro(-10, 10, true,
              new ArrayList<List<IPixel>>());
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }


    try {
      new ChangeTransparencyMacro(2, -2000, true,
              new ArrayList<List<IPixel>>());
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }

    try {
      new ChangeTransparencyMacro(10, 10, false,
              new ArrayList<List<IPixel>>());
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }

  }


  @Test
  public void testInvalidExecuteMacro() {
    this.init();

    MacroCollageEffects changeTransparencyMacro = new ChangeTransparencyMacro(2, 2,
            true, new ArrayList<List<IPixel>>());
    try {
      changeTransparencyMacro.executeMacro(null);
      fail("Arguments can't be null");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }


  @Test
  public void testChangeTransparencyAlpha() {
    this.init();

    List<List<IPixel>> pixelsOnCurrentLayer = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      pixelsOnCurrentLayer.add(new ArrayList<>());
      for (int j = 0; j < 15; j++) {
        pixelsOnCurrentLayer.get(i).add(new Pixel(21, 50,
                68, 10));
      }
    }

    this.layer2.addImage(0, 0, pixelsOnCurrentLayer);

    List<List<IPixel>> pixelsOnCurrentLayer2 = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      pixelsOnCurrentLayer2.add(new ArrayList<>());
      for (int j = 0; j < 15; j++) {
        pixelsOnCurrentLayer2.get(i).add(new Pixel(120, 60,
                20, 100));
      }
    }

    this.layer3.addImage(0, 0, pixelsOnCurrentLayer2);

    MacroCollageEffects macro = new ChangeTransparencyMacro(10, 15, true,
            pixelsOnCurrentLayer);
    macro.executeMacro(this.layer3);


    for (List<IPixel> list : this.layer3.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(114, p.getRedComponent());
        assertEquals(59, p.getGreenComponent());
        assertEquals(22, p.getBlueComponent());
        assertEquals(106, p.getAlphaComponent());
      }
    }


  }


  @Test
  public void testChangeTransparencyNoAlpha() {
    this.init();

    List<List<IPixel>> pixelsOnCurrentLayer = new ArrayList<>();

    for (int i = 0; i < 10; i++) {
      pixelsOnCurrentLayer.add(new ArrayList<>());
      for (int j = 0; j < 15; j++) {
        pixelsOnCurrentLayer.get(i).add(new Pixel(15, 150,
                70));
      }
    }

    this.layer4.addImage(0, 0, pixelsOnCurrentLayer);

    MacroCollageEffects macro = new ChangeTransparencyMacro(10, 10, false,
            pixelsOnCurrentLayer);

    macro.executeMacro(this.layer4);

    for (List<IPixel> list : this.layer4.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(15, p.getRedComponent());
        assertEquals(150, p.getGreenComponent());
        assertEquals(70, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }

  }




}