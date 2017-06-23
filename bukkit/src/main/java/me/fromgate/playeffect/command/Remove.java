package me.fromgate.playeffect.command;

import me.fromgate.playeffect.Effects;
import me.fromgate.playeffect.Util;
import me.fromgate.playeffect.common.Message;
import org.bukkit.command.CommandSender;

@CmdDefine(command = "playeffect", description = Message.HLP_REMOVE, permission = "playeffect.config", subCommands = {"remove|rmv"},
        allowConsole = true, shortDescription = "&3/playeffect remove <effectNumber>")
public class Remove extends Cmd {
    //addCmd("remove", "config", "hlp_remove","&3/playeffect remove <effect number>",'b',true);


    @Override
    public boolean execute(CommandSender sender, String[] args) {
        String arg = Commander.unsplit(args, 1);
        if (Util.isIntegerGZ(arg)) {
            if (Effects.removeStaticEffect(Integer.parseInt(arg) - 1)) {
                Message.MSG_REMOVED.print(sender, arg);
            } else {
                Message.MSG_REMOVEFAILED.print(sender, arg);
            }
        } else {
            Message.MSG_UNKNOWNEFFECT.print(sender, arg);
        }
        return true;
    }
}
