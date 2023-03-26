import org.junit.Test;

import controller.CollageController;
import controller.CollageGUIController;
import model.CollageProject;
import view.CollageTextView;

import static org.junit.Assert.assertEquals;

public class CollageGuiViewControllerTest {

  Appendable out;
  CollageProject collageModel;
  CollageTextView collageTextView;
  CollageController collageController;
  Readable in;

  @Test
  public void testOne() {

    this.out = new StringBuilder();
    ModelConfirmMethodCallValidReturnMock mockModel =
            new ModelConfirmMethodCallValidReturnMock(this.out);

    GUIViewConfirmMethodCallValidReturnMock view = new GUIViewConfirmMethodCallValidReturnMock(this.out);
    CollageGUIController controller = new CollageGUIController(mockModel);

    assertEquals("",mockModel.getLog());
    controller.addLayer("Layer 1");
    assertEquals("Added a Layer to the project with the given name = Layer 1\n",
            mockModel.getLog());


//    controller.loadProject("res/project/saveOneLayer");
//    assertEquals("Loaded a project with the given name = Layer 1\n",
//            mockModel.getLog());

  }





}
