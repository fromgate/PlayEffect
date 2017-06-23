package me.fromgate.playeffect.command;

import me.fromgate.playeffect.VisualEffect;
import me.fromgate.playeffect.Wand;
import me.fromgate.playeffect.common.Message;
import org.bukkit.entity.Player;

@CmdDefine(command = "playeffect", description = Message.HLP_WAND, permission = "playeffect.wand",
        subCommands = {"wand"}, shortDescription = "&3/playeffect wand <effect> [param]")
public class WandCmd extends Cmd {
// addCmd("wand", "wand", "hlp_wand","&3/playeffect wand <effect> [param]",'b'); // +

    @Override
    public boolean execute(Player player, String[] args) {
        if (args.length <= 1) return false;
        String effect = args[1];
        if (!VisualEffect.contains(effect)) return Message.MSG_UNKNOWNEFFECT.print(player, effect);
        String param = Commander.unsplit(args, 2);
        return setWandMode(player, effect, param);
    }


    private boolean setWandMode(Player player, String effect, String param) {
        if (player == null) return false;
        if (!VisualEffect.contains(effect)) return false;
        Wand.setWand(player, effect, param);
        Message.MSG_WANDENABLED.print(player, effect, param);
        return true;
    }
}
