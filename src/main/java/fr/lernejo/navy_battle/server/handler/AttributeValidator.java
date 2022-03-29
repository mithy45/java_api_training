package fr.lernejo.navy_battle.server.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

public interface AttributeValidator {
    boolean hasValidAttribute(JsonNode node);
    boolean hasValidAttribute(HashMap node);
    String getStringBody(InputStream inputStream) throws IOException;
}
