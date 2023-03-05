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

  public void display(String... messages) {
    try {
      for (String message : messages) {
        this.view.renderMessage(message);
        this.view.renderMessage("\n");
      }
    } catch (IOException e) {
      //
    }
  }


  private void menu() {
    display("'To create a new Project, new-project width height' Creates the project with the given name and given dimensions" +
            "'To load an existing project, load-project path-to-project-file' loads a project into the program " +
            "'To save a project,  save-project projectPath' save the project as one file  " +
            "'To save an image, save-image imagePath' save the result of applying all filters on the image " +
            "'To add a layer to the project, add-layer layerName' Adds a new layer with the given name to the top of the whole project." +
            "'To add an image to a layer, add-image-to-layer layer-name image-path x-pos y-pos' places an image on the layer such that the top left corner of the image is at (x-pos, y-pos)" +
            " Adds the image to the given layer with no offset " +
            " 'To set a filter set-filter layer-name filter-option' sets the filter of the given layer" +
            "'To quit the program, quit' quits the project and loses all unsaved work");
  }

  /*

# Sets the image on the layer to the provided image, but offset 100
# pixels to the right and 50 pixels down from the top left
add-image-to-layer tako-blue image/tako.ppm 100 50
   */


  @Override
  public void runProgram() throws IllegalStateException {
    Scanner sc = new Scanner(this.in);
    display();

    if (!sc.hasNext()) {
      throw new IllegalStateException("Ran out of inputs.");
    }

    while (sc.hasNext()) {
      String command = sc.next();

      //if it is a comment, skip to next line.
      if (command.startsWith("#")) {
        sc.nextLine();
      } else if (command.equals("q") || (command.equals("Q") || (command.equals("Quit") || (command.equals("quit"))))) {
        display("Program Quit. Thank You!");
        break;
      } else if (command.equals("help") || command.equals("h")) {
        //display the options if the user needs help remembering how to do something.
        menu();
      }


    }
  }


}
