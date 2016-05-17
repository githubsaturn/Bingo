package com.example.kasra.bingo.router;

import com.example.kasra.bingo.Bingo;
import com.example.kasra.bingo.BingoCustomFunction;
import com.example.kasra.bingo.BingoServer;
import com.x5.template.Chunk;
import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Kasra on 3/31/2016.
 * A base class for all routing classes
 */

public class MainPage extends BaseRouter
{
	public static final String URI_BASE = "/pages/index.html";

	@Override
	public NanoHTTPD.Response createResponse(final NanoHTTPD.IHTTPSession session)
	{

		try
		{
			InputStream reader = Bingo.getApplicationContext().getAssets().open(
					StaticHtml.STATIC_FOLDER.substring(0, StaticHtml.STATIC_FOLDER.length() - 1) + URI_BASE);
			String rawData = convertStreamToString(reader);

			Chunk html = theme.makeChunk();
			html.append(rawData);

			html.set("appPackage", Bingo.getApplicationContext().getPackageName());

			Map<String, BingoCustomFunction> customFunctions = Bingo.getCustomFunctionMap();
			List<FunctionsToShow> functionsToShows = new ArrayList<>();
			for (String functionName : customFunctions.keySet())
			{
				functionsToShows.add(new FunctionsToShow(functionName, customFunctions.get(functionName).vars));
			}

			html.set("customFunctions", functionsToShows);

			return BingoServer.newFixedLengthResponse(NanoHTTPD.Response.Status.OK, BingoServer.MIME_HTML, html.toString());
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	/**
	 * Perhaps the hackiest way that I've see to convert InputSteam to string!
	 *
	 * @param is input steam
	 * @return string that is constructed using input stream
	 */
	static private String convertStreamToString(java.io.InputStream is)
	{
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	public static class FunctionsToShow
	{
		String name;
		String[] vars;

		public FunctionsToShow(String name, String[] vars)
		{
			this.name = name;
			this.vars = vars;
		}
	}

}
