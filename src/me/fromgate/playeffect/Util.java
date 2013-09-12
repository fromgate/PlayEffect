package me.fromgate.playeffect;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;


public class Util extends FGUtilCore  {
    PlayEffect plg;

    public Util(PlayEffect plugin, boolean vcheck, boolean savelng, String language, String devbukkitname, String version_name, String plgcmd, String px){
        super (plugin, vcheck, savelng, language, devbukkitname, version_name, plgcmd, px);
        this.plg = plugin;
        InitCmd();
        if (savelng) this.SaveMSG();
    }



    public void InitCmd(){
        cmds.clear();
        cmdlist = "";
        addCmd("help", "config", "hlp_thishelp","&3/playeffect help [page]",'b');
        addCmd("test", "config", "hlp_test","&3/playeffect test",'b');
        addCmd("set", "config", "hlp_set","&3/playeffect set <effect> [param]",'b');
        for (VisualEffect ve : VisualEffect.values()){
            if (ve == VisualEffect.BASIC) continue;
            String ven = ve.name().toLowerCase();
            addCmd(ven, ven, "hlp_"+ven,"&3/playeffect "+ven+" [location] [parameter:value]",'b');
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
        /* trimDouble(loc.getX())+","+
        trimDouble(loc.getY())+","+
        trimDouble(loc.getZ())+","+
        (float)trimDouble(loc.getYaw())+","+
        (float)trimDouble(loc.getPitch());*/
    }
    /*public static double trimDouble(double d){
        int i = (int) (d*1000);
        return ((double)i)/1000;
    }*/

    public static Color colorByName(String colorname, Color defcolor){
        if (colorname.equalsIgnoreCase("random")) return Color.fromRGB(PlayEffect.instance.u.random.nextInt(255), PlayEffect.instance.u.random.nextInt(255), PlayEffect.instance.u.random.nextInt(255));
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
            if ((ln.length==3)&&PlayEffect.instance.u.isInteger(ln[0],ln[1],ln[2]))
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
    
    public static Long timeToTicks(Long time){
        return Math.max(1, (time/50));
    }
    
    public static Long parseTime(String time){
        int hh = 0; // часы
        int mm = 0; // минуты
        int ss = 0; // секунды
        int tt = 0; // тики
        int ms = 0; // миллисекунды
        if (PlayEffect.instance.u.isInteger(time)){
            ss = Integer.parseInt(time);
        } else if (time.matches("^[0-5][0-9]:[0-5][0-9]$")){
            String [] ln = time.split(":");
            if (PlayEffect.instance.u.isInteger(ln[0])) mm = Integer.parseInt(ln[0]);
            if (PlayEffect.instance.u.isInteger(ln[1])) ss = Integer.parseInt(ln[1]);
        } else if (time.matches("^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]$")){
            String [] ln = time.split(":");
            if (PlayEffect.instance.u.isInteger(ln[0])) hh = Integer.parseInt(ln[0]);
            if (PlayEffect.instance.u.isInteger(ln[1])) mm = Integer.parseInt(ln[1]);
            if (PlayEffect.instance.u.isInteger(ln[2])) ss = Integer.parseInt(ln[2]);
        } else if (time.endsWith("ms")) {
            String s = time.replace("ms", "");
            if (PlayEffect.instance.u.isInteger(s)) ms = Integer.parseInt(s);
        } else if (time.endsWith("h")) {
            String s = time.replace("h", "");
            if (PlayEffect.instance.u.isInteger(s)) hh = Integer.parseInt(s);
        } else if (time.endsWith("m")) {
            String s = time.replace("m", "");
            if (PlayEffect.instance.u.isInteger(s)) mm = Integer.parseInt(s);
        } else if (time.endsWith("s")) {
            String s = time.replace("s", "");
            if (PlayEffect.instance.u.isInteger(s)) ss = Integer.parseInt(s);
        } else if (time.endsWith("t")) {
            String s = time.replace("t", "");
            if (PlayEffect.instance.u.isInteger(s)) tt = Integer.parseInt(s);
        }

        /*
        ms = ms
        ticks = tt*50
        sec = ss*1000
        min = mm*1000*60 = 60000
        hour == hh*60*60*1000 =3600000
         */
        return (long) ((hh*3600000)+(mm*60000)+(ss*1000)+(tt*50)+ms);
    }

    
}

