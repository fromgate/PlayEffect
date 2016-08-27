package me.fromgate.playeffect.command;

import me.fromgate.playeffect.EffectQueue;
import me.fromgate.playeffect.Effects;
import me.fromgate.playeffect.PlayEffectPlugin;
import org.bukkit.command.CommandSender;

@CmdDefine(command = "playeffect", description = "hlp_reload", permission = "playeffect.config", subCommands = {"reload"},
        allowConsole = true, shortDescription = "&3/playeffect reload")
public class Reload extends Cmd {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Effects.stopAllEffects();
        EffectQueue.clearQueue();
        PlayEffectPlugin.getPlugin().loadCfg();
        Effects.reloadEffects();
        Effects.startAllEffects();
        getUtil().printMSG(sender, "msg_reloaded");
        return true;
    }


}
