package me.fromgate.playeffect.effect;

import org.bukkit.Location;
import org.bukkit.entity.EnderSignal;
import org.bukkit.entity.EntityType;

public class EffectEye extends BasicEffect {
    
    
    @Override
    protected void play(Location loc) {
        EnderSignal e = (EnderSignal) loc.getWorld().spawnEntity(loc.add(0.5D, 0.5D,0.5D), EntityType.ENDER_SIGNAL);
        e.remove();
    }

    

}
