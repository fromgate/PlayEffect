package me.fromgate.playeffect.command;

import me.fromgate.playeffect.Effects;

import org.bukkit.command.CommandSender;

@CmdDefine(command = "playeffect", description = "hlp_hide", permission = "playeffect.show", 
subCommands = { "hide" }, allowConsole=true, shortDescription = "&3/playeffect hide <effect id>")
public class Hide extends Cmd {
	@Override
	public boolean execute (CommandSender sender, String [] args){
		if (args.length<1) return false;
        if (Effects.setEnabled(args[1], false)) getUtil().printMSG(sender,"msg_hideeffect",args[1]);
        else getUtil().printMSG(sender,"msg_unknown",args[1]);
        return true;
	}
}
