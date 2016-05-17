package com.example.kasra.bingo.Utils;

/**
 * Created by Kasra on 3/31/2016.
 * Helper class for loading and saving files
 */
public class Storage
{
	private static Storage instance = new Storage();

	public static Storage getInstance()
	{
		return instance;
	}

	// TODO:
	public String readFile(String localPath)
	{
		return "<h1>HELLO " + localPath + "</h1>";
	}
}
