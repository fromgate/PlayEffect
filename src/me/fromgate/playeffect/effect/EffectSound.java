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

import me.fromgate.playeffect.PlayEffect;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;

public class EffectSound extends BasicEffect {
    
    Sound sound = Sound.CLICK;
    float pitch = 1;
    float volume = 1;
    boolean playsmoke = false;
    
    @Override
    public void onInit(){
        String soundstr = getParam("type","CLICK");
        try{
            sound = Sound.valueOf(soundstr.toUpperCase());
        } catch (Exception e){
            sound = Sound.CLICK;
        }
        pitch = getParam("pitch",1f);
        volume = getParam("volume",1f);
        playsmoke = getParam ("showsmoke",PlayEffect.instance.playSmokeForSound);
    }

    @Override
    protected void play(Location loc) {
        loc.getWorld().playSound(loc, sound, volume, pitch);
        if (playsmoke) loc.getWorld().playEffect(loc, Effect.SMOKE, 4);
    }

}

