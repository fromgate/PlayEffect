package me.fromgate.playeffect;

import me.fromgate.playeffect.util.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.text.DecimalFormat;
import java.util.*;


public class Util extends FGUtilCore implements Listener {
    PlayEffectPlugin plg;

    public Util(PlayEffectPlugin plugin, boolean savelng, String language, String plgcmd) {
        super(plugin, savelng, language, plgcmd, "playeffect");
        this.plg = plugin;
        initMessages();
        InitCmd();
        if (savelng) this.SaveMSG();
    }

    @Override
    public boolean checkCmdPerm(CommandSender sender, String command) {
        String cmd = command;
        VisualEffect ve = VisualEffect.getEffectByName(command);
        if (ve != null) cmd = ve.name();
        if (!cmds.containsKey(cmd.toLowerCase())) return false;
        Cmd cm = cmds.get(cmd.toLowerCase());
        if (sender instanceof Player) return (cm.perm.isEmpty() || sender.hasPermission(cm.perm));
        else return cm.console;
    }


    public void initMessages() {
        addMSG("lst_title", "String list:");
        addMSG("lst_footer", "Page: [%1% / %2%]");
        addMSG("lst_listisempty", "List is empty");
        addMSG("msg_config", "Configuration");
        addMSG("cfgmsg_general_check-updates", "Check updates: %1%");
        addMSG("cfgmsg_general_language", "Language: %1%");
        addMSG("cfgmsg_general_language-save", "Save translation file: %1%");
        addMSG("hlp_list", "%1% - list of placed effects");
        addMSG("hlp_set", "%1% - setup effect");

        addMSG("hlp_potion", "%1% - play Potion break effect");

        addMSG("hlp_ender_signal", "%1% - play Ender Signal effect");
        addMSG("hlp_ender_eye", "%1% - play Ender eye effect");
        addMSG("hlp_portal", "%1% - play Portal effect");

        addMSG("hlp_lightning", "%1% - play Lightning effect");

        addMSG("hlp_explosion", "%1% - play Explosion effect");
        addMSG("hlp_explosion_normal", "%1% - play explode effect");
        addMSG("hlp_explosion_huge", "%1% - play Huge explosion effect");
        addMSG("hlp_explosion_large", "%1% - play Large explode effect");

        addMSG("hlp_fireworks_explode", "%1% - play Fireworks explosion effect");
        addMSG("hlp_fireworks_spark", "%1% - play Fireworks spark effect");

        addMSG("hlp_water_bubble", "%1% - play Water bubble effect (works only underwater)");
        addMSG("hlp_water_splash", "%1% - play Water splash effect");
        addMSG("hlp_water_wake", "%1% - play Water wake effect");
        addMSG("hlp_water_drop", "%1% - play Water drop effect");

        addMSG("hlp_suspended", "%1% - play Suspend effect (works only underwater)");
        addMSG("hlp_suspended_depth", "%1% - play Depth suspend effect");

        addMSG("hlp_crit", "%1% - play Crit effect");
        addMSG("hlp_crit_magic", "%1% - play Magic crit effect");


        addMSG("hlp_smoke", "%1% - play Smoke effect (you can define direction)");
        addMSG("hlp_smoke_normal", "%1% - play Normal smoke effect");
        addMSG("hlp_smoke_large", "%1% - play Large smoke effect");


        addMSG("hlp_spell", "%1% - play Spell effect");
        addMSG("hlp_spell_instant", "%1% - play Instant spell effect");
        addMSG("hlp_spell_mob", "%1% - play Mob spell effect");
        addMSG("hlp_spell_mob_ambient", "%1% - play Mob spell (ambient) effect");
        addMSG("hlp_spell_witch", "%1% - play Witch magic effect");

        addMSG("hlp_drip_water", "%1% - play Drip water effect");
        addMSG("hlp_drip_lava", "%1% - play Drip lava effect");

        addMSG("hlp_villager_angry", "%1% - play Angry villager effect");
        addMSG("hlp_villager_happy", "%1% - play Happy villager effect");

        addMSG("hlp_flame", "%1% - play Flame (new) effect");
        addMSG("hlp_flame_spawner", "%1% - play Flame effect");

        addMSG("hlp_item_crack", "%1% - play Icon crack effect");
        addMSG("hlp_block_crack", "%1% - play Block crack effect");
        addMSG("hlp_block_crack_sound", "%1% - play Block crack effect with break sound");
        addMSG("hlp_block_dust", "%1% - play Block dust effect");

        addMSG("hlp_town_aura", "%1% - play Town aura effect");

        addMSG("hlp_note", "%1% - play Note effect");
        addMSG("hlp_enchantment_table", "%1% - play Runes (magic book) effect");
        addMSG("hlp_lava", "%1% - play Lava effect");
        addMSG("hlp_footstep", "%1% - play Footstep effect");
        addMSG("hlp_cloud", "%1% - play Smoke effect");
        addMSG("hlp_redstone", "%1% - play Redstone dust effect");
        addMSG("hlp_snowball", "%1% - play Snowball effect");
        addMSG("hlp_snow_shovel", "%1% - play Snow showel effect");
        addMSG("hlp_slime", "%1% - play Slime effect");
        addMSG("hlp_heart", "%1% - play Heart effect");
        addMSG("hlp_barrier", "%1% - play Barrier effect");

        addMSG("hlp_item_take", "%1% - play Item take effect (I don''know what is it)");
        addMSG("hlp_mob_appearance", "%1% - play Mob appearance effect");

        addMSG("hlp_sound", "%1% - play Sound effect");
        addMSG("hlp_song", "%1% - play Song effect");
        addMSG("msg_effectset", "Effect was succesfully set");
        addMSG("msg_unknowneffect", "Unknown effect type %1%");
        addMSG("hlp_info", "%1% - show info about defined effect");
        addMSG("hlp_remove", "%1% - remove static effect");
        addMSG("hlp_wand", "%1% - to enable/disable wand mode");
        addMSG("msg_wandenabled", "Wand mode enabled");
        addMSG("msg_seteffect", "Effect set %1%");
        addMSG("msg_removed", "Effect %1% removed");
        addMSG("msg_efflist", "Effect list");
        addMSG("msg_efflistempty", "Effect list is empty");
        addMSG("msg_efflist", "Effect list");
        addMSG("msg_efflistempty", "Effect list is empty");
        addMSG("hlp_show", "%1% - show effect(s)");
        addMSG("hlp_hide", "%1% - hide effect(s)");
        addMSG("hlp_resart", "%1% - restart static effects");
        addMSG("hlp_reload", "%1% - reload configuration");
        addMSG("hlp_check", "%1% - find effect around you");
        addMSG("msg_restarted", "All effects restarted!");
        addMSG("msg_reloaded", "All effects reloaded and restarted!");
        addMSG("msg_removefailed", "Failed to remove effect %1%");
        addMSG("msg_consoleneedcoord", "You must define effect location when executing command play <effect> by console");
        addMSG("msg_wrongeffect", "Failed parse effect name %1%");
    }

