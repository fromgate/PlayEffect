package me.fromgate.playeffect.nms;

import net.minecraft.server.v1_10_R1.EntityFireworks;
import net.minecraft.server.v1_10_R1.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_10_R1.World;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class InstantFireworks extends EntityFireworks {
    Player[] players = null;

    public InstantFireworks(World world, Player... p) {
        super(world);
        players = p;
        this.setSize(0.25F, 0.25F);
    }

    boolean gone = false;

    @Override
    public void m() {
        if (gone)
            return;
        if (!world.isClientSide) {
            gone = true;
            if (players != null)
                if (players.length > 0) {
                    Player aplayer[];
                    int j = (aplayer = players).length;
                    for (int i = 0; i < j; i++) {
                        Player player = aplayer[i];
                        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityStatus(this, (byte) 17));
                    }
                } else {
                    world.broadcastEntityEffect(this, (byte) 17);
                }
            die();
        }
    }
}