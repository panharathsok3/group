package view;

import java.io.IOException;

/**
 * A SetGameTextView is a visual representation of the CollageProjectModelImpl. It prints messages
 * to the user.
 */
public class CollageTextView implements CollageView {

  private final Appendable out;

  /**
   * A visualization of the CollageProjectModelImpl.
   * @param out the output for the collage
   * @throws IllegalArgumentException when the given arguments are null
   */
  public CollageTextView(Appendable out)
      throws IllegalArgumentException {
    if (out == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    this.out = out;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.out.append(message);
  }
}
