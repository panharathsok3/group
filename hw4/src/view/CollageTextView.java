package view;

import java.io.IOException;
import model.CollageProjectModel;

/**
 * A SetGameTextView is a visual representation of the CollageProjectModelImpl.
 */
public class CollageTextView implements CollageView {

  private final CollageProjectModel collage;
  private final Appendable out;

  /**
   * A visualization of the CollageProjectModelImpl.
   * @param collage the collage that will be visualized
   * @param out the output for the collage
   * @throws IllegalArgumentException when the given arguments are null
   */
  public CollageTextView(CollageProjectModel collage, Appendable out)
      throws IllegalArgumentException {
    if (collage == null || out == null) {
      throw new IllegalArgumentException("Arguments can't be null");
    }

    this.collage = collage;
    this.out = out;
  }

  @Override
  public void renderMessage(String message) throws IOException {
    this.out.append(message);
  }
}
