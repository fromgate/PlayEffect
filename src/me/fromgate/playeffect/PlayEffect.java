package me.fromgate.playeffect;


import org.bukkit.plugin.java.JavaPlugin;

/*
 *  TODO
 * 1. /play <effectname> <?param>
 * 2. Менеджер эффектов: 
 *    - Определение игроков поблизости (с кэшированием)
 *    - Создание массива повторяющихся эффектов и сохранение эффектов
 *    - /playeffect wand <effect> <time> <?param>
 *    
 * 3. Все эффекты проходят через очередь.
 * 4. Над-класс эффектов:
 *    - эффект
 *    - повторяемость (интервал, время)
 * 
 */

public class PlayEffect extends JavaPlugin {
    /*
     * Конфигурация
     */
    boolean version_check = false;
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
        u = new Util (this, version_check, language_save, language, "playeffect", "PlayEffect", "playeffect", "&6[PlayEffect]&f ");
        instance = this;
        EffectQueue.init(effectpertick,queue_tick_interval);
        cmd = new Cmd(this);
        getServer().getPluginManager().registerEvents(u, this);
        getCommand("playeffect").setExecutor(cmd);
        NMSLib.init();
        Effects.loadEffects();
        WEGLib.init();
    }

    @Override
    public void onDisable() {
        Effects.stopAllEffects();
    }
    
    public static void play (VisualEffect effect, String param){
        Effects.playEffect(effect, param);
    }
    
    
    /*
     * API
     * 
     * play (VisualEffect type, Location loc, String param)
     * playSmoke
     * playSignal
     * playPotion
     * playFlame
     * playExplosion
     * play....
     * 
     * 
     */
    
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
    
    private void loadCfg(){
        reloadConfig();
        version_check = getConfig().getBoolean("general.check-updates",false);
        language = getConfig().getString("general.language","english");
        language_save= getConfig().getBoolean("general.language-save",true);
        effectpertick = getConfig().getInt("effects.queue.effects-per-tick",100);
        queue_tick_interval = getConfig().getInt("effects.queue.tick-interval",1);
        play_sound_smoke = getConfig().getBoolean("effects.play-smoke-for-sound",true);
        wand_item=getConfig().getString("effects.wand-item","COAL");
    }

    
    
    
}
