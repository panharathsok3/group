import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import model.CollageProject;
import model.CollageProjectModel;
import model.CollageProjectModelImpl;
import org.junit.Test;
import view.CollageTextView;
import view.CollageView;

public class CollageTextViewTest {

  Appendable out;
  CollageView view;
  CollageProject collage;

  @Test
  public void testInvalidConstructor() {
    try {
      this.view = new CollageTextView(null, this.out);
      fail("the given arguments cannot be null");
    } catch (IllegalArgumentException e) {
      //do nothing
    }

    this.collage = new CollageProjectModelImpl();

    try {
      this.view = new CollageTextView(this.collage, null);
      fail("the given arguments cannot be null");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
  }

  @Test
  public void renderMessage() {
    this.collage = new CollageProjectModelImpl();
    this.out = new StringBuilder();
    this.view = new CollageTextView(this.collage, this.out);

    try {
      this.view.renderMessage("a");
    } catch (IOException e) {
      fail("unexpected IOException");
    }

    assertEquals("a", this.out.toString());

    try {
      this.view.renderMessage("Do something");
    } catch (IOException e) {
      fail("unexpected IOException");
    }

    assertEquals("aDo something", this.out.toString());

  }
}