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

import org.bukkit.Effect;
import org.bukkit.Location;

public class EffectPotion extends BasicEffect {

    int clrparam = 0;

    @Override
    public void onInit() {
        clrparam = Math.max(0, Math.min(10, getParam("param", 0)));
    }

    @Override
    protected void play(Location loc) {
        loc.getWorld().playEffect(loc, Effect.POTION_BREAK, clrparam);
    }


}
