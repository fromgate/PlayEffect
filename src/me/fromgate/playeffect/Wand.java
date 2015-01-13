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

import me.fromgate.playeffect.effect.BasicEffect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class Wand {
    private static PlayEffect plg(){
        return PlayEffect.instance;
    }

    public static void setWand(Player p, String effect, String param){
        p.setMetadata("PE-effect", new FixedMetadataValue (plg(), effect));
        p.setMetadata("PE-param", new FixedMetadataValue (plg(), param));
    }

    public static void clearWand (Player p){
        if (p.hasMetadata("PE-effect")) p.removeMetadata("PE-effect", PlayEffect.instance);
        if (p.hasMetadata("PE-param")) p.removeMetadata("PE-param", PlayEffect.instance);
    }

    public static boolean hasWand(Player p){
        return p.hasMetadata("PE-effect")&&p.hasMetadata("PE-param");
    }

    public static BasicEffect getWandEffect(Player p, Location loc){
        if (hasWand(p)){
            String effect = p.getMetadata("PE-effect").get(0).asString();
            String param = "loc:"+Util.locationToStrLoc(loc)+" "+"loc2:"+Util.locationToStrLoc(p.getLocation())+" "+p.getMetadata("PE-param").get(0).asString();
            BasicEffect be = Effects.createEffect(effect, param);
            return be;
        }
        return null;
    }

    public static void toggleEffect (Player p, Block clicked){
        if (!hasWand (p)) return;
        Location l = clicked.getRelative(BlockFace.UP).getLocation();
        if (l.getBlockY()>=l.getWorld().getMaxHeight()-1) return;
        int remove = Effects.getEffectInLocation(l);
        if (remove<0){
            BasicEffect be = getWandEffect(p,l);
            if (be == null) return;
            if (Effects.createStaticEffect(be, true)) plg().u.printMSG(p, "msg_seteffect",be.toString());
            else plg().u.printMSG(p, "msg_unabletoset");
        } else {
            String efstr = Effects.getStaticEffectStr(remove);
            if (Effects.removeStaticEffect(remove)) plg().u.printMSG(p, "msg_removed",efstr);
            else plg().u.printMSG(p, "msg_removefailed",efstr);
        }

    }

}
