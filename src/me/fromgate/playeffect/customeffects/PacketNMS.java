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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.VisualEffect;
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
	private static String [] tested_versions = {"v1_8_R1"};
	private static boolean disabled = true;
	private static boolean activated = false;
	private static String obcPrefix = "org.bukkit.craftbukkit.";
	private static String nmsPrefix = "net.minecraft.server.";
	private static String version = "";
	private static Class<?> Vec3D;
	private static Constructor<?> newVec3D;
	private static Class<?> Packet;
	private static Class<?> Packet60Explosion;
	private static Class<?> CraftServer;
	private static Class<?> CraftWorld;
	private static Class<?> DedicatedPlayerList; 
	private static Class<?> WorldServer;
	private static Class<?> PacketPlayOutWorldParticles;
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
	private static Class<?> EnumParticle; //net.minecraft.server.v1_8_R1.EnumParticle
	private static Method enumParticle_name; //Enum.name();
	private static Map<VisualEffect,Object> enumParticles;

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
			newVec3D = Vec3D.getConstructor(double.class,double.class,double.class);
			Packet = Class.forName(nmsPrefix+"Packet");
			Packet60Explosion = Class.forName(nmsPrefix+((version.startsWith("v1_6")||version.startsWith("v1_5")) ? "Packet60Explosion": "PacketPlayOutExplosion"));
			CraftServer = Class.forName(obcPrefix+"CraftServer");
			CraftWorld = Class.forName(obcPrefix+"CraftWorld");
			DedicatedPlayerList = Class.forName(nmsPrefix+"DedicatedPlayerList");
			WorldServer = Class.forName(nmsPrefix+"WorldServer");

			getHandleCS = CraftServer.getMethod("getHandle");
			sendPacketNearby = DedicatedPlayerList.getMethod("sendPacketNearby", double.class, double.class, double.class, double.class, int.class, Packet);
			getHandleCW = CraftWorld.getMethod("getHandle");
			dimensionField = WorldServer.getField("dimension");
			newPacket = Packet60Explosion.getConstructor(double.class,double.class,double.class,float.class,List.class,Vec3D);
			PacketPlayOutWorldParticles = Class.forName(nmsPrefix+ "PacketPlayOutWorldParticles");
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
			EnumParticle = Class.forName(nmsPrefix+"EnumParticle"); //net.minecraft.server.v1_8_R1.EnumParticle
			fillParticles();
			disabled = false;
		} catch (Exception e) {
			log(e.getMessage());
			log("Warning! PacketNMS library is not compatible with "+Bukkit.getVersion());
			log("Check updates of PlayEffect at http://dev.bukkit.org/bukkit-plugins/playeffect/");
			log("or use this version at your own risk.");
			//log("You can also install ProtocolLib to prevent compatibility issues.");
			log("Error message:");
			e.printStackTrace();
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
		log("Warning! PlayEffect was not tested with server version:"+Bukkit.getBukkitVersion());
		log("Check updates at http://dev.bukkit.org/bukkit-plugins/playeffect/");
		log("or use this version at your own risk.");
		//log("You can also install ProtocolLib to prevent compatibility issues.");
	}

	public static boolean isDisabled(){
		return disabled;
	}

	public static void sendExplosionPacket(Location loc, float size){
		if (disabled) return;
		try {
			Object v = newVec3D.newInstance((double) 0,(double) 0,(double) 0);
			Object handleCraftServer = getHandleCS.invoke(Bukkit.getServer()); //DedicatedPlayerList
			Object worldServer = getHandleCW.invoke(loc.getWorld());
			int dimension = dimensionField.getInt(worldServer);
			Object packet = newPacket.newInstance(loc.getX(),loc.getY(),loc.getZ(),size, new ArrayList<Object>(),v);
			sendPacketNearby.invoke(handleCraftServer, loc.getX(), loc.getY(), loc.getZ(),64,dimension,packet);
		} catch (Exception e) {
			disabled = true;
			log("Failed to create explosion effect.");
			e.printStackTrace();
		}
	}

	private static void fillParticles (){
		enumParticles = new HashMap<VisualEffect,Object>();
		for (VisualEffect ve : VisualEffect.values()){
			for (Object obj : EnumParticle.getEnumConstants()){
				String name = null;
				try {
					enumParticle_name = obj.getClass().getMethod("name");
					name = (String) enumParticle_name.invoke(obj);
				} catch (Exception e){
				}
				if (name == null) continue;
				if (name.equalsIgnoreCase(ve.name())) {
					enumParticles.put(ve, obj);
					continue;
				}
			}
		}
	}

	private static Object getEnumParticle (VisualEffect effect) { 
		if (disabled) return null;
		return enumParticles.containsKey(effect) ? enumParticles.get(effect) : null;
	}

	public static void sendParticlesPacket(VisualEffect effect, Location loc, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount){
		Object enumParticle = getEnumParticle (effect);
		if (enumParticle == null) return;
		if (loc == null) return;
		int [] extra = new int [0];
		if (id>0) extra = (effect == VisualEffect.ITEM_CRACK) ? new int[] {id,data} : new int[] { data << 12 | id & 0xFFF };
		try {
			Object sPacket= PacketPlayOutWorldParticles.newInstance();
			for (Field f : PacketPlayOutWorldParticles.getDeclaredFields()){
				f.setAccessible(true);
				String fld = f.getName();
				if (fld.equals("a")) f.set(sPacket, enumParticle);
				if (fld.equals("j")) f.set(sPacket, true);

				if (fld.equals("b")) f.set(sPacket, (float) loc.getX());
				if (fld.equals("c")) f.set(sPacket, (float) loc.getY());
				if (fld.equals("d")) f.set(sPacket, (float) loc.getZ());

				if (fld.equals("e")) f.set(sPacket, offsetX);
				if (fld.equals("f")) f.set(sPacket, offsetY);
				if (fld.equals("g")) f.set(sPacket, offsetZ);

				if (fld.equals("h")) f.set(sPacket, speed);
				if (fld.equals("i")) f.set(sPacket, particleCount);
				if (fld.equals("k")) f.set(sPacket, extra);
			}
			Object handleCraftServer = getHandleCS.invoke(Bukkit.getServer()); //DedicatedPlayerList
			Object worldServer = getHandleCW.invoke(loc.getWorld());
			int dimension = dimensionField.getInt(worldServer);
			sendPacketNearby.invoke(handleCraftServer, loc.getX(), loc.getY(), loc.getZ(),64,dimension,sPacket);
		}catch (Exception e){
			disabled = true;
			log("Failed to create particles effect.");

		}
	}

	// Old. Not working in 1.8.1 :(
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

	// Old. Not working in 1.8.1 :(
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
