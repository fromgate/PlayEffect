package me.fromgate.playeffect.command;

import me.fromgate.playeffect.Effects;

import org.bukkit.command.CommandSender;

@CmdDefine(command = "playeffect", description = "hlp_show", permission = "playeffect.show", 
subCommands = { "show" }, allowConsole=true, shortDescription = "&3/playeffect show <effect id>")
public class Show extends Cmd {
	
	
	@Override
	public boolean execute (CommandSender sender, String [] args){
		if (args.length<1) return false;
        if (Effects.setEnabled(args[1], true)) getUtil().printMSG(sender,"msg_showeffect",args[1]);
        else getUtil().printMSG(sender,"msg_unknown",args[1]);
        return true;
	}
}
