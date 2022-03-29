package fr.lernejo.navy_battle.server.response;

import com.fasterxml.jackson.core.JsonProcessingException;

public class ResponseFire {
    private final String consequence;
    private final boolean shipLeft;

    public ResponseFire(String consequence, boolean shipLeft) {
        this.consequence = consequence;
        this.shipLeft = shipLeft;
    }

    public String getJsonString() throws JsonProcessingException {
        // return new ObjectMapper().writeValueAsString(this);
        return new String("{\"consequence\":\"" + consequence + "\", " +
                "\"shipleft\":\"" + shipLeft + "\"}");
    }
}
