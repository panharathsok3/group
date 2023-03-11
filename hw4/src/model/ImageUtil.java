package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and returns the pixels on the image as a 2D array.
   * @param filename the path of the file
   * @return the pixels on the image as a 2D array
   * @throws IllegalStateException when the file could not be retrieved
   *                               or the file is not a PPM file
   */
  public ArrayList<ArrayList<Pixel>> readPPM(String filename) throws IllegalStateException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      throw new IllegalStateException("File " + filename + " not found!");
    }

    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalStateException("Invalid PPM file: plain RAW file should begin with P3");
    }

    int width = sc.nextInt();
    int height = sc.nextInt();

    ArrayList<ArrayList<Pixel>> pixelsOnImage = new ArrayList<>();

    for (int i = 0; i < height; i++) {
      pixelsOnImage.add(new ArrayList<>());
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        pixelsOnImage.get(i).add(new Pixel(r, g, b));
      }
    }
    return pixelsOnImage;
  }

}

