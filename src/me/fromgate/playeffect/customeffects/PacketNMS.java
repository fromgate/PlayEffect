package me.fromgate.playeffect.customeffects;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import me.fromgate.playeffect.PlayEffect;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;


public class PacketNMS {
	private static PlayEffect plg(){
		return PlayEffect.instance;
	}
	private static String [] tested_versions = {"v1_6_R2","v1_6_R3","v1_7_R1"};
	private static boolean disabled = true;
	private static boolean activated = false;
	private static String obcPrefix = "org.bukkit.craftbukkit.";
	private static String nmsPrefix = "net.minecraft.server.";
	private static String version = "";
	private static Class<?> Vec3D;
	private static Class<?> Packet;
	private static Class<?> Packet60Explosion;
	private static Class<?> CraftServer;
	private static Class<?> CraftWorld;
	private static Class<?> DedicatedPlayerList; 
	private static Class<?> WorldServer;
	private static Class<?> Packet63WorldParticles;
	private static Method aVec3D; 
	private static Method getHandleCS;
	private static Method sendPacketNearby;
	private static Method getHandleCW;
	private static Field dimensionField;
	private static Constructor<?> newPacket;
	private static Method world_getHandle;
	private static Method broadcastEntityEffect;
	private static Class<?> CraftFirework;
	private static Method firework_getHandle;
	private static Class<?> EntityFireworks;
	private static Field expectedLifespan;
	private static Field ticksFlown;
	private static Class<?> NmsWorld;
	private static Class<?> NmsEntity;




