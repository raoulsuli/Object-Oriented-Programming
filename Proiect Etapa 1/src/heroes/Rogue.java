package heroes;

import utils.HeroType;
import utils.LocationType;

public class Rogue extends Hero {
    private int backstabCount = 0;
    Rogue() {
        super();
        this.setType(HeroType.Rogue);
        final int initialHP = 600;
        final int scaleHP = 40;
        this.setCurrentHP(initialHP);
        this.setMaxHP(initialHP);
        this.setScaleHP(scaleHP);
    }

    private int getBackstabCount() {
        return backstabCount;
    }

    private void setBackstabCount(final int backstabCount) {
        this.backstabCount = backstabCount;
    }

    final float backstab() {
        final float baseDmg = 200f;
        final float scaleDmg = 20f;
        float damage = baseDmg + scaleDmg * this.getLevel();
        final int count = 3;
        if (getBackstabCount() == count) {
            setBackstabCount(0);
        }
        if (getBackstabCount() == 0 && this.getLocationType().
                equals(LocationType.Woods)) {
            final float mul = 1.5f;
            damage *= mul;
        }
        setBackstabCount(getBackstabCount() + 1);
        return this.addLandMultiplier(damage);
    }

    final float paralysis() {
        final float baseDmg = 40f;
        final float scaleDmg = 10f;
        float damage = baseDmg + scaleDmg * this.getLevel();
        return this.addLandMultiplier(damage);
    }
    private void attack(final Hero hero, final float multiplier1, final float multiplier2) {
        float backstab = this.backstab();
        backstab += backstab * multiplier1;
        backstab = Math.round(backstab);
        float paralysis = this.paralysis();
        paralysis += paralysis * multiplier2;
        paralysis = Math.round(paralysis);
        float totalDmg = backstab + paralysis;
        final int roundSp = 3;
        int nRounds = roundSp;
        if (this.getLocationType().equals(LocationType.Woods)) {
            nRounds = roundSp * 2;
        }
        this.dealDamage(hero, totalDmg, paralysis, nRounds, true);
    }
    public final void isAttackedBy(final Hero hero) {
        if (hero instanceof Wizard) {
            if (backstabCount == 1) {
                setBackstabCount(0);
            }
        }
        hero.attack(this);
    }

    public final void attack(final Knight knight) {
        final float knightMul1 = -1 / 10f;
        final float knightMul2 = -1 / 5f;
        attack(knight, knightMul1, knightMul2);
    }

    public final void attack(final Pyromancer pyromancer) {
        final float pyroMul1 = 1 / 4f;
        final float pyroMul2 = 1 / 5f;
        attack(pyromancer, pyroMul1, pyroMul2);
    }

    public final void attack(final Rogue rogue) {
        final float rogueMul1 = 1 / 5f;
        final float rogueMul2 = -1 / 10f;
        attack(rogue, rogueMul1, rogueMul2);
    }

    public final void attack(final Wizard wizard) {
        final float wizMul = 1 / 4f;
        if (backstabCount == 1) {
            setBackstabCount(0);
        }
        attack(wizard, wizMul, wizMul);
    }
}
