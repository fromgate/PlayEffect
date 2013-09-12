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
    
    
    public Util u;
    private Cmd cmd;
    public static PlayEffect  instance;
    
    
    @Override
    public void onEnable() {
        u = new Util (this, version_check, language_save, language, "playeffect", "PlayEffect", "playeffect", "&3[PlayEffect]&f ");
        instance = this;
        EffectQueue.init(effectpertick);
        cmd = new Cmd(this);
        getCommand("playeffect").setExecutor(cmd);
        NMSLib.init();
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
    

}
