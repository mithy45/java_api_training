package fr.lernejo.navy_battle.server.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import fr.lernejo.navy_battle.game.CellArray;
import fr.lernejo.navy_battle.server.response.ResponseCell;
import fr.lernejo.navy_battle.server.response.ResponseFire;

import java.io.*;
import java.util.Arrays;

public class HttpHandlerFire implements HttpHandler, AttributeValidator {

    private final CellArray game = new CellArray();

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String body; int statusCode;
        if (!httpExchange.getRequestMethod().equalsIgnoreCase("GET"))
        {
            body = "Not Found"; statusCode = 404;
        }
        else if (!hasValidAttribute(httpExchange))
        {
            body = "Bad request"; statusCode = 400;
        }
        else
        {
            body = getInfoFromCell(new ObjectMapper().readValue(getStringBody(httpExchange.getRequestBody()), ResponseCell.class).getCell()); statusCode = 200;
        }
        httpExchange.sendResponseHeaders(statusCode, body.length());
        try (OutputStream os = httpExchange.getResponseBody()) {
            os.write(body.getBytes());
        }
    }

    @Override
    public boolean hasValidAttribute(HttpExchange httpExchange)
    {
        String userCell = (String) httpExchange.getAttribute("cell");
        char i = userCell.charAt(0);
        char j = userCell.charAt(1);
        if (userCell == null || userCell.length() != 2 ||
                !(Character.isLetter(i)
                        && Character.isDigit(j)))
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
        String consequence =
                game.getInfoFromCell(Arrays.asList(line).indexOf(cell.charAt(0)),
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
}
