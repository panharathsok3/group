package controller;

import java.util.List;
import model.IPixel;

/**
 *
 */
public interface CollageControllerWithOtherFileTypes extends CollageController {

  /**
   *
   * @param filename
   * @return
   */
  List<List<IPixel>> readImage(String filename);
}
