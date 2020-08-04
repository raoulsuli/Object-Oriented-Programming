package angels;

public final class AngelFactory {
    private static AngelFactory instance = null;
    private AngelFactory() { }

    public static AngelFactory getInstance() {
        if (instance == null) {
            instance = new AngelFactory();
        }
        return instance;
    }

    public Angel createAngel(final String type) {
        switch (type) {
            case "DamageAngel" : return new DamageAngel();
            case "TheDoomer" : return new TheDoomer();
            default: return null;
        }
    }
}
