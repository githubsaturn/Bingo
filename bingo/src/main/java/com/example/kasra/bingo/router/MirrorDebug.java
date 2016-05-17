package com.example.kasra.bingo.router;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.example.kasra.bingo.Bingo;
import com.example.kasra.bingo.BingoServer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Kasra on 3/31/2016.
 * A base class for all routing classes
 */

public class MirrorDebug extends BaseRouter {
    public static final String URI_BASE = "/reflect";

    @Override
    public NanoHTTPD.Response createResponse(final NanoHTTPD.IHTTPSession session) {
        {
            Map<String, List<String>> decodedQueryParameters =
                    BingoServer.decodeQueryParams(session.getQueryParameterString());

            StringBuilder sb = new StringBuilder();
            sb.append("<html>");
            sb.append("<head><title>Debug Server</title></head>");
            sb.append("<body>");
            sb.append("<h1>Debug Server</h1>");
            sb.append("<p>This page gets called as long as the URI starts with " + URI_BASE + "</p>");

            sb.append("<p><blockquote><b>URI</b> = ").append(
                    String.valueOf(session.getUri())).append("<br />");

            sb.append("<b>Method</b> = ").append(
                    String.valueOf(session.getMethod())).append("</blockquote></p>");

            sb.append("<h3>Headers</h3><p><blockquote>").
                    append(toString(session.getHeaders())).append("</blockquote></p>");

            sb.append("<h3>Parms</h3><p><blockquote>").
                    append(toString(session.getParms())).append("</blockquote></p>");

            sb.append("<h3>Parms (multi values?)</h3><p><blockquote>").
                    append(toString(decodedQueryParameters)).append("</blockquote></p>");

            try {
                Map<String, String> files = new HashMap<String, String>();
                session.parseBody(files);
                sb.append("<h3>Files</h3><p><blockquote>").
                        append(toString(files)).append("</blockquote></p>");
            } catch (Exception e) {
                e.printStackTrace();
            }

            sb.append("</body>");
            sb.append("</html>");

            final Semaphore e = new Semaphore(1);

            try {
                e.acquire();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }

            Handler h = new Handler(Looper.getMainLooper());
            h.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(Bingo.getApplicationContext(), "Mirror response... " + session.getParms().get("msg"), Toast.LENGTH_LONG).show();
                    e.release();
                }
            });

            try {
                e.acquire();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }


            return BingoServer.newFixedLengthResponse(sb.toString());
        }
    }

    private String toString(Map<String, ? extends Object> map) {
        if (map.size() == 0) {
            return "";
        }
        return unsortedList(map);
    }

    private String unsortedList(Map<String, ? extends Object> map) {
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>");
        for (Map.Entry entry : map.entrySet()) {
            listItem(sb, entry);
        }
        sb.append("</ul>");
        return sb.toString();
    }

    private void listItem(StringBuilder sb, Map.Entry entry) {
        sb.append("<li><code><b>").append(entry.getKey()).
                append("</b> = ").append(entry.getValue()).append("</code></li>");
    }
}
