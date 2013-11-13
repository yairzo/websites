package huard3.utils.io;

/**
 * This type was created in VisualAge.
 */
import java.io.OutputStream;

public class OutputStreamAsciiWriter extends java.io.Writer
{
	private OutputStream output;
	private byte[] buff;
	/**
	 * OutputStreamExactWriter constructor comment.
	 */
	public OutputStreamAsciiWriter(OutputStream output)
	{
		super();
		this.output = output;
		buff = new byte[20];
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
	public void write(char[] cbuf, int off, int len) throws java.io.IOException
	{
		if (cbuf.length > buff.length)
		{
			buff = new byte[cbuf.length];
		}
		for (int i = 0; i < cbuf.length; ++i)
		{
			buff[i] = (byte) (cbuf[i]);
		}
		for (int i = 0; i < len; ++i)
			output.write(buff[off + i]);
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
		write(str.toCharArray(), 0, str.length());
	}
	/**
	 * write method comment.
	 */
	public void write(String str, int off, int len) throws java.io.IOException
	{
		write(str.toCharArray(), off, len);
	}
}
