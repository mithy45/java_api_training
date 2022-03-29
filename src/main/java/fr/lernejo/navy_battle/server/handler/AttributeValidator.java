package fr.lernejo.navy_battle.server.handler;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public interface AttributeValidator {
    boolean hasValidAttribute(HttpExchange httpExchange) throws IOException;
    String getStringBody(InputStream inputStream) throws IOException;
}
