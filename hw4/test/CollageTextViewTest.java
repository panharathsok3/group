import org.junit.Test;

import java.io.IOException;

import model.CollageProject;
import model.CollageProjectModelImpl;
import view.CollageTextView;
import view.CollageView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * This is a test class for CollageTextView.
 */
public class CollageTextViewTest {

  Appendable out;
  CollageView view;
  CollageProject collage;

  @Test
  public void testInvalidConstructor() {
    try {
      this.view = new CollageTextView(this.out);
      fail("the given arguments cannot be null");
    } catch (IllegalArgumentException e) {
      //do nothing
    }

    this.collage = new CollageProjectModelImpl();

    try {
      this.view = new CollageTextView(null);
      fail("the given arguments cannot be null");
    } catch (IllegalArgumentException e) {
      //do nothing
    }
  }

  @Test
  public void renderMessage() {
    this.collage = new CollageProjectModelImpl();
    this.out = new StringBuilder();
    this.view = new CollageTextView(this.out);

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

    assertTrue(this.renderMessageWithMock());
  }

  /**
   * Test for rendering message.
   *
   * @param view    the appendable.
   * @param message the message to be rendered.
   * @return true if the exception was not thrown, false otherwise.
   */
  public boolean renderMessage(CollageView view, String message) {
    try {
      view.renderMessage(message);
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Mock testing.
   * @return true if the Mock renders an error message
   */
  public boolean renderMessageWithMock() {

    this.out = new StringBuilder();
    this.view = new CollageTextView(this.out);

    Appendable badAppendable = new BadAppendable();
    CollageView badView = new CollageTextView(badAppendable);

    assertTrue(renderMessage(this.view, "Welcome to the Collage "));
    assertEquals("Welcome to the Collage ", this.out.toString());
    assertTrue(renderMessage(this.view, " To Quit, press q."));
    assertEquals("Welcome to the Collage  To Quit, press q.", this.out.toString());

    assertFalse(renderMessage(badView, "Should throw exception"));
    return true;
  }



}