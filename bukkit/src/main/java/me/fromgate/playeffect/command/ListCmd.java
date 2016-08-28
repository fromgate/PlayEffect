package me.fromgate.playeffect.command;

import me.fromgate.playeffect.Effects;
import me.fromgate.playeffect.common.Message;
import org.bukkit.command.CommandSender;


@CmdDefine(command = "playeffect", description = Message.HLP_LIST, permission = "playeffect.config",
        subCommands = {"list|lst"}, allowConsole = true, shortDescription = "&3/playeffect list [page]")
public class ListCmd extends Cmd {

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        String id = "";
        int page = 1;
        for (String a : Commander.getSubArgs(args, 1)) {
            if (a.matches("\\d+")) page = Integer.parseInt(a);
            else id = a;
        }
        Effects.printEffectsList(sender, page, id);
        return true;
    }

}
