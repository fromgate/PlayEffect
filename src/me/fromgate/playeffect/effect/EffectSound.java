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
        playsmoke = getParam ("showsmoke",PlayEffect.instance.play_sound_smoke);
    }

    @Override
    protected void play(Location loc) {
        loc.getWorld().playSound(loc, sound, volume, pitch);
        if (playsmoke) loc.getWorld().playEffect(loc, Effect.SMOKE, 4);
    }

}


/*
public static void soundPlay (Location loc, Map<String,String> params){
if (params.isEmpty()) return;
String sndstr = "";
String strvolume ="1";
String strpitch = "1";
float pitch = 1;
float volume = 1;
if (params.containsKey("param")){
    String param = Util.getParam(params, "param", "");
    if (param.isEmpty()) return;
    if (param.contains("/")){
        String[] prm = param.split("/");
        if (prm.length>1){
            sndstr = prm[0];
            strvolume = prm[1];
            if (prm.length>2) strpitch = prm[2];
        }
    } else sndstr = param;
    if (strvolume.matches("[0-9]+-?\\.[0-9]*")) volume = Float.parseFloat(strvolume);
    if (strpitch.matches("[0-9]+-?\\.[0-9]*")) pitch = Float.parseFloat(strpitch);            
} else {
    sndstr = Util.getParam(params, "type", "");
    pitch = Util.getParam(params, "pitch", 1.0f);
    volume = Util.getParam(params, "volume", 1.0f);
}
Sound sound = getSoundStr (sndstr);
loc.getWorld().playSound(loc, sound, volume, pitch);
}*/