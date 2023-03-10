import org.junit.Test;

import java.util.ArrayList;

import model.Effects.BulkAssignFilter;
import model.Effects.MacroCollageEffects;
import model.Layer;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BulkAssignMacroTest {
  Pixel pixel1;
  Pixel pixel2;
  Pixel pixel3;
  Pixel pixel4;
  Pixel pixel5;
  Layer layer1;
  Layer layer2;
  Layer layer3;

  Layer layer4;

  public void init() {
    this.pixel1 = new Pixel(0, 0, 0, 1);
    this.pixel2 = new Pixel(120, 72, 99);
    this.pixel3 = new Pixel(21, 50, 68, 100);
    this.pixel4 = new Pixel(12, 11, 10);
    this.pixel5 = new Pixel(1, 1, 1);

    this.layer1 = new Layer("L1", 10, 20, 255);
    this.layer2 = new Layer("L2", 10, 15, 0);
    this.layer3 = new Layer("L3", 2, 2, 255);
    this.layer4 = new Layer("L4", 100, 100, 20);
  }

  @Test
  public void testInvalidBulkAssignFilterMacro() {

    try {
      new BulkAssignFilter(-2, 2, "brighten-value");
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }

    try {
      new BulkAssignFilter(2, -2000, "brighten-value");
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }

    try {
      new BulkAssignFilter(0, 0, null);
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }

    try {
      new BulkAssignFilter(2, 4, "");
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }

  }


  @Test
  public void testExecutesMacro() {
    this.init();

    ArrayList<ArrayList<Pixel>> pixelsOnCurrentLayer = new ArrayList<>();


    for (int i = 0; i < 10; i++) {
      pixelsOnCurrentLayer.add(new ArrayList<>());
      for (int j = 0; j < 15; j++) {
        pixelsOnCurrentLayer.get(i).add(this.pixel3);
      }
    }

    this.layer2.addImage(0, 0, pixelsOnCurrentLayer);


    MacroCollageEffects macro = new BulkAssignFilter(10, 15, "red-component");
    macro.executeMacro(this.layer2);

    for (ArrayList<Pixel> lop1 : this.layer2.getPixelsOnLayer()) {
      for (Pixel p : lop1) {
        assertEquals(21, p.getRedComponent());
        assertEquals(0, p.getGreenComponent());
        assertEquals(0, p.getBlueComponent());
        assertEquals(100, p.getAlphaComponent());
      }
    }

  }


  @Test
  public void testBulkExecutesRedComponent() {
    this.init();
    ArrayList<ArrayList<Pixel>> pixelsOnCurrentLayer = new ArrayList<>();


    for (int i = 0; i < 10; i++) {
      pixelsOnCurrentLayer.add(new ArrayList<>());
      for (int j = 0; j < 15; j++) {
        pixelsOnCurrentLayer.get(i).add(this.pixel3);
      }
    }

    this.layer2.addImage(0, 0, pixelsOnCurrentLayer);

    MacroCollageEffects macroCollageEffects = new BulkAssignFilter(10,
            15, "red-component");

    macroCollageEffects.executeMacro(this.layer2);

    for (ArrayList<Pixel> lop1 : this.layer2.getPixelsOnLayer()) {
      for (Pixel p : lop1) {
        assertEquals(21, p.getRedComponent());
        assertEquals(0, p.getGreenComponent());
        assertEquals(0, p.getBlueComponent());
        assertEquals(100, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBulkExecutesGreenComponent() {
    this.init();
    ArrayList<ArrayList<Pixel>> pixelsOnCurrentLayer = new ArrayList<>();


    for (int i = 0; i < 2; i++) {
      pixelsOnCurrentLayer.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        pixelsOnCurrentLayer.get(i).add(this.pixel4);
      }
    }

    this.layer3.addImage(0, 0, pixelsOnCurrentLayer);

    MacroCollageEffects macroCollageEffects = new BulkAssignFilter(2,
            2, "green-component");

    macroCollageEffects.executeMacro(this.layer3);

    for (ArrayList<Pixel> lop1 : this.layer3.getPixelsOnLayer()) {
      for (Pixel p : lop1) {
        assertEquals(0, p.getRedComponent());
        assertEquals(11, p.getGreenComponent());
        assertEquals(0, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBulkExecutesBlueComponent() {
    this.init();
    ArrayList<ArrayList<Pixel>> pixelsOnCurrentLayer = new ArrayList<>();


    for (int i = 0; i < 100; i++) {
      pixelsOnCurrentLayer.add(new ArrayList<>());
      for (int j = 0; j < 100; j++) {
        pixelsOnCurrentLayer.get(i).add(this.pixel5);
      }
    }

    this.layer4.addImage(0, 0, pixelsOnCurrentLayer);

    MacroCollageEffects macroCollageEffects = new BulkAssignFilter(100,
            100, "blue-component");

    macroCollageEffects.executeMacro(this.layer4);

    for (ArrayList<Pixel> lop1 : this.layer4.getPixelsOnLayer()) {
      for (Pixel p : lop1) {
        assertEquals(0, p.getRedComponent());
        assertEquals(0, p.getGreenComponent());
        assertEquals(1, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }
  }


}
