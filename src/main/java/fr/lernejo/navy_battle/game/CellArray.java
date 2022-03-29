package fr.lernejo.navy_battle.game;

import java.security.SecureRandom;
import java.util.Map;

public class CellArray {

    private final Cell tab[][] = new Cell[10][10];
    private final Map<String, Integer> lengthBoat = Map.ofEntries(
            Map.entry("p", 5),
            Map.entry("c", 4),
            Map.entry("r", 3),
            Map.entry("t", 2)
    );

    public CellArray()
    {
        for (int i = 0; i != 10; i = i + 1)
        {
            for (int j = 0; j != 10; j = j + 1)
            {
                tab[i][j] = new Cell();
            }
        }
        initTabBoat();
    }

    public void initTabBoat() {
        for (int j = 0; j != 5; j = j + 1)
            tab[0][j].setBoat('p');
        for (int j = 0; j != 4; j = j + 1)
            tab[8][j].setBoat('c');
        for (int j = 0; j != 3; j = j + 1)
            tab[j][8].setBoat('r');
        for (int j = 2; j != 5; j = j + 1)
            tab[4][j].setBoat('r');
        for (int j = 0; j != 2; j = j + 1)
            tab[j][6].setBoat('t');
    }

    public String getInfoFromCell(int i, int j)
    {
        if (!tab[i][j].isOccupied())
            return "miss";
        int max = 0;
        for (int y = 0; y != 10; y = y + 1)
        {
            for (int x = 0; x!= 10; x = x + 1)
            {
                if (tab[y][x].toString().equals(tab[i][j].toString()))
                    max = max + 1;
            }
        }
        if (max == lengthBoat.get(tab[i][j].toString()))
            return "sunk";
        return "hit";
    }

    public String getRandomCell()
    {
        String line[] = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        return line[new SecureRandom().nextInt(line.length)]
                + String.valueOf(new SecureRandom().nextInt(10));
    }
}
