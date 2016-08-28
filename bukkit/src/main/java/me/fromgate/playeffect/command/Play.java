package me.fromgate.playeffect.command;

import me.fromgate.playeffect.Effects;
import me.fromgate.playeffect.Util;
import me.fromgate.playeffect.VisualEffect;
import me.fromgate.playeffect.common.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;


@CmdDefine(command = "playeffect", description = Message.CMD_PLAY, permission = "playeffect.play",
        subCommands = {""}, allowConsole = true, shortDescription = "&3/playeffect <EffectType> [parameters]")
public class Play extends Cmd {

    @Override
    public boolean checkParams(String[] params) {
        if (params.length == 0) return false;
        return (VisualEffect.contains(params[0]));
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (args.length == 0) return false;
        if (!checkParams(args)) {
            return Message.MSG_WRONGEFFECT.print(sender, args[0]);
        }
        return playEffect(sender, args[0], Commander.unsplit(args, 1));
    }

    @SuppressWarnings("deprecation")
    private boolean playEffect(CommandSender sender, String cmd, String arg) {
        Map<String, String> params = Effects.parseParams(arg);
        Player player = null;
        String paramPlayer = Effects.getParam(params, "player", "");
        if (!paramPlayer.isEmpty()) player = Bukkit.getPlayerExact(paramPlayer);
        if ((player == null) && (sender instanceof Player)) player = (Player) sender;
        if ((player == null) & (!arg.contains("loc:"))) {
            return Message.MSG_CONSOLENEEDCOORD.print(cmd + " " + arg);
        }
        VisualEffect ve = VisualEffect.getEffectByName(cmd);
        if (ve == null) return false;
        params = Util.processLocation(sender, params);
        Effects.playEffect(ve, params);
        return true;
    }

}
