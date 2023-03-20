import java.util.List;
import model.ILayer;
import model.IPixel;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import model.effects.BrightenDarkenMacro;
import model.effects.MacroCollageEffects;
import model.Layer;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


/**
 * A test class for BrightenDarkenMacro.
 */
public class BrightenDarkenMacroTest {

  IPixel pixel1;
  IPixel pixel2;
  IPixel pixel3;
  IPixel pixel4;
  IPixel pixel5;
  IPixel pixel6;
  IPixel pixel7;

  ILayer layer1;
  ILayer layer2;
  ILayer layer3;
  ILayer layer4;
  ILayer layer5;
  ILayer layer6;

  @Before
  public void init() {
    this.pixel1 = new Pixel(0, 0, 0, 1);
    this.pixel2 = new Pixel(120, 72, 99);
    this.pixel3 = new Pixel(21, 50, 68, 100);
    this.pixel4 = new Pixel(12, 11, 10, 21);
    this.pixel5 = new Pixel(1, 1, 1);
    this.pixel6 = new Pixel(33, 55, 99);
    this.pixel7 = new Pixel(4, 4, 4);


    this.layer1 = new Layer("L1", 15, 15, 255);
    this.layer2 = new Layer("L2", 6, 6, 0);
    this.layer3 = new Layer("L3", 2, 2, 255);
    this.layer4 = new Layer("L4", 20, 20, 100);
    this.layer5 = new Layer("L5", 100, 100, 70);
    this.layer6 = new Layer("L6", 1, 1, 255);


  }

  @Test
  public void testInvalidBrightenDarkenMacro() {

    try {
      new BrightenDarkenMacro(-2, 2, "brighten-value", true);
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }

    try {
      new BrightenDarkenMacro(2, -2000, "brighten-value", true);
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }

    try {
      new BrightenDarkenMacro(0, 0, null, true);
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }

    try {
      new BrightenDarkenMacro(2, 4, "", true);
      fail("arguments cannot be null or negative");
    } catch (IllegalArgumentException iae) {
      //do nothing because we want it to fail.
    }


  }

