package me.fromgate.playeffect.nms;

import net.minecraft.server.v1_9_R1.BlockPosition;
import net.minecraft.server.v1_9_R1.PacketPlayOutExplosion;
import net.minecraft.server.v1_9_R1.Vec3D;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftServer;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.ArrayList;



public class NmsEffects_v1_9_R1 {

    public static void playExplosion(Location loc, float size, double distance) {
        PacketPlayOutExplosion packetPlayOutExplosion = new PacketPlayOutExplosion(loc.getX(), loc.getY(), loc.getZ(), size, new ArrayList<BlockPosition>(), new Vec3D(0, 0, 0));
        ((CraftServer) Bukkit.getServer()).getHandle().sendPacketNearby(null, loc.getX(), loc.getY(), loc.getZ(), distance,
                ((CraftWorld) loc.getWorld()).getHandle().dimension, packetPlayOutExplosion);
    }


    public static void playFirework(Location location, FireworkEffect effect, Player... players) {
        InstantFireworks_v1_9_R1 firework = new InstantFireworks_v1_9_R1(((CraftWorld) location.getWorld()).getHandle(), players);
        FireworkMeta meta = ((Firework) firework.getBukkitEntity()).getFireworkMeta();
        meta.addEffect(effect);

        ((Firework) firework.getBukkitEntity()).setFireworkMeta(meta);

        firework.setPosition(location.getX(), location.getY(), location.getZ());

        if ((((CraftWorld) location.getWorld()).getHandle()).addEntity(firework)) {
            firework.setInvisible(true);
        }
    }
}