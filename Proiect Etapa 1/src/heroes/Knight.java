package heroes;

import utils.HeroType;

public class Knight extends Hero {
    Knight() {
        super();
        this.setType(HeroType.Knight);
        final int initialHP = 900;
        final int scaleHP = 80;
        this.setMaxHP(initialHP);
        this.setScaleHP(scaleHP);
        this.setCurrentHP(initialHP);
    }

    final float execute(final Hero hero) {
         final float percentageDmg = 20f;
         final float percentageScale = 1f;
         final float baseDmg = 200f;
         final float scaleDmg = 30f;
         float hpLimit = percentageDmg + percentageScale * this.getLevel();
         final int maxHp = 40;
         if (hpLimit > maxHp) {
             hpLimit = maxHp;
         }
         final float cent = 100f;
         hpLimit /= cent;
         if (hero.getCurrentHP() < 0) {
             return this.addLandMultiplier(baseDmg + scaleDmg * this.getLevel());
         }
         if (hero.getCurrentHP() <= hero.getMaxHP() * hpLimit) {
             hero.setCurrentHP(0);
             hero.setDead(true);
             return -1f;
         }
         float damage = baseDmg + scaleDmg * this.getLevel();
         return this.addLandMultiplier(damage);
    }

    final float slam() {
        final float baseDmg = 100f;
        final float scaleDmg = 40f;
        float damage = baseDmg + scaleDmg * this.getLevel();
        return this.addLandMultiplier(damage);
    }

    private void attack(final Hero hero, final float multiplier1, final float multiplier2) {
        float execute = this.execute(hero);
        if (execute == -1f) {
            return;
        }
        execute += execute * multiplier1;
        execute = Math.round(execute);
        float slam = this.slam();
        slam += slam * multiplier2;
        float totalDmg = execute + slam;
        this.dealDamage(hero, totalDmg, 0, 1, false);
    }
    public final void isAttackedBy(final Hero hero) {
        hero.attack(this);
    }

    public final void attack(final Knight knight) {
        final float knightMultiplier = 1 / 5f;
        attack(knight, 0, knightMultiplier);
    }

    public final void attack(final Pyromancer pyromancer) {
        final float pyroMultiplier = 1 / 10f;
        attack(pyromancer, pyroMultiplier, -pyroMultiplier);
    }

    public final void attack(final Rogue rogue) {
        final float rogueMultiplier1 = 3 / 20f;
        final float rogueMultiplier2 = -1 / 5f;
        attack(rogue, rogueMultiplier1, rogueMultiplier2);
    }

    public final void attack(final Wizard wizard) {
        final float wizardMultiplier1 = -1 / 5f;
        final float wizardMultiplier2 = 1 / 20f;
        attack(wizard, wizardMultiplier1, wizardMultiplier2);
    }
}
