import model.Layer;
import model.Pixel;

/**
 * This is used for testing the Macros.
 */
public abstract class AbstractMacroTest {

  Pixel pixel1;
  Pixel pixel2;
  Pixel pixel3;
  Pixel pixel4;
  Pixel pixel5;
  Layer layer1;
  Layer layer2;
  Layer layer3;

  /**
   * Initializes the Macro.
   */
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

}
