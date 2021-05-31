package heroes;

import utils.HeroType;

public class Pyromancer extends Hero {

    Pyromancer() {
        super();
        this.setType(HeroType.Pyromancer);
        final int initialHP = 500;
        final int scaleHP = 50;
        this.setMaxHP(initialHP);
        this.setCurrentHP(initialHP);
        this.setScaleHP(scaleHP);
    }

    final float fireblast() {
        final float baseDmg = 350f;
        final float scaleDmg = 50f;
        return baseDmg + scaleDmg * this.getLevel();
    }

    final float[]  ignite() {
         final float baseDmg = 150f;
         final float scaleDmg = 20f;
         final float dps = 50f;
         final float dpsScale = 30f;
         float dmgRound = baseDmg + scaleDmg * this.getLevel();
         float dmgPerRounds = dps + dpsScale * this.getLevel();
         float[] damages = new float[2];
         damages[0] = dmgRound;
         damages[1] = dmgPerRounds;
        return damages;
    }

    private void attack(final Hero hero, final float multiplier) {
        float fireblast = this.fireblast(); // 1st spell
        fireblast += fireblast * multiplier; // race multiplier
        fireblast = Math.round(fireblast);
        fireblast = this.addLandMultiplier(fireblast);
        float[] ignite = this.ignite(); // 2nd spell
        ignite[0] += ignite[0] * multiplier;
        ignite[0] = Math.round(ignite[0]);
        ignite[1] += ignite[1] * multiplier;
        ignite[1] = Math.round(ignite[1]);
        ignite[0] = this.addLandMultiplier(ignite[0]);
        ignite[1] = this.addLandMultiplier(ignite[1]);
        float totalDmg = fireblast + ignite[0];
        this.dealDamage(hero, totalDmg, ignite[1], 2, false);
    }

    public final void isAttackedBy(final Hero hero) {
        hero.attack(this);
    }
    public final void attack(final Knight knight) {
        final float knightMultiplier = 1 / 5f;
        attack(knight, knightMultiplier);
    }

    public final void attack(final Pyromancer pyromancer) {
        final float pyroMultiplier = -1 / 10f;
        attack(pyromancer, pyroMultiplier);
    }

    public final void attack(final Rogue rogue) {
        final float rogueMultiplier = -1 / 5f;
        attack(rogue, rogueMultiplier);
    }

    public final void attack(final Wizard wizard) {
        final float wizardMultiplier = 1 / 20f;
        attack(wizard, wizardMultiplier);
    }
}
