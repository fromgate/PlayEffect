package me.fromgate.playeffect;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import me.fromgate.playeffect.effect.BasicEffect;

public class EffectQueue {
    private static List<BasicEffect> queue = new ArrayList<BasicEffect>();
    
    public static void addToQueue (BasicEffect effect){
        if (effect==null) return;
        if (isPlayersAround(effect.getLocation(),effect.getType().getVisibilityDistance())) queue.add(effect);
    }
    
    public static void init(final int ept, final int ttime){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(PlayEffect.instance, new Runnable(){
            @Override
            public void run() {
                if (queue.isEmpty()) return;
                for (int i = 0; i<Math.min(ept, queue.size());i++){
                    queue.get(0).playEffect();
                    queue.remove(0);
                }
            }
        }, 20, ttime);
    }
    
    public static boolean isPlayersAround(Location loc, int distance){
        if (loc==null) return false;
        if (!loc.getWorld().getChunkAt(loc.getBlockX()>>4, loc.getBlockZ()>>4).isLoaded()) return false;
        for (Player p : Bukkit.getOnlinePlayers())
            if (loc.getWorld().equals(loc.getWorld())&&(loc.distance(p.getLocation())<=distance)) return true;
        return false;
    }
}
