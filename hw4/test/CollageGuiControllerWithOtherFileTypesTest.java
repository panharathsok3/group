import controller.CollageControllerWithOtherFileTypes;
import controller.CollageGUIControllerWithOtherFileTypes;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import model.CollageProject;
import model.CollageProjectModelImpl;
import model.IPixel;
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

    List<List<IPixel>> image = controller.readImage("res/doggoJPG.jpg");




    for (int i = 0; i < image.size(); i++) {
      for (int j = 0; j < image.get(0).size(); j++) {
        try {
          FileWriter writer = new FileWriter("res/new");
          writer.write(image.get(i).get(j).getRedComponent() + "\n");
          writer.write(image.get(i).get(j).getGreenComponent() + "\n");
          writer.write(image.get(i).get(j).getBlueComponent() + "\n");
          System.out.println(image.get(i).get(j).getRedComponent());
        } catch (IOException e) {
          // do nothing
        }
      }
    }

  }

}
