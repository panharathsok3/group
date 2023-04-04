import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import controller.CollageGUIController;
import controller.Features;
import model.CollageProject;
import model.CollageProjectModelImpl;
import org.junit.Before;
import org.junit.Test;
import view.GUIView;
import view.JFrameView;

/**
 * A CollageGUIControllerTest is a test class for CollageGUIController.
 */
public class CollageGUIControllerTest {
  Features controller;
  CollageProject model;
  Appendable out;

  @Before
  public void init() {
    this.model = new CollageProjectModelImpl();
    this.out = new StringBuilder();
  }

  @Test
  public void testInvalidConstructor() {
    try {
      this.controller = new CollageGUIController(null);
      fail("Arguments can't be null");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
  }

  @Test
  public void runEntireProgram() {
    this.controller = new GUIControllerConfirmMethodCallValidReturnMock(this.out);
    this.controller.newProject("C1", "1", "2");
    this.controller.addLayer("Layer1");
    this.controller.addImageToLayer("Layer1", "src/image", "1", "1");
    this.controller.saveImage("src/coolImage");
    this.controller.saveProject("src/project");
    this.controller.loadProject("src/project");
    this.controller.setFilter("Layer1", "darken-multiply");

    assertEquals("newProject: project name = C1, height = 1, width = 2\n"
        + "addLayer: layerName = Layer1\n"
        + "addImageToLayer: layerName = Layer1, filePath = src/image, xPos = 1, yPos = 1\n"
        + "saveImage: filePath = src/coolImage\n"
        + "saveProject: filePath = src/project\n"
        + "loadProject: filePath = src/project\n"
        + "setFilter: layerName = Layer1, filterOption = darken-multiply\n", this.out.toString());
  }

  @Test
  public void testNewProject() {
    this.controller = new GUIControllerConfirmMethodCallValidReturnMock(this.out);
    this.controller.newProject("C1", "1", "2");

    assertEquals("newProject: project name = C1, height = 1, width = 2\n",
        this.out.toString());
  }

  @Test
  public void testLoadProject() {
    this.controller = new GUIControllerConfirmMethodCallValidReturnMock(this.out);
    this.controller.loadProject("src/CoolProjectShouldGive100");

    assertEquals("loadProject: filePath = src/CoolProjectShouldGive100\n",
        this.out.toString());
  }

  @Test
  public void testAddLayer() {
    this.controller = new GUIControllerConfirmMethodCallValidReturnMock(this.out);
    this.controller.addLayer("Layer1");

    assertEquals("addLayer: layerName = Layer1\n",
        this.out.toString());
  }

  @Test
  public void testAddImageToLayer() {
    this.controller = new GUIControllerConfirmMethodCallValidReturnMock(this.out);
    this.controller.addImageToLayer("Layer1", "src/file", "1", "2");

    assertEquals("addImageToLayer: layerName = Layer1, filePath = src/file, xPos = 1, "
            + "yPos = 2\n", this.out.toString());
  }

  @Test
  public void testSaveProject() {
    this.controller = new GUIControllerConfirmMethodCallValidReturnMock(this.out);
    this.controller.saveProject("src/project");

    assertEquals("saveProject: filePath = src/project\n",
        this.out.toString());
  }

  @Test
  public void testSaveImage() {
    this.controller = new GUIControllerConfirmMethodCallValidReturnMock(this.out);
    this.controller.saveImage("src/coolImage.ppm");

    assertEquals("saveImage: filePath = src/coolImage.ppm\n",
        this.out.toString());
  }

  @Test
  public void testSetFilter() {
    this.controller = new GUIControllerConfirmMethodCallValidReturnMock(this.out);
    this.controller.setFilter("Layer1", "darken-multiply");

    assertEquals("setFilter: layerName = Layer1, filterOption = darken-multiply\n",
        this.out.toString());
  }

  @Test
  public void testProjectMade() {
    this.controller = new CollageGUIController(this.model);
    GUIView view = new JFrameView();
    this.controller.setView(view);

    assertFalse(this.controller.projectMade());

    this.controller.newProject("C1", "600", "800");
    assertTrue(this.controller.projectMade());


    this.controller = new CollageGUIController(this.model);
    view = new JFrameView();
    this.controller.setView(view);
    assertFalse(this.controller.projectMade());

    this.controller.loadProject("res/project/saveImmediately");
    assertTrue(this.controller.projectMade());
  }

}
