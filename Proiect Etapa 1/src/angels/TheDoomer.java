package angels;

import heroes.Hero;

public class TheDoomer extends Angel {
    TheDoomer() {
        super();
        this.setType("TheDoomer");
    }
    public final void kill(final Hero hero) {
        hero.setDead(true);
        hero.setCurrentHP(0);
    }
}
