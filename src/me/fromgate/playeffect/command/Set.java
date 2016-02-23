package me.fromgate.playeffect.command;

import java.util.Map;

import me.fromgate.playeffect.Effects;
import me.fromgate.playeffect.Util;
import me.fromgate.playeffect.VisualEffect;
import me.fromgate.playeffect.effect.BasicEffect;

import org.bukkit.command.CommandSender;



@CmdDefine(command = "playeffect", description = "hlp_set", permission = "playeffect.set", subCommands = { "set" },
allowConsole=true, shortDescription = "&3/playeffect set <effect> id:<id> [param]")
public class Set extends Cmd {
	//addCmd("set", "set", "hlp_set","&3/playeffect set <effect> [param]",'b');
	
	@Override
	public boolean execute (CommandSender sender, String [] args){
		if (args.length<=1) return false;
		String arg1 = args[1];
		String arg2 = Commander.unsplit(args, 2);
		if (VisualEffect.contains(arg1)){
				Map<String,String> params = Effects.parseParams(arg2);
				params = Util.processLocation(sender, params);
                if (setEffect (sender,arg1,params)) getUtil().printMSG(sender, "msg_effectset",arg1);
                else getUtil().printMSG(sender, "msg_effectnotset",arg1);
            } else getUtil().printMSG(sender, "msg_unknowneffect",arg1);
		return true;
	}
	
	
    private boolean setEffect(CommandSender sender, String effect, Map<String,String> params) {
        VisualEffect ve = VisualEffect.getEffectByName(effect);
        if (ve == null) return false;
        String id = Effects.getId(Effects.getParam(params, "id", ""));
        String time = Effects.getParam(params, "time", Long.toString(ve.getRepeatTicks())+"t"); 
        BasicEffect be = Effects.createEffect(ve, params);
        Effects.createStaticEffect(be, id,time, true);
        return true;
    }
    
}
