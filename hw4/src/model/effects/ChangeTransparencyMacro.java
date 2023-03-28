package model.effects;

import java.util.List;
import model.ILayer;
import model.IPixel;
import model.Pixel;

/**
 * This class changes the transparency of the given layer based on the composite image from the
 * previous layer.
 */
public class ChangeTransparencyMacro implements MacroCollageEffects {

  private final int height;
  private final int width;
  private final boolean hasAlpha;
  private final List<List<IPixel>> prevLayer;

  /**
   * Creates a ChangeTransparencyMacro which changes the transparency of the given layer based on
   * the composite image from the previous layer.
   * @param height the height of the layer
   * @param width the width of the layer
   * @param hasAlpha true if and only if the original image has an alpha value
   * @param prevLayer a 2D array of IPixels from the previous layer
   * @throws IllegalArgumentException if the given arguments are null
   */
  public ChangeTransparencyMacro(int height, int width, boolean hasAlpha,
      List<List<IPixel>> prevLayer) throws IllegalArgumentException {
    if (prevLayer == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    this.height = height;
    this.width = width;
    this.hasAlpha = hasAlpha;
    this.prevLayer = prevLayer;
  }

  @Override
  public void executeMacro(ILayer layer) throws IllegalArgumentException {
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        int red = layer.getPixelsOnLayer().get(i).get(j).getRedComponent();
        int green = layer.getPixelsOnLayer().get(i).get(j).getGreenComponent();
        int blue = layer.getPixelsOnLayer().get(i).get(j).getBlueComponent();
        int alpha = layer.getPixelsOnLayer().get(i).get(j).getAlphaComponent();

        if (!this.hasAlpha) {
          red = (int) (red * alpha / 255f);
          green = (int) (green * alpha / 255f);
          blue = (int) (blue * alpha / 255f);
        } else {
          int originalAlpha = alpha;
          int dR = this.prevLayer.get(i).get(j).getRedComponent();
          int dG = this.prevLayer.get(i).get(j).getGreenComponent();
          int dB = this.prevLayer.get(i).get(j).getBlueComponent();
          int dA = this.prevLayer.get(i).get(j).getAlphaComponent();

          double alphaPrime = (originalAlpha / 255f) + (dA / 255f) * (1 - (originalAlpha / 255f));

          red = (int) (((originalAlpha / 255f * red) + (dR * (dA / 255f)
              * (1 - (originalAlpha / 255f)))) * (1f / alphaPrime));
          green = (int) (((originalAlpha / 255f * green) + (dG * (dA / 255f)
              * (1 - (originalAlpha / 255f)))) * (1f / alphaPrime));
          blue = (int) (((originalAlpha / 255f * blue) + (dB * (dA / 255f)
              * (1 - (originalAlpha / 255f)))) * (1f / alphaPrime));
          alpha = (int) (alphaPrime * 255);
        }

        layer.getPixelsOnLayer().get(i).set(j, new Pixel(red, green, blue, alpha));
      }
    }
  }
}
