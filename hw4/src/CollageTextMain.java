
import controller.CollageController;
import controller.CollageControllerImpl;

import java.io.InputStreamReader;
import model.CollageProject;
import model.CollageProjectModelImpl;
import view.CollageTextView;
import view.CollageView;

public class CollageTextMain {

  //demo main
  public static void main(String[] args) {

    String filename;
    Appendable out = new StringBuilder();
    CollageProject collage = new CollageProjectModelImpl();
    CollageView view = new CollageTextView(collage, System.out);
    CollageController controller = new CollageControllerImpl(new InputStreamReader(System.in)
        , collage, view);
    controller.runProgram();

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "src/tako.ppm";
    }

  }

}
