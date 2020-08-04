package heroes;

final class HeroFactory {
    private static HeroFactory instance = null;

    private HeroFactory() { }

    static HeroFactory getInstance() {
        if (instance == null) {
            instance = new HeroFactory();
        }
        return instance;
    }

    Hero createHero(final String type) {
        switch (type) {
            case "K" : return new Knight();
            case "P" : return new Pyromancer();
            case "R" : return new Rogue();
            case "W" : return new Wizard();
            default : return null;
        }
    }
}
