package me.fromgate.playeffect;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;


public class Util extends FGUtilCore implements Listener {
    PlayEffect plg;
    
    private static PlayEffect plg(){
        return PlayEffect.instance;
    }

    public Util(PlayEffect plugin, boolean savelng, String language, String plgcmd){
        super (plugin, savelng, language, plgcmd,"playeffect");
        this.plg = plugin;
        InitCmd();
        initMessages();
        if (savelng) this.SaveMSG();
    }


    private void initMessages(){
        addMSG("msg_outdated", "%1% is outdated!");
        addMSG("msg_pleasedownload", "Please download new version (%1%) from ");
        addMSG("hlp_help", "Help");
        addMSG("hlp_thishelp", "%1% - this help");
        addMSG("hlp_execcmd", "%1% - execute command");
        addMSG("hlp_typecmd", "Type %1% - to get additional help");
        addMSG("hlp_typecmdpage", "Type %1% - to see another page of this help");
        addMSG("hlp_commands", "Command list:");
        addMSG("hlp_cmdparam_command", "command");
        addMSG("hlp_cmdparam_page", "page");
        addMSG("hlp_cmdparam_parameter", "parameter");
        addMSG("cmd_unknown", "Unknown command: %1%");
        addMSG("cmd_cmdpermerr", "Something wrong (check command, permissions)");
        addMSG("enabled", "enabled");
        addMSG("disabled", "disabled");
        addMSG("lst_title", "String list:");
        addMSG("lst_footer", "Page: [%1% / %2%]");
        addMSG("lst_listisempty", "List is empty");
        addMSG("msg_config", "Configuration");
        addMSG("cfgmsg_general_check-updates", "Check updates: %1%");
        addMSG("cfgmsg_general_language", "Language: %1%");
        addMSG("cfgmsg_general_language-save", "Save translation file: %1%");
        addMSG("hlp_list", "%1% - list of placed effects");
        addMSG("hlp_set", "%1% - setup effect");
        addMSG("hlp_smoke", "%1% - play Smoke effect");
        addMSG("hlp_signal", "%1% - play Ender Signal effect");
        addMSG("hlp_potion", "%1% - play Potion break effect");
        addMSG("hlp_flame", "%1% - play Flame effect");
        addMSG("hlp_explosion", "%1% - play Explosion effect");
        addMSG("hlp_eye", "%1% - play Ender eye effect");
        addMSG("hlp_lightning", "%1% - play Lightning effect");
        addMSG("hlp_note", "%1% - play Note effect");
        addMSG("hlp_portal", "%1% - play Portal effect");
        addMSG("hlp_cloud", "%1% - play Smoke effect");
        addMSG("hlp_hugeexplosion", "%1% - play Huge explosion effect");
        addMSG("hlp_largeexplode", "%1% - play Large explode effect");
        addMSG("hlp_spark", "%1% - play Spark effect");
        addMSG("hlp_bubble", "%1% - play Bubble effect (works only underwater)");
        addMSG("hlp_suspend", "%1% - play Suspend effect (works only underwater)");
        addMSG("hlp_depthsuspend", "%1% - play Depth suspend effect (I don''know what is it)");
        addMSG("hlp_townaura", "%1% - play Town aura effect");
        addMSG("hlp_crit", "%1% - play Crit effect");
        addMSG("hlp_magiccrit", "%1% - play Magic crit effect");
        addMSG("hlp_mobspell", "%1% - play Mob spell effect");
        addMSG("hlp_mobspellambient", "%1% - play Mob spell (ambient) effect");
        addMSG("hlp_spell", "%1% - play Spell effect");
        addMSG("hlp_instantspell", "%1% - play Instant spell effect");
        addMSG("hlp_witchmagic", "%1% - play Witch magic effect");
        addMSG("hlp_runes", "%1% - play Runes (magic book) effect");
        addMSG("hlp_explode", "%1% - play explode effect");
        addMSG("hlp_flamenew", "%1% - play Flame (new) effect");
        addMSG("hlp_lava", "%1% - play Lava effect");
        addMSG("hlp_footstep", "%1% - play Footstep effect");
        addMSG("hlp_splash", "%1% - play Splash effect");
        addMSG("hlp_largesmoke", "%1% - play Large smoke effect");
        addMSG("hlp_reddust", "%1% - play Reddust effect");
        addMSG("hlp_snowball", "%1% - play Snowball effect");
        addMSG("hlp_dripwater", "%1% - play Drip water effect");
        addMSG("hlp_driplava", "%1% - play Drip lava effect");
        addMSG("hlp_snowshovel", "%1% - play Snow showel effect");
        addMSG("hlp_slime", "%1% - play Slime effect");
        addMSG("hlp_heart", "%1% - play Heart effect");
        addMSG("hlp_angry", "%1% - play Angry villager effect");
        addMSG("hlp_happy", "%1% - play Happy villager effect");
        addMSG("hlp_tilecrack", "%1% - play Tile crack effect");
        addMSG("hlp_iconcrack", "%1% - play Icon crack effect");
        addMSG("hlp_firework", "%1% - play Firework effect");
        addMSG("hlp_sound", "%1% - play Sound effect");
        addMSG("hlp_song", "%1% - play Song effect");
        addMSG("msg_effectset", "Effect was succesfully set");
        addMSG("msg_unknowneffect", "Unknown effect type %1%");
        addMSG("hlp_info", "%1% - show info about defined effect");
        addMSG("hlp_remove", "%1% - remove static effect");
        addMSG("hlp_wand", "%1% - to enable/disable wand mode");
        addMSG("msg_wandenabled", "Wand mode enabled");
        addMSG("msg_seteffect", "Effect set %1% (%2%)");
        addMSG("msg_removed", "Effect %1% removed");
        addMSG("hlp_show", "Unknown message (hlp_show)");
        addMSG("hlp_hide", "Unknown message (hlp_hide)");
        addMSG("hlp_resart", "Unknown message (hlp_resart)");
        addMSG("hlp_reload", "Unknown message (hlp_reload)");
        addMSG("msg_efflist", "Effect list");
        addMSG("msg_efflistempty", "Effect list is empty");
        addMSG("msg_efflist", "Effect list");
        addMSG("msg_efflistempty", "Effect list is empty");
        addMSG("hlp_show", "%1% - show effect(s)");
        addMSG("hlp_show", "%1% - hide effect(s)");
        addMSG("hlp_resart", "%1% - restart static effects");
        addMSG("hlp_reload", "%1% - reload configuration");
        addMSG("hlp_check", "%1% - find effect around you");
        addMSG("msg_restarted", "All effects restarted!");
        addMSG("msg_reloaded", "All effects reloaded and restarted!");
        addMSG("msg_removefailed", "Failed to remove effect %1%");
        addMSG("msg_consoleneedcoord", "You must define effect location when executing command play <effect> by console");
        addMSG("msg_wrongeffect", "Failed parse effect name %1%");
    }
    private void InitCmd(){
        cmds.clear();
        cmdlist = "";
        addCmd("help", "config", "hlp_thishelp","&3/playeffect help [page]",'b',true);
        addCmd("list", "config", "hlp_list","&3/playeffect list [page]",'b',true);
        addCmd("info", "config", "hlp_info","&3/playeffect info <effect id | number>",'b',true);
        addCmd("remove", "config", "hlp_remove","&3/playeffect remove <effect number>",'b',true);
        addCmd("set", "set", "hlp_set","&3/playeffect set <effect> [param]",'b');
        addCmd("wand", "wand", "hlp_wand","&3/playeffect wand <effect> [param]",'b');
        addCmd("show", "show", "hlp_show","&3/playeffect show <effect id>",'b',true);
        addCmd("hide", "show", "hlp_hide","&3/playeffect hide <effect id>",'b',true);
        addCmd("check", "config", "hlp_check","&3/playeffect check [radius]",'b');
        addCmd("restart", "config", "hlp_resart","&3/playeffect restart",'b',true);
        addCmd("reload", "config", "hlp_reload","&3/playeffect reload",'b',true);

        for (VisualEffect ve : VisualEffect.values()){
            if (ve == VisualEffect.BASIC) continue;
            String ven = ve.name().toLowerCase();
            addCmd(ven, "play", "hlp_"+ven,"&3/playeffect "+ven+" [parameters]",'b',true);
        }
    }

