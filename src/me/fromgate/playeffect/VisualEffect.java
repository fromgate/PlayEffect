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
import me.fromgate.playeffect.effect.EffectBlockCrackSound;
import me.fromgate.playeffect.effect.EffectExplosion;
import me.fromgate.playeffect.effect.EffectEye;
import me.fromgate.playeffect.effect.EffectFirework;
import me.fromgate.playeffect.effect.EffectFlame;
import me.fromgate.playeffect.effect.EffectLightning;
import me.fromgate.playeffect.effect.EffectParticles;
import me.fromgate.playeffect.effect.EffectPotion;
import me.fromgate.playeffect.effect.EffectSignal;
import me.fromgate.playeffect.effect.EffectSmoke;
import me.fromgate.playeffect.effect.EffectSong;
import me.fromgate.playeffect.effect.EffectSound;

public enum VisualEffect  {
	
	
	
    BASIC("basic", BasicEffect.class,"",0,"id,time,loc,loc2,radius,amount,chance,land,draw,freq,dur"),
    
    POTION ("POTION_BOTTLE", EffectPotion.class,"",5,"param"),
    
    ENDER_SIGNAL ("SIGNAL", EffectSignal.class,"",5,""),
    ENDER_EYE ("EYE", EffectEye.class,"",5,""),
    PORTAL("ENDER_PORTAL", EffectParticles.class,"effectname:portal num:30",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    
    LIGHTNING ("LIGHTNING", EffectLightning.class,"",128,5,"lchance,mode"),
    
    EXPLOSION ("EXPLOSION_OLD", EffectExplosion.class,"",5,""),
    EXPLOSION_HUGE ("HUGEEXPLOSION", EffectParticles.class,"effectname:hugeexplosion num:1",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    EXPLOSION_LARGE ("LARGEEXPLODE", EffectParticles.class,"effectname:largeexplode num:10 speed:3 offset:1",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    EXPLOSION_NORMAL ("EXPLODE", EffectParticles.class,"effectname:explode num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    
    FIREWORKS_EXPLODE("FIREWORK", EffectFirework.class,"color:random",128,20,"type,color"),
    FIREWORKS_SPARK ("SPARK", EffectParticles.class,"effectname:fireworksSpark num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    
    WATER_BUBBLE ("BUBBLE", EffectParticles.class,"effectname:bubble num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    WATER_SPLASH("SPLASH", EffectParticles.class,"effectname:splash num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    WATER_WAKE("WAKE", EffectParticles.class,"effectname:wake num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    WATER_DROP("WATER_DROP",EffectParticles.class,"effectname:droplet num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    
    SUSPENDED("SUSPEND", EffectParticles.class,"effectname:suspended num:25",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SUSPENDED_DEPTH("DEPTHSUSPEND", EffectParticles.class,"effectname:depthsuspend num:25",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    
    CRIT("CRIT", EffectParticles.class,"effectname:crit num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    CRIT_MAGIC("MAGICCRIT", EffectParticles.class,"effectname:magicCrit num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    
    SMOKE ("smoke", EffectSmoke.class,"",5,"wind"),
    SMOKE_NORMAL("SMOKE_NORMAL", EffectParticles.class,"effectname:smoke num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SMOKE_LARGE("LARGESMOKE", EffectParticles.class,"effectname:largesmoke num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),

    SPELL ("SPELL", EffectParticles.class,"effectname:spell num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SPELL_INSTANT ("INSTANTSPELL", EffectParticles.class,"effectname:instantSpell num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SPELL_MOB ("MOBSPELL", EffectParticles.class,"effectname:mobSpell num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SPELL_MOB_AMBIENT ("MOBSPELLAMBIENT", EffectParticles.class,"effectname:mobSpellAmbient num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SPELL_WITCH ("WITCHMAGIC", EffectParticles.class,"effectname:witchMagic num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),

    DRIP_WATER("DRIPWATER", EffectParticles.class,"effectname:dripWater num:5 offsetY:0 offsetX:0.8 offsetZ:0.8",64,5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    DRIP_LAVA("DRIPWATER", EffectParticles.class,"effectname:dripLava num:5 offsetY:0 offsetX:0.8 offsetZ:0.8",64,5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    
    VILLAGER_ANGRY("ANGRY", EffectParticles.class,"effectname:angryVillager num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    VILLAGER_HAPPY("HAPPY", EffectParticles.class,"effectname:happyVillager num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    
    FLAME ("FLAMENEW", EffectParticles.class,"effectname:flame num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    FLAME_SPAWNER ("flame_old", EffectFlame.class,"",5,""),
    
    TOWN_AURA ("TOWNAURA", EffectParticles.class,"effectname:townaura num:25",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    NOTE ("NOTE", EffectParticles.class,"effectname:note num:1",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    ENCHANTMENT_TABLE ("RUNES", EffectParticles.class,"effectname:enchantmenttable num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    LAVA ("LAVA", EffectParticles.class,"effectname:lava num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    FOOTSTEP("FOOTSTEP", EffectParticles.class,"effectname:footstep num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    CLOUD("CLOUD", EffectParticles.class,"effectname:cloud num:10",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    REDSTONE("REDDUST",EffectParticles.class,"effectname:reddust num:5 speed:0",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SNOWBALL("SNOWBALL", EffectParticles.class,"effectname:snowballpoof num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SNOW_SHOVEL("SNOWSHOVEL", EffectParticles.class,"effectname:snowshovel num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),

    SLIME("SLIME", EffectParticles.class,"effectname:slime num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    HEART("HEART", EffectParticles.class,"effectname:heart num:2",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    BARRIER("BARRIER", EffectParticles.class,"effectname:barrier num:",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    
    ITEM_CRACK ("ICONCRACK", EffectParticles.class,"effectname:iconcrack_ num:5 item:iron_sword:0",5,"num,speed,offset,offsetX,offsetY,offsetZ,item"),
    BLOCK_CRACK("BLOCKCRACK", EffectParticles.class,"effectname:blockcrack_ num:5 block:GLASS:0",5,"num,speed,offset,offsetX,offsetY,offsetZ,item,block"),
//  TILECRACK(EffectParticles.class,"effectname:tilecrack_ num:5 block:GLASS:0",5,"num,speed,offset,offsetX,offsetY,offsetZ,item,block"),
    BLOCK_CRACK_SOUND("BLOCKCRACKSOUND", EffectBlockCrackSound.class,"block:GLASS:0",5,"item,block"),
    BLOCK_DUST ("BLOCKDUST",EffectParticles.class,"effectname:blockdust_ num:5 block:GLASS:0",5,"num,speed,offset,offsetX,offsetY,offsetZ,item,block"),
    
    ITEM_TAKE("ITEM_TAKE",EffectParticles.class,"effectname:take num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ,item,block"),
    MOB_APPEARANCE("MOB_APPEARANCE",EffectParticles.class,"effectname:mobappearance num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    
    SOUND("SOUND", EffectSound.class,"",128,10,"type,pitch,volume"),
    SONG("SONG", EffectSong.class,"",128,5,"disc");

    private Class<? extends BasicEffect > clazz;
    private String alias; //Old name!
    private String param;
    private long min_rpt_time;
    private String paramlist;
    private int distance;
    
    private VisualEffect (String alias, Class<? extends BasicEffect> clazz, String param, int distance, long minrpt, String paramlist){
    	this.alias = alias;
        this.clazz = clazz;
        this.param = param;
        this.min_rpt_time= minrpt;
        this.paramlist=paramlist;
        this.distance = distance;
    }
    
    private VisualEffect (String alias, Class<? extends BasicEffect> clazz, String param, long minrpt, String paramlist){
    	this.alias = alias;
        this.clazz = clazz;
        this.param = param;
        this.min_rpt_time= minrpt;
        this.paramlist=paramlist;
        this.distance = 32;
    }

    public String getAlias(){
    	return this.alias;
    }
    
    public int getVisibilityDistance(){
        return this.distance;
    }
    
    public Class<? extends BasicEffect> getClazz(){
        return this.clazz;
    }

    public String getParam(){
        return this.param;
    }

    public String getParamList(){
        return this.paramlist;
    }

    public boolean isValidParam(String param){
        return PlayEffect.instance.u.isWordInList(param, BASIC.getParamList())||PlayEffect.instance.u.isWordInList(param, getParamList());
    }

    public static boolean isValidParameter (String param){
        for (VisualEffect ve :  VisualEffect.values())
            if (PlayEffect.instance.u.isWordInList(param, ve.getParamList())) return true;
        return false;
    }

    public long getRepeatTicks(){
        return this.min_rpt_time;
    }

    public static boolean contains(String id){
        String vn = id;
        if (vn.equalsIgnoreCase("tilecrack")) vn = "blockcrack";
        for (VisualEffect ve :VisualEffect.values()){
            if (ve == VisualEffect.BASIC) continue;
            if (ve.name().equalsIgnoreCase(vn)) return true;
            if (ve.getAlias().equalsIgnoreCase(vn)) return true;
        }
        return false;
    }

    public static VisualEffect getEffectByName(String name){
        String vn = name;
        if (vn.equalsIgnoreCase("tilecrack")) vn = "BLOCK_CRACK";
        for (VisualEffect ve : VisualEffect.values()){
            if (ve.name().equalsIgnoreCase(vn)) return ve;
            if (ve.getAlias().equalsIgnoreCase(vn)) return ve;
        }
        return null;
    }
    

}

