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

public enum DrawType {
    NORMAL,
    LINE,
    CIRCLE,
    PLAIN,
    AREA;
    public static boolean contains(String str){
        for (DrawType dt : DrawType.values())
            if (dt.name().equalsIgnoreCase(str)) return true;
        return false;
    }
    
    public boolean isUsingSecondLoc() {
        if (this == DrawType.NORMAL) return false;
        if (this == DrawType.CIRCLE) return false;
        return true;
    }
}
