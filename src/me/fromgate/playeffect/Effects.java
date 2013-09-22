package me.fromgate.playeffect;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import me.fromgate.playeffect.effect.*;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class Effects {

    private static PlayEffect plg(){
        return PlayEffect.instance;
    }
    
    private static Util u(){
        return plg().u;
    }

    private static List<StaticEffect> effects = new ArrayList<StaticEffect>();

    protected static void stopAllEffects(){
        for (StaticEffect se : effects)
            se.stopRepeater();
    }
    
    protected static void startAllEffects(){
        for (StaticEffect se : effects)
            se.startRepeater();
    }


    
    public int countEffectsId (String id){
        int count = 0;
        for (StaticEffect se :effects)
            if (se.getId().equalsIgnoreCase(id)) count++;
        return count;
    }
    
    public static boolean createStaticEffect (BasicEffect be, String id, String time, boolean run){
        if (be == null) return false;
        Long rpt = Util.timeToTicks(Util.parseTime(time));
        StaticEffect se = new StaticEffect (id,be,rpt);
        effects.add(se);
        saveEffects();        
        return true;
    }

    public static boolean createStaticEffect (BasicEffect be, boolean save){
        if (be == null) return false;
        Long rpt = Util.timeToTicks(Util.parseTime(be.getParam("time")));
        String id = getId(be.getParam("id",""));
        StaticEffect se = new StaticEffect (id,be,rpt);
        effects.add(se);
        if (save) saveEffects();
        return true;
    }

    public static boolean removeStaticEffect(int num){
        if (num<0) return false;
        if (num>=effects.size()) return false;
        effects.get(num).stopRepeater();
        effects.remove(num);
        saveEffects();
        return true;
        
    }
        
/*    public static void setEnabled(String id, boolean enable){
        for (int i = effects.size()-1; i>=0; i--)
            if (effects.get(i).getId().equalsIgnoreCase(id)) effects.get(i).setEnabled(enable);
    } */

    public static void playEffect (VisualEffect effect, Map<String,String> params){
        BasicEffect e = createEffect   (effect, params);
        if (e!=null) EffectQueue.addToQueue(e);
    }
    
    public static BasicEffect createEffect (String effectstr, String param){
        if (!VisualEffect.contains(effectstr)) return null;
        VisualEffect ve = VisualEffect.valueOf(effectstr.toUpperCase());
        return createEffect (ve, param);
    }
    
    public static BasicEffect createEffect (VisualEffect effect, String param){
        Map<String,String> params = parseParams(param);
        return createEffect (effect, params);
    }

    public static BasicEffect createEffect (VisualEffect effect, Map<String,String> params){
        Location loc = null;
        Map<String,String> fullparams = parseParams(effect.getParam());
        if (!params.isEmpty())
            for (String key : params.keySet())
                fullparams.put(key, params.get(key));
                
        if (fullparams.containsKey("loc"))
            loc = Util.parseLocation(fullparams.get("loc"));
        if (loc != null) return createEffect (effect, loc, fullparams);
        return null;
    }




    public static void playEffect (VisualEffect effect, String param){
        Map<String,String> params = parseParams(param);
        playEffect (effect, params);
    }


    private static BasicEffect createEffect(VisualEffect effect, Location loc,Map<String, String> params) {
        if (effect == VisualEffect.BASIC) return null;
        Class<? extends BasicEffect> efc = effect.getClazz();
        try {
            BasicEffect be = (BasicEffect) efc.newInstance();
            be.initEffect(effect, loc, params);
            return be;
        } catch (Exception e){
            PlayEffect.instance.u.log("Failed to create effect "+effect.name());
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String,String> parseParams (String param){
        Map<String, String> params = new HashMap<String,String>();
        String [] ln = param.trim().replaceAll(" +", " ").split(" ");
        if (ln.length == 0) return params;
        for (String str : ln){
            String key = "param";
            String value = str;
            if (str.contains(":")){
                key = str.substring(0,str.indexOf(":"));
                value = str.substring(key.length()+1);
            }
            params.put(key, value);
        }
        return params;
    }

    public static String getParam (String param, String key, String defaultvalue){
        return getParam (parseParams(param),key,defaultvalue);
    }

    public static String getParam (Map<String,String> params, String key, String defaultvalue){
        if (!params.containsKey(key)) return defaultvalue;
        return params.get(key);
    }

    public static String getId (String effectid){
        int idcount = 0;
        if (!effectid.isEmpty()) return effectid;
        boolean found = false;
        do{
            found = false;
            for (int i = 0; i<effects.size();i++){
                if (effects.get(i).getId().equalsIgnoreCase("effect"+idcount)){
                    found = true;
                    idcount++;
                    break; // ?
                }
            }
        } while (found); 

        return "effect"+idcount;
    }

    public static void printEffectsList(CommandSender sender, int page,String id){
        int lpp = 1000;
        if (sender instanceof Player) lpp = 15;
        List<String> list = new ArrayList<String> ();
        if (!effects.isEmpty()){
            for (int i = 0; i<effects.size();i++){
                StaticEffect se = effects.get(i);
                if (!id.isEmpty()&&(!se.getId().equalsIgnoreCase(id))) continue;
                list.add("&3"+(i+1)+" &a"+effects.get(i).toString());
            }
            plg().u.printPage(sender, list, page, "msg_efflist", "", false,lpp);
        } else plg().u.printMSG (sender,"msg_efflistempty"); 
    }
    
    public static void printEffectsInfo(CommandSender sender, String numid){
        List<String> ln = new ArrayList<String>();
        if (u().isIntegerGZ(numid)){
            int num = Integer.parseInt(numid);
            if (effects.size()<=num)
                ln.addAll(effects.get(num-1).getInfo());
        } else {
            for (int i = 0; i<effects.size(); i++)
                if (effects.get(i).getId().equalsIgnoreCase(numid))
                    ln.addAll(effects.get(i).getInfo());    
        }
        u().printPage(sender, ln, 1, "msg_effectinfo", "", false,1000);
    }


    private static void saveEffects(){
        YamlConfiguration cfg = new YamlConfiguration();
        for (StaticEffect se : effects) se.saveEffect(cfg);
        File f = new File (plg().getDataFolder()+File.separator+"effects.yml");
        if (f.exists()) f.delete();
        try {
            cfg.save(f);
        } catch (Exception e) {
            plg().u.log("Failed to save effects.yml file");
        }
    }
    
    protected static void reloadEffects(){
        effects.clear();
        loadEffects();
    }

    protected static void loadEffects(){
        YamlConfiguration cfg = new YamlConfiguration();
        File f = new File (plg().getDataFolder()+File.separator+"effects.yml");
        if (!f.exists()) return;
        try {
            cfg.load(f);
        } catch (Exception e) {
            plg().u.log("Failed to load effects.yml file");
            return;
        }
        
        VisualEffect ve;
        for (String uid : cfg.getKeys(false)){
            Map<String,String> params = new HashMap<String,String>();
            ConfigurationSection cs = cfg.getConfigurationSection(uid);
            for (String key : cs.getKeys(true)) 
                params.put(key, cs.getString(key));
            String vestr = Effects.getParam(params, "effect", "");
            if (vestr.isEmpty()) continue;
            if (!VisualEffect.contains(vestr)) continue;
            ve = VisualEffect.valueOf(vestr);
            if (ve == null) continue;
            if (ve == VisualEffect.BASIC) continue;
            BasicEffect be = Effects.createEffect(ve, params);
            if (be == null) continue;
            Effects.createStaticEffect(be,false);
        }
        
        
        
    }
    
    public static int getEffectInLocation(Location loc){
        if (effects.size()==0) return -1;
        for (int i = 0; i<effects.size();i++)
            if (effects.get(i).inLocation(loc)) return i;
        return -1;
    }
    
    private static StaticEffect getStaticEffect(int num){
        if (num<0) return null;
        if (num>=effects.size()) return null;
        return effects.get(num);
    }
    
    public static String getStaticEffectStr (int num){
        StaticEffect se = getStaticEffect(num);
        if (se == null) return "";
        return se.toString();
    }
    
    public static boolean setEnabled(String id, boolean show){
        boolean found = false;
        for (StaticEffect se : effects)
            if (se.getId().equalsIgnoreCase(id)) {
                se.setEnabled(show);
                found = true;
            }
        return found;
    }
    
    
}
