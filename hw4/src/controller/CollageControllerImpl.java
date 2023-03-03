package controller;

import model.CollageProjectModel;
import view.CollageView;

/**
 * A CollageControllerImpl is the controller for the CollageProjectModelImpl.
 */
public class CollageControllerImpl implements CollageController {
  private final Readable in;
  private final CollageProjectModel collage;
  private final CollageView view;

  /**
   * Represents the controller for this CollageProjectModelImpl.
   * @param in used to parse input from the user
   * @param collage the collage that will be worked on
   * @param view the view of the CollageProjectModelImpl
   * @throws IllegalArgumentException if any of the given parameters is null
   */
  public CollageControllerImpl(Readable in, CollageProjectModel collage, CollageView view)
      throws IllegalArgumentException{
    if (in == null || collage == null || view == null) {
      throw new IllegalArgumentException("the given arguments cannot be null");
    }

    this.in = in;
    this.collage = collage;
    this.view = view;
  }

  @Override
  public void runProgram() throws IllegalStateException {

  }
}
