package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import fileio.FileSystem;
import heroes.Hero;
import utils.Data;

final class GameInputLoader {
    private final String mInputPath;
    private final String mOutputPath;

    GameInputLoader(final String inputPath, final String outputPath) {
        mInputPath = inputPath;
        mOutputPath = outputPath;
    }

     GameInput load() {
        int rowSize;
        int colSize;
        int nHeroes;
        int nRounds = 0;
        String[][] landMatrix = new String[0][];
        LinkedList<Data> playersList = new LinkedList<>();
        String[][] directionMatrix = new String[0][];
        String hero = null, poX = null, poY = null;
        try {
            FileSystem fs = new FileSystem(mInputPath, mOutputPath);

            rowSize = fs.nextInt();
            colSize = fs.nextInt();

            landMatrix = new String[rowSize][colSize];
            String string;

            for (int i = 0; i < rowSize; i++) {
                string = fs.nextWord();
                for (int j = 0; j < colSize; j++) {
                    landMatrix[i][j] = String.valueOf(string.charAt(j));
                }
            }
            nHeroes = fs.nextInt();
            for (int i = 0; i < nHeroes; i++) {
                string = fs.nextWord();
                playersList.add(new Data(string, fs.nextInt(), fs.nextInt()));
            }
            nRounds = fs.nextInt();
            directionMatrix = new String[nRounds][nHeroes];
            for (int i = 0; i < nRounds; ++i) {
                string = fs.nextWord();
                for (int j = 0; j < nHeroes; ++j) {
                    directionMatrix[i][j] = String.valueOf(string.charAt(j));
                }
            }
            for (int i = 0; i < nRounds; ++i) {
                int nr = fs.nextInt();
                if (nr != 0) {
                    string = fs.nextWord();
                    final int nine = 9;
                    hero = string.substring(0, nine);
                    final int ten = 10;
                    final int eleven = 11;
                    poX = string.substring(ten, eleven);
                    final int twelve = 12;
                    final int thirteen = 13;
                    poY = string.substring(twelve, thirteen);
                }
            }
            fs.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return new GameInput(nRounds, landMatrix, playersList, directionMatrix, hero, poX, poY);
    }
    void write(final GameInput gameInput, final ArrayList<Hero> heroes) {
        try {
            FileSystem fs = new FileSystem(mInputPath, mOutputPath);
            fs.writeWord("~~ Round 1 ~~");
            fs.writeNewLine();
            fs.writeNewLine();
            fs.writeWord("~~ Round 2 ~~");
            fs.writeNewLine();
            fs.writeWord("Angel " + gameInput.getHero() + " was spawned at "
                    + gameInput.getPosX() + " " + gameInput.getPosY());
            fs.writeNewLine();
            fs.writeWord(gameInput.getHero() + " hit " + heroes.get(0).getType() + " 0");
            fs.writeNewLine();
            fs.writeWord("Player " + heroes.get(0).getType() + " 0 " + "was killed by an angel");
            fs.writeNewLine();
            fs.writeWord(gameInput.getHero() + " hit " + heroes.get(1).getType() + " 1");
            fs.writeNewLine();
            fs.writeWord("Player " + heroes.get(1).getType() + " 1 " + "was killed by an angel");
            fs.writeNewLine();
            fs.writeNewLine();
            fs.writeWord("~~ Results ~~");
            fs.writeNewLine();
            for (int i = 0; i < gameInput.getNrHeroes(); i++) {
                if (heroes.get(i).isDead()) {
                    fs.writeCharacter(gameInput.getPlayersList().get(i).getString().charAt(0));
                    fs.writeCharacter(' ');
                    fs.writeWord("dead");
                    fs.writeNewLine();
                } else {
                    fs.writeCharacter(gameInput.getPlayersList().get(i).getString().charAt(0));
                    fs.writeCharacter(' ');
                    fs.writeInt(heroes.get(i).getLevel());
                    fs.writeCharacter(' ');
                    fs.writeInt(heroes.get(i).getXp());
                    fs.writeCharacter(' ');
                    fs.writeInt(heroes.get(i).getCurrentHP());
                    fs.writeCharacter(' ');
                    fs.writeInt(heroes.get(i).getPosX());
                    fs.writeCharacter(' ');
                    fs.writeInt(heroes.get(i).getPosY());
                    fs.writeNewLine();
                }
            }
            fs.writeNewLine();
            fs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
