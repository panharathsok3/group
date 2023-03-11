import java.io.IOException;

import view.CollageView;

/**
 * Mock to test communication between the view and the controller.
 */
public class MockCollageView implements CollageView {

  private StringBuilder log = new StringBuilder();

  /**
   * Helps us see a log communication between the controller and view.
   * @return a transcript of what the controller sent to the view.
   */
  public String getLog() {
    return log.toString();
  }
  @Override
  public void renderMessage(String message) throws IOException {
    this.log.append(message).append("\n");
  }
}
