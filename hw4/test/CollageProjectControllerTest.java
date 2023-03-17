import java.io.FileWriter;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import controller.CollageController;
import controller.CollageControllerImpl;
import model.CollageProject;
import model.CollageProjectModelImpl;
import view.CollageTextView;
import view.CollageView;

import static org.junit.Assert.assertEquals;
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
      this.collageController = new CollageControllerImpl(null, this.collageModel,
          this.collageTextView);
      fail("Cannot pass in a null readable, model or view");
    } catch (IllegalArgumentException iae) {
      //do nothing
    }

    try {
      this.collageController = new CollageControllerImpl(new StringReader(""), null,
          this.collageTextView);
      fail("Cannot pass in a null readable, model or view");
    } catch (IllegalArgumentException iae) {
      //do nothing
    }

    try {
      this.collageController = new CollageControllerImpl(new StringReader(""), this.collageModel,
          null);
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
      fail("No more inputs");
    } catch (IllegalStateException ise) {
      assertEquals("Ran out of inputs.", ise.getMessage());
    }
  }


  @Test
  public void outOfInputs() {
    this.in = new StringReader("new-project C1 3 3");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    this.collageTextView = new CollageTextView(this.collageModel, new StringBuilder());
    this.collageController = new CollageControllerImpl(this.in, this.collageModel,
        this.collageTextView);

    try {
      this.collageController.runProgram();
      fail("out of inputs");
    } catch(IllegalStateException ise) {
      // do nothing
    }
  }
  @Test
  public void testCallMockOnAllMethodsForModel() {

    Readable r = new StringReader("new-project C1 3 3\n"
        + "load-project src/saveProjectAndLoadImmediately\n"
        + "add-layer L2\n"
        + "add-image-to-layer L2 src/tako.ppm 0 0\n"
        + "save-image src/modifiedTako.ppm\n"
        + "set-filter L2 red-component\n"
        + "save-project src/saveOneLayer txt\n"
        + "quit");
    Appendable out = new StringBuilder();

    CollageProject collageProject = new ModelConfirmMethodCallValidReturnMock(out);
    CollageView view = new CollageTextView(collageProject, out);
    CollageController controller = new CollageControllerImpl(r, collageProject, view);

    controller.runProgram();

    assertEquals("Created a new project with the given arguments = C1, 3, 3\n"
        + "Loaded a project with the given argument = src/saveProjectAndLoadImmediately\n"
        + "Added a Layer to the project with the given name = L2\n"
        + "Added an Image to a layer with the given arguments = L2, src/tako.ppm, 0, 0\n"
        + "Saved an image with the given argument = src/modifiedTako.ppm\n"
        + "Applied a filter with the given arguments = L2, red-component\n"
        + "Saved a project with the given arguments = src/saveOneLayer, txt\n"
        + "The program has ended\n", out.toString());
  }

  @Test
  public void testQuitImmediately() {
    Readable r = new StringReader("quit");

    Appendable out = new StringBuilder();

    CollageProject collageProject = new ModelConfirmMethodCallValidReturnMock(out);
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    CollageController controller = new CollageControllerImpl(r, collageProject, view);

    controller.runProgram();

    assertEquals("The program has ended\n", out.toString());

  }

  @Test
  public void testCreateProject() {

    this.in = new StringReader("new-project C1 3 3 quit");
    this.out = new StringBuilder();

    this.collageModel = new ModelConfirmMethodCallValidReturnMock(this.out);
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);


    collageController.runProgram();

    assertEquals("Created a new project with the given arguments = C1, 3, 3\n"
            + "The program has ended\n",
            this.out.toString());
  }

  @Test
  public void testCreateProjectWithNullThenQuit() {
    this.in = new StringReader("new-project null null null quit");
    this.out = new StringBuilder();

    this.collageModel = new ModelConfirmMethodCallValidReturnMock(this.out);
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);


    collageController.runProgram();

    assertEquals("Created a new project with the given arguments = null, 0, 0\n"
            + "The program has ended\n",
        this.out.toString());

    this.in = new StringReader("new-project C1 null null quit");
    this.out = new StringBuilder();

    this.collageModel = new ModelConfirmMethodCallValidReturnMock(this.out);
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);


    collageController.runProgram();

    assertEquals("Created a new project with the given arguments = C1, 0, 0\n"
            + "The program has ended\n",
        this.out.toString());

    this.in = new StringReader("new-project C1 1 null quit");
    this.out = new StringBuilder();

    this.collageModel = new ModelConfirmMethodCallValidReturnMock(this.out);
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);


    collageController.runProgram();

    assertEquals("Created a new project with the given arguments = C1, 1, 0\n"
            + "The program has ended\n",
        this.out.toString());

    this.in = new StringReader("new-project C1 1 null quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);

    collageController.runProgram();

    assertEquals("Arguments can't be null\n"
            + "The program has ended\n",
        this.out.toString());

    this.in = new StringReader("new-project C1 null null quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);

    collageController.runProgram();

    assertEquals("Arguments can't be null\n"
            + "The program has ended\n",
        this.out.toString());

    this.in = new StringReader("new-project null null null quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);

    collageController.runProgram();

    assertEquals("Arguments can't be null\n"
            + "The program has ended\n",
        this.out.toString());

    this.in = new StringReader("new-project C1 1 null quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);

    collageController.runProgram();

    assertEquals("Arguments can't be null\n"
            + "The program has ended\n",
        this.out.toString());
  }


  @Test
  public void testAddLayerToProject() {
    this.in = new StringReader("new-project C1 3 3 add-layer L1 quit");
    this.out = new StringBuilder();

    this.collageModel = new ModelConfirmMethodCallValidReturnMock(this.out);
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);


    collageController.runProgram();
    assertEquals("Created a new project with the given arguments = C1, 3, 3\n" +
                    "Added a Layer to the project with the given name = L1\n"
            + "The program has ended\n",
            this.out.toString());
  }

  @Test
  public void testInvalidAddLayerToProject() {
    this.in = new StringReader("new-project C1 3 3 add-layer L1 add-layer L1 quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);

    collageController.runProgram();

    assertEquals("Arguments can't be null or the layer already exist\n"
            + "The program has ended\n",
        this.out.toString());

    this.in = new StringReader("add-layer L1 quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);

    collageController.runProgram();

    assertEquals("The project hasn't been made yet\n"
            + "The program has ended\n",
        this.out.toString());
  }

  @Test
  public void testAddImageToLayer() {
    this.in = new StringReader("new-project C1 3 3 add-layer L1 " +
            "add-image-to-layer L1 src/tako.ppm 0 0 quit");
    this.out = new StringBuilder();

    this.collageModel = new ModelConfirmMethodCallValidReturnMock(this.out);
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);


    try {
      collageController.runProgram();

    } catch (IllegalStateException isa) {
      fail(isa.getMessage());
    }

    assertEquals("Created a new project with the given arguments = C1, 3, 3\n" +
                    "Added a Layer to the project with the given name = L1\n" +
                    "Added an Image to a layer with the given arguments = L1, src/tako.ppm, 0, 0\n"
            + "The program has ended\n",
            this.out.toString());
  }

  @Test
  public void testInvalidAddImageToLayer() {
    this.in = new StringReader("new-project C1 3 3 add-layer L1 " +
        "add-image-to-layer L2 src/tako.ppm 0 0 quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);

    collageController.runProgram();

    assertEquals("Arguments can't be null or negative or the layer doesn't exist\n"
            + "The program has ended\n",
        this.out.toString());


    this.in = new StringReader("add-image-to-layer L2 src/tako.ppm 0 0 quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);

    collageController.runProgram();

    assertEquals("The layer already exists or the project hasn't been made yet\n"
            + "The program has ended\n",
        this.out.toString());

    this.in = new StringReader("new-project C1 3 3 add-layer L1 " +
        "add-image-to-layer L2 src/tako.ppm -1 0 quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);

    collageController.runProgram();

    assertEquals("Arguments can't be null or negative or the layer doesn't exist\n"
            + "The program has ended\n",
        this.out.toString());

    this.in = new StringReader("new-project C1 3 3 add-layer L1 " +
        "add-image-to-layer L2 src/tako.ppm 0 -1 quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);

    collageController.runProgram();

    assertEquals("Arguments can't be null or negative or the layer doesn't exist\n"
            + "The program has ended\n",
        this.out.toString());
  }

  @Test
  public void testSetFilter() {
    this.in = new StringReader("new-project C1 3 3 add-layer L1 " +
            "add-image-to-layer L1 src/tako.ppm 0 0 set-filter L1 red-component quit");
    this.out = new StringBuilder();

    this.collageModel = new ModelConfirmMethodCallValidReturnMock(this.out);
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);


    try {
      collageController.runProgram();
    } catch (IllegalStateException isa) {
      fail(isa.getMessage());
    }

    assertEquals("Created a new project with the given arguments = C1, 3, 3\n" +
                    "Added a Layer to the project with the given name = L1\n" +
                    "Added an Image to a layer with the given arguments = L1, src/tako.ppm, 0, 0\n" +
                    "Applied a filter with the given arguments = L1, red-component\n"
            + "The program has ended\n",
            this.out.toString());
  }

  @Test
  public void testInvalidSetFilter() {
    this.in = new StringReader("set-filter L2 brighten-luma quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    assertEquals("The project hasn't been made yet\n"
        + "The program has ended\n", this.out.toString());

    this.in = new StringReader("new-project C1 2 2 set-filter L2 brighten-luma quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    assertEquals("Arguments can't be null or the layer doesn't exist or filter doesn't "
        + "exist\nThe program has ended\n", this.out.toString());

    this.in = new StringReader("new-project C1 2 2 add-layer L2 set-filter L2 rar quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    assertEquals("Arguments can't be null or the layer doesn't exist or filter doesn't "
        + "exist\nThe program has ended\n", this.out.toString());
  }


  @Test
  public void testLoadProject() {

    this.in = new StringReader("load-project src/saveOneLayer quit");
    this.out = new StringBuilder();

    this.collageModel = new ModelConfirmMethodCallValidReturnMock(this.out);
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);


    collageController.runProgram();

    assertEquals("Loaded a project with the given argument = src/saveOneLayer\n"
              + "The program has ended\n",
              this.out.toString());
  }

  @Test
  public void testInvalidLoadProject() {
    this.in = new StringReader("load-project null quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    assertEquals("File can't be open or file is not enough to start a load a project\n"
        + "The program has ended\n", this.out.toString());

    this.in = new StringReader("load-project FileDoesntExist quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    assertEquals("File can't be open or file is not enough to start a load a project\n"
        + "The program has ended\n", this.out.toString());

    try {
      FileWriter fileWriter = new FileWriter("res/project/nothingInsideController");
      fileWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    this.in = new StringReader("load-project res/project/nothingInsideController quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    assertEquals("File can't be open or file is not enough to start a load a project\n"
        + "The program has ended\n", this.out.toString());
  }


  @Test
  public void testSaveProject() {

    this.in = new StringReader("load-project src/saveOneLayer save-project src/saveOneLayer txt quit");
    this.out = new StringBuilder();

    this.collageModel = new ModelConfirmMethodCallValidReturnMock(this.out);
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);


    try {
      collageController.runProgram();
    } catch (IllegalStateException isa) {
      fail(isa.getMessage());
    }

    assertEquals("Loaded a project with the given argument = src/saveOneLayer\n" +
                    "Saved a project with the given arguments = src/saveOneLayer, txt\n"
        + "The program has ended\n",
            this.out.toString());
  }

  @Test
  public void testInvalidSaveProject() {
    this.in = new StringReader("save-project A A quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    assertEquals("The project hasn't been made yet\n"
        + "The program has ended\n", this.out.toString());
  }


  @Test
  public void testSaveImage() {
    this.in = new StringReader("save-image src/tako.ppm quit");
    this.out = new StringBuilder();

    this.collageModel = new ModelConfirmMethodCallValidReturnMock(this.out);
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);


    try {
      collageController.runProgram();
    } catch (IllegalStateException isa) {
      fail(isa.getMessage());
    }

    assertEquals("Saved an image with the given argument = src/tako.ppm\n"
            + "The program has ended\n",
            this.out.toString());
  }

  @Test
  public void testInvalidSaveImage() {
    this.in = new StringReader("save-image A quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    assertEquals("The project hasn't been made yet\n"
        + "The program has ended\n", this.out.toString());
  }

  @Test
  public void testInvalidCommand() {
    this.in = new StringReader("rawr quit");
    this.out = new StringBuilder();

    this.collageModel = new ModelConfirmMethodCallValidReturnMock(this.out);
    CollageView view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    assertEquals("Command doesn't exist\n"
        + "The program has ended\n", this.out.toString());
  }
}
