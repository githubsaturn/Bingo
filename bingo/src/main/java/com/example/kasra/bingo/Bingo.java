package com.example.kasra.bingo;

import android.app.Application;
import android.content.Context;
import com.example.kasra.bingo.Utils.Logger;
import com.example.kasra.bingo.Utils.NetUtils;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Kasra on 3/14/2016.
 * Main Library class for Bingo
 */
public class Bingo
{
	private static final int DEFAULT_PORT = 55555;
	private static final Object locker = new Object();
	private static final int MAX_ALLOWABLE_RETRY = 10;

	static private BingoServer bingoServer;
	static private boolean initialized;

	private static Application applicationContext;

	public static void startAsync(Context context)
	{

		synchronized (locker)
		{

			if (initialized)
			{
				Logger.log("Bingo already initialized... Ignoring this...");
				return;
			}

			applicationContext = (Application) context.getApplicationContext();

			initialized = true;

			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					startNonAsync();
				}
			}).start();

		}
	}

	static public Application getApplicationContext()
	{
		return applicationContext;
	}

	private static void startNonAsync()
	{
		int port = DEFAULT_PORT;
		int counter = 0;

		while (bingoServer == null)
		{

			if (counter > MAX_ALLOWABLE_RETRY)
			{
				throw new RuntimeException("Cannot allocate a port after " + MAX_ALLOWABLE_RETRY + " tries...  Something is wrong...");
			}

			bingoServer = new BingoServer(port);

			try
			{
				bingoServer.start(1000);
			}
			catch (IOException e)
			{
				e.printStackTrace();
				try
				{
					bingoServer.stop();
				}
				catch (Exception ignore)
				{
				}
				bingoServer = null;
				port = new Random().nextInt(DEFAULT_PORT) + 10;
			}

			counter++;
		}

		Logger.log(" -------------------------------------------------------------------------------------------------------");
		Logger.log(" -.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-. BINGO SERVER STARTED -.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-. ");
		Logger.log(" If your development machine and android device are on the same wifi: http://" + NetUtils.getIPAddress(true) + ":" + port);
		Logger.log(" Otherwise, run \"adb forward tcp:" + port + " tcp:" + port + "\" in your terminal and then try http://127.0.0.1:" + port);
		Logger.log(" -------------------------------------------------------------------------------------------------------");
	}
}