    private void InitCmd() {
        cmds.clear();
        cmdlist = "";
        addCmd("help", "config", "hlp_thishelp", "&3/playeffect help [page]", 'b', true); // +-
        addCmd("list", "config", "hlp_list", "&3/playeffect list [page]", 'b', true);  // +
        addCmd("info", "config", "hlp_info", "&3/playeffect info <effect id | number>", 'b', true); // +
        addCmd("remove", "config", "hlp_remove", "&3/playeffect remove <effect number>", 'b', true); // +
        addCmd("set", "set", "hlp_set", "&3/playeffect set <effect> [param]", 'b'); // +
        addCmd("wand", "wand", "hlp_wand", "&3/playeffect wand <effect> [param]", 'b'); // +
        addCmd("show", "show", "hlp_show", "&3/playeffect show <effect id>", 'b', true); // +
        addCmd("hide", "show", "hlp_hide", "&3/playeffect hide <effect id>", 'b', true); // +
        addCmd("check", "config", "hlp_check", "&3/playeffect check [radius]", 'b'); // +
        addCmd("restart", "config", "hlp_resart", "&3/playeffect restart", 'b', true); // +
        addCmd("reload", "config", "hlp_reload", "&3/playeffect reload", 'b', true);

        for (VisualEffect ve : VisualEffect.values()) {
            if (ve == VisualEffect.BASIC) continue;
            String ven = ve.name().toLowerCase();
            addCmd(ven, "play", "hlp_" + ven, "&3/playeffect " + ven + " [parameters]", 'b', true);
        }
    }

