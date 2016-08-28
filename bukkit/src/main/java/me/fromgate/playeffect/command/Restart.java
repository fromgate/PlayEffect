package me.fromgate.playeffect.command;

import me.fromgate.playeffect.EffectQueue;
import me.fromgate.playeffect.Effects;
import me.fromgate.playeffect.common.Message;
import org.bukkit.command.CommandSender;


@CmdDefine(command = "playeffect", description = Message.HLP_RESART, permission = "playeffect.config", subCommands = {"restart"},
        allowConsole = true, shortDescription = "&3/playeffect restart")
public class Restart extends Cmd {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        Effects.stopAllEffects();
        EffectQueue.clearQueue();
        Effects.startAllEffects();
        Message.MSG_RESTARTED.print(sender);
        return true;
    }
}
