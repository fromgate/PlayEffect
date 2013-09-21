package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.NMSLib;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.Util;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

public class EffectFirework extends BasicEffect {

    private FireworkEffect.Type ftype = FireworkEffect.Type.BALL; 
    private Color color = null;

    boolean ftypernd = false;
    boolean colorrnd = false;
    
    
    @Override
    public void onInit(){
        String ftstr = this.getParam("type", "random");
        if (ftstr.equalsIgnoreCase("random")) ftypernd = true;
        try{
            if (ftstr.equalsIgnoreCase("random")) ftype = FireworkEffect.Type.values()[PlayEffect.instance.u.getRandomInt(FireworkEffect.Type.values().length)];
            else ftype = FireworkEffect.Type.valueOf(ftstr.toUpperCase());
        } catch (Exception e){
            ftype = FireworkEffect.Type.BALL;
        }
        if (getParam ("color","random").equalsIgnoreCase("random")) colorrnd = true;
        color = Util.colorByName(getParam ("color","random"), Color.YELLOW);
    }
    
    
    @Override
    protected void play(final Location loc) {
        if (ftypernd) ftype = FireworkEffect.Type.values()[PlayEffect.instance.u.getRandomInt(FireworkEffect.Type.values().length)];
        if (colorrnd) color = Util.colorByName(getParam ("color","random"), Color.YELLOW);
        final FireworkEffect fe = FireworkEffect.builder().with(ftype).withColor(color).flicker(true).build();
        Bukkit.getScheduler().runTask(PlayEffect.instance, new Runnable(){
            @Override
            public void run() {
                try {
                    NMSLib.playFirework(loc.getWorld(), loc, fe);
                } catch (Exception e) {
                    e.printStackTrace();
                }                
            }
        });

    }

}
