package me.fromgate.playeffect.command;

import me.fromgate.playeffect.EffectQueue;
import me.fromgate.playeffect.Effects;
import me.fromgate.playeffect.PlayEffectPlugin;
import me.fromgate.playeffect.common.Message;
import org.bukkit.command.CommandSender;

@CmdDefine(command = "playeffect", description = Message.HLP_RELOAD, permission = "playeffect.config", subCommands = {"reload"},
        allowConsole = true, shortDescription = "&3/playeffect reload")
public class Reload extends Cmd {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Effects.stopAllEffects();
        EffectQueue.clearQueue();
        PlayEffectPlugin.getPlugin().loadCfg();
        Effects.reloadEffects();
        Effects.startAllEffects();
        Message.MSG_RELOADED.print(sender);
        return true;
    }


}