    public static String locationToString(Location loc) {
        if (loc == null) return "";
        DecimalFormat fmt = new DecimalFormat("####0.##");
        String lstr = loc.toString();
        try {
            lstr = "[" + loc.getWorld().getName() + "] " + fmt.format(loc.getX()) + ", " + fmt.format(loc.getY()) + ", " + fmt.format(loc.getZ());
        } catch (Exception e) {
        }
        return lstr;
    }


    public static Location parseLocation(String strloc) {
        Location loc = null;
        if (strloc.isEmpty()) return null;
        String[] ln = strloc.split(",");
        if (!((ln.length == 4) || (ln.length == 6))) return null;
        World w = Bukkit.getWorld(ln[0]);
        if (w == null) return null;
        for (int i = 1; i < ln.length; i++)
            if (!(ln[i].matches("-?\\d+(\\.\\d+)?"))) return null;
        loc = new Location(w, Double.parseDouble(ln[1]), Double.parseDouble(ln[2]), Double.parseDouble(ln[3]));
        if (ln.length == 6) {
            loc.setYaw(Float.parseFloat(ln[4]));
            loc.setPitch(Float.parseFloat(ln[5]));
        }
        return loc;
    }

    public static String locationToStrLoc(Location loc) {
        if (loc == null) return "";
        StringBuilder sb = new StringBuilder(loc.getWorld().getName()).append(",");
        sb.append(loc.getX()).append(",");
        sb.append(loc.getY()).append(",");
        sb.append(loc.getZ());
        return sb.toString();
    }

    public static List<Location> buildCircle(Location loc, int radius, double step) {
        if (step <= 0) return buildCircle(loc, radius);
        List<Location> circle = new ArrayList<Location>();
        double a = loc.getX();
        double b = loc.getZ();
        for (double x = -radius + step * 2; x <= radius - step * 2; x += step) {
            double z = Math.sqrt(radius * radius - x * x);
            circle.add(new Location(loc.getWorld(), a + x, loc.getY(), b + z));
            circle.add(new Location(loc.getWorld(), a + x, loc.getY(), b - z));
        }

        for (double z = -step * 3; z <= step * 3; z += step) {
            double x = Math.sqrt(radius * radius - z * z);
            circle.add(new Location(loc.getWorld(), a + x, loc.getY(), b + z));
            circle.add(new Location(loc.getWorld(), a - x, loc.getY(), b + z));
        }

        return circle;
    }

    public static List<Location> buildCircle(Location loc, int radius) {
        List<Location> circle = new ArrayList<Location>();
        if (loc == null) return circle;
        if (radius < 1) {
            circle.add(loc.getBlock().getLocation());
            return circle;
        }
        World w = loc.getWorld();
        int x = 0;
        int z = radius;
        int delta = 2 - 2 * radius;
        int error = 0;
        for (; z >= 0; ) {
            circle.add(new Location(w, loc.getBlockX() + x, loc.getY(), loc.getBlockZ() + z));
            circle.add(new Location(w, loc.getBlockX() - x, loc.getY(), loc.getBlockZ() + z));
            circle.add(new Location(w, loc.getBlockX() - x, loc.getY(), loc.getBlockZ() - z));
            circle.add(new Location(w, loc.getBlockX() + x, loc.getY(), loc.getBlockZ() - z));
            error = 2 * (delta + z) - 1;
            if ((delta < 0) && (error <= 0)) {
                ++x;
                delta += 2 * x + 1;
                continue;
            }
            error = 2 * (delta - x) - 1;
            if ((delta > 0) && (error > 0)) {
                --z;
                delta += 1 - 2 * z;
                continue;
            }
            ++x;
            delta += 2 * (x - z);
            --z;
        }
        return circle;
    }

