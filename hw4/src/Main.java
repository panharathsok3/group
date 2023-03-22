import controller.CollageController;
import controller.CollageControllerImpl;
import controller.CollageGUIController;
import controller.Features;
import model.CollageProject;
import model.CollageProjectModelImpl;
import view.GUIView;
import view.JFrameView;

public class Main {
  public static void main(String[] args) {
    CollageProject collage = new CollageProjectModelImpl();
    GUIView view = new JFrameView();

    CollageController textUIController = new CollageControllerImpl();
    Features controller = new CollageGUIController(collage, textUIController);
    controller.setView(view);
  }
}
