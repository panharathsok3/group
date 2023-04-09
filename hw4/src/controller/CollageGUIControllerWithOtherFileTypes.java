package controller;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
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
    ColorModel colorModel;
    int pixel;

    try {
      image = ImageIO.read(file);
      colorModel = image.getColorModel();
      colorSpace = colorModel.getColorSpace();

      for (int i = 0; i < colorSpace.getNumComponents(); i ++) {

      }

    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    return null;
  }
}
