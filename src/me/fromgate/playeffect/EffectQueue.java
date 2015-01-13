/*  
 *  PlayEffect, Minecraft bukkit plugin
 *  (c)2013-2015, fromgate, fromgate@gmail.com
 *  http://dev.bukkit.org/bukkit-plugins/playeffect/
 *    
 *  This file is part of PlayEffect.
 *  
 *  PlayEffect is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  PlayEffect is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with PlayEffect.  If not, see <http://www.gnorg/licenses/>.
 * 
 */

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
    
    @SuppressWarnings("deprecation")
	public static boolean isPlayersAround(Location loc, int distance){
        if (loc==null) return false;
        if (!loc.getWorld().getChunkAt(loc.getBlockX()>>4, loc.getBlockZ()>>4).isLoaded()) return false;
        for (Player p : Bukkit.getOnlinePlayers())
            if (loc.getWorld().equals(p.getWorld())&&(loc.distance(p.getLocation())<=distance)) return true;
        return false;
    }
    
    protected static void clearQueue(){
        queue.clear();
    }
}
