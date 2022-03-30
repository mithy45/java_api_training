package fr.lernejo.navy_battle.server.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.game.CellArray;
import fr.lernejo.navy_battle.server.response.ResponseFire;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HttpHandlerFire implements HttpHandler, AttributeValidator {

    private final CellArray game = new CellArray();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String body; int statusCode;
        HashMap node = getJson(httpExchange.getRequestURI().getQuery());
        if (!httpExchange.getRequestMethod().equalsIgnoreCase("GET")) {
            body = "Not Found"; statusCode = 404;
        }
        else if (!hasValidAttribute(node)) {
            body = "Bad request"; statusCode = 400;
        }
        else {
            body = getInfoFromCell(node.get("cell").toString()); statusCode = 200;
        }
        httpExchange.getRequestHeaders().put("Content-Type", Collections.singletonList("application/json"));
        httpExchange.sendResponseHeaders(statusCode, body.length());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(body.getBytes());}
    }

    @Override
    public boolean hasValidAttribute(JsonNode node) {
        return false;
    }

    @Override
    public boolean hasValidAttribute(HashMap node)
    {
        String cell = node.get("cell").toString();
        if (cell == null)
            return false;
        char i = cell.charAt(0);
        char j = cell.charAt(1);
        if (cell.length() != 2 || !(Character.isLetter(i) && Character.isDigit(j)))
        {
            return false;
        }
        if (!(i >= 'A' && i <= 'J'))
        {
            return false;
        }
        return true;
    }

    private String getInfoFromCell(String cell) throws JsonProcessingException {
        String line[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
            String consequence = game.getInfoFromCell(Arrays.asList(line).indexOf(Character.toString(cell.charAt(0))),
            Integer.parseInt(String.valueOf(cell.charAt(1))));
        boolean sunk = false;
        if (consequence.equals("sunk"))
        {
            sunk = true;
        }
        return new ResponseFire(consequence, sunk).getJsonString();
    }

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

    public HashMap<String, String> getJson(String body) throws IOException {
        String[] params = body.split("&");
        HashMap<String, String> map = new HashMap<String, String>();
        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }
}
