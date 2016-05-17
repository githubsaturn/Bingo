package com.example.kasra.bingo.router;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Kasra on 3/31/2016.
 * A base router for all pages
 */
public abstract class BaseRouter
{
	public abstract NanoHTTPD.Response createResponse(NanoHTTPD.IHTTPSession session);
}
