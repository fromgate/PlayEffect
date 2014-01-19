package me.fromgate.playeffect.customeffects;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;

public class PacketProtocolLib {
	private static boolean connected = false;
	private static ProtocolManager protocolManager;
	public static boolean isProtocolLibFound(){
		return connected;
	}

	public static void init(){
		try{
			if (Bukkit.getPluginManager().getPlugin("ProtocolLib")!=null){
				protocolManager = ProtocolLibrary.getProtocolManager();
				connected = true;
			} 
		} catch (Throwable e){
		}
	}


	public static void sendExplosionPacket(Location loc, float size){
		if (!connected) return;
		PacketContainer explosion  = protocolManager.createPacket(PacketType.Play.Server.EXPLOSION);
		explosion.getDoubles().write(0, loc.getX());
		explosion.getDoubles().write(1, loc.getY());
		explosion.getDoubles().write(2, loc.getZ());
		explosion.getFloat().write(0, size);
		protocolManager.broadcastServerPacket(explosion, loc, 64);
	}

	public static void sendParticlesPacket(Location loc, String effectName, float xOffset, float yOffset, float zOffset, float effectSpeed, int amount){
		if (!connected) return;
		PacketContainer particles = protocolManager.createPacket(PacketType.Play.Server.WORLD_PARTICLES);
		particles.getStrings().write(0, effectName);
		particles.getFloat().write(0, (float) loc.getX());
		particles.getFloat().write(1, (float) loc.getY());
		particles.getFloat().write(2, (float) loc.getZ());
		particles.getFloat().write(3, xOffset);
		particles.getFloat().write(4, yOffset);
		particles.getFloat().write(5, zOffset);
		particles.getFloat().write(6, effectSpeed);
		particles.getIntegers().write(0, amount);
		protocolManager.broadcastServerPacket(particles, loc, 64);
	}


	public static void playFirework164(World world, Location loc, FireworkEffect fe) {
		if (!connected) return;
		Firework fw = (Firework) world.spawn(loc, Firework.class);
		FireworkMeta data = (FireworkMeta) fw.getFireworkMeta();
		data.clearEffects();
		data.setPower(1);
		data.addEffect(fe);
		fw.setFireworkMeta(data);
		PacketContainer entityEffect = protocolManager.createPacket(PacketType.Play.Server.ENTITY_STATUS);
		entityEffect.getIntegers().write(0, fw.getEntityId());
		entityEffect.getBytes().write(0, (byte) 17);
		protocolManager.broadcastServerPacket(entityEffect, loc, 64);
		fw.remove();
	} 

}
