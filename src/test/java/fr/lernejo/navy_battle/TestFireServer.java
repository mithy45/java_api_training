package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.server.Server;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TestFireServer {
    @Test
    void TestFireMiss() throws IOException, InterruptedException {
        Server server = new Server(7654);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:7654/api/game/fire?cell=I9"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        Assertions.assertEquals("{" +
                "\"consequence\": \"miss\", " +
                "\"shipLeft\": true" +
                "}", response.body());
        server.stop();
    }
}
