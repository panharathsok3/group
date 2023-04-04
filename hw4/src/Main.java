import controller.CollageController;
import controller.CollageControllerImpl;
import controller.CollageGUIController;
import controller.Features;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;
import model.CollageProject;
import model.CollageProjectModelImpl;
import view.CollageTextView;
import view.CollageView;
import view.GUIView;
import view.JFrameView;

/**
 * The Main class is used to run the program either as an interactive text program or as a
 * graphical user interface. It can also take in a script file to execute the commands as well
 * and it will exit upon finishing.
 */
public class Main {

  /**
   * This main class is used to run either an interactive text program or used as a GUI that has
   * the same functionality as the interactive text program. It also accepts a script to execute the
   * program as well which will exit upon finishing.
   * @param args the arguments from the command line that will either be used as a text program or
   *             as a GUI
   * @throws IllegalStateException if the command doesn't exist
   *                               or if the given filepath isn't found
   */
  public static void main(String[] args) throws IllegalStateException {

    if (args.length > 2) {
      throw new IllegalStateException("Command doesn't exist");
    }

    if (args.length != 0) {
      if (args[0].equals("-text")) {

        if (args.length == 2) {
          FileReader reader = null;
          Readable input = null;

          try {
            reader = new FileReader(args[1]);
            Scanner sc = new Scanner(reader);
            StringBuilder s = new StringBuilder();
            while (sc.hasNextLine()) {
              s.append(sc.next()).append(" ");
            }
            input = new StringReader(s.toString());
          } catch (FileNotFoundException e) {
            throw new IllegalStateException("File not found");
          }

          CollageProject collage = new CollageProjectModelImpl();
          CollageView view = new CollageTextView(System.out);
          CollageController controller = new CollageControllerImpl(input,
              collage, view);
          controller.runProgram();
          System.exit(0);
        }

        CollageProject collage = new CollageProjectModelImpl();
        CollageView view = new CollageTextView(System.out);
        CollageController controller = new CollageControllerImpl(new InputStreamReader(System.in),
            collage, view);
        controller.runProgram();
        System.exit(0);
      }
    }


    CollageProject collage = new CollageProjectModelImpl();
    GUIView view = new JFrameView();
    Features controller = new CollageGUIController(collage);
    controller.setView(view);
  }
}
