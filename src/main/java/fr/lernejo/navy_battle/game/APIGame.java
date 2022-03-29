package fr.lernejo.navy_battle.game;

import fr.lernejo.navy_battle.server.response.ResponseStart;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class APIGame {
    private final CellArray game = new CellArray();
    private final ResponseStart response;

    public APIGame(ResponseStart responseStart)
    {
        response = responseStart;
    }

    public void start() {
        try
        {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request =
                    HttpRequest.newBuilder().uri(URI.create(response.getUrl()
                                    + "/api/game/fire?cell=" + game.getRandomCell()))
                            .setHeader("Accept", "application/json")
                            .setHeader("Content-Type", "application/json")
                            .GET()
                            .build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }
}
