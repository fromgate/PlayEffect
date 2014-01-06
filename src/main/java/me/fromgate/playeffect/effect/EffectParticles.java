package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.NMSLib;
import me.fromgate.playeffect.PlayEffect;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class EffectParticles extends BasicEffect {
    private String effectname = "cloud";
    private float offsetX = 0f;
    private float offsetY = 0f;
    private float offsetZ = 0f;
    private float speed = 0.1f;
    private int number = 10;

    @Override
    @SuppressWarnings("deprecation")
    public void onInit(){
        effectname = getParam ("effectname","cloud");
        if (NMSLib.getVersion().startsWith("v1_6")||NMSLib.getVersion().startsWith("v1_5")){
            if (effectname.equalsIgnoreCase("blockcrack_")) effectname = "tilecrack_";
            if (effectname.equalsIgnoreCase("blockdust_")) effectname = "tilecrack_";
        } else if (effectname.equalsIgnoreCase("tilecrack_")) effectname = "blockcrack_";
        if (effectname.endsWith("_")) {
            ItemStack item = PlayEffect.instance.u.parseItemStack(getParam("item","GLASS:0"));
            if (item==null){
                u().logOnce("itemparsefail_"+getParam("item","GLASS:0"), "Failed to play effect "+this.getType().name()+". Wrong block (item) type: "+getParam("item","GLASS:0"));
                return;
            }
            effectname = effectname + item.getTypeId()+"_"+item.getDurability();
        }

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

}

