package heroes;

import main.GameInput;
import utils.HeroType;
import utils.LocationType;
import utils.Singleton;

import java.util.ArrayList;

public abstract class Hero {
    private int maxHP;
    private int scaleHP;
    private int xp;
    private HeroType type;
    private int level;
    private int currentHP;
    private int posX;
    private int posY;
    private float damageOverTime;
    private int nRoundsDPS;
    private boolean isDead;
    private boolean isParalysed;
    private int nRoundsParalysis;

    Hero() {
        xp = 0;
        scaleHP = 0;
        maxHP = 0;
        type = null;
        level = 0;
        currentHP = -1;
        posX = -1;
        posY = -1;
        damageOverTime = 0;
        nRoundsDPS = 0;
        isDead = false;
        isParalysed = false;
        nRoundsParalysis = 0;
    }

    final int getMaxHP() {
        return maxHP;
    }

    public final int getCurrentHP() {
        return currentHP;
    }

    public final void setCurrentHP(final int currentHP) {
        this.currentHP = currentHP;
    }

    public final  int getXp() {
        return xp;
    }

    final void setMaxHP(final int maxHP) {
        this.maxHP = maxHP;
    }

    private void setXp(final int xp) {
        this.xp = xp;
    }

    public HeroType getType() {
        return type;
    }

    private int getScaleHP() {
        return scaleHP;
    }

    final void setScaleHP(final int scaleHP) {
        this.scaleHP = scaleHP;
    }

    final void setType(final HeroType type) {
        this.type = type;
    }

    public final int getLevel() {
        return level;
    }

    private void setLevel(final int level) {
        this.level = level;
    }

    public final int getPosX() {
        return posX;
    }

    public final int getPosY() {
        return posY;
    }

    public final void setPosX(final int posX) {
        this.posX = posX;
    }

    public final void setPosY(final int posY) {
        this.posY = posY;
    }

    private float getDamageOverTime() {
        return damageOverTime;
    }

    private void setDamageOverTime(final float damageOverTime) {
        this.damageOverTime = damageOverTime;
    }

    private int getRoundsDPS() {
        return nRoundsDPS;
    }

    private void setRoundsDPS(final int roundsDPS) {
        this.nRoundsDPS = roundsDPS;
    }

    public final boolean isParalysed() {
        return isParalysed;
    }

    public final void setParalysed(final boolean paralysed) {
        isParalysed = paralysed;
    }

    public final int getRoundsParalysis() {
        return nRoundsParalysis;
    }

    public final void setRoundsParalysis(final int roundsParalysis) {
        this.nRoundsParalysis = roundsParalysis;
    }

    public static ArrayList<Hero> createHeroes(final GameInput gameInput) {
        ArrayList<Hero> heroes = new ArrayList<>();
        int nHeroes = gameInput.getNrHeroes();
        HeroFactory heroFactory = HeroFactory.getInstance();

        for (int i = 0; i < nHeroes; i++) {
            heroes.add(heroFactory.createHero(gameInput.getPlayersList().get(i).getString()));
            heroes.get(i).setPosX(gameInput.getPlayersList().get(i).getX());
            heroes.get(i).setPosY(gameInput.getPlayersList().get(i).getY());
        }
        return heroes;
    }

    final LocationType getLocationType() {
        int positionX = this.getPosX();
        int positionY = this.getPosY();
        String[][] map = Singleton.getInstance();
        switch (map[positionX][positionY]) {
            case "W":
                return LocationType.Woods;
            case "D":
                return LocationType.Desert;
            case "L":
                return LocationType.Land;
            default:
                return LocationType.Volcanic;
        }
    }

    final float addLandMultiplier(final float damage) {
        if (this.getLocationType().equals(LocationType.Volcanic)
                && this.getType().equals(HeroType.Pyromancer)) {
            final float mul = 1 / 4f;
            return Math.round(damage + damage * mul);
        }
        if (this.getLocationType().equals(LocationType.Land)
                && this.getType().equals(HeroType.Knight)) {
            final float mul = 3 / 20f;
            return Math.round(damage + damage * mul);
        }
        if (this.getLocationType().equals(LocationType.Desert)
                && this.getType().equals(HeroType.Wizard)) {
            final float mul = 1 / 10f;
            return Math.round(damage + damage * mul);
        }
        if (this.getLocationType().equals(LocationType.Woods)
                && this.getType().equals(HeroType.Rogue)) {
            final float mul = 3 / 20f;
            return Math.round(damage + damage * mul);
        }
        return damage;
    }

    final void dealDamage(final Hero hero, final float damage,
                          final float damageOverT, final int nRounds,
                          final boolean damagePar) {
        hero.setCurrentHP(Math.round(hero.getCurrentHP() - damage));
        if (!hero.isDead() && damageOverT != 0) {
            hero.setDamageOverTime(damageOverT);
            hero.setRoundsDPS(nRounds);
            if (damagePar) {
                hero.setParalysed(true);
                hero.setRoundsParalysis(nRounds);
            }
        } else if (!hero.isDead() && nRounds != 0) {
            hero.setParalysed(true);
            hero.setRoundsParalysis(1);
        }
    }

    public final void updateXP(final Hero loser) {
        final int maxV = 200;
        final int multiplier = 40;
        final int diffLevel = this.getLevel() - loser.getLevel();
        this.setXp(this.getXp() + Math.max(0, maxV - diffLevel * multiplier));
    }

    public final void updateLevel() {
        final int maxV = 250;
        final int divide = 50;
        if (this.getXp() > maxV + this.getLevel() * divide) {
            this.setLevel(((this.getXp() - maxV) / divide) + 1);
            this.setMaxHP(this.getMaxHP() + this.getScaleHP() * this.getLevel());
            this.setCurrentHP(this.getMaxHP());
        }
    }

    public final void setDead(final boolean dead) {
        isDead = dead;
    }

    public final boolean isDead() {
        return isDead;
    }
    public final void checkDead() {
        if (this.getCurrentHP() < 0) {
            this.setCurrentHP(0);
            this.setDead(true);
            this.setPosX(-1);
            this.setPosY(-1);
        }
    }
    public final void checkDOT() {
        if (this.getDamageOverTime() != 0) {
            float dot = this.getDamageOverTime();
            this.setCurrentHP((int) (this.getCurrentHP() - dot));
            this.setRoundsDPS(this.getRoundsDPS() - 1);
            if (this.getRoundsDPS() == 0) {
                this.setDamageOverTime(0);
            }
        }
    }

    public abstract void isAttackedBy(Hero hero);
    public abstract void attack(Knight knight);
    public abstract void attack(Pyromancer pyromancer);
    public abstract void attack(Rogue rogue);
    public abstract void attack(Wizard wizard);
}
