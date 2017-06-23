/*  
 *  PlayEffect, Minecraft bukkit plugin
 *  (c)2013-2017, fromgate, fromgate@gmail.com
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


public class EffectSmoke extends BasicEffect {

    private int param = 4;

    @Override
    public void onInit() {
        this.param = parseSmokeDirection(getParam("wind", "all"));
    }

    @Override
    protected void play(Location loc) {
        World world = loc.getWorld();
        if (this.param < 9) {
            world.playEffect(loc, Effect.SMOKE, this.param);
        } else if (this.param == 9) {
            for (int i = 0; i < 9; i++) world.playEffect(loc, Effect.SMOKE, i);
        } else if (this.param == 10) {
            world.playEffect(loc, Effect.SMOKE, Util.getRandomInt(9));
        }
    }


    private int parseSmokeDirection(String dir_str) {
        switch (dir_str.toLowerCase()) {
            case "n":
            case "north": return 7;
            case "nw":
            case "northwest": return 8;
            case "ne":
            case "northeast": return 6;
            case"s":
            case "south": return 1;
            case"sw":
            case "southwest": return 2;
            case"se":
            case "southeast": return 0;
            case "w":
            case "west": return 5;
            case "e":
            case "east": return 3;
            case "calm":
            case "up": return 4;
            case "all": return 9;
            case "rnd":
            case "random": return 10;
        }
        return 10;
    }




}
