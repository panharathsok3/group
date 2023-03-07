
import controller.CollageController;
import controller.CollageControllerImpl;
import java.io.InputStreamReader;
import model.CollageProjectModel;
import model.CollageProjectModelImpl;
import model.ImageUtil;
import view.CollageTextView;
import view.CollageView;

public class Main {

  //demo main
  public static void main(String[] args) {

    String filename;
    Appendable out = new StringBuilder();
    CollageProjectModel collage = new CollageProjectModelImpl(100, 100);
    CollageView view = new CollageTextView(collage, System.out);
    CollageController controller = new CollageControllerImpl(new InputStreamReader(System.in)
        , collage, view);
    controller.runProgram();

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "src/tako.ppm";
    }
    ImageUtil.readPPM(filename);
  }

}
