package me.fromgate.playeffect;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.fromgate.playeffect.effect.BasicEffect;

public class EffectQueue {
    static int effectpertick = 100;
    static List<BasicEffect> queue = new ArrayList<BasicEffect>();
    
    public static void addToQueue (BasicEffect effect){
        if (effect==null) return;
        if (isPlayersAround(effect.getLocation())) queue.add(effect);
    }
    
    public static void init(int ept){
        effectpertick = ept;
        Bukkit.getScheduler().runTaskTimer(PlayEffect.instance, new Runnable(){

            @Override
            public void run() {
                if (queue.isEmpty()) return;
                for (int i = 0; i<Math.min(effectpertick, queue.size());i++){
                    queue.get(0).playEffect();
                    queue.remove(0);
                }
            }
        }, 1, 1);
    }

    
    
    public static boolean isPlayersAround(Location loc){
        if (loc==null) return false;
        if (!loc.getWorld().getChunkAt(loc.getBlockX()>>4, loc.getBlockZ()>>4).isLoaded()) return false;
        //Long time = System.currentTimeMillis();
        for (Player p : Bukkit.getOnlinePlayers())
            if (loc.getWorld().equals(loc.getWorld())&&(loc.distance(p.getLocation())<=PlayEffect.instance.effect_visible_distance)) return true;
        return false;
    }

}
