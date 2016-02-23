package me.fromgate.playeffect.command;

import me.fromgate.playeffect.EffectQueue;
import me.fromgate.playeffect.Effects;

import org.bukkit.command.CommandSender;


@CmdDefine(command = "playeffect", description = "hlp_resart", permission = "playeffect.config", subCommands = {"restart"},
allowConsole=true, shortDescription = "&3/playeffect restart")
public class Restart extends Cmd {
	
	@Override
	public boolean execute (CommandSender sender, String [] args){
        Effects.stopAllEffects();
        EffectQueue.clearQueue();
        Effects.startAllEffects();
        getUtil().printMSG(sender, "msg_restarted");
		return true;
	}
}
