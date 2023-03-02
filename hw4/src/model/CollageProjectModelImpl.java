package model;


import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CollageProjectModelImpl implements CollageProjectModel {

  //Hashmap that internally matches a layers to its pixels.
  private final Map<String, ArrayList<ArrayList<Image>>> collageDirectory;

 private int canvasHeight;

 private int canvasWidth;

 private final float[][] filter;


  /**
   * Creates a collage with images.
   */
  public CollageProjectModelImpl() {
    this.collageDirectory = new HashMap<>();
    this.filter = filter;
  }

  public CollageProjectModelImpl(int canvasHeight, int canvasWidth) {
    this.collageDirectory = new HashMap<>();
    this.filter = filter;
  }


  @Override
  public int getHeight(String name) {
    return this.canvasHeight;
  }

  //the name of each layer
  @Override
  public int getWidth(String name) {
    return this.canvasWidth;
  }

  @Override
  public Color[][] getIndividualComponent(String name) {
    return new Color[0][];
  }

  @Override
  public void newProject(String name, int height, int width) {

  }

  @Override
  public void addLayerToProject(String name) {

  }

  @Override
  public void addImageToLayer(String layerName, String imageName, int xPos, int yPos) {

  }


  //key value pairs
  private void UpdateCollageDirectory(String layerName,ArrayList<ArrayList<Image>> image ) {
    this.collageDirectory.put(layerName,image);
  }

  @Override
  public void setFilter(String layerName, String filterOption, double filterValue) {

    ArrayList<ArrayList<Image>> project = collageDirectory.get(layerName);

    float r = 0;
    float g = 0;
    float b = 0;

    for (int row = 0; row < project.size(); row++) {
      for (int col = 0; col < project.get(0).size(); col++) {

     Image currentLayer = project.get(row).get(col);
        int red = currentLayer.getGraphics().getColor().getRed();
        int green = currentLayer.getGraphics().getColor().getGreen();
        int blue = currentLayer.getGraphics().getColor().getBlue();



        r += filterValue * currentLayer.getGraphics().getColor().getRed();
        g += filterValue * currentLayer.getGraphics().getColor().getRed();
        b += filterValue * currentLayer.getGraphics().getColor().getRed();



        int newRedColor = (int) Math.round((filterValue[0] * red) + (filterValue[1] * green) + (filterValue[2] * blue));
        int newGreenColor = (int) Math.round((filterValue[3] * red) + (filterValue[4] * green) + (filterValue[5] * blue));
        int newBlueColor = (int) Math.round((filterValue[6] * red) + (filterValue[7] * green) + (filterValue[8] * blue));

       return new Color(Math.max(0,Math.min(255,Math.round(r)))),

        Math.max(Math.min(newRedColor, 255), 0));
        (Math.max(Math.min(newGreenColor, 255), 0));
        (Math.max(Math.min(newBlueColor, 255), 0));
      }
    }

  }

}
