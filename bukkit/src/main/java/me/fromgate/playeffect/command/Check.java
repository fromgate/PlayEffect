package me.fromgate.playeffect.command;

import me.fromgate.playeffect.Effects;
import org.bukkit.entity.Player;


@CmdDefine(command = "playeffect", description = "hlp_check", permission = "playeffect.config",
        subCommands = {"check"}, allowConsole = false, shortDescription = "&3/playeffect check [radius]")
public class Check extends Cmd {

    @Override
    public boolean execute(Player player, String[] args) {
        int radius = args.length > 1 && args[1].matches("\\d+") ? Integer.parseInt(args[1]) : 8;
        radius = Math.min(1, radius);
        Effects.printAroundEffects(player, radius);
        return true;
    }
}
