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

import me.fromgate.playeffect.command.Commander;
import me.fromgate.playeffect.util.UpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class PlayEffectPlugin extends JavaPlugin {
    boolean versionCheck = true;
    boolean languageSave = false;
    String language = "english";
    int effectsPerTick = 500;
    int queueTickInterval = 1;
    int effectVisibleDistance = 32;
    public boolean playSmokeForSound = true;
    String wand_item = "COAL";
    boolean useProtocolLib = true;

    public Util u;
    //private Cmd cmd;
    public static PlayEffectPlugin instance;

    @Override
    public void onEnable() {
        loadCfg();
        saveCfg();
        u = new Util(this, languageSave, language, "playeffect");
        UpdateChecker.init(this, "PlayEffect", "66204", "playeffect", this.versionCheck);

        instance = this;

        EffectQueue.init(effectsPerTick, queueTickInterval);
        getServer().getPluginManager().registerEvents(u, this);
        Commander.init(this);
        Effects.loadEffects();
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

    private void saveCfg() {
        getConfig().set("general.check-updates", versionCheck);
        getConfig().set("general.language", language);
        getConfig().set("general.language-save", languageSave);
        getConfig().set("effects.wand-item", wand_item);
        getConfig().set("effects.play-smoke-for-sound", playSmokeForSound);
        getConfig().set("effects.queue.effects-per-tick", effectsPerTick);
        getConfig().set("effects.queue.tick-interval", queueTickInterval);
        getConfig().set("system.ProtcolLib-support", useProtocolLib);

        saveConfig();
    }

    public void loadCfg() {
        reloadConfig();
        versionCheck = getConfig().getBoolean("general.check-updates", true);
        language = getConfig().getString("general.language", "english");
        languageSave = getConfig().getBoolean("general.language-save", false);
        effectsPerTick = getConfig().getInt("effects.queue.effects-per-tick", 100);
        queueTickInterval = getConfig().getInt("effects.queue.tick-interval", 1);
        playSmokeForSound = getConfig().getBoolean("effects.play-smoke-for-sound", false);
        wand_item = getConfig().getString("effects.wand-item", "COAL");
        useProtocolLib = getConfig().getBoolean("system.ProtcolLib-support", true);
    }


    public static Util getUtil() {
        return instance.u;
    }

    public static PlayEffectPlugin getPlugin() {
        return instance;
    }


}
