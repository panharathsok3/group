package controller;

import java.io.IOException;
import java.util.NoSuchElementException;
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
    boolean running = true;
   // display();

    if (!sc.hasNext()) {
      throw new IllegalStateException("Ran out of inputs.");
    }

    while (running) {
      String command = this.readValueString(sc);

      switch (command) {
        case "quit":
          running = false;
          break;
        case "new-project":
          int height = this.readValueInteger(sc);
          int width = this.readValueInteger(sc);
          this.collage.newProject(height, width);
          break;
        case "load-project":
          //this.collage.lo
        default:
          throw new IllegalArgumentException("");

      }

    }
  }

  /**
   * Reads the next user input as a String and returns it.
   * @param scan the scanner to read in the next user input
   * @return
   * @throws IllegalStateException if and only if the controller is unable to successfully read
   *                               input
   */
  private String readValueString(Scanner scan) throws IllegalStateException {

    String data;

    try {
      data = scan.next();
    } catch (NoSuchElementException e) {
      throw new IllegalStateException("No more inputs");
    }

    return data;
  }

  /**
   * Reads the next user input as an Integer and returns it.
   * @param scan the scanner to read in the next user input
   * @return the row or column number subtracted by 1
   * @throws IllegalStateException if and only if the controller is unable to successfully read
   *                               input
   */
  private int readValueInteger(Scanner scan) throws IllegalStateException {

    String data = this.readValueString(scan);

    int dataInt = 0;
    boolean wrongStringInput = false;

    try {
      dataInt = Integer.parseInt(data);
    } catch (NumberFormatException e) {
      wrongStringInput = true;
    }

    while (wrongStringInput) {
      data = this.readValueString(scan);

      wrongStringInput = false;
      try {
        dataInt = Integer.parseInt(data);
      } catch (NumberFormatException e) {
        wrongStringInput = true;
      }
    }

    return dataInt;
  }


}
