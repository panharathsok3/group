package model.effects;

import java.util.List;
import model.ILayer;
import model.IPixel;
import model.Pixel;

/**
 * This class is used to apply a difference blending filter onto a layer.
 */
public class InversionDifferenceMacro implements MacroCollageEffects {

  private final int height;
  private final int width;
  private final List<List<IPixel>> pixelsLayerBelow;

  /**
   * Creates an InversionDifferenceMacro to difference blending filter onto a layer.
   * @param pixelsLayerBelow the 2D List of the pixel below
   * @throws IllegalArgumentException if the given argument is null
   */
  public InversionDifferenceMacro(int height, int width, List<List<IPixel>> pixelsLayerBelow)
      throws IllegalArgumentException {
    if (pixelsLayerBelow == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }
    this.height = height;
    this.width = width;
    this.pixelsLayerBelow = pixelsLayerBelow;
  }

  @Override
  public void executeMacro(ILayer layer) throws IllegalArgumentException {
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        int newRed = Math.abs(layer.getPixelsOnLayer().get(i).get(j).getRedComponent()
                                - pixelsLayerBelow.get(i).get(j).getRedComponent());
        int newGreen = Math.abs(layer.getPixelsOnLayer().get(i).get(j).getGreenComponent()
            - pixelsLayerBelow.get(i).get(j).getGreenComponent());
        int newBlue = Math.abs(layer.getPixelsOnLayer().get(i).get(j).getBlueComponent()
            - pixelsLayerBelow.get(i).get(j).getBlueComponent());
        int alpha = layer.getPixelsOnLayer().get(i).get(j).getAlphaComponent();
        layer.getPixelsOnLayer().get(i).set(j, new Pixel(newRed, newGreen, newBlue, alpha));
      }
    }
  }
}