    public static String locationToString(Location loc){
        if (loc == null) return "";
        DecimalFormat fmt = new DecimalFormat("####0.##");
        String lstr = loc.toString();
        try {
            lstr = "["+loc.getWorld().getName()+"] "+fmt.format(loc.getX())+", "+fmt.format(loc.getY())+", "+fmt.format(loc.getZ());
        } catch (Exception e){
        }
        return lstr;
    }


    public static Location parseLocation (String strloc){
        Location loc = null;
        if (strloc.isEmpty()) return null;
        String [] ln = strloc.split(",");
        if (!((ln.length==4)||(ln.length==6))) return null;
        World w = Bukkit.getWorld(ln[0]);
        if (w==null) return null;
        for (int i = 1; i<ln.length; i++)
            if (!(ln[i].matches("-?[0-9]+[0-9]*\\.[0-9]+")||ln[i].matches("-?[1-9]+[0-9]*"))) return null;
        loc = new Location (w, Double.parseDouble(ln[1]),Double.parseDouble(ln[2]),Double.parseDouble(ln[3]));
        if (ln.length==6){
            loc.setYaw(Float.parseFloat(ln[4]));
            loc.setPitch(Float.parseFloat(ln[5]));
        }
        return loc;
    }

    public static String locationToStrLoc(Location loc){
        if (loc == null) return "";
        return loc.getWorld().getName()+","+
        loc.getBlockX()+","+
        loc.getBlockY()+","+
        loc.getBlockZ();
    }

