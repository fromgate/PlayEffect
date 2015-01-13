/*  
 *  PlayEffect, Minecraft bukkit plugin
 *  (c)2013-2015, fromgate, fromgate@gmail.com
 *  http://dev.bukkit.org/bukkit-plugins/playeffect/
 *    
 *  This file is part of PlayEffect.
 *  
 *  PlayEffect is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  PlayEffect is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with PlayEffect.  If not, see <http://www.gnorg/licenses/>.
 * 
 */

package me.fromgate.playeffect;


import java.io.IOException;
import java.util.Map;
import me.fromgate.playeffect.customeffects.AdditionalEffects;
import me.fromgate.playeffect.customeffects.PacketNMS;
import me.fromgate.playeffect.effect.BasicEffect;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayEffect extends JavaPlugin {
    boolean versionCheck = true;
    boolean languageSave = false;
    String language = "english";
    int effectsPerTick=500;
    int queueTickInterval=1;
    int effectVisibleDistance=32;
    public boolean playSmokeForSound = true;
    String wand_item = "COAL";
    boolean useProtocolLib = true;
    
    
    public Util u;
    private Cmd cmd;
    public static PlayEffect  instance;
    
    

    @Override
    public void onEnable() {
        loadCfg();
        saveCfg();
        u = new Util (this, languageSave, language, "playeffect");
        u.initUpdateChecker("PlayEffect", "66204", "playeffect", this.versionCheck);
        instance = this;
        // ProtocolLib is temporary disabled. Waiting for new particles support :)
    	//if (useProtocolLib) PacketProtocolLib.init();
    	//if (PacketProtocolLib.isProtocolLibFound()) u.log("ProtocolLib found and connected."); 
    	PacketNMS.init();
        EffectQueue.init(effectsPerTick,queueTickInterval);
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
        getConfig().set("general.check-updates",versionCheck);
        getConfig().set("general.language",language);
        getConfig().set("general.language-save",languageSave);
        getConfig().set("effects.wand-item",wand_item);
        getConfig().set("effects.play-smoke-for-sound",playSmokeForSound);
        getConfig().set("effects.queue.effects-per-tick",effectsPerTick);
        getConfig().set("effects.queue.tick-interval",queueTickInterval);
        getConfig().set("system.firework-play-method",AdditionalEffects.fireworkMethod);
        getConfig().set("system.ProtcolLib-support",useProtocolLib);
        
        saveConfig();
    }

    protected void loadCfg(){
        reloadConfig();
        versionCheck = getConfig().getBoolean("general.check-updates",true);
        language = getConfig().getString("general.language","english");
        languageSave= getConfig().getBoolean("general.language-save",false);
        effectsPerTick = getConfig().getInt("effects.queue.effects-per-tick",100);
        queueTickInterval = getConfig().getInt("effects.queue.tick-interval",1);
        playSmokeForSound = getConfig().getBoolean("effects.play-smoke-for-sound",false);
        wand_item=getConfig().getString("effects.wand-item","COAL");
        AdditionalEffects.fireworkMethod = getConfig().getInt("system.firework-play-method",-1);
        useProtocolLib = getConfig().getBoolean("system.ProtcolLib-support",true);
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
