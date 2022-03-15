package com.codegym.games.minesweeper;

import com.codegym.engine.cell.Game;

public class GameObject extends Game {
    public int x;
    public int y;
    public boolean isMine;
    public int countMineNeighbors;

    GameObject(int x, int y, boolean isMine) {
        this.x = x;
        this.y = y;
        this.isMine = isMine;
    }

}
