package me.fromgate.playeffect.command;

import me.fromgate.playeffect.Effects;
import me.fromgate.playeffect.common.Message;
import org.bukkit.command.CommandSender;

@CmdDefine(command = "playeffect", description = Message.HLP_HIDE, permission = "playeffect.show",
        subCommands = {"hide"}, allowConsole = true, shortDescription = "&3/playeffect hide <effect id>")
public class Hide extends Cmd {
    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length < 1) return false;
        if (Effects.setEnabled(args[1], false)) {
            Message.MSG_HIDEEFFECT.print(sender, args[1]);
        } else {
            Message.MSG_UNKNOWNEFFECT.print(sender, args[1]);
        }
        return true;
    }
}
