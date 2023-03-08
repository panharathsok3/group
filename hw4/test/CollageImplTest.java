import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import model.CollageProject;
import model.CollageProjectModelImpl;
import model.Layer;
import model.Pixel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CollageImplTest {

  Pixel pixel1;
  Pixel pixel2;
  Pixel pixel3;
  Pixel pixel4;
  Pixel pixel5;
  Layer layer1;
  Layer layer2;
  Layer layer3;

  CollageProject project1;
  CollageProject project2;
  CollageProject project3;
  CollageProject project4;


  @Before
  public void init() {
    this.pixel1 = new Pixel(0, 0, 0, 1);
    this.pixel2 = new Pixel(120, 72, 99);
    this.pixel3 = new Pixel(21, 50, 68, 100);
    this.pixel4 = new Pixel(12, 11, 10);
    this.pixel5 = new Pixel(1, 1, 1);
    this.layer1 = new Layer("L1", 10, 10, 0);
    this.layer2 = new Layer("L2", 10, 10, 0);
    this.layer3 = new Layer("L3", 10, 10, 0);
    this.project1 = new CollageProjectModelImpl();
    this.project2 = new CollageProjectModelImpl();
    this.project3 = new CollageProjectModelImpl();
    this.project4 = new CollageProjectModelImpl();
  }


  @Test
  public void testSavePPM() {
    this.init();

  }

  @Test
  public void testCreateNewProject() {
    this.init();
    this.project4.newProject("C1", 100, 100);
  }


  @Test
  public void testAddLayerToProject() {
    this.init();

    this.project2.newProject("C1", 5, 5);

    this.project2.addLayer(this.layer2.getName());
    this.project2.addLayer(this.layer3.getName());
    this.project2.addLayer(this.layer1.getName());

    this.project2.setFilter(this.layer2.getName(),"red-component");
    this.project2.setFilter(this.layer2.getName(),"darken-luma");

    this.project2.setFilter(this.layer3.getName(),"green-component");
    this.project2.setFilter(this.layer1.getName(),"brightness-value");


    int blueAfterRedFilter = this.layer2.getPixelsOnLayer().get(5).get(5).getBlueComponent();
    int greenAfterRedFilter = this.layer2.getPixelsOnLayer().get(5).get(5).getBlueComponent();

    assertEquals(0, blueAfterRedFilter);
    assertEquals(0,greenAfterRedFilter);
  }

  @Test
  public void addLayerWithUsedName() {
    this.init();
    this.project1.newProject("C1", 20, 2);
    this.project1.addLayer(this.layer1.getName());
    this.project1.addLayer(this.layer2.getName());
    this.project1.addLayer(this.layer3.getName());


    try{
      this.project1.addLayer("L1");
      fail("There is already a layer with the name L1");
    } catch (IllegalStateException illegalStateException) {
      // do nothing because we want it to fail;
    }
  }

  @Test
  public void testCommandsWithoutCreatingNewProject() {
    this.init();

    try {
      this.project1.addLayer(this.layer1.getName());
      fail("A project has not been created yet");
    } catch (IllegalStateException projectNotMade) {
      // do nothing because we want it to fail;
    }


    try {
      this.project1.addImageToLayer(this.layer2.getName(),"src/tako.ppm",2,2);
      fail("A project has not been created yet");
    } catch (IllegalStateException projectNotMade) {
      // do nothing because we want it to fail;
    }

    try{
      this.project1.setFilter(this.layer3.getName(),"darken-luma");
      fail("A project has not been created yet");
    } catch (IllegalStateException projectNotMade) {
      // do nothing because we want it to fail;
    }

    try{
      this.project1.saveImage("src/tako.ppm");
      fail("A project has not been created yet");
    }catch (IllegalStateException projectNotMade) {
      // do nothing because we want it to fail;
    }




  }

  @Test
  public void nullArgsForSetFilter() {
    try{
      this.project1.setFilter(null,"brighten-intensity");
      fail("Arguments can't be null");
    } catch(IllegalArgumentException illegalArgumentException) {
      // do nothing because we want it to fail;
    }

    try{
      this.project1.setFilter(this.layer3.getName(),null);
      fail("Arguments can't be null");
    } catch(IllegalArgumentException illegalArgumentException) {
      // do nothing because we want it to fail;
    }
  }


  @Test
  public void nullArgsForSaveProjects() {
    try{
      this.project1.saveProject(null,"ppm");
      fail("Arguments can't be null");
    } catch(IllegalArgumentException illegalArgumentException) {
      // do nothing because we want it to fail;
    }

    try{
      this.project1.setFilter("src/fileName",null);
      fail("Arguments can't be null");
    } catch(IllegalArgumentException illegalArgumentException) {
      // do nothing because we want it to fail;
    }
  }

}
