package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.NMSLib;
import me.fromgate.playeffect.PlayEffect;

import org.bukkit.Location;

public class EffectParticles extends BasicEffect {
    private String effectname = "cloud";
    private float offsetX = 0f;
    private float offsetY = 0f;
    private float offsetZ = 0f;
    private float speed = 0.1f;
    private int number = 10;
    
    @Override
    public void onInit(){
        effectname = getParam ("effectname","cloud");
        if (effectname.equalsIgnoreCase("tilecrack_")) effectname = effectname + PlayEffect.instance.u.parseItemStack(getParam("item","GLASS:0")).getTypeId()+"_"+
                PlayEffect.instance.u.parseItemStack(getParam("item","GLASS:0")).getDurability();
        else if (effectname.equalsIgnoreCase("iconcrack_")) effectname = effectname + PlayEffect.instance.u.parseItemStack(getParam("item","GLASS:0")).getTypeId();
        number = getParam("num", number);
        speed = getParam("speed",0.1f);
        offsetX = getParam("offsetX",getParam("offset",0f));
        offsetY = getParam("offsetY",getParam("offset",0f));
        offsetZ = getParam("offsetZ",getParam("offset",0f));
    }
    
    @Override
    protected void play(Location l) {
        Location loc = l;
        loc.setX(loc.getBlockX()+0.5);
        loc.setY(loc.getBlockY()+0.5);
        if (effectname.equalsIgnoreCase("footstep")) {
            loc.setY(loc.getBlockY());
            offsetY= 0f;
        } else if (effectname.equalsIgnoreCase("dripLava")||effectname.equalsIgnoreCase("dripWater")) {
            loc.setY(loc.getBlockY()+0.9);
            offsetY= 0f;
        }
        loc.setZ(loc.getBlockZ()+0.5);
        NMSLib.sendParticlesPacket(loc, effectname, offsetX, offsetY, offsetZ, speed, number);
    }
    
    
    
    
    /*
     * 
     * 
     * Smoke    "smoke"
Large Smoke     "largesmoke"
Block b reaking anim.    "iconcrack_"
Snowball/Egg break/Iron Golem creation  "snowballpoof"
Tool break  "tilecrack_"
Nether Portal/Eye of Ender /Dragon egg/enderman and ender chest     "portal"
Water Splash    "splash"
Water Bubbles   "bubble"
Mycelium spores     "townaura"
Explosion   "hugeexplosion"
Flame   "flame"
Heart   "heart"
Cloud   "cloud"
Critical Hit Spark  "crit"
Magic Weapon Critical Spark     "magicCrit"
Note Block  "note"
Magic Runes     "enchantmenttable"
Lava Spark  "lava"
Footsteps   "footstep"
Redstone Fumes  "reddust"
Water Dripping  "dripWater"
Lava Dripping   "dripLava"
Slime Splatter  "slime"
     * 
     * 
     * 
hugeexplosion
largeexplode
fireworksSpark

- bubble
suspended
depthsuspend
+ townaura

crit
magicCrit
smoke
mobSpell
mobSpellAmbient
spell
instantSpell
+ witchMagic
+ note
+ portal
+ enchantmenttable //RUNES
+ explode
+ flame
+ lava
+ footstep
+ splash
+ largesmoke
+ cloud
+ reddust
+ snowballpoof
+ dripWater
+ dripLava
+ snowshovel
+ slime
+ heart
+ angryVillager
+ happyVillager
iconcrack_*
tilecrack_*_*
     */
}

