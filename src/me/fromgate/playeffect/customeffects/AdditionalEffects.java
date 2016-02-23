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

package me.fromgate.playeffect.customeffects;

import me.fromgate.playeffect.PlayEffectPlugin;
import me.fromgate.playeffect.VisualEffect;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class AdditionalEffects {
	
	public static int fireworkMethod = -1;
	public static String [] fireworkMethodNames = {"default (>=1.7)","standart (>=1.7)","older (<=1.6)","ProtocolLib (<=1.6)"};
	

	public static void sendExplosionPacket(Location loc, float size){
		PacketNMS.sendExplosionPacket(loc, size);
		/* if (PacketProtocolLib.isProtocolLibFound()) PacketProtocolLib.sendExplosionPacket(loc, size);
		else PacketNMS.sendExplosionPacket(loc, size); */
	}

	//sendParticlesPacket(VisualEffect effect, Location loc, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius)
	public static void sendParticlesPacket(Location loc, String effectname, int id, int data, float xOffset, float yOffset, float zOffset, float effectSpeed, int amount){
		VisualEffect ve = VisualEffect.getEffectByName(effectname);
		if (ve == null) return;
		PacketNMS.sendParticlesPacket(ve, loc, id, data, xOffset, yOffset, zOffset, effectSpeed, amount);
		/*if (PacketProtocolLib.isProtocolLibFound()) PacketProtocolLib.sendParticlesPacket(loc, effectname, xOffset, yOffset, zOffset, effectSpeed, amount);
		else PacketNMS.sendParticlesPacket(loc, effectname, xOffset, yOffset, zOffset, effectSpeed, amount); */
	}
	
	
 
	/**
	 * @param method - firework effect type:
	 *                 -1 - auto
	 *                  0 - default, using internal lib, recommended for 1.7.2 and later
	 *                  1 - standart, standart bukkit detonate method, recommended for 1.7.2 and later
	 *                  2 - older vesion, using internal lib, recommended for 1.6.4 and older
	 *                  3 - ProtocolLib, using ProtocolLib, recommended for 1.6.4 and older
	 * @param loc     - Location where firework will played 
	 * @param fe      - Firework effect type
	 */
	public static void playFirework(Location loc, FireworkEffect fe){
		if (fireworkMethod<0) fireworkMethod = determineFireworkMethod();
		World world = loc.getWorld();
		switch (fireworkMethod){
		case 0:
			PacketNMS.playFirework172(world, loc, fe);
			break;
		case 1:
			playFirework172 (loc,fe);
			break;
		case 2:
			PacketNMS.playFirework164(world, loc, fe);
			break;
		case 3:
			PacketProtocolLib.playFirework164(world, loc, fe);
			break;
		}
	}
	
	private static int determineFireworkMethod(){
		int method = 0;
		if (PacketNMS.isDisabled()) {
			if (isBukkitOld()&&PacketProtocolLib.isProtocolLibFound()) method = 3;
			else method = 1;
		} else {
			if (isBukkitOld()) method = 2;//PacketNMS.playFirework(world, loc, fe);
			else method = 0;
		}
		PlayEffectPlugin.instance.u.log("Determined firework method: "+method+ " ["+fireworkMethodNames[method]+"]");
		return method;
	}
	
	// Old. Not working in 1.8.1
	public static void playFirework172 (Location loc, FireworkEffect fe){
		if (isBukkitOld()) return;
		Firework fw = (Firework) loc.getWorld().spawn(loc, Firework.class);
		FireworkMeta data = (FireworkMeta) fw.getFireworkMeta();
		data.clearEffects();
		data.setPower(1);
		data.addEffect(fe);
		fw.setFireworkMeta(data);
		fw.detonate();
	}
	
	private static boolean isBukkitOld(){
		return Bukkit.getBukkitVersion().startsWith("1.4")||
				Bukkit.getBukkitVersion().startsWith("1.5")||
				Bukkit.getBukkitVersion().startsWith("1.6");
	}


}
