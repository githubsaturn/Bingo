package com.example.kasra.bingo.Utils.common.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * IO utils
 *
 * @author Vladislav Bauer
 */

public class IOUtils
{

	private IOUtils()
	{
		throw new AssertionError();
	}


	/**
	 * Close closable object and wrap {@link IOException} with {@link RuntimeException}
	 *
	 * @param closeable closeable object
	 */
	public static void close(Closeable closeable)
	{
		if (closeable != null)
		{
			try
			{
				closeable.close();
			}
			catch (IOException e)
			{
				throw new RuntimeException("IOException occurred. ", e);
			}
		}
	}

	/**
	 * Close closable and hide possible {@link IOException}
	 *
	 * @param closeable closeable object
	 */
	public static void closeQuietly(Closeable closeable)
	{
		if (closeable != null)
		{
			try
			{
				closeable.close();
			}
			catch (IOException e)
			{
				// Ignored
			}
		}
	}
	/**
	 * Perhaps the hackiest way that I've see to convert InputSteam to string!
	 *
	 * @param is input steam
	 * @return string that is constructed using input stream
	 */
	static public String convertStreamToString(java.io.InputStream is)
	{
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

}
