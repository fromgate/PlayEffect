/*  
 *  PlayEffect, Minecraft bukkit plugin
 *  (c)2013-2015, fromgate, fromgate@gmail.com
 *  http://dev.bukkit.org/bukkit-plugins/playeffect/
 *    
 *  This file is part of PlayEffect.
 *  
 *  PlayEffect is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  PlayEffect is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with PlayEffect.  If not, see <http://www.gnorg/licenses/>.
 * 
 */

/*
 * Original idea by NerdsWBNerds
 * http://bukkit.org/threads/lib-instant-fireworks-no-launch-sound-no-visible-rocket-just-the-firework-effect.269919/
 * Thank you very much, NerdsWBNerds :)
 * 
 */

package me.fromgate.playeffect.firework;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import net.minecraft.server.v1_8_R1.EntityFireworks;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_8_R1.World;

public class InstantFireworks18R1 extends EntityFireworks {
	Player[] players = null;

	public InstantFireworks18R1(World world, Player... p) {
		super(world);
		players = p;
		this.a(0.25F, 0.25F);
	}

	boolean gone = false;

	@Override
	public void s_() {
		if (gone) {
			return;
		}

		if (!this.world.isStatic) {
			gone = true;

			if (players != null) 
				if (players.length > 0) 
					for (Player player : players) 
						(((CraftPlayer) player).getHandle()).playerConnection.sendPacket(new PacketPlayOutEntityStatus(this, (byte) 17));
			else world.broadcastEntityEffect(this, (byte) 17);
			this.die();
		}
	}

	public static void spawn(Location location, FireworkEffect effect, Player... players) {
		try {
			InstantFireworks18R1 firework = new InstantFireworks18R1(((CraftWorld) location.getWorld()).getHandle(), players);
			
			FireworkMeta meta = ((Firework) firework.getBukkitEntity()).getFireworkMeta();
			meta.addEffect(effect);
			((Firework) firework.getBukkitEntity()).setFireworkMeta(meta);
			
			firework.setPosition(location.getX(), location.getY(), location.getZ());

			if ((((CraftWorld) location.getWorld()).getHandle()).addEntity(firework)) {
				firework.setInvisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
