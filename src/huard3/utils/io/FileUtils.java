package huard3.utils.io;

/**
 * Insert the type's description here.
 * Creation date: (15/01/2001 15:05:07)
 * @author: Shlomi Noach
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class FileUtils
{
	public static char LINE_DELIMETER = '\r';
	private static FileUtils instance = new FileUtils();
	/**
	 * FileUtils constructor comment.
	 */
	public FileUtils()
	{
		super();
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (25/02/2002 15:14:08)
	 * @return start.io.FileUtils
	 */
	public static FileUtils instance()
	{
		return instance;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (23/10/2001 15:48:19)
	 * @param fileName java.lang.String
	 * @param data java.lang.String
	 */
	public Object readObjectFile(File f)
	{
		Object result = null;
		try
		{
			ObjectInputStream input = new ObjectInputStream(new FileInputStream(f));
			result = input.readObject();
			input.close();
		}
		catch (Exception e)
		{
			// result stays 'null' ;
		}
		return result;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (15/01/2001 15:05:23)
	 * @param source java.io.File
	 * @param dest java.io.File
	 */
	public String readTextFile(File f)
	{
		return readTextFile(f, LINE_DELIMETER);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (15/01/2001 15:05:23)
	 * @param source java.io.File
	 * @param dest java.io.File
	 */
	public String readTextFile(File f, char lineDelimeter)
	{
		String data = null;
		try
		{
			int size = (int)f.length(); // Assuming, of course, file size is less than 4giga
			StringBuffer buff = new StringBuffer(size);
			BufferedReader reader = new BufferedReader(new AsciiFileReader(f));

			String s = null;
			while ((s = reader.readLine()) != null)
			{
				buff.append(s);
				buff.append(lineDelimeter);
			}
			reader.close();

			data = buff.toString();
		}
		catch (IOException e)
		{
			// data stays 'null'
		}
		
		return data;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (15/01/2001 15:05:23)
	 * @param source java.io.File
	 * @param dest java.io.File
	 */
	public String readTextFile(File f, String lineDelimeter)
	{
		String data = null;
		try
		{
			int size = (int)f.length(); // Assuming, of course, file size is less than 4giga
			StringBuffer buff = new StringBuffer(size);
			BufferedReader reader = new BufferedReader(new AsciiFileReader(f));
			

			String s = null;
			while ((s = reader.readLine()) != null)
			{
				buff.append(s);
				buff.append(lineDelimeter);
			}
			reader.close();
			

			data = buff.toString();
			
		}
		catch (IOException e)
		{
			// data stays 'null'
			System.out.println(e);
		}
		return data;
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (15/01/2001 15:05:23)
	 * @param source java.io.File
	 * @param dest java.io.File
	 */
	public String readCompressedTextFile(File f, char lineDelimeter)
	{
		String data = null;
		try
		{
			StringBuffer buff = new StringBuffer();
			BufferedReader reader =
				new BufferedReader(
					new InputStreamAsciiReader(new GZIPInputStream(new FileInputStream(f))));

			String s = null;
			while ((s = reader.readLine()) != null)
			{
				buff.append(s);
				buff.append(lineDelimeter);
			}
			reader.close();

			data = buff.toString();
		}
		catch (IOException e)
		{
			// data stays 'null'
		}
		return data;
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (15/01/2001 15:05:23)
	 * @param source java.io.File
	 * @param dest java.io.File
	 */
	public boolean writeTextFile(File f, String text)
	{
		try
		{
			Writer writer = new AsciiFileWriter(f);
			writer.write(text);
			writer.close();
		}
		catch (IOException e)
		{
			return false; // failure 
		}
		return true; // success
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (23/10/2001 15:48:19)
	 * @param fileName java.lang.String
	 * @param data java.lang.String
	 */
	public boolean writeObjectFile(File f, Serializable obj)
	{
		try
		{
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(f));
			output.writeObject(obj);
			output.close();
		}
		catch (IOException e)
		{
			return false; // failure 
		}
		return true; // success

	}
	/**
	 * Insert the method's description here.
	 * Creation date: (15/01/2001 15:05:23)
	 * @param source java.io.File
	 * @param dest java.io.File
	 */
	public boolean writeCompressedTextFile(File f, String text)
	{
		try
		{
			Writer writer =
				new OutputStreamAsciiWriter(new GZIPOutputStream(new FileOutputStream(f)));
			writer.write(text);
			writer.close();
		}
		catch (IOException e)
		{
			return false; // failure 
		}
		return true; // success
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (15/01/2001 15:05:23)
	 * @param source java.io.File
	 * @param dest java.io.File
	 */
	public boolean copyTextFile(File source, File dest)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new AsciiFileReader(source));
			PrintWriter writer = new PrintWriter(new AsciiFileWriter(dest));

			String s = null;
			while ((s = reader.readLine()) != null)
			{
				writer.println(s);
			}

			writer.close();
			reader.close();

			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (25/02/2002 15:36:19)
	 * @return java.lang.String
	 */
	public String toString()
	{
		return "[FileUtils]";
	}
	
	public static void main (String[] args){
		File f = new File("/usr/local/tomcat/webapps/huard/html/239.html");
		FileUtils fu = new FileUtils();
		String html = fu.readTextFile(f,"\r");
		System.out.println(html);
	}
}