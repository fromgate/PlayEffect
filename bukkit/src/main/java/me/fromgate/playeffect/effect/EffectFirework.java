package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.EffectColor;
import me.fromgate.playeffect.PlayEffectPlugin;
import me.fromgate.playeffect.nms.NmsEffects;
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
    public void onInit() {
        String ftstr = this.getParam("type", "random");
        if (ftstr.equalsIgnoreCase("random")) ftypernd = true;
        try {
            if (ftstr.equalsIgnoreCase("random"))
                ftype = FireworkEffect.Type.values()[PlayEffectPlugin.instance.u.getRandomInt(FireworkEffect.Type.values().length)];
            else ftype = FireworkEffect.Type.valueOf(ftstr.toUpperCase());
        } catch (Exception e) {
            ftype = FireworkEffect.Type.BALL;
        }
        if (getParam("color", "random").equalsIgnoreCase("random")) colorrnd = true;
        color = EffectColor.getBukkitColor(getParam("color", "random"));
    }


    @Override
    protected void play(final Location loc) {
        if (ftypernd)
            ftype = FireworkEffect.Type.values()[PlayEffectPlugin.instance.u.getRandomInt(FireworkEffect.Type.values().length)];
        if (colorrnd) color = EffectColor.getBukkitColor("RANDOM");
        Bukkit.getScheduler().runTaskLater(PlayEffectPlugin.instance, new Runnable() {
            @Override
            public void run() {
                FireworkEffect fe = FireworkEffect.builder().with(ftype).withColor(color).flicker(true).build();
                NmsEffects.playFirework(loc, fe);
            }
        }, 1);

    }

}
