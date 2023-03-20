package model.effects;


import java.util.List;
import model.ILayer;
import model.IPixel;
import model.Pixel;

public class DarkenMultiplyBrightenScreenMacro implements MacroCollageEffects {

  private final int height;
  private final int width;
  private final List<List<IPixel>> prevLayerImage;
  private final boolean brightenDarken;

  /**
   *
   * @param height
   * @param width
   * @param prevLayerImage
   * @param brightenDarken true if and only if this is used for darken-multiply and it will be used
   *                       for brighten-screen otherwise
   * @throws IllegalArgumentException
   */
  public DarkenMultiplyBrightenScreenMacro(int height, int width, List<List<IPixel>> prevLayerImage,
      boolean brightenDarken) throws IllegalArgumentException {

    if (prevLayerImage == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    this.height = height;
    this.width = width;
    this.prevLayerImage = prevLayerImage;
    this.brightenDarken = brightenDarken;
  }

  @Override
  public void executeMacro(ILayer layer) throws IllegalArgumentException {

    if (layer == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        IPixel pixel = layer.getPixelsOnLayer().get(i).get(j).convertRGBtoHSL();
        IPixel pixelPrev = this.prevLayerImage.get(i).get(j).convertRGBtoHSL();

        double hue = pixel.getHueComponent();
        double saturation = pixel.getSaturationComponent();
        double lightness = pixel.getLightnessComponent();

        double prevLightness = pixelPrev.getLightnessComponent();

        if (this.brightenDarken) {
          layer.getPixelsOnLayer().get(i).set(j, new Pixel(hue, saturation,
              lightness * prevLightness).convertHSLtoRGB());
        } else {
          layer.getPixelsOnLayer().get(i).set(j, new Pixel(hue, saturation,
              (1 - ((1 - lightness) * (1 - prevLightness)))).convertHSLtoRGB());
        }
      }
    }
  }
}
