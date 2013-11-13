package huard3.utils.io;

/**
 * This type was created in VisualAge.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class InputStreamAsciiReader extends Reader
{
	private InputStream input;
	/**
	 * NeutralFileReader constructor comment.
	 * @param lock java.lang.Object
	 */
	public InputStreamAsciiReader(InputStream input)
	{
		super();
		this.input = input;
	}
	/**
	 * close method comment.
	 */
	public void close() throws java.io.IOException
	{
		input.close();
	}
	/**
	 * This method was created in VisualAge.
	 * @return int
	 */
	public int read() throws IOException
	{
		return input.read();
	}
	/**
	 * This method was created in VisualAge.
	 * @return int
	 */
	public int read(char[] cbuf) throws IOException
	{
		return read(cbuf, 0, cbuf.length);
	}
	/**
	 * read method comment.
	 */
	public int read(char[] cbuf, int off, int len) throws java.io.IOException
	{
		boolean done = false;
		int count = 0;
		while ((count < len) && !done)
		{
			int readInt = input.read();
			if (readInt < 0)
			{
				done = true;
			}
			else
			{
				cbuf[off + count] = (char)readInt;
				++count;
			}
		}
		if (count == 0)
			return -1;
		return count;
	}
}
