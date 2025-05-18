package sumguytho.spider.util;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Does nothing with provided input.
 */
public class FakeOutputStream extends OutputStream {
	public FakeOutputStream() { }
	@Override
	public void write(int arg0) throws IOException { }
}
