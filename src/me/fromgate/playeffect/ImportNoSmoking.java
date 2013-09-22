package me.fromgate.playeffect;

import java.io.File;

import me.fromgate.playeffect.effect.BasicEffect;

import org.bukkit.configuration.file.YamlConfiguration;

public class ImportNoSmoking {

    private static PlayEffect plg(){
        return PlayEffect.instance;
    }
    // Import NoSmoking! v0.0.7 smoke (effect) points

    public static void loadSmokePoints(){
        int count = 0;
        try{
            File f = new File (plg().getDataFolder()+File.separator+"smokepoints.yml");
            YamlConfiguration spl = new YamlConfiguration();
            if (!f.exists()) return;
            spl.load(f);
            for (String key : spl.getKeys(false)){
                if (!key.equalsIgnoreCase("point-list-version")){
                    String id = "id:"+Effects.getId(spl.getString(key+".id"));
                    String strloc = "loc:"+spl.getString(key+".world")+","+spl.getInt(key+".x")+","+spl.getInt(key+".y")+","+spl.getInt(key+".z");
                    VisualEffect ve = importSmokeType(spl.getInt(key+".effect.type",100));
                    if (ve == null) continue;
                    String time = "time:"+Integer.toString(5*spl.getInt(key+".effect.rate",1))+"t";
                    //boolean enabled = spl.getBoolean(key+".active",true);
                    String param = importSmokeParam (ve, spl.getInt(key+".effect.param",0));
                    param = (param.isEmpty() ? "" : param+" ")+strloc+" "+time+" "+id;
                    BasicEffect be = Effects.createEffect(ve, param);
                    Effects.createStaticEffect(be, true);
                    count++;
                }
            }
            File f2 = new File (plg().getDataFolder()+File.separator+"smokepoints.yml.old");
            f.renameTo(f2);
        } catch (Exception e){
            e.printStackTrace();
        }
       
       if (count>0) plg().u.log("Imported "+count+" effects from NoSmoking! configuration file (smokepoints.yml)");

    }

    private static String importSmokeParam (VisualEffect ve, int param){
        switch (ve){
        case SMOKE: return "wind:"+importDirWind(param);
        case POTION: return "param:"+Integer.toString(param);
        case SOUND: return "type:"+importSound(param);
        case SONG: return "disc:"+importSong(param);
        case LIGHTNING: return "lchance:"+importLightChance(param)+" mode:"+importLightMode(param);
        case LARGEEXPLODE: return "num:5 offset:0.8 speed:3";
        default: return "";
        }
    }

    private static String importSong(int param) {
        switch (param){
        case 0: return "DISK13";
        case 1: return "CAT";
        case 2: return "BLOCKS";
        case 3: return "CHIRP";
        case 4: return "FAR";
        case 5: return "MALL";
        case 6: return "MELLOHI";
        case 7: return "STAL";
        case 8: return "STRAD";
        case 9: return "WARD";
        case 10: return "DISC11";
        case 11: return "WAIT";
        }
        return "WAIT";
    }

    private static VisualEffect importSmokeType(int smoke){
        //String [] effects = {"smoke","flame","signal","potion","pearl","eye","song","sfx", "light", "explosion"};
        switch (smoke){
        case 0: return VisualEffect.SMOKE;
        case 1: return VisualEffect.FLAME;
        case 2: return VisualEffect.SIGNAL;
        case 3: return VisualEffect.POTION;
        case 4: return VisualEffect.PORTAL;
        case 5: return VisualEffect.EYE;
        case 6: return VisualEffect.SONG; //ммммм.... мммммм....
        case 7: return VisualEffect.SOUND;
        case 8: return VisualEffect.LIGHTNING;
        case 9: return VisualEffect.LARGEEXPLODE;
        }
        return null;
    }
    private static String importDirWind(int param){
        switch (param){
        case 7: return "n";
        case 8: return "nw";
        case 6: return "ne";
        case 1: return "s";
        case 2: return "sw";
        case 0: return "se";
        case 5: return "w";
        case 3: return "e";
        case 4: return "up";
        case 9: return "random"; // вообще это wind - возможо в будущем надо будет это учесть
        case 10: return "all";
        case 11: return "random";
        }
        return "up";
    }



    private static String importSound(int param) {
        switch (param){
        case 0: return "BLAZE_HIT";
        case 1: return "SHOOT_ARROW";
        case 2: return "CLICK";
        case 3: return "WOOD_CLICK";
        case 4: return "DOOR_OPEN";
        case 5: return "FIRE_IGNITE";
        case 6: return "GHAST_FIREBALL";
        case 7: return "GHAST_MOAN";
        case 8: return "ZOMBIE_METAL";
        case 9: return "ZOMBIE_WOOD";
        case 10: return "ZOMBIE_WOODBREAK";
        }
        return "CLICK";
    }

    private static int importLightChance(int param){
        return param%1000;
    }

    private static String importLightMode (int param){
        //   private String lightmode = "anytime,day,night,day-storm,night-storm,storm";
        int nsmode = param/1000;
        switch (nsmode){
        case 0: return "anytime";
        case 1: return "day";
        case 2: return "night";
        case 3: return "day-storm";
        case 4: return "night-storm";
        case 5: return "storm";
        }
        return "anytime";
    }


}
