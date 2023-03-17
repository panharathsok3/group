import java.io.IOException;
import java.nio.CharBuffer;


/**
 * Fails when we try to read and are not successful.
 */
public class BadReadable implements Readable {
  @Override
  public int read(CharBuffer cb) throws IOException {
    throw new IOException("Unable to read inputs");
  }
}
