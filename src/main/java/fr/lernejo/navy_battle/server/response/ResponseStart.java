package fr.lernejo.navy_battle.server.response;

public class ResponseStart {
    private final String id;
    private final String url;
    private final String message;

    public ResponseStart(String id, String url, String message) {
        this.id = id;
        this.url = url;
        this.message = message;
    }

    public String getJsonString() {
        String s = new String("{\"id\":\"" + id + "\", \"url\":\"" + url +
                "\", " +
                "\"message\":\"" + message + "\"}");
        return s;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }
}
