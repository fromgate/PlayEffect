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
import org.bukkit.Location;
import org.bukkit.World;

public class EffectLightning extends BasicEffect {

    private final static String LIGHTMODE = "anytime,day,night,day-storm,night-storm,storm";
    private int chance = 50;
    private String mode = "storm";


    @Override
    public void onInit() {
        chance = getParam("lchance", 100);
        mode = getParam("mode", "anytime");
        if (!Util.isWordInList(mode, LIGHTMODE)) mode = "storm";
    }


    @Override
    protected void play(Location loc) {
        if (isTimeToBolt(loc.getWorld())) loc.getWorld().strikeLightningEffect(loc);
    }

    private boolean isTimeToBolt(World w) {
        if (!Util.rollDiceChance(chance)) return false;
        if (mode.contains("night") && day(w)) return false;
        if (mode.contains("day") && (!day(w))) return false;
        if (mode.contains("storm") && (!w.hasStorm())) return false;
        return true;
    }

    private boolean day(World w) {
        long time = w.getTime();
        return time < 12300 || time > 23850;
    }

}
