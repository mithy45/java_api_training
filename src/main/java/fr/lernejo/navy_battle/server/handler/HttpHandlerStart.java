package fr.lernejo.navy_battle.server.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.game.APIGame;
import fr.lernejo.navy_battle.server.response.ResponseStart;

import java.io.*;
import java.net.URL;
import java.util.UUID;

public class HttpHandlerStart implements HttpHandler, AttributeValidator {

    private final int port;
    private final String id = UUID.randomUUID().toString();
    public HttpHandlerStart(int port) {
        this.port = port;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String body;
        int statusCode;
        if (!httpExchange.getRequestMethod().equalsIgnoreCase("POST")) {
            body = "Not Found";
            statusCode = 404;
        }
        else if (!hasValidAttribute(httpExchange))
        {
            body = "Bad Request";
            statusCode = 400;
        }
        else
        {
            body = new ResponseStart(id, "http://localhost:" +
                    Integer.toString(port),
                    "May the best code win").getJsonString();
            statusCode = 202;
            APIGame game =
                    new APIGame(new ObjectMapper().
                            readValue(getStringBody(httpExchange.getRequestBody()),
                                    ResponseStart.class));
            game.start();
        }
        httpExchange.sendResponseHeaders(statusCode, body.length());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }

    @Override
    public boolean hasValidAttribute(HttpExchange httpExchange) throws IOException {
        String body = getStringBody(httpExchange.getRequestBody());
        System.out.println("valid " + body);
        ResponseStart responseStart = new ObjectMapper().readValue(body,
                ResponseStart.class);
        System.out.println("good");
        String userID = responseStart.getId();
        String userURL = responseStart.getUrl();
        String userMessage = responseStart.getMessage();
        if (userID == null || userURL == null || userMessage == null)
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
