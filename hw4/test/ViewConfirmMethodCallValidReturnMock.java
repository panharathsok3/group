import java.io.IOException;

import view.CollageView;

/**
 * Mock to test communication between the view and the controller.
 */
public class ViewConfirmMethodCallValidReturnMock implements CollageView {

  private final Appendable log;

  public ViewConfirmMethodCallValidReturnMock(Appendable log) {
    this.log = log;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.log.append(message);
  }
}
