package me.fromgate.playeffect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.fromgate.playeffect.effect.*;

import org.bukkit.Location;


public class Effects {

    private static List<StaticEffect> effects = new ArrayList<StaticEffect>();
    
    
    public static void addStaticEffect (BasicEffect be, String id, String time, boolean run){
        PlayEffect.instance.u.BC("Time: "+time);
        if (be == null) return;
        Long rpt = Util.timeToTicks(Util.parseTime(time));
        PlayEffect.instance.u.BC("Time: "+time+" = "+rpt);
        StaticEffect se = new StaticEffect (id,be,rpt);
        se.startRepeater();
        effects.add(se);
    }
    
    public static void removeStaticEffect(String id){
        for (int i = effects.size()-1; i>=0; i--)
            if (effects.get(i).getId().equalsIgnoreCase(id)) effects.remove(i);
    }
    
    public static void setEnabled(String id, boolean enable){
        for (int i = effects.size()-1; i>=0; i--)
            if (effects.get(i).getId().equalsIgnoreCase(id)) effects.get(i).setEnabled(enable);
    }
    

    //private static Map<VisualEffect,Class<? extends BasicEffect>> effects = new HashMap <VisualEffect,Class<? extends BasicEffect>>(); 

   /* public static void playEffect (VisualEffect effect, Location loc, Map<String,String> params){
        Map<String,String> fullparams = parseParams(effect.getParam());
        if (!params.isEmpty())
            for (String key : params.keySet())
                fullparams.put(key, params.get(key));
        Location l = loc;
        if (fullparams.containsKey("loc")){
            Location tl = Util.parseLocation(fullparams.get("loc"));
            if (tl!=null) l = tl;
        }
        EffectQueue.addToQueue(createEffect (effect, l, fullparams));
    } */



    /*public static void playEffect (VisualEffect effect, Map<String,String> params){
        Location loc = null;
        Map<String,String> fullparams = parseParams(effect.getParam());
        if (!params.isEmpty())
            for (String key : params.keySet())
                fullparams.put(key, params.get(key));
        if (fullparams.containsKey("loc"))
            loc = Util.parseLocation(fullparams.get("loc"));
        if (loc != null) EffectQueue.addToQueue(createEffect (effect, loc, fullparams));
    } */

    public static void playEffect (VisualEffect effect, Map<String,String> params){
        BasicEffect e = createEffect   (effect, params);
        if (e!=null) EffectQueue.addToQueue(e);
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



    /*
    public static void playEffect (VisualEffect effect, Location loc, String param){
        //String fullparam = (effect.getParam().isEmpty() ? "" : effect.getParam()+" ")+param;
        Location l = null;
        Map<String,String> params = parseParams(param);
        for (String key : params.keySet()){
            if (key.equalsIgnoreCase("loc"))
                l = Util.parseLocation(params.get("loc"));
        }
        if (l==null) l = loc;
        playEffect (effect, l, params);
    } */

    private static BasicEffect createEffect(VisualEffect effect, Location loc,Map<String, String> params) {
        //PlayEffect.instance.u.BC(effect.name()+ " : "+effect.getClazz().getName()+" : "+effect.getParam());
        //if (!effects.containsKey(effect)) return null;
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



    public String getId (String effectid){
        int idcount = 0;
        if (!effectid.isEmpty()) return effectid;
        String id = "id";
        //boolean newfound=false;

        /*do {newfound = false;
        for (int i = 0; i< smog.size(); i++) {
            newfound = false;
            if (smog.get(i).id.equalsIgnoreCase("id"+Integer.toString(idcount))) {
                newfound = true;
                idcount++;
            }
        }
        } while (newfound); */

        id = "id"+Integer.toString(idcount);
        idcount++;
        return id;
    }


}
