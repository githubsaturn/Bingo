package com.example.kasra.bingo.router;

import com.example.kasra.bingo.BingoServer;

import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Kasra on 3/31/2016.
 * A base class for all routing classes
 */

public class DummyRouter extends BaseRouter {

    public static final String URI_BASE = "/dummy";

    @Override
    public NanoHTTPD.Response createResponse(NanoHTTPD.IHTTPSession session) {
        String msg = "<html><body><h1>Hello server</h1>\n";
        Map<String, String> parms = session.getParms();
        if (parms.get("username") == null) {
            msg += "<form action='?' method='get'>\n  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
        } else {
            msg += "<p>Hello, " + parms.get("username") + "!</p>";
        }
        return BingoServer.newFixedLengthResponse(msg + "</body></html>\n");
    }
}
