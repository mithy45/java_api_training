package fr.lernejo.navy_battle.server;

import com.sun.net.httpserver.HttpServer;
import fr.lernejo.navy_battle.server.handler.HttpHandlerFire;
import fr.lernejo.navy_battle.server.handler.HttpHandlerPing;
import fr.lernejo.navy_battle.server.handler.HttpHandlerStart;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class Server {

    private final HttpServer server;

    public Server(int port) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.setExecutor(Executors.newFixedThreadPool(1));
        server.createContext("/ping", new HttpHandlerPing());
        server.createContext("/api/game/start", new HttpHandlerStart(port));
        server.createContext("/api/game/fire", new HttpHandlerFire());
    }

    public void start()
    {
        server.start();
    }

    public void stop()
    {
        server.stop(0);
    }
}
