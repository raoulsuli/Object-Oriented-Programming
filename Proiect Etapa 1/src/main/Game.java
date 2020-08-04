package main;

import angels.Angel;
import angels.AngelFactory;
import angels.TheDoomer;
import heroes.Hero;

import java.util.ArrayList;

class Game {

    final ArrayList<Hero> play(final GameInput gameInput) {
        ArrayList<Hero> heroes = Hero.createHeroes(gameInput);
        for (int i = 0; i < gameInput.getNrRounds(); i++) {
            for (int j = 0; j < gameInput.getNrHeroes(); j++) {
                if (heroes.get(j).isParalysed()) {
                    heroes.get(j).setRoundsParalysis(heroes.get(j).getRoundsParalysis() - 1);
                    if (heroes.get(j).getRoundsParalysis() == 0) {
                        heroes.get(j).setParalysed(false);
                    }
                    continue;
                }
                switch (gameInput.getDirectionMatrix()[i][j]) {
                    case "R":
                        heroes.get(j).setPosY(heroes.get(j).getPosY() + 1);
                        break;
                    case "L":
                        heroes.get(j).setPosY(heroes.get(j).getPosY() - 1);
                        break;
                    case "U":
                        heroes.get(j).setPosX(heroes.get(j).getPosX() - 1);
                        break;
                    case "D":
                        heroes.get(j).setPosX(heroes.get(j).getPosX() + 1);
                        break;
                    default :
                }
            }
            for (int p = 0; p < heroes.size(); p++) {
                if (heroes.get(p).isDead()) {
                    continue;
                }
                for (int q = p + 1; q < heroes.size(); q++) {
                    if (heroes.get(q).isDead()) {
                        continue;
                    }
                    int posX = heroes.get(p).getPosX();
                    int pos1X = heroes.get(q).getPosX();
                    int posY = heroes.get(p).getPosY();
                    int pos1Y = heroes.get(q).getPosY();
                    if (posX == pos1X && posY == pos1Y) {
                        heroes.get(p).checkDOT();
                        heroes.get(q).checkDOT();
                        if (heroes.get(p).getCurrentHP() < 0 || heroes.get(q).getCurrentHP() < 0) {
                            heroes.get(p).checkDead();
                            heroes.get(q).checkDead();
                            continue;
                        }
                        heroes.get(q).isAttackedBy(heroes.get(p));
                        heroes.get(p).isAttackedBy(heroes.get(q));
                        heroes.get(p).checkDead();
                        heroes.get(q).checkDead();
                        if (heroes.get(q).isDead()) {
                            heroes.get(p).updateXP(heroes.get(q));
                            heroes.get(p).updateLevel();
                        } else if (heroes.get(p).isDead()) {
                            heroes.get(q).updateXP(heroes.get(p));
                            heroes.get(q).updateLevel();
                        }
                    }
                }
            }
            if (i != 0 && gameInput.getHero().equals("TheDoomer")) {
                TheDoomer doomer = (TheDoomer) AngelFactory.getInstance().
                        createAngel("TheDoomer");
                assert doomer != null;
                if (heroes.get(0).getPosX() == Integer.parseInt(gameInput.getPosX())
                        && heroes.get(0).getPosY() == Integer.parseInt(gameInput.getPosY())) {
                    doomer.kill(heroes.get(0));
                }
                if (heroes.get(1).getPosX() == Integer.parseInt(gameInput.getPosX())
                        && heroes.get(1).getPosY() == Integer.parseInt(gameInput.getPosY())) {
                    doomer.kill(heroes.get(1));
                }
            }
        }
        return heroes;
    }
}
