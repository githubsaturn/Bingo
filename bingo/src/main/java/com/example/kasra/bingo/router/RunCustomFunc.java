package com.example.kasra.bingo.router;

import android.os.Handler;
import android.os.Looper;
import com.example.kasra.bingo.Bingo;
import com.example.kasra.bingo.BingoCustomFunction;
import com.example.kasra.bingo.BingoServer;
import com.example.kasra.bingo.Utils.common.util.IOUtils;
import fi.iki.elonen.NanoHTTPD;

import java.util.List;
import java.util.Map;

/**
 * Created by Kasra on 3/31/2016.
 * A base class for all routing classes
 */

public class RunCustomFunc extends BaseRouter
{
	public static final String URI_BASE = "/customfunc";

	@Override
	public NanoHTTPD.Response createResponse(final NanoHTTPD.IHTTPSession session)
	{

		if (session.getMethod().equals(BingoServer.Method.POST))
		{
			String rawInput = IOUtils.convertStreamToString(session.getInputStream());
			Map<String, List<String>> params = BingoServer.decodeQueryParams(rawInput);

			String customMethodName = params.get("methodname").get(0);
			final BingoCustomFunction method = Bingo.getCustomFunctionMap().get(customMethodName);
			int numberOfParams = method.vars.length;
			final String[] args = new String[numberOfParams];
			for (int i = 0; i < args.length; i++)
			{
				args[i] = params.get(method.vars[i]).get(0);
			}

			try
			{
				new Handler(Looper.getMainLooper()).post(new Runnable()
				{
					@Override
					public void run()
					{
						String msg = method.onCall(args);
					}
				});
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			// TODO: 5/17/2016 ADD LOCK
			return BingoServer.newFixedLengthResponse("");
		}
		else
		{
			throw new RuntimeException("This endpoint only accept post requests");
		}

	}

	private String toString(Map<String, ? extends Object> map)
	{
		if (map.size() == 0)
		{
			return "";
		}
		return unsortedList(map);
	}

	private String unsortedList(Map<String, ? extends Object> map)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<ul>");
		for (Map.Entry entry : map.entrySet())
		{
			listItem(sb, entry);
		}
		sb.append("</ul>");
		return sb.toString();
	}

	private void listItem(StringBuilder sb, Map.Entry entry)
	{
		sb.append("<li><code><b>").append(entry.getKey()).
				append("</b> = ").append(entry.getValue()).append("</code></li>");
	}
}
