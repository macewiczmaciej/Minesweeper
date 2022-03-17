package com.codegym.games.minesweeper;

import com.codegym.engine.cell.Color;
import com.codegym.engine.cell.Game;

import java.util.ArrayList;
import java.util.List;

public class MinesweeperGame extends Game {
    private static final int SIDE = 9;
    private static final String MINE = "\uD83D\uDCA3";
    private GameObject[][] gameField = new GameObject[SIDE][SIDE];
    private int countMinesOnField;
    private static final String FLAG = "\uD83D\uDEA9";
    private int countFlags;


    //initialize game
    @Override
    public void initialize() {
        setScreenSize(SIDE, SIDE);
        createGame();
    }

    //create game
    private void createGame() {
        for (int y = 0; y < SIDE; y++) {
            for (int x = 0; x < SIDE; x++) {
                boolean isMine = getRandomNumber(10) < 1;
                if (isMine) {
                    countMinesOnField++;
                }
                gameField[y][x] = new GameObject(x, y, isMine);
                setCellColor(x, y, Color.ORANGE);
            }
        }
        countFlags = countMinesOnField;
        countMineNeighbors();
    }

    //getting neighbors of each mine on field
    private List<GameObject> getNeighbors(GameObject gameObject) {
        List<GameObject> result = new ArrayList<>();
        for (int y = gameObject.y - 1; y <= gameObject.y + 1; y++) {
            for (int x = gameObject.x - 1; x <= gameObject.x + 1; x++) {
                if (y < 0 || y >= SIDE) {
                    continue;
                }
                if (x < 0 || x >= SIDE) {
                    continue;
                }
                if (gameField[y][x] == gameObject) {
                    continue;
                }
                result.add(gameField[y][x]);
            }
        }
        return result;
    }

    //Counting mine neighbors
    private void countMineNeighbors() {
        for (int j = 0; j < SIDE; j++) {
            for (int i = 0; i < SIDE; i++) {
                GameObject gameObject = gameField[j][i];
                if (gameObject.isMine == false) {
                    List<GameObject> neighbors = getNeighbors(gameObject);
                    for (GameObject n : neighbors) {
                        if (n.isMine) {
                            gameObject.countMineNeighbors++;
                        }
                    }
                }
            }
        }
    }

    //opening tile
    private void openTile(int x, int y) {
        GameObject gameObject = gameField[y][x];

        //changing open status to true
        setCellColor(x, y, Color.GREEN);
        gameObject.isOpen = true;

        if (gameObject.isMine) {
            setCellValue(gameObject.x, gameObject.y, MINE);
        } else if (gameObject.countMineNeighbors == 0) {
            setCellValue(x, y, "");
            List<GameObject> neighbors = getNeighbors(gameObject);
            for (GameObject n : neighbors) {
                if (!n.isOpen) {
                    openTile(n.x, n.y);
                }
            }
        } else {
            setCellNumber(x, y, gameObject.countMineNeighbors);
        }
    }

    private void markTile(int x, int y) {
        GameObject gameObject = gameField[y][x];
        if (gameObject.isFlag) {
            setCellColor(x, y, Color.ORANGE);
            setCellValue(x, y, "");
            gameObject.isFlag = false;
            countFlags++;
        } else if (!gameObject.isOpen & countFlags > 0) {
            gameObject.isFlag = true;
            setCellColor(x, y, Color.YELLOW);
            setCellValue(x, y, FLAG);
            countFlags--;
        }

    }


    @Override
    public void onMouseLeftClick(int x, int y) {
        openTile(x, y);
    }

    @Override
    public void onMouseRightClick(int x, int y) {
        markTile(x, y);
    }
}