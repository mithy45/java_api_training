package fr.lernejo.navy_battle.server.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class HttpHandlerPing implements HttpHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String body = new String("OK");
        httpExchange.sendResponseHeaders(200, body.length());
        try (OutputStream os = httpExchange.getResponseBody())
        {
            os.write(body.getBytes());
        }
    }
}
