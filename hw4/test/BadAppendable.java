import java.io.IOException;

/**
 * Appendable that always throws IOException.
 */
public class BadAppendable implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("Failed!");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("Failed!");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("Failed!");
  }
}
