
import controller.CollageController;
import controller.CollageControllerImpl;

import java.io.InputStreamReader;
import model.CollageProject;
import model.CollageProjectModelImpl;
import view.CollageTextView;
import view.CollageView;

/**
 * Main class for running the collage program.
 */
public class CollageTextMain {

  /**
   * Main method for running the program on a terminal.
   * @param args the arguments being red from the system
   */
  public static void main(String[] args) {
    CollageProject collage = new CollageProjectModelImpl();
    CollageView view = new CollageTextView(System.out);
    CollageController controller = new CollageControllerImpl(new InputStreamReader(System.in),
        collage, view);
    controller.runProgram();
  }
}
