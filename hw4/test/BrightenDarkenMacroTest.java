import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;

import model.Effects.BrightenDarkenMacro;
import model.Effects.BulkAssignFilter;
import model.Effects.MacroCollageEffects;
import model.Layer;
import model.Pixel;

import static org.junit.Assert.assertEquals;


/**
 * A test class for BrightenDarkenMacro.
 */
public class BrightenDarkenMacroTest {

  Pixel pixel1;
  Pixel pixel2;
  Pixel pixel3;
  Pixel pixel4;
  Pixel pixel5;
  Layer layer1;
  Layer layer2;
  Layer layer3;

  public void init() {
    this.pixel1 = new Pixel(0, 0, 0, 1);
    this.pixel2 = new Pixel(120, 72, 99);
    this.pixel3 = new Pixel(21, 50, 68, 100);
    this.pixel4 = new Pixel(12, 11, 10);
    this.pixel5 = new Pixel(1, 1, 1);
    this.layer1 = new Layer("L1", 10, 20, 255);
    this.layer2 = new Layer("L2", 10, 15, 0);
    this.layer3 = new Layer("L3", 2, 2, 255);
  }

  @Test
  public void testBrightenLuma() {
    this.init();

    ArrayList<ArrayList<Pixel>> pixels = new ArrayList<>();
    Pixel pixel = new Pixel(5, 5, 5);

    for (int i = 0; i < 2; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        pixels.get(i).add(pixel);
      }
    }

    this.layer3.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
        new BrightenDarkenMacro(2, 2, "brighten-luma", true);
    brightenMacroLuma.executeMacro(this.layer3);

    for (ArrayList<Pixel> list : this.layer3.getPixelsOnLayer()) {
      for (Pixel p : list) {
        assertEquals(80, p.getRedComponent());
        assertEquals(80, p.getGreenComponent());
        assertEquals(80, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBrightenByLuma() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(this.pixel3);
    lop.add(this.pixel5);
    lop.add(this.pixel1);


    for (int i = 0; i < currentLayer.size(); i++) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        currentLayer.get(i).get(j).modifyComponentByBrightness("brighten-luma", true);

        int newRed = currentLayer.get(i).get(j).getRedComponent();
        int newGreen = currentLayer.get(i).get(j).getGreenComponent();
        int newBlue = currentLayer.get(i).get(j).getBlueComponent();
        int newAlpha = currentLayer.get(i).get(j).getAlphaComponent();


        assertEquals(newRed, pixel1.getRedComponent());
        assertEquals(newGreen, pixel1.getGreenComponent());
        assertEquals(newBlue, pixel1.getBlueComponent());
        assertEquals(newAlpha, pixel1.getAlphaComponent());

        assertEquals(newRed, pixel3.getRedComponent());
        assertEquals(newGreen, pixel3.getGreenComponent());
        assertEquals(newBlue, pixel3.getBlueComponent());
        assertEquals(newAlpha, pixel3.getAlphaComponent());

        assertEquals(newRed, pixel5.getRedComponent());
        assertEquals(newGreen, pixel5.getGreenComponent());
        assertEquals(newBlue, pixel5.getBlueComponent());
        assertEquals(newAlpha, pixel5.getAlphaComponent());

      }
    }
  }


  @Test
  public void testBrightenByIntensity() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(this.pixel3);

    for (int i = 0; i < currentLayer.size(); i++) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        currentLayer.get(i).get(j).modifyComponentByBrightness("brighten-intensity", true);

        int newRed = currentLayer.get(i).get(j).getRedComponent();
        int newGreen = currentLayer.get(i).get(j).getGreenComponent();
        int newBlue = currentLayer.get(i).get(j).getBlueComponent();
        int newAlpha = currentLayer.get(i).get(j).getAlphaComponent();


