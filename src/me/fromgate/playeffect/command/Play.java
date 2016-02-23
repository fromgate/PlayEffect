package me.fromgate.playeffect.command;

import java.util.Map;

import me.fromgate.playeffect.Effects;
import me.fromgate.playeffect.Util;
import me.fromgate.playeffect.VisualEffect;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


@CmdDefine(command = "playeffect", description = "hlp_hide", permission = "playeffect.play", 
subCommands = { "" }, allowConsole=true, shortDescription = "&3/playeffect <EffectType> [parameters]")
public class Play extends Cmd {

	@Override
	public boolean checkParams(String [] params){
		if (params.length==0) return false;
		return (VisualEffect.contains(params[0]));
	}
	
	@Override
	public boolean execute (CommandSender sender, String [] args){
		if (args.length == 0) return false;
		if (!checkParams (args)) return getUtil().returnMSG(true, sender, "msg_wrongeffect",args[0]);
		return playEffect (sender,args[0], Commander.unsplit(args,1));
	}
	
	private boolean playEffect(CommandSender sender, String cmd, String arg) {
        Map<String,String> params = Effects.parseParams(arg);
        Player player = null;
        String paramPlayer = Effects.getParam(params, "player", "");
        if (!paramPlayer.isEmpty()) player = Bukkit.getPlayerExact(paramPlayer);
        if((player==null)&&(sender instanceof Player)) player = (Player) sender; 
        if ((player==null)&(!arg.contains("loc:"))) {
            getUtil().printMSG(sender, "msg_consoleneedcoord",cmd+" "+arg);
        }
        VisualEffect ve = VisualEffect.getEffectByName(cmd);
        if (ve == null) return false;
        params = Util.processLocation(sender, params);
        Effects.playEffect(ve,  params);
        return true;
    }

}
