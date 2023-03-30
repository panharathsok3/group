import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import model.ILayer;
import model.IPixel;
import model.Pixel;
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

/**
 * This is a test class for CollageProjectController.
 */
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

    CollageControllerImpl controller = new CollageControllerImpl(new BadReadable(),
        this.collageModel, this.collageTextView = new CollageTextView(out));

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
    this.collageTextView = new CollageTextView(new StringBuilder());
    this.collageController = new CollageControllerImpl(this.in, this.collageModel,
        this.collageTextView);

    try {
      this.collageController.runProgram();
      fail("out of inputs");
    } catch (IllegalStateException ise) {
      // do nothing
    }
  }

  @Test
  public void testCallMockOnAllMethodsForModel() {

    Readable r = new StringReader("new-project C1 3 3\n"
        + "add-layer L2\n"
        + "add-image-to-layer L2 src/tako.ppm 0 0\n"
        + "set-filter L2 red-component\n"
        + "quit");
    Appendable out = new StringBuilder();

    CollageProject collageProject = new ModelConfirmMethodCallValidReturnMock(out);
    CollageView view = new CollageTextView(out);
    CollageController controller = new CollageControllerImpl(r, collageProject, view);

    controller.runProgram();

    assertEquals("Created a new project with the given arguments = C1, 3, 3\n"
        + "Added a Layer to the project with the given name = L2\n"
        + "Added an Image to a layer with the given arguments = L2, 0, 0\n"
        + "Applied a filter with the given arguments = L2, red-component\n"
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
    assertEquals("Created a new project with the given arguments = C1, 3, 3\n"
            + "Added a Layer to the project with the given name = L1\n"
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
    this.in = new StringReader("new-project C1 3 3 add-layer L1 "
        + "add-image-to-layer L1 src/tako.ppm 0 0 quit");
    this.out = new StringBuilder();

    this.collageModel = new ModelConfirmMethodCallValidReturnMock(this.out);
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);


    try {
      collageController.runProgram();

    } catch (IllegalStateException isa) {
      fail(isa.getMessage());
    }

    assertEquals("Created a new project with the given arguments = C1, 3, 3\n"
            + "Added a Layer to the project with the given name = L1\n"
            + "Added an Image to a layer with the given arguments = L1, 0, 0\n"
            + "The program has ended\n",
            this.out.toString());
  }

  @Test
  public void testInvalidAddImageToLayer() {
    this.in = new StringReader("new-project C1 3 3 add-layer L1 "
        + "add-image-to-layer L2 src/tako.ppm 0 0 quit");
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

    this.in = new StringReader("new-project C1 3 3 add-layer L1 "
        + "add-image-to-layer L2 src/tako.ppm -1 0 quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);

    collageController.runProgram();

    assertEquals("Arguments can't be null or negative or the layer doesn't exist\n"
            + "The program has ended\n",
        this.out.toString());

    this.in = new StringReader("new-project C1 3 3 add-layer L1 "
        + "add-image-to-layer L2 src/tako.ppm 0 -1 quit");
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
    this.in = new StringReader("new-project C1 3 3 add-layer L1 "
        + "add-image-to-layer L1 src/tako.ppm 0 0 set-filter L1 red-component quit");
    this.out = new StringBuilder();

    this.collageModel = new ModelConfirmMethodCallValidReturnMock(this.out);
    CollageView view = new ViewConfirmMethodCallValidReturnMock(out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);


    try {
      collageController.runProgram();
    } catch (IllegalStateException isa) {
      fail(isa.getMessage());
    }

    assertEquals("Created a new project with the given arguments = C1, 3, 3\n"
            + "Added a Layer to the project with the given name = L1\n"
            + "Added an Image to a layer with the given arguments = L1, 0, 0\n"
            + "Applied a filter with the given arguments = L1, red-component\n"
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


  @Test
  public void testSaveProjectImmediately() {
    this.init();
    this.in = new StringReader("new-project C1 2 2 save-project res/project/saveImmediately "
        + "PPM quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("res/project/saveImmediately"));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    assertEquals("C1", sc.next());
    assertEquals("2", sc.next());
    assertEquals("2", sc.next());
    assertEquals("255", sc.next());
    assertEquals("Background", sc.next());
    assertEquals("normal", sc.next());

    while (sc.hasNext()) {
      assertEquals("255", sc.next());
      assertEquals("255", sc.next());
      assertEquals("255", sc.next());
      assertEquals("255", sc.next());
    }

  }

  @Test
  public void testSaveProjectWithOneLayer() {
    this.init();

    this.in = new StringReader("new-project C1 200 200 add-layer L1 save-project "
        + "res/project/saveOneLayer PPM quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("res/project/saveOneLayer"));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    assertEquals("C1", sc.next());
    assertEquals("200", sc.next());
    assertEquals("200", sc.next());
    assertEquals("255", sc.next());
    assertEquals("Background", sc.next());
    assertEquals("normal", sc.next());

    for (int i = 0; i < 200; i++) {
      for (int j = 0; j < 200; j++) {
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
      }
    }

    assertEquals("L1", sc.next());
    assertEquals("normal", sc.next());

    while (sc.hasNext()) {
      assertEquals("255", sc.next());
      assertEquals("255", sc.next());
      assertEquals("255", sc.next());
      assertEquals("0", sc.next());
    }
  }

  @Test
  public void testSaveProjectAfterModification() {
    this.init();

    this.in = new StringReader("new-project C1 2 2 add-layer L1 add-image-to-layer L1 "
        + "src/tako.ppm 0 0 save-project res/project/saveAfterModification PPM quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("res/project/saveAfterModification"));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    assertEquals("C1", sc.next());
    assertEquals("2", sc.next());
    assertEquals("2", sc.next());
    assertEquals("255", sc.next());
    assertEquals("Background", sc.next());
    assertEquals("normal", sc.next());

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
      }
    }

    assertEquals("L1", sc.next());
    assertEquals("normal", sc.next());

    while (sc.hasNext()) {
      assertEquals("173", sc.next());
      assertEquals("179", sc.next());
      assertEquals("151", sc.next());
      assertEquals("255", sc.next());
    }

    this.in = new StringReader("load-project res/project/saveAfterModification set-filter L1 "
        + "darken-intensity save-project res/project/saveAfterModification PPM quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    try {
      sc = new Scanner(new FileInputStream("res/project/saveAfterModification"));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found!");
    }

    builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    assertEquals("C1", sc.next());
    assertEquals("2", sc.next());
    assertEquals("2", sc.next());
    assertEquals("255", sc.next());
    assertEquals("Background", sc.next());
    assertEquals("normal", sc.next());

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
      }
    }

    assertEquals("L1", sc.next());
    assertEquals("darken-intensity", sc.next());

    while (sc.hasNext()) {
      assertEquals("173", sc.next());
      assertEquals("179", sc.next());
      assertEquals("151", sc.next());
      assertEquals("255", sc.next());
    }

  }

  @Test
  public void testInvalidSaveProjects() {
    this.in = new StringReader("save-project res/project/saveAfterModification PPM quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    try {
      this.collageController.runProgram();
      fail("The project hasn't been made yet");
    } catch (IllegalStateException e) {
      // do nothing
    }

    try {
      this.collageController.saveProject(null, "ppm");
      fail("Arguments can't be null");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.collageController.saveProject("src/fileName", null);
      fail("Arguments can't be null");
    } catch (IllegalArgumentException e) {
      // do nothing
    }
  }


  @Test
  public void testValidLoadProjectImmediately() {
    this.init();

    this.in = new StringReader("new-project C1 2 2 "
        + "save-project res/project/saveProjectAndLoadImmediately PPM quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    this.in = new StringReader("load-project res/project/saveProjectAndLoadImmediately quit");
    this.out = new StringBuilder();

    CollageProject model = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, model, view);
    this.collageController.runProgram();

    assertEquals("C1", model.getProjectName());
    assertEquals(2, model.getHeight());
    assertEquals(2, model.getWidth());
    assertEquals(255, model.getMaxValue());

    ILayer backgroundLayer = model.getLayers().get(0);
    assertEquals("Background", backgroundLayer.getName());

    Map<String, String> layerWithFilter = model.getFiltersOnProject();
    assertEquals("normal", layerWithFilter.get("Background"));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidLoadProjectAfterAddingLayer() {
    this.init();

    this.in = new StringReader("new-project C1 2 2 add-layer L1 "
        + "save-project res/project/saveProjectAndLoadAfterAddingLayer PPM quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    this.in = new StringReader("load-project res/project/saveProjectAndLoadAfterAddingLayer"
        + " quit");
    this.out = new StringBuilder();

    CollageProject model = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, model, view);
    this.collageController.runProgram();

    assertEquals("C1", model.getProjectName());
    assertEquals(2, model.getHeight());
    assertEquals(2, model.getWidth());
    assertEquals(255, model.getMaxValue());

    ILayer backgroundLayer = model.getLayers().get(0);
    assertEquals("Background", backgroundLayer.getName());

    Map<String, String> layerWithFilter = model.getFiltersOnProject();
    assertEquals("normal", layerWithFilter.get("Background"));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }

    backgroundLayer = model.getLayers().get(1);
    assertEquals("L1", backgroundLayer.getName());

    layerWithFilter = model.getFiltersOnProject();
    assertEquals("normal", layerWithFilter.get("L1"));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(0,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testValidLoadProjectAfterAddingLayerAndModifying() {
    this.init();

    this.in = new StringReader("new-project C1 2 2 add-layer L1 add-image-to-layer L1 "
        + "src/tako.ppm 0 0 set-filter L1 darken-intensity "
        + "save-project res/project/saveProjectAndLoadAfterAddingLayerAndModifying PPM quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    this.in = new StringReader("load-project "
        + "res/project/saveProjectAndLoadAfterAddingLayerAndModifying quit");
    this.out = new StringBuilder();

    CollageProject model = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, model, view);
    this.collageController.runProgram();

    assertEquals("C1", model.getProjectName());
    assertEquals(2, model.getHeight());
    assertEquals(2, model.getWidth());
    assertEquals(255, model.getMaxValue());

    ILayer backgroundLayer = model.getLayers().get(0);
    assertEquals("Background", backgroundLayer.getName());

    Map<String, String> layerWithFilter = model.getFiltersOnProject();
    assertEquals("normal", layerWithFilter.get("Background"));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(255,
            backgroundLayer.getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }

    backgroundLayer = model.getLayers().get(1);
    assertEquals("L1", backgroundLayer.getName());

    layerWithFilter = model.getFiltersOnProject();
    assertEquals("darken-intensity", layerWithFilter.get("L1"));

    for (int i = 0; i < 2; i ++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(173, backgroundLayer.getPixelsOnLayer().get(i).get(j)
            .getRedComponent());
        assertEquals(179, backgroundLayer.getPixelsOnLayer().get(i).get(j)
            .getGreenComponent());
        assertEquals(151, backgroundLayer.getPixelsOnLayer().get(i).get(j)
            .getBlueComponent());
        assertEquals(255, backgroundLayer.getPixelsOnLayer().get(i).get(j)
            .getAlphaComponent());
      }
    }
  }

  @Test
  public void testLoadProjectInTheMiddleOfWorkingOnAProject() {
    this.init();

    this.in = new StringReader("new-project C1 2 2 "
        + "save-project res/project/saveProjectAndLoadWhileWorking PPM quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    this.in = new StringReader("new-project C1 2 2 add-layer L1 add-image-to-layer L1 "
        + "src/tako.ppm 0 0 set-filter L1 darken-intensity "
        + "load-project res/project/saveProjectAndLoadWhileWorking quit");
    this.out = new StringBuilder();

    CollageProject model = new CollageProjectModelImpl();
    view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, model, view);
    this.collageController.runProgram();

    model.newProject("C1", 2, 2);
    this.collageController.saveProject("res/project/saveProjectAndLoadWhileWorking",
        "PPM");

    model.newProject("C2", 2, 2);
    model.addLayer("L1");
    model.addImageToLayer("L1", this.getImageFromTakoPPM(), 0, 0);
    model.setFilter("L1", "darken-intensity");
    this.collageController.loadProject("res/project/saveProjectAndLoadWhileWorking");

    List<ILayer> backgroundLayer = model.getLayers();
    assertEquals(1, backgroundLayer.size());
    assertEquals("Background", backgroundLayer.get(0).getName());

    Map<String, String> layerWithFilter = model.getFiltersOnProject();
    assertEquals(1, layerWithFilter.size());
    assertEquals("normal", layerWithFilter.get("Background"));

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(255,
            backgroundLayer.get(0).getPixelsOnLayer().get(i).get(j).getRedComponent());
        assertEquals(255,
            backgroundLayer.get(0).getPixelsOnLayer().get(i).get(j).getGreenComponent());
        assertEquals(255,
            backgroundLayer.get(0).getPixelsOnLayer().get(i).get(j).getBlueComponent());
        assertEquals(255,
            backgroundLayer.get(0).getPixelsOnLayer().get(i).get(j).getAlphaComponent());
      }
    }
  }

  @Test
  public void testInvalidLoadProjectController() {
    this.init();

    this.in = new StringReader("");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);

    try {
      this.collageController.loadProject(null);
      fail("Arguments can't be null");
    } catch (IllegalArgumentException e) {
      // do nothing
    }

    try {
      this.collageController.loadProject("a");
      fail("File not found");
    } catch (IllegalStateException e) {
      //do nothing
    }

    try {
      FileWriter fileWriter = new FileWriter("res/project/nothingInside");
      fileWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    try {
      this.collageController.loadProject("res/project/nothingInside");
      fail("Nothing is inside the file");
    } catch (IllegalStateException e) {
      //do nothing
    }

    try {
      FileWriter fileWriter = new FileWriter("res/project/nothingInside");
      fileWriter.write("C1" + "\n");
      fileWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    try {
      this.collageController.loadProject("res/project/nothingInside");
      fail("Not enough to make a project");
    } catch (IllegalStateException e) {
      //do nothing
    }

    try {
      FileWriter fileWriter = new FileWriter("res/project/nothingInside");
      fileWriter.write("C1" + "\n");
      fileWriter.write("100 100" + "\n");
      fileWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    try {
      this.collageController.loadProject("res/project/nothingInside");
      fail("Not enough to make a project");
    } catch (IllegalStateException e) {
      //do nothing
    }

    try {
      FileWriter fileWriter = new FileWriter("res/project/nothingInside");
      fileWriter.write("C1" + "\n");
      fileWriter.write("a 100" + "\n");
      fileWriter.write("255" + "\n");
      fileWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    try {
      this.collageController.loadProject("res/project/nothingInside");
      fail("Not enough to make a project");
    } catch (IllegalStateException e) {
      //do nothing
    }

    try {
      FileWriter fileWriter = new FileWriter("res/project/nothingInside");
      fileWriter.write("C1" + "\n");
      fileWriter.write("100 a" + "\n");
      fileWriter.write("255" + "\n");
      fileWriter.close();
    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException");
    }

    try {
      this.collageController.loadProject("res/project/nothingInside");
      fail("Not enough to make a project");
    } catch (IllegalStateException e) {
      //do nothing
    }
  }


  @Test
  public void testSaveImageOfBackGround() {
    this.init();

    this.in = new StringReader("new-project C1 2 2 save-image res/Images/background.ppm quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("res/Images/background.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found!");
    }

    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    assertEquals("P3", sc.next());
    assertEquals("2", sc.next());
    assertEquals("2", sc.next());
    assertEquals("255", sc.next());

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
        assertEquals("255", sc.next());
      }
    }
  }

  @Test
  public void testValidSaveImageAfterPuttingAnImage() {
    this.init();

    this.in = new StringReader("new-project C1 2 2 "
        + "add-layer L1 "
        + "add-image-to-layer L1 src/tako.ppm 0 0 "
        + "save-image res/Images/saveImageImmediately.ppm quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("res/Images/saveImageImmediately.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found!");
    }

    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    assertEquals("P3", sc.next());
    assertEquals("2", sc.next());
    assertEquals("2", sc.next());
    assertEquals("255", sc.next());

    while (sc.hasNext()) {
      assertEquals("173", sc.next());
      assertEquals("179", sc.next());
      assertEquals("151", sc.next());
    }
  }

  @Test
  public void testSaveImageThenModifyIt() {
    this.init();

    this.in = new StringReader("new-project C1 2 2 "
        + "add-layer L1 "
        + "add-image-to-layer L1 src/tako.ppm 0 0 "
        + "set-filter L1 darken-intensity "
        + "save-image res/Images/saveImageModified.ppm quit");
    this.out = new StringBuilder();

    this.collageModel = new CollageProjectModelImpl();
    CollageView view = new ViewConfirmMethodCallValidReturnMock(this.out);
    this.collageController = new CollageControllerImpl(this.in, this.collageModel, view);
    this.collageController.runProgram();

    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream("res/Images/saveImageModified.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found!");
    }

    StringBuilder builder = new StringBuilder();
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    sc = new Scanner(builder.toString());

    assertEquals("P3", sc.next());
    assertEquals("2", sc.next());
    assertEquals("2", sc.next());
    assertEquals("255", sc.next());

    while (sc.hasNext()) {
      assertEquals("6", sc.next());
      assertEquals("12", sc.next());
      assertEquals("0", sc.next());
    }
  }

  @Test
  public void testInvalidSaveImage() {

    this.collageController = new CollageControllerImpl(this.collageModel, false);

    try {
      this.collageController.saveImage("src/new");
      fail("file doesn't exist");
    } catch (IllegalStateException e) {
      // do nothing
    }

    try {
      this.collageController.saveImage(null);
      fail("Arguments can't be null");
    } catch (IllegalStateException e) {
      // do nothing
    }
  }

  /**
   * Returns a 2d Array of Pixels from tako.ppm.
   * @return a 2d Array of Pixels from tako.ppm
   */
  private List<List<IPixel>> getImageFromTakoPPM() {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream("src/tako.ppm"));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File not found!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();
    int maxValue = sc.nextInt();

    List<List<IPixel>> pixelsOnImage = new ArrayList<>();

    for (int i = 0; i < height; i++) {
      pixelsOnImage.add(new ArrayList<>());
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        pixelsOnImage.get(i).add(new Pixel(r, g, b));

      }
    }

    return pixelsOnImage;
  }

}
