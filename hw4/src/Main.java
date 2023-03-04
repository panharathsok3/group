import model.ImageUtil;

public class Main {

  //demo main
  public static void main(String[] args) {
    String filename;

    if (args.length > 0) {
      filename = args[0];
    } else {
      filename = "src/tako.ppm";
    }
    ImageUtil.readPPM(filename);
  }

}
