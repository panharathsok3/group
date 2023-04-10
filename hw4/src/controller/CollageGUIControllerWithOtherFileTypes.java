package controller;

import java.awt.Transparency;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;
import model.CollageProject;
import model.IPixel;
import view.CollageView;

/**
 *
 */
public class CollageGUIControllerWithOtherFileTypes extends CollageControllerImpl
    implements CollageControllerWithOtherFileTypes {

  public CollageGUIControllerWithOtherFileTypes(Readable in, CollageProject collage,
      CollageView view) throws IllegalArgumentException {
    super(in, collage, view);
  }

  public CollageGUIControllerWithOtherFileTypes(CollageProject collage, boolean projectMade) {
    super(collage, projectMade);
  }

  @Override
  public List<List<IPixel>> readImage(String filename) {
    File file = new File(filename);

    BufferedImage image;
    ColorSpace colorSpace;
    int pixel;

    try {
      image = ImageIO.read(file);

      for (int i = 0; i < image.getHeight(); i ++) {
        for (int j = 0; j < image.getWidth(); j++) {
          int rgb = image.getRGB(j, i);
          int red = (rgb >> 16) & 0x000000FF;
          int green = (rgb >> 8) & 0x000000FF;
          int blue = (rgb) & 0x000000FF;
        }
      }

    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    return null;
  }
}
