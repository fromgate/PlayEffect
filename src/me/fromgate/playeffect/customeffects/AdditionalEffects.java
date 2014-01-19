package me.fromgate.playeffect.customeffects;

import me.fromgate.playeffect.PlayEffect;

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
		if (PacketProtocolLib.isProtocolLibFound()) PacketProtocolLib.sendExplosionPacket(loc, size);
		else PacketNMS.sendExplosionPacket(loc, size);
	}

	public static void sendParticlesPacket(Location loc, String effectname, float xOffset, float yOffset, float zOffset, float effectSpeed, int amount){
		if (PacketProtocolLib.isProtocolLibFound()) PacketProtocolLib.sendParticlesPacket(loc, effectname, xOffset, yOffset, zOffset, effectSpeed, amount);
		else PacketNMS.sendParticlesPacket(loc, effectname, xOffset, yOffset, zOffset, effectSpeed, amount);
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
		PlayEffect.instance.u.log("Determined firework method: "+method+ " ["+fireworkMethodNames[method]+"]");
		return method;
	}

	
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
