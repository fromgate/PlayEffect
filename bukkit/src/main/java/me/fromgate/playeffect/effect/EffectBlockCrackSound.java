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

package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.Util;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class EffectBlockCrackSound extends BasicEffect {
    private ItemStack block;

    @Override
    public void onInit() {
        String blockStr = getParam("item", "");
        if (blockStr.isEmpty()) blockStr = getParam("block", "GLASS:0");
        block = u().parseItemStack(blockStr);
    }

    @Override
    protected void play(Location loc) {
        if ((block == null) || (!block.getType().isBlock())) {
            u().logOnce("blockcracksound" + Util.locationToString(loc), "Failed to play BLOCKCRACKSOUND effect. Wrong block type: " + (getParam("item", "").isEmpty() ? getParam("block", "N/A").isEmpty() : getParam("item", "")));
            return;
        }
        World w = loc.getWorld();
        w.playEffect(loc, Effect.STEP_SOUND, block.getType(), 32);
    }


}
