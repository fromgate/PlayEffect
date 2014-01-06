package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.Effects;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.Util;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class EffectFirework extends BasicEffect {
    private FireworkEffect.Type ftype = FireworkEffect.Type.BALL; 
    private Color color = null;
    boolean ftypernd = false;
    boolean colorrnd = false;
    boolean rocket = false;
    
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
        rocket = this.getParam("rocket", Effects.getFireworkRocketDefault());
    }
    
    
    @Override
    protected void play(final Location loc) {
        if (ftypernd) ftype = FireworkEffect.Type.values()[PlayEffect.instance.u.getRandomInt(FireworkEffect.Type.values().length)];
        if (colorrnd) color = Util.colorByName(getParam ("color","random"), Color.YELLOW);
        FireworkEffect fe = FireworkEffect.builder().with(ftype).withColor(color).flicker(true).build();
        Firework fw = (Firework) loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta data = (FireworkMeta) fw.getFireworkMeta();
        data.clearEffects();
        data.setPower(0);
        data.addEffect(fe);
        fw.setFireworkMeta(data);
        fw.detonate();
        /*
        // old firework-detonate code disabled;          
        try {
            if (rocket) NMSLib.playFireworkRocket(loc.getWorld(), loc, fe); 
            else NMSLib.playFirework(loc.getWorld(), loc, fe);
        } catch (Exception e) {
            e.printStackTrace();
        } */
    }

}
