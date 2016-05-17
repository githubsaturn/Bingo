package com.example.kasra.bingo.router;

import com.example.kasra.bingo.Bingo;
import com.example.kasra.bingo.BingoServer;
import com.example.kasra.bingo.Utils.Logger;
import fi.iki.elonen.NanoHTTPD;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Kasra on 3/31/2016.
 * A base class for all routing classes
 */

public class StaticHtml extends BaseRouter
{
    public static final String URI_BASE = "/";

	private static final String STATIC_FOLDER = "static/";

	@Override
	public NanoHTTPD.Response createResponse(NanoHTTPD.IHTTPSession session)
	{
		{
			String pathFile = session.getUri().substring(URI_BASE.length());

			if (pathFile.isEmpty() || pathFile.equals("/"))
			{
				pathFile = "index.html";
			}

			pathFile = STATIC_FOLDER + pathFile;

			Logger.log("STATIC FILE: " + pathFile);

			String mimeType = BingoServer.MIME_PLAINTEXT;
            if (pathFile.endsWith(".html"))
            {
                mimeType = BingoServer.MIME_HTML;
            }
            else if (pathFile.endsWith(".css"))
            {
                mimeType = BingoServer.MIME_CSS;
            }

			try
			{
				InputStream reader = Bingo.getApplicationContext().getAssets().open(pathFile);
				return BingoServer.newChunkedResponse(NanoHTTPD.Response.Status.OK, mimeType, reader);
			}
			catch (IOException e)
			{
				throw new RuntimeException(e);
			}
		}
	}
}
