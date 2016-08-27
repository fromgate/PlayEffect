package me.fromgate.playeffect.command;

import me.fromgate.playeffect.Effects;
import org.bukkit.command.CommandSender;

@CmdDefine(command = "playeffect", description = "hlp_remove", permission = "playeffect.config", subCommands = {"remove|rmv"},
        allowConsole = true, shortDescription = "&3/playeffect remove <effectNumber>")
public class Remove extends Cmd {
    //addCmd("remove", "config", "hlp_remove","&3/playeffect remove <effect number>",'b',true);


    @Override
    public boolean execute(CommandSender sender, String[] args) {
        String arg = Commander.unsplit(args, 1);
        if (getUtil().isIntegerGZ(arg)) {
            if (Effects.removeStaticEffect(Integer.parseInt(arg) - 1)) getUtil().printMSG(sender, "msg_removed", arg);
            else getUtil().printMSG(sender, "msg_removefailed", arg);
        } else getUtil().printMSG(sender, "msg_unknowneffect", arg);
        return true;
    }
}
