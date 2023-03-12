import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import controller.CollageController;
import controller.CollageControllerImpl;
import model.CollageProject;
import model.CollageProjectModelImpl;
import view.CollageTextView;
import view.CollageView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

public class CollageProjectControllerTest {

  CollageProject collageModel;
  CollageTextView collageTextView;
  CollageController collageController;
  Appendable out;
  Readable in;


  @Before
  public void init() {
    collageModel = new CollageProjectModelImpl();
  }


  @Test
  public void invalidControllerConstructorTest() {

    try {
      this.collageController = new CollageControllerImpl(null, this.collageModel, this.collageTextView);
      fail("Cannot pass in a null readable, model or view");
    } catch (IllegalArgumentException iae) {
      //do nothing
    }

    try {
      this.collageController = new CollageControllerImpl(new StringReader(""), null, this.collageTextView);
      fail("Cannot pass in a null readable, model or view");
    } catch (IllegalArgumentException iae) {
      //do nothing
    }

    try {
      this.collageController = new CollageControllerImpl(new StringReader(""), this.collageModel, null);
      fail("Cannot pass in a null readable, model or view");
    } catch (IllegalArgumentException iae) {
      //do nothing
    }
  }

  @Test
  public void outOfInputsMessage() {

    Appendable out = new StringBuilder();

    CollageControllerImpl controller = new CollageControllerImpl(new BadReadable(), this.collageModel,
            this.collageTextView = new CollageTextView(this.collageModel, out));

    try {
      controller.runProgram();
      fail();
    } catch (IllegalStateException ise) {
      assertEquals("Ran out of inputs.", ise.getMessage());
    }
  }


  @Test
  public void outOfInputs() {
    this.in = new StringReader("new-project C1 3 3");
    this.out = new StringBuilder("");

    this.collageModel = new CollageProjectModelImpl();
    this.collageTextView = new CollageTextView(this.collageModel,new StringBuilder(""));
    this.collageController = new CollageControllerImpl(this.in,this.collageModel,this.collageTextView);

    try {
      this.collageController.runProgram();
      fail("out of inputs");
    } catch(IllegalStateException ise) {
      //
      }
  }

  @Test
  public void testCreateNewProject() {

    Readable r = new StringReader("new-project C1 3 3 " +
            "load-project src/saveOneLayer add-layer L2 add-image-to-layer L2 src/tako.ppm 0 0 " +
            "set-filter L2 red-component save-image src/tako.ppm " +
            "save-project src/saveOneLayer txt quit");
    Appendable out = new StringBuilder();

    CollageProject collageProject = new CollageProjectModelImpl();
    CollageView view = new CollageTextView(collageProject, out);
    CollageController controller = new CollageControllerImpl(r,collageProject,view);

    controller.runProgram();

    assertEquals("abc",out.toString());
  }

  @Test
  public void testCreateNewProj() {

    Readable r = new StringReader("new-project C1 3 3 " +
            "load-project src/saveOneLayer add-layer L2 add-image-to-layer L2 src/tako.ppm 0 0 " +
            "set-filter L2 red-component save-image src/tako.ppm " +
            "save-project src/saveOneLayer txt quit");

    Appendable out = new StringBuilder();

    CollageProject collageProject = new CollageProjectModelImpl();
    CollageView view = new CollageTextView(collageProject, out);
    CollageController controller = new CollageControllerImpl(r,collageProject,view);

    controller.runProgram();

    assertEquals("abc",out.toString());
  }


  @Test
  public void testCreateProject() {

    this.in = new StringReader("new-project C1 3 3 quit");
    this.out = new StringBuilder("");

    this.collageModel = new MockCollageImpl(this.out);
    this.collageTextView = new CollageTextView(this.collageModel,new StringBuilder(""));
    this.collageController = new CollageControllerImpl(this.in,this.collageModel,this.collageTextView);


    try {
      collageController.runProgram();

    } catch (IllegalStateException isa) {
      fail(isa.getMessage());
    }

    assertEquals(" Created a new project with the given arguments = C1, 3, 3 ",
            this.out.toString());
  }


