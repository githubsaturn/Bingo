package com.example.kasra.bingo.Utils;

import android.util.Log;

/**
 * Created by Kasra on 3/15/2016.
 * Helper methods
 */

public class Logger
{
	private static final String TAG = "Bingo: ";

	public static void log(String msg)
	{
		System.out.println(TAG + msg);
	}

	public static void w(String msg)
	{
		Log.w(TAG, msg);
	}

}