package fr.lernejo.navy_battle.client;
import fr.lernejo.navy_battle.game.APIGame;
import fr.lernejo.navy_battle.server.response.ResponseStart;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class Client implements ClientInterface{
    private final HttpClient client = HttpClient.newHttpClient();
    private final String id = UUID.randomUUID().toString();

    private final String url;
    private final int port;

    public Client(int port, String url) {
        this.port = port;
        this.url = url;
    }

    @Override
    public void toPing() {
        try
        {
            HttpRequest requetePost = HttpRequest.newBuilder().uri(URI.create(url + "/ping")).setHeader("Accept", "application/json").setHeader("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(new ResponseStart(id, url, "Ping").getJsonString()))
                    .build();
            String response = client.send(requetePost,
                    HttpResponse.BodyHandlers.ofString()).body();
        }
        catch (Exception e)
        {
            System.err.println("Erreur ! " + e.getMessage());
        }
    }

    @Override
    public void toStart() {
        try
        {
            HttpRequest requetePost =
                    HttpRequest.newBuilder().uri(URI.create(url + "/api/game/start")).setHeader("Accept", "application/json").setHeader("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(new ResponseStart(id,
                            url, "C'est parti les amis !").getJsonString()))
                    .build();
            String response = client.send(requetePost,
                    HttpResponse.BodyHandlers.ofString()).body();
            System.out.println(response);
        }
        catch (Exception e)
        {
            System.err.println("Erreur ! " + e.getMessage());
        }
    }

    @Override
    public void toFire() {
        try
        {
            HttpRequest requetePost = HttpRequest.newBuilder().uri(URI.create(url + "/api/game/fire")).setHeader("Accept", "application/json").setHeader("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(new ResponseStart(id,
                            url, "C'est parti les amis !").getJsonString()))
                    .build();
            String response = client.send(requetePost,
                    HttpResponse.BodyHandlers.ofString()).body();
            System.out.println(response);
        }
        catch (Exception e)
        {
            System.err.println("Erreur ! " + e.getMessage());
        }
    }
}
