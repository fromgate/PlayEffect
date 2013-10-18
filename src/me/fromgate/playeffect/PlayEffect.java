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
    public Util u;
    private Cmd cmd;
    public static PlayEffect  instance;
    
    @Override
    public void onEnable() {
        loadCfg();
        saveCfg();
        u = new Util (this, language_save, language, "playeffect");
        u.initUpdateChecker("PlayEffect", "66204", "a2a7b26dd4dc9bc496c80de4b49e87cb42e34ae3","playeffect", this.version_check);
        instance = this;
        EffectQueue.init(effectpertick,queue_tick_interval);
        cmd = new Cmd(this);
        getServer().getPluginManager().registerEvents(u, this);
        getCommand("playeffect").setExecutor(cmd);
        NMSLib.init();
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
        saveConfig();
    }
    
    protected void loadCfg(){
        reloadConfig();
        version_check = getConfig().getBoolean("general.check-updates",true);
        language = getConfig().getString("general.language","english");
        language_save= getConfig().getBoolean("general.language-save",false);
        effectpertick = getConfig().getInt("effects.queue.effects-per-tick",100);
        queue_tick_interval = getConfig().getInt("effects.queue.tick-interval",1);
        play_sound_smoke = getConfig().getBoolean("effects.play-smoke-for-sound",true);
        wand_item=getConfig().getString("effects.wand-item","COAL");
    }

    ////////////////////////////////////////////////////////////////
    public static void play (VisualEffect effect, Location loc, String param){
        if (effect == null) return;
        if (loc == null) return;
        Effects.playEffect(effect, param+" loc:"+Util.locationToStrLoc(loc));
    }
    
    public static void play (VisualEffect effect, String param){
        Effects.playEffect(effect, param);
    }
    
    public static void play (String effect, String param){
        if (!VisualEffect.contains(effect)) return;
        VisualEffect ve = VisualEffect.valueOf(effect.toUpperCase());
        if (ve == null) return;
        Effects.playEffect(ve, param);
    }
    
    public static void play (String effect, Map<String,String> params){
        if (!VisualEffect.contains(effect)) return;
        VisualEffect ve = VisualEffect.valueOf(effect.toUpperCase());
        if (ve == null) return;
        Effects.playEffect(ve, params);
    }

    public static boolean set(VisualEffect ve, String param){
        BasicEffect be = Effects.createEffect(ve, param);
        if (be == null) return false;
        return Effects.createStaticEffect(be, true);
    }
    
    public static boolean set(VisualEffect ve, Map<String,String> params){
        BasicEffect be = Effects.createEffect(ve, params);
        if (be == null) return false;
        return Effects.createStaticEffect(be, true);
    }

    public static boolean set(String effect, String param){
        if (!VisualEffect.contains(effect)) return false;
        VisualEffect ve = VisualEffect.valueOf(effect.toUpperCase());        
        return set(ve,param);
    }
    
    public static boolean set(String effect, Map<String,String> params){
        if (!VisualEffect.contains(effect)) return false;
        VisualEffect ve = VisualEffect.valueOf(effect.toUpperCase());        
        return set (ve,params);
    }
    
}
