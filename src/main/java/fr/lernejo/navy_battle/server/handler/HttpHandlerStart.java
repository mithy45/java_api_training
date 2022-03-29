package fr.lernejo.navy_battle.server.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.game.APIGame;
import fr.lernejo.navy_battle.server.response.ResponseStart;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

public class HttpHandlerStart implements HttpHandler, AttributeValidator {

    private final int port;
    private final String id = UUID.randomUUID().toString();
    public HttpHandlerStart(int port) {
        this.port = port;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String body; int statusCode;
        JsonNode node = new ObjectMapper().readTree(getStringBody(httpExchange.getRequestBody()));
        if (!httpExchange.getRequestMethod().equalsIgnoreCase("POST")) {
            body = "Not Found"; statusCode = 404;
        }
        else if (!hasValidAttribute(node)) {
            body = "Bad Request"; statusCode = 400;
        }
        else {
            APIGame game = new APIGame(node); body = new ResponseStart(id, "http://localhost:" + port, "May the best code win").getJsonString();
            statusCode = 202; game.start();
        }
        httpExchange.sendResponseHeaders(statusCode, body.length());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(body.getBytes()); }
    }

    @Override
    public boolean hasValidAttribute(JsonNode node) {
        String userURL = node.get("url").textValue();
        if (node.get("id") == null || userURL == null || node.get("message") == null)
        {
            return false;
        }
        try
        {
            new URL(userURL);
        }catch (Exception e)
        {
            System.err.println("Error URL START " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean hasValidAttribute(HashMap node) {
        return false;
    }

    @Override
    public String getStringBody(InputStream inputStream) throws IOException {
        InputStreamReader isr =  new InputStreamReader(inputStream,"utf-8");
        BufferedReader br = new BufferedReader(isr);
        int b;
        StringBuilder buf = new StringBuilder(512);
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }
        br.close();
        isr.close();
        return buf.toString();
    }
}
