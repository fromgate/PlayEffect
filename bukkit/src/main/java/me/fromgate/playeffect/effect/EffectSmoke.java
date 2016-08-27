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

import me.fromgate.playeffect.PlayEffectPlugin;
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
        World w = loc.getWorld();
        if (this.param < 9) w.playEffect(loc, Effect.SMOKE, this.param);
        else if (this.param == 9) for (int i = 0; i < 9; i++) w.playEffect(loc, Effect.SMOKE, i);
        else if (this.param == 10) w.playEffect(loc, Effect.SMOKE, PlayEffectPlugin.instance.u.getRandomInt(9));
    }


    private int parseSmokeDirection(String dir_str) {
        if (dir_str.equalsIgnoreCase("n") || dir_str.equalsIgnoreCase("north")) return 7;
        if (dir_str.equalsIgnoreCase("nw") || dir_str.equalsIgnoreCase("northwest")) return 8;
        if (dir_str.equalsIgnoreCase("ne") || dir_str.equalsIgnoreCase("northeast")) return 6;
        if (dir_str.equalsIgnoreCase("s") || dir_str.equalsIgnoreCase("south")) return 1;
        if (dir_str.equalsIgnoreCase("sw") || dir_str.equalsIgnoreCase("southwest")) return 2;
        if (dir_str.equalsIgnoreCase("se") || dir_str.equalsIgnoreCase("southeast")) return 0;
        if (dir_str.equalsIgnoreCase("w") || dir_str.equalsIgnoreCase("west")) return 5;
        if (dir_str.equalsIgnoreCase("e") || dir_str.equalsIgnoreCase("east")) return 3;
        if (dir_str.equalsIgnoreCase("calm") || dir_str.equalsIgnoreCase("up")) return 4;
        if (dir_str.equalsIgnoreCase("all")) return 9;
        if (dir_str.equalsIgnoreCase("rnd") || dir_str.equalsIgnoreCase("random")) return 10;
        return 10;
    }


}