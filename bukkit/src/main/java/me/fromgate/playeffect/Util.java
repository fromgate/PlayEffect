package me.fromgate.playeffect;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.text.DecimalFormat;
import java.util.*;


public class Util {
    private static PlayEffectPlugin plg;
    private static Random random;

    public static void init(PlayEffectPlugin plugin){
        plg = plugin;
        random = new Random();
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
        for (int i = 1; i < ln.length; i++) {
            if (!(ln[i].matches("-?\\d+(\\.\\d+)?"))) return null;
        }
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
        for (int i = locs.size() - 1; i >= 0; i = i - step) {
            out.add(locs.get(i));
        }
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

    public static Map<String, String> processLocation(CommandSender sender, Map<String, String> params) {
        Map<String, String> newparams = new HashMap<String, String>();
        if (!params.containsKey("loc")) params.put("loc", "view");
        if (!params.containsKey("loc2")) params.put("loc2", "eye");

        if ((sender instanceof Player)) {
            Player p = (Player) sender;
            for (String key : params.keySet()) {
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
        List<Block> blocks = p.getLineOfSight(null, 100);
        if (blocks.isEmpty()) return null;
        if (blocks.size() <= 2) return blocks.get(0).getLocation();
        return blocks.get(blocks.size() - 2).getLocation();
    }

    public static boolean compareItemStr(int item_id, int item_data, String itemstr) {
        return compareItemStrIgnoreName(item_id, item_data, 1, itemstr);
    }

    // Надо использовать маску: id:data*amount, id:data, id*amount

    @SuppressWarnings("deprecation")
    public static boolean compareItemStr(ItemStack item, String str) {
        String itemstr = str;
        String name = "";
        if (itemstr.contains("$")) {
            name = str.substring(0, itemstr.indexOf("$"));
            name = ChatColor.translateAlternateColorCodes('&', name.replace("_", " "));
            itemstr = str.substring(name.length() + 1);
        }
        if (itemstr.isEmpty()) return false;
        if (!name.isEmpty()) {
            String iname = item.hasItemMeta() ? item.getItemMeta().getDisplayName() : "";
            if (!name.equals(iname)) return false;
        }
        return compareItemStrIgnoreName(item.getTypeId(), item.getDurability(), item.getAmount(), itemstr); // ;compareItemStr(item, itemstr);
    }


    @SuppressWarnings("deprecation")
    public static boolean compareItemStrIgnoreName(int item_id, int item_data, int item_amount, String itemstr) {
        if (!itemstr.isEmpty()) {
            int id = -1;
            int amount = 1;
            int data = -1;
            String[] si = itemstr.split("\\*");
            if (si.length > 0) {
                if ((si.length == 2) && si[1].matches("[1-9]+[0-9]*")) amount = Integer.parseInt(si[1]);
                String ti[] = si[0].split(":");
                if (ti.length > 0) {
                    if (ti[0].matches("[0-9]*")) id = Integer.parseInt(ti[0]);
                    else {
                        try {
                            id = Material.getMaterial(ti[0]).getId();
                        } catch (Exception e) {
                            //logOnce("unknownitem" + ti[0], "Unknown item: " + ti[0]);
                        }
                    }
                    if ((ti.length == 2) && (ti[1]).matches("[0-9]*")) data = Integer.parseInt(ti[1]);
                    return ((item_id == id) && ((data < 0) || (item_data == data)) && (item_amount >= amount));
                }
            }
        }
        return false;
    }


    public static boolean rollDiceChance(int chance) {
        return (random.nextInt(100) < chance);
    }

    public static int tryChance(int chance) {
        return random.nextInt(chance);
    }

    public static int getRandomInt(int maxvalue) {
        return random.nextInt(maxvalue);
    }

    public static boolean isWordInList(String word, String str) {
        String[] ln = str.split(",");
        if (ln.length > 0)
            for (int i = 0; i < ln.length; i++)
                if (ln[i].equalsIgnoreCase(word)) return true;
        return false;
    }

    public static boolean isIntegerGZ(String str) {
        return (str.matches("[1-9]+[0-9]*"));
    }

    @SuppressWarnings("deprecation")
    public static ItemStack parseItemStack(String itemstr) {
        if (itemstr.isEmpty()) return null;

        String istr = itemstr;
        String enchant = "";
        String name = "";

        if (istr.contains("$")) {
            name = istr.substring(0, istr.indexOf("$"));
            istr = istr.substring(name.length() + 1);
        }
        if (istr.contains("@")) {
            enchant = istr.substring(istr.indexOf("@") + 1);
            istr = istr.substring(0, istr.indexOf("@"));
        }
        int id = -1;
        int amount = 1;
        short data = 0;
        String[] si = istr.split("\\*");

        if (si.length > 0) {
            if (si.length == 2) amount = Math.max(getMinMaxRandom(si[1]), 1);
            String ti[] = si[0].split(":");
            if (ti.length > 0) {
                if (ti[0].matches("[0-9]*")) id = Integer.parseInt(ti[0]);
                else {
                    Material m = Material.getMaterial(ti[0].toUpperCase());
                    if (m == null) {
                        //logOnce("wrongitem" + ti[0], "Could not parse item material name (id) " + ti[0]);
                        return null;
                    }
                    id = m.getId();
                }
                if ((ti.length == 2) && (ti[1]).matches("[0-9]*")) data = Short.parseShort(ti[1]);
                ItemStack item = new ItemStack(id, amount, data);
                if (!enchant.isEmpty()) {
                    item = setEnchantments(item, enchant);
                }
                if (!name.isEmpty()) {
                    ItemMeta im = item.getItemMeta();
                    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name.replace("_", " ")));
                    item.setItemMeta(im);
                }
                return item;
            }
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public static ItemStack setEnchantments(ItemStack item, String enchants) {
        ItemStack i = item.clone();
        if (enchants.isEmpty()) return i;
        String[] ln = enchants.split(",");
        for (String ec : ln) {
            if (ec.isEmpty()) continue;
            Color clr = colorByName(ec);
            if (clr != null) {
                if (isIdInList(item.getTypeId(), "298,299,300,301")) {
                    LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
                    meta.setColor(clr);
                    i.setItemMeta(meta);
                }
            } else {
                String ench = ec;
                int level = 1;
                if (ec.contains(":")) {
                    ench = ec.substring(0, ec.indexOf(":"));
                    level = Math.max(1, getMinMaxRandom(ec.substring(ench.length() + 1)));
                }
                Enchantment e = Enchantment.getByName(ench.toUpperCase());
                if (e == null) continue;
                i.addUnsafeEnchantment(e, level);
            }
        }
        return i;
    }

    public static Color colorByName(String colorname) {
        Color[] clr = {Color.WHITE, Color.SILVER, Color.GRAY, Color.BLACK,
                Color.RED, Color.MAROON, Color.YELLOW, Color.OLIVE,
                Color.LIME, Color.GREEN, Color.AQUA, Color.TEAL,
                Color.BLUE, Color.NAVY, Color.FUCHSIA, Color.PURPLE};
        String[] clrs = {"WHITE", "SILVER", "GRAY", "BLACK",
                "RED", "MAROON", "YELLOW", "OLIVE",
                "LIME", "GREEN", "AQUA", "TEAL",
                "BLUE", "NAVY", "FUCHSIA", "PURPLE"};
        for (int i = 0; i < clrs.length; i++)
            if (colorname.equalsIgnoreCase(clrs[i])) return clr[i];
        return null;
    }

    public static int getMinMaxRandom(String minmaxstr) {
        int min = 0;
        int max = 0;
        String strmin = minmaxstr;
        String strmax = minmaxstr;
        if (minmaxstr.contains("-")) {
            strmin = minmaxstr.substring(0, minmaxstr.indexOf("-"));
            strmax = minmaxstr.substring(minmaxstr.indexOf("-") + 1);
        }
        if (strmin.matches("[1-9]+[0-9]*")) min = Integer.parseInt(strmin);
        max = min;
        if (strmax.matches("[1-9]+[0-9]*")) max = Integer.parseInt(strmax);
        if (max > min) return min + tryChance(1 + max - min);
        else return min;
    }

    public static boolean isIdInList(int id, String str) {
        if (!str.isEmpty()) {
            String[] ln = str.split(",");
            if (ln.length > 0)
                for (int i = 0; i < ln.length; i++)
                    if ((!ln[i].isEmpty()) && ln[i].matches("[0-9]*") && (Integer.parseInt(ln[i]) == id)) return true;
        }
        return false;
    }
}