    public static List<Location> buildPlain(Location loc1, Location loc2) {
        List<Location> plain = new ArrayList<Location>();
        if (loc1 == null) return plain;
        if (loc2 == null) return plain;
        if (loc1.getBlock().equals(loc2.getBlock())) {
            plain.add(loc1.getBlock().getLocation());
            return plain;
        }
        for (int x = Math.min(loc1.getBlockX(), loc2.getBlockX()); x <= Math.max(loc1.getBlockX(), loc2.getBlockX()); x++)
            for (int z = Math.min(loc1.getBlockZ(), loc2.getBlockZ()); z <= Math.max(loc1.getBlockZ(), loc2.getBlockZ()); z++)
                plain.add(new Location(loc1.getWorld(), x, loc1.getBlockY(), z));
        return plain;
    }

    public static List<Location> buildPlain(Location loc1, Location loc2, double step) {
        if (step <= 0) return buildPlain(loc1, loc2);
        List<Location> plain = new ArrayList<Location>();
        if (loc1 == null) return plain;
        if (loc2 == null) return plain;
        if (loc1.getBlock().equals(loc2.getBlock())) {
            plain.add(loc1.getBlock().getLocation());
            return plain;
        }
        Location min = new Location(loc1.getWorld(), Math.min(loc1.getX(), loc2.getX()), Math.min(loc1.getY(), loc2.getY()), Math.min(loc1.getZ(), loc2.getZ()));
        Location max = new Location(loc1.getWorld(), Math.max(loc1.getX(), loc2.getX()), Math.max(loc1.getY(), loc2.getY()), Math.max(loc1.getZ(), loc2.getZ()));
        for (double x = min.getX(); x <= max.getX(); x += step)
            for (double z = min.getZ(); z <= max.getZ(); z += step)
                plain.add(new Location(loc1.getWorld(), x, loc1.getBlockY(), z));
        return plain;
    }

    public static List<Location> buildLine(Location loc1, Location loc2, double step) {
        if (step == 0) return buildLine(loc1, loc2);
        List<Location> line = new ArrayList<Location>();
        if (loc1 == null) return line;

        line.add(loc1);
        if (loc2 == null) return line;
        if (!loc1.getWorld().equals(loc2.getWorld())) return line;
        World w = loc1.getWorld();
        double distance = loc1.distance(loc2);
        double stepCount = distance / step;
        if (stepCount < 1) return line;
        double dx = (loc2.getX() - loc1.getX()) / stepCount;
        double dy = (loc2.getY() - loc1.getY()) / stepCount;
        double dz = (loc2.getZ() - loc1.getZ()) / stepCount;
        double length = 0;
        Location nextLoc = loc1;

        while (step > 0 && length <= distance) {
            nextLoc = new Location(w, nextLoc.getX() + dx, nextLoc.getY() + dy, nextLoc.getZ() + dz, nextLoc.getYaw(), nextLoc.getPitch());
            line.add(nextLoc);
            length += step;
        }
        return line;
    }