  @Test
  public void testAddLayerToProject() {
    this.in = new StringReader("new-project C1 3 3 add-layer L1 quit");
    this.out = new StringBuilder("");

    this.collageModel = new MockCollageImpl(this.out);
    this.collageTextView = new CollageTextView(this.collageModel,new StringBuilder(""));
    this.collageController = new CollageControllerImpl(this.in,this.collageModel,this.collageTextView);


    try {
      collageController.runProgram();
    } catch (IllegalStateException isa) {
      fail(isa.getMessage());
    }
    assertEquals(" Created a new project with the given arguments = C1, 3, 3 " +
                    " Added a Layer to the project with the given name = L1",
            this.out.toString());
  }

  @Test
  public void testAddImageToLayer() {
    this.in = new StringReader("new-project C1 3 3 add-layer L1 " +
            "add-image-to-layer L1 src/tako.ppm 0 0 quit");
    this.out = new StringBuilder("");

    this.collageModel = new MockCollageImpl(this.out);
    this.collageTextView = new CollageTextView(this.collageModel,new StringBuilder(""));
    this.collageController = new CollageControllerImpl(this.in,this.collageModel,this.collageTextView);


    try {
      collageController.runProgram();

    } catch (IllegalStateException isa) {
      fail(isa.getMessage());
    }

    assertEquals(" Created a new project with the given arguments = C1, 3, 3 " +
                    " Added a Layer to the project with the given name = L1" +
                    " Added an Image to a layer with the given arguments = L1, src/tako.ppm, 0, 0",
            this.out.toString());

  }

  @Test
  public void testSetFilter() {
    this.in = new StringReader("new-project C1 3 3 add-layer L1 " +
            "add-image-to-layer L1 src/tako.ppm 0 0 set-filter L1 red-component quit");
    this.out = new StringBuilder("");

    this.collageModel = new MockCollageImpl(this.out);
    this.collageTextView = new CollageTextView(this.collageModel,new StringBuilder(""));
    this.collageController = new CollageControllerImpl(this.in,this.collageModel,this.collageTextView);


    try {
      collageController.runProgram();
    } catch (IllegalStateException isa) {
      fail(isa.getMessage());
    }

    assertEquals(" Created a new project with the given arguments = C1, 3, 3 " +
                    " Added a Layer to the project with the given name = L1" +
                    " Added an Image to a layer with the given arguments = L1, src/tako.ppm, 0, 0" +
                    " Applied a filter with the given arguments = L1, red-component",
            this.out.toString());

  }

  @Test
  public void testLoadProject() {

      this.in = new StringReader("load-project src/saveOneLayer quit");
      this.out = new StringBuilder("");

      this.collageModel = new MockCollageImpl(this.out);
      this.collageTextView = new CollageTextView(this.collageModel,new StringBuilder(""));
      this.collageController = new CollageControllerImpl(this.in,this.collageModel,this.collageTextView);


      try {
        collageController.runProgram();
      } catch (IllegalStateException isa) {
        fail(isa.getMessage());
      }

      assertEquals(" Loaded a project with the given argument = src/saveOneLayer",
              this.out.toString());
  }


  @Test
  public void testSaveProject() {

    this.in = new StringReader("load-project src/saveOneLayer save-project src/saveOneLayer txt quit");
    this.out = new StringBuilder("");

    this.collageModel = new MockCollageImpl(this.out);
    this.collageTextView = new CollageTextView(this.collageModel,new StringBuilder(""));
    this.collageController = new CollageControllerImpl(this.in,this.collageModel,this.collageTextView);


    try {
      collageController.runProgram();
    } catch (IllegalStateException isa) {
      fail(isa.getMessage());
    }

    assertEquals(" Loaded a project with the given argument = src/saveOneLayer" +
                    " Saved a project with the given arguments = src/saveOneLayer, txt",
            this.out.toString());
  }


  @Test
  public void testSaveImage() {
    this.in = new StringReader("save-image src/tako.ppm quit");
    this.out = new StringBuilder("");

    this.collageModel = new MockCollageImpl(this.out);
    this.collageTextView = new CollageTextView(this.collageModel,new StringBuilder(""));
    this.collageController = new CollageControllerImpl(this.in,this.collageModel,this.collageTextView);


    try {
      collageController.runProgram();
    } catch (IllegalStateException isa) {
      fail(isa.getMessage());
    }

    assertEquals(" Saved an image with the given argument = src/tako.ppm",
            this.out.toString());
  }







}