	public static void init(){
		if (activated) return;
		String pkg = Bukkit.getServer().getClass().getPackage().getName();
		String [] v = pkg.split("\\.");
		if (v.length==4){
			version = v[3];
			obcPrefix = "org.bukkit.craftbukkit."+version+".";
			nmsPrefix = "net.minecraft.server."+version+".";;
		}
		isTestedInform();
		try {
			Vec3D = Class.forName(nmsPrefix+"Vec3D");
			Packet = Class.forName(nmsPrefix+"Packet");
			Packet60Explosion = Class.forName(nmsPrefix+((version.startsWith("v1_6")||version.startsWith("v1_5")) ? "Packet60Explosion": "PacketPlayOutExplosion"));
			CraftServer = Class.forName(obcPrefix+"CraftServer");
			CraftWorld = Class.forName(obcPrefix+"CraftWorld");
			DedicatedPlayerList = Class.forName(nmsPrefix+"DedicatedPlayerList");
			WorldServer = Class.forName(nmsPrefix+"WorldServer");
			aVec3D = Vec3D.getMethod("a", double.class, double.class, double.class); 
			getHandleCS = CraftServer.getMethod("getHandle");
			sendPacketNearby = DedicatedPlayerList.getMethod("sendPacketNearby", double.class, double.class, double.class, double.class, int.class, Packet);
			getHandleCW = CraftWorld.getMethod("getHandle");
			dimensionField = WorldServer.getField("dimension");
			newPacket = Packet60Explosion.getConstructor(double.class,double.class,double.class,float.class,List.class,Vec3D);
			Packet63WorldParticles = Class.forName(nmsPrefix+((version.startsWith("v1_6")||version.startsWith("v1_5")) ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles"));
			world_getHandle = CraftWorld.getMethod("getHandle"); 
			CraftFirework = Class.forName(obcPrefix+"entity.CraftFirework");
			firework_getHandle = CraftFirework.getMethod("getHandle"); //EntityFireworks
			EntityFireworks = Class.forName(nmsPrefix+"EntityFireworks");
			expectedLifespan = EntityFireworks.getField("expectedLifespan");
			ticksFlown = EntityFireworks.getDeclaredField("ticksFlown");
			ticksFlown.setAccessible(true);
			NmsWorld = Class.forName(nmsPrefix+"World");
			NmsEntity = Class.forName(nmsPrefix+"Entity");
			broadcastEntityEffect = NmsWorld.getMethod("broadcastEntityEffect",NmsEntity ,byte.class);
			disabled = false;
		} catch (Exception e) {
			log(e.getMessage());
			log("Warning! PacketNMS library is not compatible with "+Bukkit.getVersion());
			log("Check updates of PlayEffect at http://dev.bukkit.org/bukkit-plugins/playeffect/");
			log("or use this version at your own risk.");
			log("You can also install ProtocolLib to prevent compatibility issues.");
			disabled = true;
		}
		activated = true;
	}

	private static void log(String string) {
		plg().u.log(string);
	}

	public static boolean isTestedVersion(){
		for (int i = 0; i< tested_versions.length;i++){
			if (tested_versions[i].equalsIgnoreCase(version)) return true;
		}
		return false;
	}

	public static String getVersion(){
		return version;
	}

	public static void isTestedInform(){
		if (isTestedVersion()) return;
		log("Warning! PlayEffect was not tested with craftbukkit version "+Bukkit.getBukkitVersion());
		log("Check updates at http://dev.bukkit.org/bukkit-plugins/playeffect/");
		log("or use this version at your own risk.");
		log("You can also install ProtocolLib to prevent compatibility issues.");
	}

	public static boolean isDisabled(){
		return disabled;
	}

	public static void sendExplosionPacket(Location loc, float size){
		if (disabled) return;
		try {
			Object v =  aVec3D.invoke(null,0,0,0);
			Object handleCraftServer = getHandleCS.invoke(Bukkit.getServer()); //DedicatedPlayerList
			Object worldServer = getHandleCW.invoke(loc.getWorld());
			int dimension = dimensionField.getInt(worldServer);
			Object packet = newPacket.newInstance(loc.getX(),loc.getY(),loc.getZ(),size, new ArrayList<Object>(),v);
			sendPacketNearby.invoke(handleCraftServer, loc.getX(), loc.getY(), loc.getZ(),64,dimension,packet);
		} catch (Exception e) {
			disabled = true;
			log("Failed to create explosion effect.");
		}
	}


	public static void sendParticlesPacket(Location loc, String effectname, float xOffset, float yOffset, float zOffset, float effectSpeed, int amount){
		if (disabled) return;
		try{
			Object sPacket= Packet63WorldParticles.newInstance();    
			for (Field f : Packet63WorldParticles.getDeclaredFields()){
				f.setAccessible(true);
				String fld = f.getName();
				if (fld.equals("a")) f.set(sPacket, effectname);
				if (fld.equals("b")) f.set(sPacket, (float) loc.getX());
				if (fld.equals("c")) f.set(sPacket, (float) loc.getY());
				if (fld.equals("d")) f.set(sPacket, (float) loc.getZ());
				if (fld.equals("e")) f.set(sPacket, xOffset);
				if (fld.equals("f")) f.set(sPacket, yOffset);
				if (fld.equals("g")) f.set(sPacket, zOffset);
				if (fld.equals("h")) f.set(sPacket, effectSpeed);
				if (fld.equals("i")) f.set(sPacket, amount);
			}
			Object handleCraftServer = getHandleCS.invoke(Bukkit.getServer()); //DedicatedPlayerList
			Object worldServer = getHandleCW.invoke(loc.getWorld());
			int dimension = dimensionField.getInt(worldServer);
			sendPacketNearby.invoke(handleCraftServer, loc.getX(), loc.getY(), loc.getZ(),64,dimension,sPacket);
		} catch (Exception e){  
			disabled = true;
			log("Failed to create particles effect.");
		}

	}

	public static void playFirework164(final World world, final Location loc, final FireworkEffect fe){
		if (disabled) return;
		try{
			Firework fw = (Firework) world.spawn(loc, Firework.class);
			Object nms_world = null;
			Object nms_firework = null;
			nms_world = world_getHandle.invoke(world);
			nms_firework = firework_getHandle.invoke(fw);
			FireworkMeta data = (FireworkMeta) fw.getFireworkMeta();
			data.clearEffects();
			data.setPower(1);
			data.addEffect(fe);
			fw.setFireworkMeta(data);
			broadcastEntityEffect.invoke(nms_world, nms_firework, (byte) 17);
			fw.remove();
		} catch (Exception e){
			disabled = true;
			log("Failed to create firework effect.");
		}
	} 

	public static void playFirework172(World world, Location loc, FireworkEffect fe){
		if (disabled) return;
		Firework fw = (Firework) world.spawn(loc, Firework.class);
		FireworkMeta data = (FireworkMeta) fw.getFireworkMeta();
		data.clearEffects();
		data.setPower(0);
		data.addEffect(fe);
		fw.setFireworkMeta(data);
		try{
			Object nms_firework = firework_getHandle.invoke(fw);
			expectedLifespan.set(nms_firework, (int) 1);
			ticksFlown.set(nms_firework, (int) 1);
		}catch(Exception e){
			disabled = true;
			log("Failed to create firework effect.");
		}
	}
}