    public static List<Location> buildCuboid(Location loc1, Location loc2, boolean land, double step) {
        if (step <= 0) return buildCuboid(loc1, loc2, land);
        List<Location> cube = new ArrayList<Location>();
        if (loc1 == null) return cube;
        if (loc2 == null) return cube;
        if (loc1.distance(loc2) < step) {
            cube.add(loc1.getBlock().getLocation());
            return cube;
        }

        Location min = new Location(loc1.getWorld(), Math.min(loc1.getX(), loc2.getX()), Math.min(loc1.getY(), loc2.getY()), Math.min(loc1.getZ(), loc2.getZ()));
        Location max = new Location(loc1.getWorld(), Math.max(loc1.getX(), loc2.getX()), Math.max(loc1.getY(), loc2.getY()), Math.max(loc1.getZ(), loc2.getZ()));
        for (double x = min.getX(); x <= max.getX(); x += step)
            for (double y = min.getY(); y <= max.getY(); y += step)
                for (double z = min.getZ(); z <= max.getZ(); z += step)
                    cube.add(new Location(loc1.getWorld(), x, y, z));
        return cube;
    }

    public static List<Location> buildCuboid(Location loc1, Location loc2, boolean land) {
        List<Location> cube = new ArrayList<Location>();
        if (loc1 == null) return cube;
        if (loc2 == null) return cube;
        if (loc1.getBlock().equals(loc2.getBlock())) {
            cube.add(loc1.getBlock().getLocation());
            return cube;
        }
        for (int x = Math.min(loc1.getBlockX(), loc2.getBlockX()); x <= Math.max(loc1.getBlockX(), loc2.getBlockX()); x++)
            for (int z = Math.min(loc1.getBlockZ(), loc2.getBlockZ()); z <= Math.max(loc1.getBlockZ(), loc2.getBlockZ()); z++)
                for (int y = Math.min(loc1.getBlockY(), loc2.getBlockY()); y <= Math.max(loc1.getBlockY(), loc2.getBlockY()); y++) {
                    Block b = loc1.getWorld().getBlockAt(x, y, z);
                    if (b.getType() != Material.AIR) continue;
                    if (land && b.getRelative(BlockFace.DOWN).getType() == Material.AIR) continue;
                    cube.add(b.getLocation());
                }
        return cube;
    }


    public static List<Location> buildLine(Location loc1, Location loc2) {
        List<Location> line = new ArrayList<Location>();
        if (loc1 == null) return line;
        if (loc2 == null) return line;
        if (loc1.getBlock().equals(loc2.getBlock())) {
            line.add(loc1.getBlock().getLocation());
            return line;
        }
        int dx = Math.max(loc1.getBlockX(), loc2.getBlockX()) - Math.min(loc1.getBlockX(), loc2.getBlockX());
        int dy = Math.max(loc1.getBlockY(), loc2.getBlockY()) - Math.min(loc1.getBlockY(), loc2.getBlockY());
        int dz = Math.max(loc1.getBlockZ(), loc2.getBlockZ()) - Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int x1 = loc1.getBlockX();
        int x2 = loc2.getBlockX();
        int y1 = loc1.getBlockY();
        int y2 = loc2.getBlockY();
        int z1 = loc1.getBlockZ();
        int z2 = loc2.getBlockZ();
        int x = 0;
        int y = 0;
        int z = 0;
        int i = 0;
        int d = 1;
        switch (findHighest(dx, dy, dz)) {
            case 1:
                i = 0;
                d = 1;
                if (x1 > x2) d = -1;
                x = loc1.getBlockX();
                do {
                    i++;
                    y = y1 + ((x - x1) * (y2 - y1)) / (x2 - x1);
                    z = z1 + ((x - x1) * (z2 - z1)) / (x2 - x1);
                    line.add(new Location(loc1.getWorld(), x, y, z));
                    x = x + d;
                } while (i <= (Math.max(x1, x2) - Math.min(x1, x2)));
                break;
            case 2:
                i = 0;
                d = 1;
                if (y1 > y2) d = -1;
                y = loc1.getBlockY();
                do {
                    i++;
                    x = x1 + ((y - y1) * (x2 - x1)) / (y2 - y1);
                    z = z1 + ((y - y1) * (z2 - z1)) / (y2 - y1);
                    line.add(new Location(loc1.getWorld(), x, y, z));
                    y = y + d;
                } while (i <= (Math.max(y1, y2) - Math.min(y1, y2)));
                break;
            case 3:
                i = 0;
                d = 1;
                if (z1 > z2) d = -1;
                z = loc1.getBlockZ();
                do {
                    i++;
                    y = y1 + ((z - z1) * (y2 - y1)) / (z2 - z1);
                    x = x1 + ((z - z1) * (x2 - x1)) / (z2 - z1);
                    line.add(new Location(loc1.getWorld(), x, y, z));
                    z = z + d;
                } while (i <= (Math.max(z1, z2) - Math.min(z1, z2)));
                break;
        }
        return line;
    }

