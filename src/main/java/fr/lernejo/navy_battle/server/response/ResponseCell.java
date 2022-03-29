package fr.lernejo.navy_battle.server.response;

public class ResponseCell {
    private final String cell;

    public ResponseCell(String cell) {
        this.cell = cell;
    }

    public String getCell()
    {
        return cell;
    }

    public String getJsonString() {
        String s = new String("{\"cell\":\"" + cell + "\"}");
        return s;
    }
}
