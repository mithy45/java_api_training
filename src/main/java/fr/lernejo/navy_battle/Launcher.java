package fr.lernejo.navy_battle;

import fr.lernejo.navy_battle.client.Client;
import fr.lernejo.navy_battle.server.Server;

import java.io.IOException;

public class Launcher {
    public static void main(String args[]) throws IOException {
        int port = Integer.parseInt(args[0]);
        Server server = new Server(port);
        if (args.length > 1)
        {
            Client client = new Client(port, args[1]);
            client.toStart();
        }
    }
}
