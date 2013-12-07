package me.fromgate.playeffect;


import java.io.IOException;
import java.util.Map;

import me.fromgate.playeffect.effect.BasicEffect;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayEffect extends JavaPlugin {
    boolean version_check = true;
    boolean language_save = false;
    String language = "english";
    int effectpertick=500;
    int queue_tick_interval=1;
    int effect_visible_distance=32;
    public boolean play_sound_smoke = true;
    String wand_item = "COAL";
    boolean firework_rocket = false;
    public Util u;
    private Cmd cmd;
    public static PlayEffect  instance;
    

    @Override
    public void onEnable() {
        NMSLib.init();
        loadCfg();
        saveCfg();
        u = new Util (this, language_save, language, "playeffect");
        u.initUpdateChecker("PlayEffect", "66204", "a2a7b26dd4dc9bc496c80de4b49e87cb42e34ae3","playeffect", this.version_check);
        instance = this;
        EffectQueue.init(effectpertick,queue_tick_interval);
        cmd = new Cmd(this);
        getServer().getPluginManager().registerEvents(u, this);
        getCommand("playeffect").setExecutor(cmd);

        Effects.loadEffects();
        ImportNoSmoking.loadSmokePoints(); //импорт старья
        WEGLib.init();
        try {
            MetricsLite metrics = new MetricsLite(this);
            metrics.start();
        } catch (IOException e) {
        }
    }

    @Override
    public void onDisable() {
        Effects.stopAllEffects();
    }

    private void saveCfg(){
        getConfig().set("general.check-updates",version_check);
        getConfig().set("general.language",language);
        getConfig().set("general.language-save",language_save);
        getConfig().set("effects.wand-item",wand_item);
        getConfig().set("effects.play-smoke-for-sound",play_sound_smoke);
        getConfig().set("effects.queue.effects-per-tick",effectpertick);
        getConfig().set("effects.queue.tick-interval",queue_tick_interval);
        getConfig().set("effects.firework-play-rocket-sound",firework_rocket);
        saveConfig();
    }

    protected void loadCfg(){
        reloadConfig();
        version_check = getConfig().getBoolean("general.check-updates",true);
        language = getConfig().getString("general.language","english");
        language_save= getConfig().getBoolean("general.language-save",false);
        effectpertick = getConfig().getInt("effects.queue.effects-per-tick",100);
        queue_tick_interval = getConfig().getInt("effects.queue.tick-interval",1);
        play_sound_smoke = getConfig().getBoolean("effects.play-smoke-for-sound",false);
        wand_item=getConfig().getString("effects.wand-item","COAL");
        firework_rocket=getConfig().getBoolean("effects.firework-play-rocket-sound",true);
    }


    /**
     * @param effect    - VisualEffect type. Example: VisualEffect.SMOKE
     * @param loc       - Location where effect will played
     * @param param     - Any effect parameter. Example: "radius:2 num:5"
     */
    public static void play (VisualEffect effect, Location loc, String param){
        if (effect == null) return;
        if (loc == null) return;
        Effects.playEffect(effect, param+" loc:"+Util.locationToStrLoc(loc));
    }

    /**
     * @param effect    - VisualEffect type. Example: VisualEffect.SMOKE
     * @param loc       - Location where effect will played
     * @param params    - Any supported effect parameter. 
     *                    You can create HashMap<String,String> params and add 
     *                    required parameters to this map
     *                    Example: 
     *                    Map<String,String> params = new HashMap<String,String>();  
     *                    params.put ("radius","2");
     *                    params.put ("num","5");  
     */
    public static void play (VisualEffect effect, Location loc, Map<String,String> params){
        if (effect == null) return;
        if (loc == null) return;
        params.put("loc", Util.locationToStrLoc(loc));
        Effects.playEffect(effect, params);
    }

    /**
     * @param effect    - Name of the VisualEffect. Example: "smoke", "FIREWORK", etc...
     * @param loc       - Location where effect will played
     * @param params    - Any supported effect parameter. 
     *                    You can create HashMap<String,String> params and add 
     *                    required parameters to this map
     *                    Example: 
     *                    Map<String,String> params = new HashMap<String,String>();  
     *                    params.put ("radius","2");
     *                    params.put ("num","5");  
     */
    public static void play (String effect, Location loc, Map<String,String> params){
        if (!VisualEffect.contains(effect)) return;
        if (loc == null) return;
        VisualEffect ve = VisualEffect.valueOf(effect.toUpperCase());
        play (ve,loc,params);
    }

    /**
     * @param effect    - VisualEffect type. Example: VisualEffect.SMOKE
     * @param param     - Any effect parameter. Don't forget to
     *                    add "loc" parameter to define effect location 
     *                    Example: "loc:world,10,65,12 radius:2 num:5"
     */
    public static void play (VisualEffect effect, String param){
        Effects.playEffect(effect, param);
    }

    /**
     * @param effect    - Name of the VisualEffect. Example: "smoke", "FIREWORK", etc...
     * @param param     - Any effect parameter. Don't forget to
     *                    add "loc" parameter to define effect location 
     *                    Example: "loc:world,10,65,12 radius:2 num:5"
     */
    public static void play (String effect, String param){
        if (!VisualEffect.contains(effect)) return;
        VisualEffect ve = VisualEffect.valueOf(effect.toUpperCase());
        if (ve == null) return;
        Effects.playEffect(ve, param);
    }

    /**
     * @param effect    - Name of the VisualEffect. Example: "smoke", "FIREWORK", etc...
     * @param params    - Any supported effect parameter. 
     *                    You can create HashMap<String,String> params and add 
     *                    required parameters to this map
     *                    Example: 
     *                    Map<String,String> params = new HashMap<String,String>();  
     *                    params.put ("radius","2");
     *                    params.put ("num","5");  
     */
    public static void play (String effect, Map<String,String> params){
        if (!VisualEffect.contains(effect)) return;
        VisualEffect ve = VisualEffect.valueOf(effect.toUpperCase());
        if (ve == null) return;
        Effects.playEffect(ve, params);
    }

    /**
     * Set static (repeating) effect in defined location
     * 
     * @param effect    - VisualEffect type. Example: VisualEffect.SMOKE   
     * @param param     - Any effect parameter. Don't forget to
     *                    add "loc" parameter to define effect location 
     *                    Example: "loc:world,10,65,12 radius:2 num:5"
     * @return          - Return true if effect was set successfully 
     */
    public static boolean set(VisualEffect effect, String param){
        BasicEffect be = Effects.createEffect(effect, param);
        if (be == null) return false;
        return Effects.createStaticEffect(be, true);
    }

    /**
     * Set static (repeating) effect in defined location
     * 
     * @param effect    - VisualEffect type. Example: VisualEffect.SMOKE   
     * @param params    - Any supported effect parameter. 
     *                    You can create HashMap<String,String> params and add 
     *                    required parameters to this map
     *                    Example: 
     *                    Map<String,String> params = new HashMap<String,String>();
     *                    params.put ("loc","world,10,65,20");  
     *                    params.put ("radius","2");
     *                    params.put ("num","5");
     * @return          - Return true if effect was set successfully 
     */
    public static boolean set(VisualEffect ve, Map<String,String> params){
        BasicEffect be = Effects.createEffect(ve, params);
        if (be == null) return false;
        return Effects.createStaticEffect(be, true);
    }

    /**
     * Set static (repeating) effect in defined location
     * 
     * @param effect    - Name of the VisualEffect. Example: "smoke", "FIREWORK", etc...
     * @param param     - Any effect parameter. Don't forget to
     *                    add "loc" parameter to define effect location 
     *                    Example: "loc:world,10,65,12 radius:2 num:5"
     * @return          - Return true if effect was set successfully 
     */
    public static boolean set(String effect, String param){
        if (!VisualEffect.contains(effect)) return false;
        VisualEffect ve = VisualEffect.valueOf(effect.toUpperCase());        
        return set(ve,param);
    }
    
    /**
     * Set static (repeating) effect in defined location
     * 
     * @param effect    - Name of the VisualEffect. Example: "smoke", "FIREWORK", etc...   
     * @param params    - Any supported effect parameter. 
     *                    You can create HashMap<String,String> params and add 
     *                    required parameters to this map
     *                    Example: 
     *                    Map<String,String> params = new HashMap<String,String>();
     *                    params.put ("loc","world,10,65,20");  
     *                    params.put ("radius","2");
     *                    params.put ("num","5");
     * @return          - Return true if effect was set successfully 
     */
    public static boolean set(String effect, Map<String,String> params){
        if (!VisualEffect.contains(effect)) return false;
        VisualEffect ve = VisualEffect.valueOf(effect.toUpperCase());        
        return set (ve,params);
    }

}
