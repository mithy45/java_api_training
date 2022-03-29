package fr.lernejo.navy_battle.game;

import java.util.concurrent.atomic.AtomicBoolean;

public class Cell {
    private final AtomicBoolean occupied = new AtomicBoolean(false);
    private final StringBuilder boat = new StringBuilder(1);

    public Cell()
    {
        boat.append('~');
    }

    public boolean isOccupied() {
        return occupied.get();
    }

    public void setBoat(char c)
    {
        if (c != '~')
        {
            occupied.set(true);
        }
        boat.setLength(0);
        boat.append(c);
    }

    public String toString()
    {
        return boat.toString();
    }
}
