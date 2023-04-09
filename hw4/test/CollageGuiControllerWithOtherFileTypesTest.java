import controller.CollageControllerWithOtherFileTypes;
import controller.CollageGUIControllerWithOtherFileTypes;
import java.io.File;
import java.io.StringReader;
import model.CollageProject;
import model.CollageProjectModelImpl;
import org.junit.Before;
import org.junit.Test;
import view.CollageTextView;
import view.CollageView;

/**
 * A CollageGuiControllerWithOtherFileTypesTest is a test class for
 * CollageGuiControllerWithOtherFileTypes.
 */
public class CollageGuiControllerWithOtherFileTypesTest {
  Readable in;
  CollageProject model;
  CollageView view;

  @Before
  public void init() {
    this.in = new StringReader("");
    this.model = new CollageProjectModelImpl();
    this.view = new CollageTextView(new StringBuilder());
  }

  @Test
  public void readImageTest() {
    CollageControllerWithOtherFileTypes controller = new
        CollageGUIControllerWithOtherFileTypes(this.in, this.model, this.view);

    controller.readImage("res/doggoJPG.jpg");
  }

}
