package utils;

import main.GameInput;

public final class Singleton {
    private static String[][] instance = null;

    private Singleton() { };

    public static String[][] getInstance() {
        if (instance == null) {
            instance = GameInput.getLandMatrix();
        }
        return instance;
    }
}
