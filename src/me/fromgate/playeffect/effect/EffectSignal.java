package me.fromgate.playeffect.effect;

import org.bukkit.Effect;
import org.bukkit.Location;

public class EffectSignal extends BasicEffect {

    
    @Override
    protected void play(Location loc) {
        loc.getWorld().playEffect(loc, Effect.ENDER_SIGNAL,1);
    }
}
