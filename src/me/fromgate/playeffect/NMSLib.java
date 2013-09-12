package me.fromgate.playeffect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;


public class NMSLib {
    private static String [] tested_versions = {"v1_6_R2"};
    private static boolean disabled = true;
    private static boolean activated = false;

    private static String obcPrefix = "org.bukkit.craftbukkit.";
    private static String nmsPrefix = "net.minecraft.server.";
    private static String version = "";
    private static Class<?> ChunkPosition;
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


    //private static Class<?> PlayerConnection;
    //private static Class<?> CraftPlayer;
    //private static Method getHandleCP;
    //private static Method sendPacket;

    



    ////////////////////////////////////////////////////////////////////
    private static Method world_getHandle;
    private static Method broadcastEntityEffect;
    private static Class<?> CraftFirework;
    private static Method firework_getHandle;
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
            PlayEffect.instance.u.log("Found craftbukkit version: "+version);
        }
        try {
            ChunkPosition = Class.forName(nmsPrefix+"ChunkPosition");
            Vec3D = Class.forName(nmsPrefix+"Vec3D");
            Packet = Class.forName(nmsPrefix+"Packet");
            Packet60Explosion = Class.forName(nmsPrefix+"Packet60Explosion");
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
            Packet63WorldParticles = Class.forName(nmsPrefix+"Packet63WorldParticles");
            world_getHandle = CraftWorld.getMethod("getHandle"); 
            CraftFirework = Class.forName(obcPrefix+"entity.CraftFirework");
            firework_getHandle = CraftFirework.getMethod("getHandle");
            NmsWorld = Class.forName(nmsPrefix+"World");
            NmsEntity = Class.forName(nmsPrefix+"Entity");
            broadcastEntityEffect = NmsWorld.getMethod("broadcastEntityEffect",NmsEntity ,byte.class);
        } catch (Exception e) {
            PlayEffect.instance.u.log("Failed to initialize NMSLib!");
            e.printStackTrace();
        }
        activated = true;
    }

    public static boolean isTestedVersion(){
        //if (version.isEmpty()) return uselibigot;
        for (int i = 0; i< tested_versions.length;i++){
            if (tested_versions[i].equalsIgnoreCase(version)) return true;
        }
        return false;
    }

    public static boolean isDisabled(){
        return disabled;
    }



    public static void sendExplosionPacket(Location loc, int size){
        float expl_f = 1.1f; // Эээээ....
        try {
            Object v =  aVec3D.invoke(null,0,0,0);
            Object handleCraftServer = getHandleCS.invoke(Bukkit.getServer()); //DedicatedPlayerList
            Object worldServer = getHandleCW.invoke(loc.getWorld());
            int dimension = dimensionField.getInt(worldServer);
            Object packet = newPacket.newInstance(loc.getX(),loc.getY(),loc.getZ(),expl_f, fillExplosionBlocks(loc,size),v);
            sendPacketNearby.invoke(handleCraftServer, loc.getX(), loc.getY(), loc.getZ(),64,dimension,packet);
        } catch (Exception e) {
            PlayEffect.instance.u.logOnce("explfail","[NMSLib] Failed to send explosion packet!");
            e.printStackTrace();
        }
    }

    private static List<Object> fillExplosionBlocks (Location loc, int r){
        List<Object> expl_blocks = new ArrayList<Object>();
        try {
            Constructor<?> newChunkPosition;
            newChunkPosition = ChunkPosition.getConstructor(int.class,int.class,int.class);


            for (int i = -r; i<=r; i++)
                for (int j = -r; j<=r; j++)
                    for (int k = -r; k<=r; k++){
                        Location la = loc.add(i,j,k);
                        if (loc.distance(la)<=r) expl_blocks.add(newChunkPosition.newInstance(la.getBlockX(), la.getBlockY(), la.getBlockZ()));
                    }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return expl_blocks;
    }



    public static void sendParticlesPacket(Location loc, String effectname, float xOffset, float yOffset, float zOffset, float effectSpeed, int amount){
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
            PlayEffect.instance.u.logOnce("prt"+effectname,"[NMSLib] Failed to send particle "+effectname+" packet!");
            e.printStackTrace();
        }

    }
    
    
    public static void playFirework(World world, Location loc, FireworkEffect fe) throws Exception {
        Firework fw = (Firework) world.spawn(loc, Firework.class);
        Object nms_world = null;
        Object nms_firework = null;
        nms_world = world_getHandle.invoke(world, (Object[]) null);
        nms_world = world_getHandle.invoke(world);
        nms_firework = firework_getHandle.invoke(fw, (Object[]) null);
        FireworkMeta data = (FireworkMeta) fw.getFireworkMeta();
        data.clearEffects();
        data.setPower(1);
        data.addEffect(fe);
        fw.setFireworkMeta(data);
        broadcastEntityEffect.invoke(nms_world, new Object[] {nms_firework, (byte) 17});
        fw.remove();
    }



}
