package huard3.utils.io;

/**
 * This type was created in VisualAge.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

public class AsciiFileReader extends Reader
{
	private FileInputStream input;
	/**
	 * This method was created in VisualAge.
	 */
	protected AsciiFileReader()
	{
		super();
	}
	/**
	 * NeutralFileReader constructor comment.
	 * @param lock java.lang.Object
	 */
	public AsciiFileReader(File f) throws FileNotFoundException
	{
		this();
		input = new FileInputStream(f);
	}
	/**
	 * NeutralFileReader constructor comment.
	 */
	public AsciiFileReader(String fileName) throws FileNotFoundException
	{
		this();
		input = new FileInputStream(fileName);
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
			if (readInt >= 224 & readInt <= 250) readInt = readInt - 224 +1488;
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
