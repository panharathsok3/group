package Filters;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

import model.CollageProjectModelImpl;

/**
 * This class handles the operation to brighten an image
 * by doing arithmetic on its colors.
 */
public class Brighten extends Filter {

  private final int brightenValue;
  private String layerName;

  private Map<String, ArrayList<ArrayList<Image>>> collageDirectory;

  public Brighten(int filterValue) {
    super("Brighten");
    this.brightenValue = filterValue;
  }


  //add a positive number to the red , green and blue comps.
  public void setBrighten(int brightenValue, String layerName) {

    ArrayList<ArrayList<Image>> layer = collageDirectory.get(layerName);

    for (int row = 0; row < layer.size(); row += 1) {
      for (int col = 0; col < layer.get(0).size(); col += 1) {

        Image currentImageColor = layer.get(row).get(col);

        //getting the current colors
        int red = currentImageColor.getGraphics().getColor().getRed();
        int green = currentImageColor.getGraphics().getColor().getGreen();
        int blue = currentImageColor.getGraphics().getColor().getBlue();
        int maxValue = 255;

        Color newImageColor = new Color(
                Math.min(Math.max(red + brightenValue, 0), maxValue),
                Math.min(Math.max(green + brightenValue, 0), maxValue),
                Math.min(Math.max(blue + brightenValue, 0), maxValue));

//        layer.get(row).get(col) = newImageColor;
//        layer.set(currentImageColor, newImageColor);
      }
    }
    //update the collage Directory with the newLayerColor and layer
    //ToDo:wrong have to change.
    collageDirectory.put(layerName,layer);

  }

}


