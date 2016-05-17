package com.example.kasra.bingo;

import com.example.kasra.bingo.Utils.Logger;
import com.example.kasra.bingo.router.BaseRouter;
import com.example.kasra.bingo.router.DummyRouter;
import com.example.kasra.bingo.router.MainPage;
import com.example.kasra.bingo.router.ReflectDebug;
import com.example.kasra.bingo.router.StaticHtml;
import fi.iki.elonen.NanoHTTPD;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kasra on 3/14/2016.
 * Simple Server taking care of everything!
 */
public class BingoServer extends NanoHTTPD
{

	/**
	 * Common MIME type for dynamic content: css
	 */
	public static final String MIME_CSS = "text/css";
	private static final BaseRouter staticRouter = new StaticHtml();

	final private static HashMap<String, BaseRouter> routers = new LinkedHashMap<>();

	static
	{
		routers.put(ReflectDebug.URI_BASE, new ReflectDebug());
		routers.put(DummyRouter.URI_BASE, new DummyRouter());
		routers.put(MainPage.URI_BASE, new MainPage());
	}

	BingoServer(int port)
	{
		super(port);
	}

	@Override
	public Response serve(IHTTPSession session)
	{

		Logger.log("REQUEST URI: " + session.getUri());

		BaseRouter router = staticRouter;

		for (String uri : routers.keySet())
		{
			if (session.getUri().startsWith(uri))
			{
				router = routers.get(uri);
				break;
			}
		}

		try
		{
			return router.createResponse(session);
		}
		catch (Exception e)
		{
			StringWriter errors = new StringWriter();
			e.printStackTrace(new PrintWriter(errors));
			return newFixedLengthResponse(Response.Status.BAD_REQUEST, NanoHTTPD.MIME_PLAINTEXT, errors.toString());
		}
	}

	public static Map<String, List<String>> decodeQueryParams(String queryString)
	{
		return decodeParameters(queryString);
	}

}
