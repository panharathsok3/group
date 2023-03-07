package controller;

import java.io.IOException;
import java.util.Scanner;

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
   *
   * @param in      used to parse input from the user
   * @param collage the collage that will be worked on
   * @param view    the view of the CollageProjectModelImpl
   * @throws IllegalArgumentException if any of the given parameters is null
   */
  public CollageControllerImpl(Readable in, CollageProjectModel collage, CollageView view)
          throws IllegalArgumentException {
    if (in == null || collage == null || view == null) {
      throw new IllegalArgumentException("the given arguments cannot be null");
    }

    this.in = in;
    this.collage = collage;
    this.view = view;
  }





  /*

# Sets the image on the layer to the provided image, but offset 100
# pixels to the right and 50 pixels down from the top left
add-image-to-layer tako-blue image/tako.ppm 100 50
   */


  @Override
  public void runProgram() throws IllegalStateException {
    Scanner sc = new Scanner(this.in);
   // display();

    if (!sc.hasNext()) {
      throw new IllegalStateException("Ran out of inputs.");
    }

    while (sc.hasNext()) {
      String command = sc.next();

      //if it is a comment, skip to next line.
      if (command.startsWith("#")) {
        sc.nextLine();
      } else if (command.equals("q") || (command.equals("Q") || (command.equals("Quit") || (command.equals("quit"))))) {
        //display("Program Quit. Thank You!");
        break;
      } else if (command.equals("help") || command.equals("h")) {
        //display the options if the user needs help remembering how to do something.
        //menu();
      }


    }
  }


}
