package controller;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import model.CollageProject;
import view.CollageView;

/**
 * A CollageControllerImpl is the controller for the CollageProjectModelImpl. This class is used to
 * read user input and perform actions accordingly.
 */
public class CollageControllerImpl implements CollageController {
  private final Readable in;
  private final CollageProject collage;
  private final CollageView view;

  /**
   * Represents the controller for this CollageProjectModelImpl.
   *
   * @param in      used to parse input from the user
   * @param collage the collage that will be worked on
   * @param view    the view of the CollageProjectModelImpl
   * @throws IllegalArgumentException if any of the given parameters is null
   */
  public CollageControllerImpl(Readable in, CollageProject collage, CollageView view)
          throws IllegalArgumentException {
    if (in == null || collage == null || view == null) {
      throw new IllegalArgumentException("the given arguments cannot be null");
    }
    this.in = in;
    this.collage = collage;
    this.view = view;
  }

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
          this.renderMessage("The program has ended");
          break;
        case "new-project":
          String name = this.readValueString(sc);
          int height = this.readValueInteger(sc);
          int width = this.readValueInteger(sc);
          try {
            this.collage.newProject(name, height, width);
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null");
          }
          break;
        case "load-project":
          String filename = this.readValueString(sc);
          try {
            this.collage.loadProject(filename);
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null");
          } catch (IllegalStateException e) {
            this.renderMessage("File can't be open or file is not enough to start a load a "
                + "project");
          }
          break;
        case "save-project":
          String filePath = this.readValueString(sc);
          String fileType = this.readValueString(sc);
          try {
            this.collage.saveProject(filePath, fileType);
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null");
          } catch (IllegalStateException e) {
            this.renderMessage("The project hasn't been made yet");
          }
          break;
        case "add-layer":
          String layerName = this.readValueString(sc);
          try {
            this.collage.addLayer(layerName);
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null or the layer already exist");
          } catch (IllegalStateException e) {
            this.renderMessage("The project hasn't been made yet");
          }
          break;
        case "add-image-to-layer":
          String layerName1 = this.readValueString(sc);
          String imageName = this.readValueString(sc);
          int x = this.readValueInteger(sc);
          int y = this.readValueInteger(sc);
          try {
            this.collage.addImageToLayer(layerName1, imageName, x, y);
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null or negative or the layer doesn't exist");
          } catch (IllegalStateException e) {
            this.renderMessage("The layer already exists or the project hasn't been made yet");
          }
          break;
        case "set-filter":
          String layerName2 = this.readValueString(sc);
          String filterOption = this.readValueString(sc);
          try {
            this.collage.setFilter(layerName2, filterOption);
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null or the layer doesn't exist or filter "
                + "doesn't exist");
          } catch (IllegalStateException e) {
            this.renderMessage("The project hasn't been made yet");
          }
          break;
        case "save-image":
          String fileName = this.readValueString(sc);
          try {
            this.collage.saveImage(fileName);
          } catch (IllegalArgumentException e) {
            this.renderMessage("Arguments can't be null");
          } catch (IllegalStateException e) {
            this.renderMessage("The project hasn't been made yet");
          }
          break;
        default:
          this.renderMessage("Command doesn't exist");
      }
    }
  }

  /**
   * Reads the next user input as a String and returns it.
   *
   * @param scan the scanner to read in the next user input
   * @return the user input as a String
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
   *
   * @param scan the scanner to read in the next user input
   * @return the user input as an integer
   * @throws IllegalStateException if and only if the controller is unable to successfully read
   *                               input
   */
  private int readValueInteger(Scanner scan) throws IllegalStateException {

    String data = this.readValueString(scan);

    int dataInt = 0;

    try {
      dataInt = Integer.parseInt(data);
    } catch (NumberFormatException e) {
      // do nothing
    }

    return dataInt;
  }

  /**
   * Renders a message to the user.
   * @param message the message to print out
   */
  private void renderMessage(String message) {
    try {
      this.view.renderMessage(message + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Unexpected IOException\n");
    }
  }


}
