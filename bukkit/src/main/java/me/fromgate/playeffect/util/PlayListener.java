package me.fromgate.playeffect.util;


import me.fromgate.playeffect.PlayEffectPlugin;
import me.fromgate.playeffect.Util;
import me.fromgate.playeffect.Wand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayListener implements Listener {
    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
        Player p = event.getPlayer();
        if (!Wand.hasWand(p)) return;
        if (p.getItemInHand() == null) return;
        if (!Util.compareItemStr(p.getItemInHand(), PlayEffectPlugin.getPlugin().wand_item)) return;
        Wand.toggleEffect(p, event.getClickedBlock());
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Wand.clearWand(event.getPlayer());
        UpdateChecker.updateMsg(event.getPlayer());
    }

}
