package me.fromgate.playeffect;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import com.sk89q.worldedit.regions.CuboidRegionSelector;
import com.sk89q.worldedit.regions.RegionSelector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

public class WEGLib {


    /*private static PlayEffect plg(){
        return PlayEffect.instance;
    }*/

    private static boolean weconnected = false;
    private static WorldGuardPlugin worldguard;
    private static boolean wgconnected = false;
    private static WorldEditPlugin worldedit;

    public static void init(){
        wgconnected = connectToWorldGuard();
        weconnected = connectToWorldEdit();
    }

    public static boolean isWE(){
        return weconnected;
    }

    public static boolean isWG(){
        return wgconnected;
    }

    private static boolean connectToWorldGuard(){
        Plugin twn = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        if ((twn != null)&&(twn instanceof WorldGuardPlugin)){
            worldguard = (WorldGuardPlugin) twn;
            return true;
        }
        return false;
    }

    private static boolean connectToWorldEdit(){
        Plugin twn = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
        if ((twn != null)&&(twn instanceof WorldEditPlugin)){
            worldedit = (WorldEditPlugin) twn;
            return true;
        }
        return false;
    }


    public static boolean isRegionExists(String rg){
        if (!wgconnected) return false;
        if (rg.isEmpty()) return false;
        for (World w : Bukkit.getWorlds()){
            if (worldguard.getRegionManager(w).getRegions().containsKey(rg)) return true;
        }
        return false;
    }

    public List<Location> getRegionMinMaxLocations(String rg){
        List<Location> locs = new ArrayList<Location>();
        if (!wgconnected) return locs;
        ProtectedRegion prg = null;
        World world = null;
        for (World w : Bukkit.getWorlds()){
            if (worldguard.getRegionManager(w).getRegions().containsKey(rg)){
                prg = worldguard.getRegionManager(w).getRegionExact(rg);
                world = w;
                break;
            }
        }
        if (world == null) return locs;
        if (prg== null) return locs;
        locs.add(new Location (world, prg.getMinimumPoint().getX(),prg.getMinimumPoint().getY(),prg.getMinimumPoint().getZ()));
        locs.add(new Location (world, prg.getMaximumPoint().getX(),prg.getMaximumPoint().getY(),prg.getMaximumPoint().getZ()));
        return locs;
    }

    public static List<Location> getRegionLocations(String rg, boolean land){
        List<Location> locs = new ArrayList<Location>();
        if (!wgconnected) return locs;
        ProtectedRegion prg = null;
        World world = null;
        for (World w : Bukkit.getWorlds()){
            if (worldguard.getRegionManager(w).getRegions().containsKey(rg)){
                prg = worldguard.getRegionManager(w).getRegionExact(rg);
                world = w;
                break;
            }
        }
        if(prg != null){
            for (int x = prg.getMinimumPoint().getBlockX(); x<=prg.getMaximumPoint().getBlockX(); x++)
                for (int y = prg.getMinimumPoint().getBlockY(); y<=prg.getMaximumPoint().getBlockY(); y++)
                    for (int z = prg.getMinimumPoint().getBlockZ(); z<=prg.getMaximumPoint().getBlockZ(); z++){
                        Location t = new Location (world,x,y,z);
                        if (t.getBlock().isEmpty()&&t.getBlock().getRelative(BlockFace.UP).isEmpty()){
                            if (land&&t.getBlock().getRelative(BlockFace.DOWN).isEmpty()) continue;
                            t.add(0.5, 0, 0.5);
                            locs.add(t);
                        }
                    }
        }
        return locs;
    }

    public static List<Location> getSelectionLocations(Player p){
        List<Location> locs = new ArrayList<Location>();
        if (!weconnected) return locs;
        Selection sel = worldedit.getSelection(p);
        if (sel == null) return locs;
        RegionSelector rs = worldedit.getSelection(p).getRegionSelector();
        if (rs.isDefined()){
            if (!(rs instanceof CuboidRegionSelector)) return locs;
            CuboidRegionSelector crs = (CuboidRegionSelector) rs;
            try{
                Vector v1 = crs.getRegion().getPos1();
                if (v1!=null) locs.add(new Location (p.getWorld(), v1.getX(), v1.getY(),v1.getZ()));
                Vector v2 = crs.getRegion().getPos2();
                if (v2!=null) locs.add(new Location (p.getWorld(), v2.getX(), v2.getY(),v2.getZ()));
            } catch (Exception e){
                return locs;
            }
        } else{
            try{
                BlockVector bv = rs.getPrimaryPosition();
                locs.add(new Location (p.getWorld(),bv.getX(),bv.getY(), bv.getZ()));
            } catch (Exception e){
                return locs;
            }
        }
        return locs;
    }

}
