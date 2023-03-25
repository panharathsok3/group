import static org.junit.Assert.assertEquals;

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

public class InversionDifferenceMacroTest {

  ILayer layer1;
  ILayer layer2;
  ILayer layer3;
  ILayer layer4;

  @Before
  public void init() {
    this.layer1 = new Layer("L1", 10, 20, 255);
    this.layer2 = new Layer("L2", 10, 15, 0);
    this.layer3 = new Layer("L3", 2, 2, 255);
    this.layer4 = new Layer("L4", 100, 100, 20);
  }

  @Test
  public void invalidConstruction() {
    try {
      MacroCollageEffects macro = new InversionDifferenceMacro(1, 1 , null);
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



  }
}