  @Test
  public void testInvalidExecuteMarco() {
    this.init();

    MacroCollageEffects brightenMacroLuma = new BrightenDarkenMacro(2, 2,
        "brighten-luma", true);
    try {
      brightenMacroLuma.executeMacro(null);
      fail("Arguments can't be null");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }


  @Test
  public void testBrightenByLuma() {
    this.init();

    IPixel pixel = new Pixel(5, 5, 5);
    List<List<IPixel>> pixels = new ArrayList<>();


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

    for (List<IPixel> list : this.layer3.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(80, p.getRedComponent());
        assertEquals(80, p.getGreenComponent());
        assertEquals(80, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBrightenByLuma2() {
    this.init();

    List<List<IPixel>> pixels = new ArrayList<>();


    for (int i = 0; i < 6; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 6; j++) {
        pixels.get(i).add(this.pixel1);
      }
    }

    this.layer2.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(6, 6, "brighten-luma", true);
    brightenMacroLuma.executeMacro(this.layer2);

    for (List<IPixel> list : this.layer2.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(0, p.getRedComponent());
        assertEquals(0, p.getGreenComponent());
        assertEquals(0, p.getBlueComponent());
        assertEquals(1, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBrightenByLuma3() {
    this.init();

    List<List<IPixel>> pixels = new ArrayList<>();


    for (int i = 0; i < 20; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 20; j++) {
        pixels.get(i).add(this.pixel2);
      }
    }

    this.layer4.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(20, 20, "brighten-luma", true);
    brightenMacroLuma.executeMacro(this.layer4);

    for (List<IPixel> list : this.layer4.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(255, p.getRedComponent());
        assertEquals(255, p.getGreenComponent());
        assertEquals(255, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBrightenByLuma4() {
    this.init();

    List<List<IPixel>> pixels = new ArrayList<>();

    for (int i = 0; i < 1; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 1; j++) {
        pixels.get(i).add(this.pixel7);
      }
    }

    this.layer6.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(1, 1, "brighten-luma", true);
    brightenMacroLuma.executeMacro(this.layer6);

    for (List<IPixel> list : this.layer6.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(8, p.getRedComponent());
        assertEquals(8, p.getGreenComponent());
        assertEquals(8, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }
  }


  @Test
  public void testBrightenByIntensity() {
    this.init();

    List<List<IPixel>> pixelsOnCurrentLayer = new ArrayList<>();


    for (int i = 0; i < 15; i++) {
      pixelsOnCurrentLayer.add(new ArrayList<>());
      for (int j = 0; j < 15; j++) {
        pixelsOnCurrentLayer.get(i).add(this.pixel4);
      }
    }

    this.layer1.addImage(0, 0, pixelsOnCurrentLayer);

    MacroCollageEffects macroCollageEffects = new BrightenDarkenMacro(15,
            15, "brighten-intensity", true);

    macroCollageEffects.executeMacro(this.layer1);

    for (List<IPixel> lop1 : this.layer1.getPixelsOnLayer()) {
      for (IPixel p : lop1) {
        assertEquals(255, p.getRedComponent());
        assertEquals(255, p.getGreenComponent());
        assertEquals(255, p.getBlueComponent());
        assertEquals(21, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBrightenByIntensity2() {
    this.init();

    List<List<IPixel>> pixels = new ArrayList<>();


    for (int i = 0; i < 6; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 6; j++) {
        pixels.get(i).add(this.pixel1);
      }
    }

    this.layer2.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(6, 6, "brighten-intensity", true);
    brightenMacroLuma.executeMacro(this.layer2);

    for (List<IPixel> list : this.layer2.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(0, p.getRedComponent());
        assertEquals(0, p.getGreenComponent());
        assertEquals(0, p.getBlueComponent());
        assertEquals(1, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBrightenByIntensity3() {
    this.init();

    List<List<IPixel>> pixels = new ArrayList<>();


    for (int i = 0; i < 20; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 20; j++) {
        pixels.get(i).add(this.pixel2);
      }
    }

    this.layer4.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(20, 20, "brighten-intensity", true);
    brightenMacroLuma.executeMacro(this.layer4);

    for (List<IPixel> list : this.layer4.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(255, p.getRedComponent());
        assertEquals(255, p.getGreenComponent());
        assertEquals(255, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBrightenByIntensity4() {
    this.init();

    List<List<IPixel>> pixels = new ArrayList<>();

    for (int i = 0; i < 1; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 1; j++) {
        pixels.get(i).add(this.pixel7);
      }
    }

    this.layer6.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(1, 1, "brighten-intensity", true);
    brightenMacroLuma.executeMacro(this.layer6);

    for (List<IPixel> list : this.layer6.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(8, p.getRedComponent());
        assertEquals(8, p.getGreenComponent());
        assertEquals(8, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBrightenByValue() {
    this.init();

    List<List<IPixel>> pixelsOnCurrentLayer = new ArrayList<>();


    for (int i = 0; i < 6; i++) {
      pixelsOnCurrentLayer.add(new ArrayList<>());
      for (int j = 0; j < 6; j++) {
        pixelsOnCurrentLayer.get(i).add(this.pixel3);
      }
    }

    this.layer2.addImage(0, 0, pixelsOnCurrentLayer);

    MacroCollageEffects macroCollageEffects = new BrightenDarkenMacro(6,
            6, "brighten-value", true);

    macroCollageEffects.executeMacro(this.layer2);

    for (List<IPixel> lop1 : this.layer2.getPixelsOnLayer()) {
      for (IPixel p : lop1) {
        assertEquals(255, p.getRedComponent());
        assertEquals(255, p.getGreenComponent());
        assertEquals(255, p.getBlueComponent());
        assertEquals(100, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBrightenByValue2() {
    this.init();

    List<List<IPixel>> pixels = new ArrayList<>();


    for (int i = 0; i < 6; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 6; j++) {
        pixels.get(i).add(this.pixel1);
      }
    }

    this.layer2.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(6, 6, "brighten-value", true);
    brightenMacroLuma.executeMacro(this.layer2);

    for (List<IPixel> list : this.layer2.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(0, p.getRedComponent());
        assertEquals(0, p.getGreenComponent());
        assertEquals(0, p.getBlueComponent());
        assertEquals(1, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBrightenByValue3() {
    this.init();

    List<List<IPixel>> pixels = new ArrayList<>();


    for (int i = 0; i < 20; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 20; j++) {
        pixels.get(i).add(this.pixel2);
      }
    }

    this.layer4.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(20, 20, "brighten-value", true);
    brightenMacroLuma.executeMacro(this.layer4);

    for (List<IPixel> list : this.layer4.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(255, p.getRedComponent());
        assertEquals(255, p.getGreenComponent());
        assertEquals(255, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testBrightenByValue4() {
    this.init();

    List<List<IPixel>> pixels = new ArrayList<>();

    for (int i = 0; i < 1; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 1; j++) {
        pixels.get(i).add(this.pixel7);
      }
    }

    this.layer6.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(1, 1, "brighten-value", true);
    brightenMacroLuma.executeMacro(this.layer6);

    for (List<IPixel> list : this.layer6.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(8, p.getRedComponent());
        assertEquals(8, p.getGreenComponent());
        assertEquals(8, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testDarkenByLuma1() {
    this.init();


    List<List<IPixel>> pixels = new ArrayList<>();


    for (int i = 0; i < 2; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        pixels.get(i).add(this.pixel6);
      }
    }

    this.layer3.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(2, 2, "darken-luma", false);
    brightenMacroLuma.executeMacro(this.layer3);

    for (List<IPixel> list : this.layer3.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(0, p.getRedComponent());
        assertEquals(0, p.getGreenComponent());
        assertEquals(35, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testDarkenByIntensity1() {
    this.init();

    List<List<IPixel>> pixels = new ArrayList<>();


    for (int i = 0; i < 100; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 100; j++) {
        pixels.get(i).add(this.pixel4);
      }
    }

    this.layer5.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(100, 100, "darken-intensity", false);
    brightenMacroLuma.executeMacro(this.layer5);

    for (List<IPixel> list : this.layer5.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(1, p.getRedComponent());
        assertEquals(0, p.getGreenComponent());
        assertEquals(0, p.getBlueComponent());
        assertEquals(21, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testDarkenByValue1() {
    this.init();

    List<List<IPixel>> pixels = new ArrayList<>();


    for (int i = 0; i < 15; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 15; j++) {
        pixels.get(i).add(this.pixel4);
      }
    }

    this.layer1.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(15, 15, "darken-value", false);
    brightenMacroLuma.executeMacro(this.layer1);

    for (List<IPixel> list : this.layer1.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(0, p.getRedComponent());
        assertEquals(0, p.getGreenComponent());
        assertEquals(0, p.getBlueComponent());
        assertEquals(21, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testDarkenByValue2() {
    this.init();


    List<List<IPixel>> pixels = new ArrayList<>();


    for (int i = 0; i < 20; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 20; j++) {
        pixels.get(i).add(this.pixel2);
      }
    }

    this.layer4.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(20, 20, "darken-value", false);
    brightenMacroLuma.executeMacro(this.layer4);

    for (List<IPixel> list : this.layer4.getPixelsOnLayer()) {
      for (IPixel p : list) {
        assertEquals(0, p.getRedComponent());
        assertEquals(0, p.getGreenComponent());
        assertEquals(0, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }
  }

  @Test
  public void testDarkenByValue3() {
    this.init();

    List<List<IPixel>> pixels = new ArrayList<>();

    for (int i = 0; i < 2; i++) {
      pixels.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        pixels.get(i).add(this.pixel6);
      }
    }

    this.layer5.addImage(0, 0, pixels);

    MacroCollageEffects brightenMacroLuma =
            new BrightenDarkenMacro(2, 2, "darken-value", false);
    brightenMacroLuma.executeMacro(this.layer5);

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(0, this.layer5.getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(0, this.layer5.getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(0, this.layer5.getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(255, this.layer5.getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }
  }
}
