package me.fromgate.playeffect.effect;


import me.fromgate.playeffect.customeffects.AdditionalEffects;

import org.bukkit.Location;

public class EffectExplosion extends BasicEffect {
    
    @Override
    protected void play(Location loc) {
    	float size = getParam("size",1.5f);
    	AdditionalEffects.sendExplosionPacket(loc, size);
    }

}
