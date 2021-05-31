package main;


import heroes.Hero;

import java.io.IOException;
import java.util.ArrayList;

public final class Main {
    private Main() {
    }
    public static void main(final String[] args) throws IOException {
        GameInputLoader gameInputLoader = new GameInputLoader(args[0], args[1]);
        GameInput gameInput = gameInputLoader.load();
        Game game = new Game();
        ArrayList<Hero> heroes = game.play(gameInput);
        gameInputLoader.write(gameInput, heroes);
    }
}
