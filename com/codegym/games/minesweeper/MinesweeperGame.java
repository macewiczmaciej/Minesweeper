package com.codegym.games.minesweeper;

import com.codegym.engine.cell.*;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField = 0;

    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    private void createGame() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                gameField[j][i] = new GameObject(i, j, getRandomNumber(10) < 1);
                if (gameField[j][i].isMine) {
                    countMinesOnField += 1;
                }
                setCellColor(i, j, Color.ORANGE);
            }
        }
    }
}
