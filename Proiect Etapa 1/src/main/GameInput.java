package main;

import utils.Data;

import java.util.LinkedList;

public class GameInput {
    private int nrHeroes;
    private int nrRounds;
    private LinkedList<Data> mPlayersList;
    private static String[][] mLandMatrix;
    private String[][] mDirectionMatrix;
    private String hero;
    private String posX;
    private String posY;
    GameInput(final int nRounds, final String[][] landMatrix, final LinkedList<Data> playersList,
              final String[][] directionMatrix, final String her,
              final String poX, final String poY) {
        nrHeroes =  playersList.size();
        nrRounds = nRounds;
        mLandMatrix = landMatrix;
        mPlayersList = playersList;
        mDirectionMatrix = directionMatrix;
        hero = her;
        posX = poX;
        posY = poY;
    }

    public final LinkedList<Data> getPlayersList() {
        return mPlayersList;
    }
    public final int getNrHeroes() {
        return nrHeroes;
    }

    final int getNrRounds() {
        return nrRounds;
    }

    final String[][] getDirectionMatrix() {
        return mDirectionMatrix;
    }

    public static String[][] getLandMatrix() {
        return mLandMatrix;
    }

    public final String getHero() {
        return hero;
    }

    public final String getPosX() {
        return posX;
    }

    public final String getPosY() {
        return posY;
    }
}