    private static int findHighest(double x, double y, double z) {
        if ((x >= y) && (x >= z)) return 1;
        if ((y >= x) && (y >= z)) return 2;
        return 3;
    }

    public static List<Location> refilterLocations(List<Location> locs, int amount) {
        if (amount <= 0) return locs;
        if (locs.size() <= amount) return locs;
        locs = sort(locs);
        List<Location> out = new ArrayList<Location>();
        int step = locs.size() / amount;
        for (int i = locs.size() - 1; i >= 0; i = i - step)
            out.add(locs.get(i));
        return out;
    }


    public static List<Location> sort(List<Location> locs) {
        int i = 0;
        int j = locs.size() - 1;
        int x = locs.get(j / 2).hashCode();
        do {
            while (locs.get(i).hashCode() < x) ++i;
            while (locs.get(j).hashCode() > x) --j;

            if (i <= j) {
                Location temp = locs.get(i);
                locs.set(i, locs.get(j));
                locs.set(j, temp);
                i++;
                j--;
            }

        } while (i <= j);
        return locs;
    }


    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player p = event.getPlayer();
        if (!Wand.hasWand(p)) return;
        if (p.getItemInHand() == null) return;
        if (!compareItemStr(p.getItemInHand(), plg.wand_item)) return;
        Wand.toggleEffect(p, event.getClickedBlock());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Wand.clearWand(event.getPlayer());
        UpdateChecker.updateMsg(event.getPlayer());
    }


    public static Map<String, String> processLocation(CommandSender sender, Map<String, String> params) {
        Map<String, String> newparams = new HashMap<String, String>();
        if (!params.containsKey("loc")) params.put("loc", "view");
        if (!params.containsKey("loc2")) params.put("loc2", "eye");

        if ((sender instanceof Player)) {
            Player p = (Player) sender;
            for (String key : params.keySet()) {
                //newparams.put(key, params.get(key));
                String value = params.get(key);
                if (key.equalsIgnoreCase("loc") || key.equalsIgnoreCase("loc2")) {
                    if (value.equalsIgnoreCase("wg1") || value.equalsIgnoreCase("wg2")) {
                        if (WEGLib.isWE()) {
                            List<Location> locs = WEGLib.getSelectionLocations(p);
                            if (locs.size() > 0) {
                                if (value.equalsIgnoreCase("wg1")) value = Util.locationToStrLoc(locs.get(0));
                                if (value.equalsIgnoreCase("wg2")) value = Util.locationToStrLoc(locs.get(1));
                            }
                        }
                    } else if (value.equalsIgnoreCase("here")) value = Util.locationToStrLoc(p.getLocation());
                    else if (value.equalsIgnoreCase("view"))
                        value = Util.locationToStrLoc(getTargetBlockFaceLocation(p));
                    else if (value.equalsIgnoreCase("eye")) value = Util.locationToStrLoc(p.getEyeLocation());
                }
                newparams.put(key, value);
            }
            return newparams;
        }
        return params;
    }

    @SuppressWarnings("deprecation")
    public static Location getTargetBlockFaceLocation(Player p) {
        List<Block> blocks = p.getLineOfSight((HashSet<Byte>) null, 100);
        if (blocks.isEmpty()) return null;
        if (blocks.size() <= 2) return blocks.get(0).getLocation();
        return blocks.get(blocks.size() - 2).getLocation();
    }

}

