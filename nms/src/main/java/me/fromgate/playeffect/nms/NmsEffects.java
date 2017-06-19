package me.fromgate.playeffect.nms;

import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class NmsEffects {


    private static String version;

    public static void init() {
        version = getVersion().toLowerCase();
    }


    private static String getVersion() {
        String packageName = Bukkit.getServer().getClass().getPackage().getName();
        return packageName.substring(packageName.lastIndexOf('.') + 1);
    }


    public static void playExplosion(Location loc, float size, double distance) {
        switch (version) {
            case "v1_11_r1":
                NmsEffects_v1_11_R1.playExplosion(loc, size, distance);
                break;
            case "v1_10_r1":
                NmsEffects_v1_10_R1.playExplosion(loc, size, distance);
                break;
            case "v1_9_r2":
                NmsEffects_v1_9_R2.playExplosion(loc, size, distance);
                break;
            case "v1_9_r1":
                NmsEffects_v1_9_R1.playExplosion(loc, size, distance);
                break;

        }

    }


    public static void playFirework(Location location, FireworkEffect effect , Player... players) {
        switch (version) {
            case "v1_11_r1":
                NmsEffects_v1_11_R1.playFirework(location, effect, players);
            case "v1_10_r1":
                NmsEffects_v1_10_R1.playFirework(location, effect, players);
                break;
            case "v1_9_r2":
                NmsEffects_v1_9_R2.playFirework(location, effect, players);
                break;
            case "v1_9_r1":
                NmsEffects_v1_9_R1.playFirework(location, effect, players);
                break;
        }

    }
}
