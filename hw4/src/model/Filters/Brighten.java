package model.Filters;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import model.CollageProjectModelImpl;
import model.Layer;
import model.Pixel;

/**
 * This class handles the operation to brighten an image
 * by doing arithmetic on its colors.
 */
public class Brighten extends Filter {

  private final int brightenValue;
  private String layerName;

  private CollageProjectModelImpl model;

  private final LinkedHashMap<String, Layer> collageDirectory;


  public Brighten(String layerName, int filterValue) {
    super(layerName, "Brighten");
    this.brightenValue = filterValue;
    this.collageDirectory = new LinkedHashMap<>();
  }


  //add a positive number to the red , green and blue comps.
//  public Layer setBrighten(String layerName) {
//
//
//    Layer layer = collageDirectory.get(layerName);
//
//    for (int row = 0; row < l; row += 1) {
//      for (int col = 0; col < layer.getWidth(model); col += 1) {
//
//      //get all the original colors on the current layer
//        ArrayList<Pixel> layers = new ArrayList<Pixel>();
//        Layer currentLayerColors = layer.get(row).get(col);
//
//        //getting the current colors
//
//
//
//        int red = currentLayerColors.getGraphics().getColor().getRed();
//        int green = currentLayerColors.getGraphics().getColor().getGreen();
//        int blue = currentLayerColors.getGraphics().getColor().getBlue();
//        int maxValue = 255;
//
//        Color newImageColor = new Color(
//                Math.min(Math.max(red + brightenValue, 0), maxValue),
//                Math.min(Math.max(green + brightenValue, 0), maxValue),
//                Math.min(Math.max(blue + brightenValue, 0), maxValue));
//
//      }
//    }
//    //update the collage Directory with the newLayerColor and layer
//    //ToDo:wrong have to change.
//    collageDirectory.put(layerName, layer);
//    return new Layer(layerName);
//  }

}


