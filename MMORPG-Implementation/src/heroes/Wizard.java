package heroes;

import utils.HeroType;

public class Wizard extends Hero {

    Wizard() {
        super();
        this.setType(HeroType.Wizard);
        final int initialHP = 400;
        final int scaleHP = 30;
        this.setMaxHP(initialHP);
        this.setCurrentHP(initialHP);
        this.setScaleHP(scaleHP);
    }

    private float drain() {
        final float percentage = 20f;
        final float scaleP = 5f;
        final float cent = 100f;
        return (percentage + scaleP * this.getLevel()) / cent;
    }

    private float[] deflect(final Hero hero) {
        float dmgTaken = 0;
        if (hero instanceof Knight) {
            dmgTaken = ((Knight) hero).execute(this);
            if (dmgTaken == -1f) {
                dmgTaken = this.getCurrentHP();
            } else {
                dmgTaken += ((Knight) hero).slam();
            }
        } else if (hero instanceof Pyromancer) {
            dmgTaken = ((Pyromancer) hero).fireblast();
            float[] ignite = ((Pyromancer) hero).ignite();
            dmgTaken += ignite[0];
            dmgTaken = hero.addLandMultiplier(dmgTaken);
        } else if (hero instanceof Rogue) {
            dmgTaken = ((Rogue) hero).backstab();
            dmgTaken += ((Rogue) hero).paralysis();
        } else if (hero instanceof Wizard) {
            float[] damage = new float[2];
            damage[0] = 0f;
            damage[1] = 0f;
            return damage;
        }
        final float percentage = 35f;
        final float scalePer = 2f;
        final float cent = 100f;
        float damage = percentage + scalePer * this.getLevel();
        final float maxP = 70f;
        if (damage > maxP) {
            damage = maxP;
        }
        float[] damages = new float[2];
        damage = (damage / cent);
        damages[0] = damage;
        damages[1] = dmgTaken;
        return damages;
    }

    private void attack(final Hero hero, final float multiplier1, final float multiplier2) {
        float drain = this.drain();
        drain += drain * multiplier1;
        final float baseHp = (float) Math.min(0.3 * hero.getMaxHP(), hero.getCurrentHP());
        drain *= baseHp;
        drain = this.addLandMultiplier(drain);
        float[] deflect = this.deflect(hero);
        float result = deflect[0] * deflect[1];
        result =  Math.round(result);
        result += result * multiplier2;
        result = this.addLandMultiplier(result);
        float totalDmg = drain + result;
        this.dealDamage(hero, totalDmg, 0, 0, false);
    }

    public final void isAttackedBy(final Hero hero) {
        hero.attack(this);
    }

    public final void attack(final Knight knight) {
        final float knightMul1 = 1 / 5f;
        final float knightMul2 = 2 / 5f;
        attack(knight, knightMul1, knightMul2);
    }

    public final void attack(final Pyromancer pyromancer) {
        final float pyroMul1 = -1 / 10f;
        final float pyroMul2 = 3 / 10f;
        attack(pyromancer, pyroMul1, pyroMul2);
    }

    public final void attack(final Rogue rogue) {
        final float rogueMul1 = -1 / 5f;
        final float rogueMul2 = 1 / 5f;
        attack(rogue, rogueMul1, rogueMul2);
    }

    public final void attack(final Wizard wizard) {
        final float wizMul = 1 / 20f;
        attack(wizard, wizMul, 0);
    }
}
