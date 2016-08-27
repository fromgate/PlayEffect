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

package me.fromgate.playeffect;

public enum ParamType {
    //BASEEFFECTS
    LOC,
    LOC2,
    RADIUS,
    AMOUNT,
    CHANCE,
    LAND,
    DRAW,
    WIND,         //SMOKE
    PARAM,        //POTIONBREAK
    LCHANCE,      //Lightning
    MODE,         //Lightning
    EFFECTNAME,
    NUM,
    SPEED,
    OFFSET,
    OFFSETX,
    OFFSETY,
    OFFSETZ,
    ITEM,
    TYPE,
    COLOR,
    PITCH,
    VOLUME,
    DISC;


    public static boolean isValid(String param) {
        for (ParamType pt : ParamType.values())
            if (pt.name().equalsIgnoreCase(param)) return true;
        return false;
    }
}