    public static Color colorByName(String colorname, Color defcolor){
        if (colorname.equalsIgnoreCase("random")) return Color.fromRGB(plg().u.random.nextInt(255), plg().u.random.nextInt(255), plg().u.random.nextInt(255));
        Color [] clr = {Color.WHITE, Color.SILVER, Color.GRAY, Color.BLACK, 
                Color.RED, Color.MAROON, Color.YELLOW, Color.OLIVE,
                Color.LIME, Color.GREEN, Color.AQUA, Color.TEAL,
                Color.BLUE,Color.NAVY,Color.FUCHSIA,Color.PURPLE};
        String [] clrs = {"WHITE","SILVER", "GRAY", "BLACK", 
                "RED", "MAROON", "YELLOW", "OLIVE",
                "LIME", "GREEN", "AQUA", "TEAL",
                "BLUE","NAVY","FUCHSIA","PURPLE"};
        for (int i = 0; i<clrs.length;i++)
            if (colorname.equalsIgnoreCase(clrs[i])) return clr[i];

        if (colorname.contains(",")){
            String [] ln = colorname.split(",");
            if ((ln.length==3)&&plg().u.isInteger(ln[0],ln[1],ln[2]))
                return Color.fromRGB(Integer.parseInt(ln[0]), Integer.parseInt(ln[1]), Integer.parseInt(ln[2]));
        }
        return defcolor;
    }

    public static List<Location> buildCircle (Location loc, int radius){
        List<Location> circle = new ArrayList<Location>();
        World w = loc.getWorld();
        int x = 0;
        int z = radius;
        int delta = 2-2*radius;
        int error =0;
        for (;z>=0;){
            circle.add(new Location(w,loc.getBlockX()+x,loc.getY(),loc.getBlockZ()+z));
            circle.add(new Location(w,loc.getBlockX()-x,loc.getY(),loc.getBlockZ()+z));
            circle.add(new Location(w,loc.getBlockX()-x,loc.getY(),loc.getBlockZ()-z));
            circle.add(new Location(w,loc.getBlockX()+x,loc.getY(),loc.getBlockZ()-z));
            error = 2 *(delta+z)-1;
            if ((delta<0)&&(error<=0)){
                ++x;
                delta +=2*x+1;
                continue;
            }
            error = 2*(delta-x)-1;
            if ((delta>0)&&(error>0)){
                --z;
                delta += 1-2*z;
                continue;
            }
            ++x;
            delta += 2 * (x - z);
            --z;
        }
        return circle;
    }

    public static List<Location> buildPlain (Location loc1, Location loc2){
        List<Location> plain = new ArrayList<Location>();
        if (loc1 == null) return plain;
        if (loc2 == null) return plain;
        for (int x = Math.min(loc1.getBlockX(), loc2.getBlockX()); x<=Math.max(loc1.getBlockX(), loc2.getBlockX());x++)
            for (int z = Math.min(loc1.getBlockZ(), loc2.getBlockZ()); z<=Math.max(loc1.getBlockZ(), loc2.getBlockZ());z++)
                plain.add(new Location (loc1.getWorld(), x, loc1.getBlockY(), z));
        return plain;
    }

