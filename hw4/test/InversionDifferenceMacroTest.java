import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import model.ILayer;
import model.IPixel;
import model.Layer;
import model.Pixel;
import model.effects.InversionDifferenceMacro;
import model.effects.MacroCollageEffects;
import org.junit.Before;
import org.junit.Test;

/**
 * A InversionDifferenceMacroTest is a test class for InversionDifferenceMacro.
 */
public class InversionDifferenceMacroTest {

  ILayer layer1;
  ILayer layer2;
  ILayer layer3;
  ILayer layer4;

  @Before
  public void init() {
    this.layer1 = new Layer("L1", 2, 2, 255);
    this.layer2 = new Layer("L2", 2, 2, 0);
    this.layer3 = new Layer("L3", 2, 2, 255);
    this.layer4 = new Layer("L4", 2, 2, 255);
  }

  @Test
  public void invalidConstruction() {
    try {
      MacroCollageEffects macro = new InversionDifferenceMacro(1, 1 , null);
      fail("Arguments can't be null");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }

  @Test
  public void validExecuteMacro() {
    this.init();
    List<List<IPixel>> pixelsOnCurrentLayer = new ArrayList<>();


    for (int i = 0; i < 2; i++) {
      pixelsOnCurrentLayer.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        pixelsOnCurrentLayer.get(i).add(new Pixel(1, 1, 1));
      }
    }

    MacroCollageEffects macro = new InversionDifferenceMacro(2, 2, pixelsOnCurrentLayer);
    macro.executeMacro(this.layer3);

    for (List<IPixel> lop : this.layer3.getPixelsOnLayer()) {
      for (IPixel p : lop) {
        assertEquals(254, p.getRedComponent());
        assertEquals(254, p.getGreenComponent());
        assertEquals(254, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }

    List<List<IPixel>> pixelsOnCurrentLayer2 = new ArrayList<>();


    for (int i = 0; i < 2; i++) {
      pixelsOnCurrentLayer2.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        pixelsOnCurrentLayer2.get(i).add(new Pixel(144,
                123, 50));
      }
    }


    this.layer4.addImage(0, 0, pixelsOnCurrentLayer2);
    macro.executeMacro(this.layer4);

    for (List<IPixel> lop : this.layer4.getPixelsOnLayer()) {
      for (IPixel p : lop) {
        assertEquals(143, p.getRedComponent());
        assertEquals(122, p.getGreenComponent());
        assertEquals(49, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }


  }

  @Test
  public void validExecuteMacro2() {
    this.init();
    List<List<IPixel>> pixelsOnCurrentLayer = new ArrayList<>();

    for (int i = 0; i < 2; i++) {
      pixelsOnCurrentLayer.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        pixelsOnCurrentLayer.get(i).add(new Pixel(20, 30,
                40));
      }
    }

    MacroCollageEffects macro = new InversionDifferenceMacro(2, 2, pixelsOnCurrentLayer);
    macro.executeMacro(this.layer2);

    for (List<IPixel> lop : this.layer2.getPixelsOnLayer()) {
      for (IPixel p : lop) {
        assertEquals(235, p.getRedComponent());
        assertEquals(225, p.getGreenComponent());
        assertEquals(215, p.getBlueComponent());
        assertEquals(0, p.getAlphaComponent());
      }
    }

    List<List<IPixel>> image2 = new ArrayList<>();


    for (int i = 0; i < 2; i++) {
      image2.add(new ArrayList<>());
      for (int j = 0; j < 2; j++) {
        image2.get(i).add(new Pixel(210, 200, 170));
      }
    }


    this.layer4.addImage(0, 0, image2);
    macro.executeMacro(this.layer4);

    for (List<IPixel> lop : this.layer4.getPixelsOnLayer()) {
      for (IPixel p : lop) {
        assertEquals(190, p.getRedComponent());
        assertEquals(170, p.getGreenComponent());
        assertEquals(130, p.getBlueComponent());
        assertEquals(255, p.getAlphaComponent());
      }
    }


  }
}