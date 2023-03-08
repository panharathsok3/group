import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import model.CollageProject;
import model.CollageProjectModel;
import model.CollageProjectModelImpl;
import model.Layer;
import model.Pixel;

import static org.junit.Assert.assertEquals;

public class CollageImplTest {

  Layer layer1;
  Layer layer2;
  Layer layer3;

  CollageProject project1;
  CollageProject project2;
  CollageProject project3;
  CollageProject project4;


  @Before
  public void init() {
    this.layer1 = new Layer("L1", 10, 10, 0);
    this.layer2 = new Layer("L2", 10, 10, 0);
    this.layer3 = new Layer("L3", 10, 10, 0);
    this.project1 = new CollageProjectModelImpl(20, 20);
    this.project2 = new CollageProjectModelImpl(10, 10);
    this.project3 = new CollageProjectModelImpl(100, 100);
    this.project4 = new CollageProjectModelImpl(15, 20);
  }


  @Test
  public void testSavePPM() {

  }

  /*
   @Override
  public void newProject(int canvasHeight, int canvasWidth) {
    CollageProjectModelImpl projectModel = new CollageProjectModelImpl(canvasHeight, canvasWidth);
    this.addLayerToProject("Background");
    this.backgroundMade = true;
  }
   */


  @Test
  public void testCreateNewProject() {
    CollageProjectModel collageProjectModel;
  }


  @Test
  public void testAddLayerToProject() {
    this.project1.addLayerToProject(layer1.getName());

    this.layer1.getPixelsOnLayer().get(1).get(1);

    assertEquals(255,layer1.getPixelsOnLayer());
  }

  @Test
  public void addLayerWithUsedName() {
    this.project2.addLayerToProject("");
  }

}
