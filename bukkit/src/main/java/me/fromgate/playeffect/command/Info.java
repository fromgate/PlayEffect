package me.fromgate.playeffect.command;

import me.fromgate.playeffect.Effects;
import org.bukkit.command.CommandSender;


@CmdDefine(command = "playeffect", description = "hlp_info", permission = "playeffect.config", subCommands = {"info"},
        allowConsole = true, shortDescription = "&3/playeffect info <effectId | number>")
public class Info extends Cmd {
    //addCmd("info", "config", "hlp_info","&3/playeffect info <effect id | number>",'b',true);
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        String arg = Commander.unsplit(args, 1);
        Effects.printEffectsInfo(sender, arg);
        return true;
    }
}