        assertEquals(newRed, this.pixel3.getRedComponent());
        assertEquals(newGreen, this.pixel3.getGreenComponent());
        assertEquals(newBlue, this.pixel3.getBlueComponent());
        assertEquals(newAlpha, this.pixel3.getAlphaComponent());

      }
    }
  }

  @Test
  public void testBrightenByValue() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(this.pixel2);

    for (int i = 0; i < currentLayer.size(); i++) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        currentLayer.get(i).get(j).modifyComponentByBrightness("brighten-value", true);

        int newRed = currentLayer.get(i).get(j).getRedComponent();
        int newGreen = currentLayer.get(i).get(j).getGreenComponent();
        int newBlue = currentLayer.get(i).get(j).getBlueComponent();
        int newAlpha = currentLayer.get(i).get(j).getAlphaComponent();

        assertEquals(newRed, this.pixel2.getRedComponent());
        assertEquals(newGreen, this.pixel2.getGreenComponent());
        assertEquals(newBlue, this.pixel3.getBlueComponent());
        assertEquals(newAlpha, this.pixel4.getAlphaComponent());
      }
    }
  }

  @Test
  public void testDarkenByLuma() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(this.pixel2);

    for (int i = 0; i < currentLayer.size(); i++) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        currentLayer.get(i).get(j).modifyComponentByBrightness("darken-luma", false);

        int newRed = currentLayer.get(i).get(j).getRedComponent();
        int newGreen = currentLayer.get(i).get(j).getGreenComponent();
        int newBlue = currentLayer.get(i).get(j).getBlueComponent();
        int newAlpha = currentLayer.get(i).get(j).getAlphaComponent();

        assertEquals(newRed, this.pixel2.getRedComponent());
        assertEquals(newGreen, this.pixel2.getGreenComponent());
        assertEquals(newBlue, this.pixel2.getBlueComponent());
        assertEquals(newAlpha, this.pixel2.getAlphaComponent());
      }
    }
  }

  @Test
  public void testDarkenByIntensity() {
    this.init();
    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(this.pixel4);

    for (int i = 0; i < currentLayer.size(); i++) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        currentLayer.get(i).get(j).modifyComponentByBrightness("darken-intensity", false);

        int newRed = currentLayer.get(i).get(j).getRedComponent();
        int newGreen = currentLayer.get(i).get(j).getGreenComponent();
        int newBlue = currentLayer.get(i).get(j).getBlueComponent();
        int newAlpha = currentLayer.get(i).get(j).getAlphaComponent();

        assertEquals(newRed, this.pixel4.getRedComponent());
        assertEquals(newGreen, this.pixel4.getGreenComponent());
        assertEquals(newBlue, this.pixel4.getBlueComponent());
        assertEquals(newAlpha, this.pixel4.getAlphaComponent());
      }
    }
  }

  @Test
  public void testDarkenByValue() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    ArrayList<ArrayList<Pixel>> currentLayer = new ArrayList<>();
    currentLayer.add(lop);
    lop.add(this.pixel2);
    lop.add(this.pixel4);


    for (int i = 0; i < currentLayer.size(); i++) {
      for (int j = 0; j < currentLayer.get(0).size(); j++) {

        currentLayer.get(i).get(j).modifyComponentByBrightness("darken-value", false);

        int newRed = currentLayer.get(i).get(j).getRedComponent();
        int newGreen = currentLayer.get(i).get(j).getGreenComponent();
        int newBlue = currentLayer.get(i).get(j).getBlueComponent();
        int newAlpha = currentLayer.get(i).get(j).getAlphaComponent();

        assertEquals(newRed, this.pixel2.getRedComponent());
        assertEquals(newGreen, this.pixel2.getGreenComponent());
        assertEquals(newBlue, this.pixel2.getBlueComponent());
        assertEquals(newAlpha, this.pixel2.getAlphaComponent());

        assertEquals(newRed, this.pixel4.getRedComponent());
        assertEquals(newGreen, this.pixel4.getGreenComponent());
        assertEquals(newBlue, this.pixel4.getBlueComponent());
        assertEquals(newAlpha, this.pixel4.getAlphaComponent());
      }
    }
  }


  @Test
  public void testExecutesMacro() {
    this.init();

    ArrayList<Pixel> lop = new ArrayList<>();
    lop.add(this.pixel3);

    ArrayList<ArrayList<Pixel>> currentLayer = this.layer1.getPixelsOnLayer();
    currentLayer.add(lop);

    try {
      MacroCollageEffects macro = new BulkAssignFilter(10, 10, "red-component");
      macro.executeMacro(this.layer1);

      assertEquals(0, layer1.getPixelsOnLayer().get(10).get(10).getBlueComponent());
      assertEquals(0, layer1.getPixelsOnLayer().get(10).get(10).getGreenComponent());
      assertEquals(0, layer1.getPixelsOnLayer().get(10).get(10).getAlphaComponent());
    } catch(IllegalArgumentException iae) {
      //
    }


    try {
      MacroCollageEffects macro = new BulkAssignFilter(4, 4, "green-component");
      macro.executeMacro(this.layer1);

      assertEquals(0, layer1.getPixelsOnLayer().get(10).get(10).getRedComponent());
      assertEquals(0, layer1.getPixelsOnLayer().get(10).get(10).getBlueComponent());
      assertEquals(0, layer1.getPixelsOnLayer().get(10).get(10).getAlphaComponent());
    } catch(IllegalArgumentException iae) {
      //
    }

    try {
      MacroCollageEffects macro = new BulkAssignFilter(10, 10, "blue-component");
      macro.executeMacro(this.layer1);

      assertEquals(0, layer1.getPixelsOnLayer().get(10).get(10).getRedComponent());
      assertEquals(0, layer1.getPixelsOnLayer().get(10).get(10).getGreenComponent());
      assertEquals(0, layer1.getPixelsOnLayer().get(10).get(10).getAlphaComponent());
    } catch(IllegalArgumentException iae) {
      //
    }

    try {
      MacroCollageEffects macro = new BrightenDarkenMacro(10, 10, "brighten-value",true);
      macro.executeMacro(this.layer1);

      assertEquals(66, layer1.getPixelsOnLayer().get(10).get(10).getRedComponent());
      assertEquals(95, layer1.getPixelsOnLayer().get(10).get(10).getGreenComponent());
      assertEquals(113, layer1.getPixelsOnLayer().get(10).get(10).getAlphaComponent());
    } catch(IllegalArgumentException iae) {
      //
    }

  }

}
