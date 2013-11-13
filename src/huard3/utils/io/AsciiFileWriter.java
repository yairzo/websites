package huard3.utils.io;

/**
 * This type was created in VisualAge.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class AsciiFileWriter extends java.io.Writer
{
	private FileOutputStream output;
	private byte[] buff;
	/**
	 * AsciiFileWriter constructor comment.
	 */
	protected AsciiFileWriter()
	{
		super();
		buff = new byte[20];
	}
	/**
	 * AsciiFileWriter constructor comment.
	 */
	public AsciiFileWriter(File f) throws IOException
	{
		this();
		output = new FileOutputStream(f);
	}
	/**
	 * AsciiFileWriter constructor comment.
	 */
	public AsciiFileWriter(String fileName) throws IOException
	{
		this();
		output = new FileOutputStream(fileName);
	}
	/**
	 * close method comment.
	 */
	public void close() throws java.io.IOException
	{
		output.close();
	}
	/**
	 * flush method comment.
	 */
	public void flush() throws java.io.IOException
	{
		output.flush();
	}
	/**
	 * write method comment.
	 */
	public void write(char[] cbuf) throws java.io.IOException
	{
		write(cbuf, 0, cbuf.length);
	}
	/**
	 * write method comment.
	 */
	public void write(char[] cbuf, int off, int len) throws java.io.IOException
	{
		if (buff.length < len)
			buff = new byte[len];
		for (int i = 0; i < len; ++i)
		{
			int charInt = (int)cbuf[off + i];
			if (charInt >= 1488 & charInt<=1514) charInt = charInt -1488 +224; 
			buff[i] = (byte)charInt;
		}
		output.write(buff, 0, len);
	}
	/**
	 * write method comment.
	 */
	public void write(int c) throws java.io.IOException
	{
		output.write(c);
	}
	/**
	 * write method comment.
	 */
	public void write(String str) throws java.io.IOException
	{
		char[] ca = str.toCharArray();
		write(ca);
	}
	/**
	 * write method comment.
	 */
	public void write(String str, int off, int len) throws java.io.IOException
	{
		char[] ca = str.toCharArray();
		write(ca, off, len);
	}
}
