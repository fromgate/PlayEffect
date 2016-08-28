package me.fromgate.playeffect.command;

import me.fromgate.playeffect.common.Message;
import org.bukkit.command.CommandSender;


@CmdDefine(command = "playeffect", description = Message.HLP_THISHELP, permission = "playeffect.config", subCommands = {"help|hlp|\\?"},
        allowConsole = true, shortDescription = "&3/playeffect help [page]")
public class Help extends Cmd {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        int page = 1;
        if (args.length > 0 && args[0].matches("\\d+")) page = Integer.parseInt(args[0]);
        Commander.printHelp(sender, page);
        return true;
    }

}
