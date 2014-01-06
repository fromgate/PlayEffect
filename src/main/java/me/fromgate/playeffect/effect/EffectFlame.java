package me.fromgate.playeffect.effect;

import org.bukkit.Effect;
import org.bukkit.Location;

public class EffectFlame extends BasicEffect {
    @Override
    protected void play(Location loc) {
        loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES,1);
    }
}
