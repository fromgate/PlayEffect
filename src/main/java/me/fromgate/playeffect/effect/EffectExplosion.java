package me.fromgate.playeffect.effect;


import me.fromgate.playeffect.NMSLib;
import org.bukkit.Location;

public class EffectExplosion extends BasicEffect {
    
    @Override
    protected void play(Location loc) {
        NMSLib.sendExplosionPacket(loc, 5);
    }

}
