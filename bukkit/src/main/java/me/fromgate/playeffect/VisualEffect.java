/*  
 *  PlayEffect, Minecraft bukkit plugin
 *  (c)2013-2016, fromgate, fromgate@gmail.com
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

import me.fromgate.playeffect.effect.*;
import org.bukkit.Particle;

public enum VisualEffect {


    BASIC("basic", BasicEffect.class, "", 0, "id,time,loc,loc2,radius,amount,chance,land,draw,freq,dur"),

    POTION("POTION_BOTTLE", EffectPotion.class, "", 5, "param"),

    ENDER_SIGNAL("SIGNAL", EffectSignal.class, "", 5, ""),
    ENDER_EYE("EYE", EffectEye.class, "", 5, ""),
    PORTAL("ENDER_PORTAL", Particle.PORTAL, "effectname:portal num:30", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),

    LIGHTNING("LIGHTNING", EffectLightning.class, "", 128, 5, "lchance,mode"),

    EXPLOSION("EXPLOSION_OLD", EffectExplosion.class, "", 5, ""),
    EXPLOSION_HUGE("HUGEEXPLOSION", Particle.EXPLOSION_HUGE, "effectname:hugeexplosion num:1", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    EXPLOSION_LARGE("LARGEEXPLODE", Particle.EXPLOSION_LARGE, "effectname:largeexplode num:10 speed:3 offset:1", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    EXPLOSION_NORMAL("EXPLODE", Particle.EXPLOSION_NORMAL, "effectname:explode num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),

    FIREWORKS_EXPLODE("FIREWORK", EffectFirework.class, "color:random", 128, 20, "type,color"),
    FIREWORKS_SPARK("SPARK", Particle.FIREWORKS_SPARK, "effectname:fireworksSpark num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),

    WATER_BUBBLE("BUBBLE", Particle.WATER_BUBBLE, "effectname:bubble num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    WATER_SPLASH("SPLASH", Particle.WATER_SPLASH, "effectname:splash num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    WATER_WAKE("WAKE", Particle.WATER_WAKE, "effectname:wake num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    WATER_DROP("WATER_DROP", Particle.WATER_DROP, "effectname:droplet num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),

    SUSPENDED("SUSPEND", Particle.SUSPENDED, "effectname:suspended num:25", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    SUSPENDED_DEPTH("DEPTHSUSPEND", Particle.SUSPENDED_DEPTH, "effectname:depthsuspend num:25", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),

    CRIT("CRIT", Particle.CRIT, "effectname:crit num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    CRIT_MAGIC("MAGICCRIT", Particle.CRIT_MAGIC, "effectname:magicCrit num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),

    SMOKE("smoke", EffectSmoke.class, "", 5, "wind"),
    SMOKE_NORMAL("SMOKE_NORMAL", Particle.SMOKE_NORMAL, "effectname:smoke num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    SMOKE_LARGE("LARGESMOKE", Particle.SMOKE_LARGE, "effectname:largesmoke num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),

    SPELL("SPELL", Particle.SPELL, "effectname:spell num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    SPELL_INSTANT("INSTANTSPELL", Particle.SPELL_INSTANT, "effectname:instantSpell num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    SPELL_MOB("MOBSPELL", Particle.SPELL_MOB, "effectname:mobSpell num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    SPELL_MOB_AMBIENT("MOBSPELLAMBIENT", Particle.SPELL_MOB_AMBIENT, "effectname:mobSpellAmbient num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    SPELL_WITCH("WITCHMAGIC", Particle.SPELL_WITCH, "effectname:witchMagic num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),

    DRIP_WATER("DRIPWATER", Particle.DRIP_WATER, "effectname:dripWater num:5 offsetY:0 offsetX:0.8 offsetZ:0.8", 64, 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    DRIP_LAVA("DRIPLAVA", Particle.DRIP_LAVA, "effectname:dripLava num:5 offsetY:0 offsetX:0.8 offsetZ:0.8", 64, 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),

    VILLAGER_ANGRY("ANGRY", Particle.VILLAGER_ANGRY, "effectname:angryVillager num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    VILLAGER_HAPPY("HAPPY", Particle.VILLAGER_HAPPY, "effectname:happyVillager num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),

    FLAME("FLAMENEW", Particle.FLAME, "effectname:flame num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    FLAME_SPAWNER("flame_old", EffectFlame.class, "", 5, ""),

    TOWN_AURA("TOWNAURA", Particle.TOWN_AURA, "effectname:townaura num:25", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    NOTE("NOTE", Particle.NOTE, "effectname:note num:1", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    ENCHANTMENT_TABLE("RUNES", Particle.ENCHANTMENT_TABLE, "effectname:enchantmenttable num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    LAVA("LAVA", Particle.LAVA, "effectname:lava num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    FOOTSTEP("FOOTSTEP", Particle.FOOTSTEP, "effectname:footstep num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    CLOUD("CLOUD", Particle.CLOUD, "effectname:cloud num:10", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    REDSTONE("REDDUST", Particle.REDSTONE, "effectname:reddust num:5 speed:0", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    SNOWBALL("SNOWBALL", Particle.SNOWBALL, "effectname:snowballpoof num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    SNOW_SHOVEL("SNOWSHOVEL", Particle.SNOW_SHOVEL, "effectname:snowshovel num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),

    SLIME("SLIME", Particle.SLIME, "effectname:slime num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    HEART("HEART", Particle.HEART, "effectname:heart num:2", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),
    BARRIER("BARRIER", Particle.BARRIER, "effectname:barrier num:", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),

    ITEM_CRACK("ICONCRACK", Particle.ITEM_CRACK, "effectname:iconcrack_ num:5 item:iron_sword:0", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step,item"),
    BLOCK_CRACK("BLOCKCRACK", Particle.BLOCK_CRACK, "effectname:blockcrack_ num:5 block:GLASS:0", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step,item,block"),
    BLOCK_CRACK_SOUND("BLOCKCRACKSOUND", EffectBlockCrackSound.class, "block:GLASS:0", 5, "item,block"),
    BLOCK_DUST("BLOCKDUST", Particle.BLOCK_DUST, "effectname:blockdust_ num:5 block:GLASS:0", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step,item,block"),
    FALLING_DUST("FALLDUST", Particle.FALLING_DUST, "effectname:falldust num:5 block:GLASS:0", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step,item,block"),
    ITEM_TAKE("ITEM_TAKE", Particle.ITEM_TAKE, "effectname:take num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step,item,block"),
    MOB_APPEARANCE("MOB_APPEARANCE", Particle.MOB_APPEARANCE, "effectname:mobappearance num:5", 5, "num,speed,offset,offsetX,offsetY,offsetZ,step"),

    SOUND("SOUND", EffectSound.class, "", 128, 10, "type,pitch,volume"),
    SONG("SONG", EffectSong.class, "", 128, 5, "disc");

    private Class<? extends BasicEffect> clazz;
    private String alias; //Old name!
    private String param;
    private long min_rpt_time;
    private String paramlist;
    private int distance;
    private Particle spigotParticle;

    VisualEffect(String alias, Class<? extends BasicEffect> clazz, String param, int distance, long minrpt, String paramlist) {
        this.alias = alias;
        this.clazz = clazz;
        this.param = param;
        this.min_rpt_time = minrpt;
        this.paramlist = paramlist;
        this.distance = distance;
        this.spigotParticle = null;
    }

    VisualEffect(String alias, Class<? extends BasicEffect> clazz, String param, long minrpt, String paramlist) {
        this.alias = alias;
        this.clazz = clazz;
        this.param = param;
        this.min_rpt_time = minrpt;
        this.paramlist = paramlist;
        this.distance = 32;
        this.spigotParticle = null;
    }


    VisualEffect(String alias, Particle spigotParticle, String param, long minrpt, String paramlist) {
        this.alias = alias;
        this.clazz = EffectParticles.class;
        this.spigotParticle = spigotParticle;
        this.param = param;
        this.min_rpt_time = minrpt;
        this.paramlist = paramlist;
        this.distance = 32;
    }


    VisualEffect(String alias, Particle spigotParticle, String param, int distance, long minrpt, String paramlist) {
        this.alias = alias;
        this.clazz = EffectParticles.class;
        this.spigotParticle = spigotParticle;
        this.param = param;
        this.min_rpt_time = minrpt;
        this.paramlist = paramlist;
        this.distance = distance;
    }

    public String getAlias() {
        return this.alias;
    }

    public int getVisibilityDistance() {
        return this.distance;
    }

    public Class<? extends BasicEffect> getClazz() {
        return this.clazz;
    }

    public String getParam() {
        return this.param;
    }

    public String getParamList() {
        return this.paramlist;
    }

    public boolean isValidParam(String param) {
        return PlayEffectPlugin.instance.u.isWordInList(param, BASIC.getParamList()) || PlayEffectPlugin.instance.u.isWordInList(param, getParamList());
    }

    public static boolean isValidParameter(String param) {
        for (VisualEffect ve : VisualEffect.values())
            if (PlayEffectPlugin.instance.u.isWordInList(param, ve.getParamList())) return true;
        return false;
    }

    public long getRepeatTicks() {
        return this.min_rpt_time;
    }

    public static boolean contains(String id) {
        String vn = id;
        if (vn.equalsIgnoreCase("tilecrack")) vn = "blockcrack";
        for (VisualEffect ve : VisualEffect.values()) {
            if (ve == VisualEffect.BASIC) continue;
            if (ve.name().equalsIgnoreCase(vn)) return true;
            if (ve.getAlias().equalsIgnoreCase(vn)) return true;
        }
        return false;
    }

    public static VisualEffect getEffectByName(String name) {
        String vn = name;
        if (vn.equalsIgnoreCase("tilecrack")) vn = "BLOCK_CRACK";
        for (VisualEffect ve : VisualEffect.values()) {
            if (ve.name().equalsIgnoreCase(vn)) return ve;
            if (ve.getAlias().equalsIgnoreCase(vn)) return ve;
        }
        return null;
    }


    public Particle getParticle() {
        return this.spigotParticle;
    }
}

