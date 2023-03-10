import org.junit.Before;
import org.junit.Test;

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
  CollageProject collage1;
  CollageProject collage2;
  CollageProject collage3;
  CollageProject collage4;


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

    this.collage1 = new CollageProjectModelImpl();
    this.collage2 = new CollageProjectModelImpl();
    this.collage3 = new CollageProjectModelImpl();
    this.collage4 = new CollageProjectModelImpl();
  }

  @Test
  public void testValidCreateNewProject() {
    this.init();
    this.collage1.newProject("C1", 100, 100);
    this.collage2.newProject("C2", 20, 20);
    this.collage3.newProject("C3", 10, 10);
    this.collage4.newProject("C4", 2, 2);


  }

  @Test
  public void testInvalidCreateNewProject() {
    this.init();

    try {
      this.collage2.newProject(null, 20, 20);
      fail("Project name cannot be null");
    } catch (IllegalArgumentException iae) {
      // do nothing because we want it to fail.
    }

    try {
      this.collage2.newProject("C1", 0, 20);
      fail("Canvas height cannot be less than 1");
    } catch (IllegalArgumentException iae) {
      // do nothing because we want it to fail.
    }

    try {
      this.collage2.newProject("C1", 20, -2);
      fail("Canvas width cannot be less than 1");
    } catch (IllegalArgumentException iae) {
      // do nothing because we want it to fail.
    }

    try {
      this.collage2.newProject(null, -2000, 0);
      fail("Arguments name cannot be null, width and height cannot be less than 1");
    } catch (IllegalArgumentException iae) {
      // do nothing because we want it to fail.
    }

    try {
      this.collage2.newProject("", 2, 2);
      fail("Project name cannot be empty");
    } catch (IllegalArgumentException iae) {
      // do nothing because we want it to fail.
    }

  }

  @Test
  public void testInvalidAddLayer() {
    this.init();

    try {
      this.collage1.newProject("C1", 5, 5);
      this.collage1.addLayer(null);
      fail("layer name cannot be null");
    } catch (IllegalArgumentException illegalArgumentException) {
      // do nothing
    }

    try{
      this.collage2.addLayer("L0");
      fail("A  project must be created first");
    } catch (IllegalStateException illegalStateException) {
      //do nothing
    }

    try{
      this.collage1.newProject("C1", 5, 5);
      this.collage1.addLayer("L1");
      this.collage1.addLayer("L1");

      this.collage2.newProject("C2",10,10);
      this.collage2.addLayer("L1");
      this.collage2.addLayer("L2");
      this.collage2.addLayer("L1");
      this.collage2.addLayer("L2");
      fail("Cant make a layer with an already used name");
    } catch (IllegalStateException illegalStateException) {
      //do nothing
    }
  }


  @Test
  public void testAddLayerToProject() {
    this.init();

    this.collage1.newProject("C1", 5, 5);

    this.collage1.addLayer(this.layer2.getName());
    this.collage1.addLayer(this.layer3.getName());
    this.collage1.addLayer(this.layer1.getName());
  }

  @Test
  public void testCommandsWithoutCreatingNewProject() {
    this.init();

    try {
      this.collage1.addLayer(this.layer1.getName());
      fail("A project has not been created yet");
    } catch (IllegalStateException projectNotMade) {
      // do nothing because we want it to fail;
    }


    try {
      this.collage1.addImageToLayer(this.layer2.getName(), "src/tako.ppm", 2, 2);
      fail("A project has not been created yet");
    } catch (IllegalStateException projectNotMade) {
      // do nothing because we want it to fail;
    }

    try {
      this.collage1.setFilter(this.layer3.getName(), "darken-luma");
      fail("A project has not been created yet");
    } catch (IllegalStateException projectNotMade) {
      // do nothing because we want it to fail;
    }

    try {
      this.collage1.saveImage("src/tako.ppm");
      fail("A project has not been created yet");
    } catch (IllegalStateException projectNotMade) {
      // do nothing because we want it to fail;
    }
  }

  @Test
  public void testInvalidArgsAddImageToLayer() {
    this.init();

    try{
      this.collage4.newProject("C4",100,100);
      this.collage4.addImageToLayer(null,"src/tako.ppm",0,0);
      fail("arguments are invalid");
    } catch (IllegalArgumentException illegalArgumentException) {
      // do nothing because we want it to fail;
    }

    try{
      this.collage4.newProject("C4",100,100);
      this.collage4.addImageToLayer("L9", null,0,0);
      fail("arguments are invalid");
    } catch (IllegalArgumentException illegalArgumentException) {
      // do nothing because we want it to fail;
    }

    try{
      this.collage4.newProject("C4",100,100);
      this.collage4.addImageToLayer(layer1.getName(),"src/tako.ppm",-200,0);
      fail("arguments are invalid");
    } catch (IllegalArgumentException illegalArgumentException) {
      // do nothing because we want it to fail;
    }

    try{
      this.collage4.newProject("C4",100,100);
      this.collage4.addImageToLayer(layer1.getName(),"src/tako.ppm",0,120);
      fail("arguments are invalid");
    } catch (IllegalArgumentException illegalArgumentException) {
      // do nothing because we want it to fail;
    }





  }


  @Test
  public void nullArgsForSetFilter() {
    try {
      this.collage1.setFilter(null, "brighten-intensity");
      fail("Arguments can't be null");
    } catch (IllegalStateException illegalArgumentException) {
      // do nothing because we want it to fail;
    }

    try {
      this.collage1.setFilter(this.layer3.getName(), null);
      fail("Arguments can't be null");
    } catch (IllegalStateException illegalArgumentException) {
      // do nothing because we want it to fail;
    }
  }


  @Test
  public void nullArgsForSaveProjects() {
    try {
      this.collage1.saveProject(null, "ppm");
      fail("Arguments can't be null");
    } catch (IllegalArgumentException iae) {
      // do nothing because we want it to fail;
    }

    try {
      this.collage1.setFilter("src/fileName", null);
      fail("Arguments can't be null");
    } catch (IllegalStateException ise) {
      // do nothing because we want it to fail;
    }
  }

  @Test
  public void testSavePPM() {
    this.init();

  }

}
