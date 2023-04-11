package controller;

import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import model.CollageProject;
import model.ILayer;
import model.IPixel;
import model.Pixel;
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
    List<List<IPixel>> result = new ArrayList<>();

    try {
      image = ImageIO.read(file);


      for (int i = 0; i < image.getHeight(); i ++) {
        result.add(new ArrayList<>());
        for (int j = 0; j < image.getWidth(); j++) {
          Color color = new Color(image.getRGB(j, i));
          int red = color.getRed();
          int green = color.getGreen();
          int blue = color.getBlue();
          int alpha = color.getAlpha();
          result.get(i).add(new Pixel(red, green, blue, alpha));
        }
      }

    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    return result;
  }

  @Override
  public void saveImage(String filePath) throws IllegalArgumentException, IllegalStateException {
    this.throwExceptionProjectNotMade();

    if (filePath == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

//    ILayer finalImage;
//    if (filePath.endsWith(".png") || filePath.endsWith("jpg")) {
//      finalImage = this.collage.makeFinalImage(false);
//      List<ILayer> listLayer = new ArrayList<>();
//      listLayer.add(finalImage);
//
//
//    }
  }


}
