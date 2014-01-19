package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.PlayEffect;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;

public class EffectSound extends BasicEffect {
    
    Sound sound = Sound.CLICK;
    float pitch = 1;
    float volume = 1;
    boolean playsmoke = false;
    
    @Override
    public void onInit(){
        String soundstr = getParam("type","CLICK");
        try{
            sound = Sound.valueOf(soundstr.toUpperCase());
        } catch (Exception e){
            sound = Sound.CLICK;
        }
        pitch = getParam("pitch",1f);
        volume = getParam("volume",1f);
        playsmoke = getParam ("showsmoke",PlayEffect.instance.playSmokeForSound);
    }

    @Override
    protected void play(Location loc) {
        loc.getWorld().playSound(loc, sound, volume, pitch);
        if (playsmoke) loc.getWorld().playEffect(loc, Effect.SMOKE, 4);
    }

}