    public static List<Location> buildCuboid(Location loc1, Location loc2, boolean land){
        List<Location> cube = new ArrayList<Location>();
        if (loc1 == null) return cube;
        if (loc2 == null) return cube;

        for (int x = Math.min(loc1.getBlockX(), loc2.getBlockX()); x<=Math.max(loc1.getBlockX(), loc2.getBlockX());x++)
            for (int z = Math.min(loc1.getBlockZ(), loc2.getBlockZ()); z<=Math.max(loc1.getBlockZ(), loc2.getBlockZ());z++)
                for (int y = Math.min(loc1.getBlockY(), loc2.getBlockY()); y<=Math.max(loc1.getBlockY(), loc2.getBlockY());y++){
                    Block b = loc1.getWorld().getBlockAt(x, y, z); 
                    if (b.getType()!=Material.AIR) continue;
                    if (land&&b.getRelative(BlockFace.DOWN).getType()==Material.AIR) continue;
                    cube.add(b.getLocation());
                }
        return cube;
    }

    public static List<Location> buildLine (Location loc1, Location loc2){
        List<Location> line = new ArrayList<Location>();
        int dx = Math.max(loc1.getBlockX(), loc2.getBlockX())-Math.min(loc1.getBlockX(), loc2.getBlockX());
        int dy = Math.max(loc1.getBlockY(), loc2.getBlockY())-Math.min(loc1.getBlockY(), loc2.getBlockY());
        int dz = Math.max(loc1.getBlockZ(), loc2.getBlockZ())-Math.min(loc1.getBlockZ(), loc2.getBlockZ());
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
        int d=1;
        switch (findHighest(dx,dy,dz)){
        case 1:
            i = 0;
            d=1;
            if (x1>x2) d = -1;
            x= loc1.getBlockX();
            do {
                i++;
                y=y1+((x-x1)*(y2-y1))/(x2-x1);
                z=z1+((x-x1)*(z2-z1))/(x2-x1);
                line.add(new Location (loc1.getWorld(),x,y,z));
                x= x+d;
            } while (i<=(Math.max(x1, x2)-Math.min(x1, x2)));
            break;
        case 2: 
            i = 0;
            d=1;
            if (y1>y2) d = -1;
            y= loc1.getBlockY();
            do {
                i++;
                x=x1+((y-y1)*(x2-x1))/(y2-y1);
                z=z1+((y-y1)*(z2-z1))/(y2-y1);
                line.add(new Location (loc1.getWorld(),x,y,z));
                y= y+d;
            } while (i<=(Math.max(y1, y2)-Math.min(y1, y2)));
            break;
        case 3: 
            i = 0;
            d=1;
            if (z1>z2) d = -1;
            z= loc1.getBlockZ();
            do {
                i++;
                y=y1+((z-z1)*(y2-y1))/(z2-z1);
                x=x1+((z-z1)*(x2-x1))/(z2-z1);
                line.add(new Location (loc1.getWorld(),x,y,z));
                z= z+d;
            } while (i<=(Math.max(z1, z2)-Math.min(z1, z2)));
            break;
        }
        return line;
    }

    private static int findHighest(int x, int y, int z){
        if ((x>=y)&&(x>=z)) return 1;
        if ((y>=x)&&(y>=z)) return 2;
        return 3;
    }

    public static List<Location> refilterLocations (List<Location> locs, int amount){
        if (amount<=0) return locs;
        if (locs.size()<=amount) return locs;
        locs = sort (locs);
        List<Location> out = new ArrayList<Location>();
        int step = locs.size()/amount;
        for (int i = locs.size()-1; i>=0;i=i-step)
            out.add(locs.get(i));
        return out;
    }



    public static List<Location> sort (List<Location> locs){
        int i = 0;
        int j = locs.size()-1;
        int x = locs.get(j/2).hashCode(); 
        do {
            while (locs.get(i).hashCode()<x) ++i;
            while (locs.get(j).hashCode()>x) --j;

            if (i<=j){
                Location temp = locs.get(i);
                locs.set(i, locs.get(j));
                locs.set(j,temp);
                i++; j--;
            }

        } while (i<=j);
        return locs;
    }

    /*public static Long timeToTicks(Long time){
        return Math.max(1, (time/50));
    }*/


    @EventHandler(priority=EventPriority.NORMAL)
    public void onPlayerInteract (PlayerInteractEvent event){
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player p = event.getPlayer();
        if (!Wand.hasWand(p)) return;
        if (p.getItemInHand() == null) return;
        if (!compareItemStr(p.getItemInHand(), plg.wand_item)) return;
        Wand.toggleEffect(p, event.getClickedBlock());
    }

    @EventHandler(priority=EventPriority.NORMAL)
    public void onPlayerJoin (PlayerJoinEvent event){
        Wand.clearWand(event.getPlayer());
        plg.u.updateMsg(event.getPlayer());
    }

}

