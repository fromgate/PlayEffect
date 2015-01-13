package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.customeffects.AdditionalEffects;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

public class EffectParticles extends BasicEffect {
    private String effectname = "cloud";
    private int id=0;
    private int data=0;
    private float offsetX = 0f;  
    private float offsetY = 0f;
    private float offsetZ = 0f;
    private float speed = 0.1f;
    private int number = 10;
    private boolean disabled = false;
    


    @Override
    @SuppressWarnings("deprecation")
    public void onInit(){
        effectname = getParam ("effectname","cloud");
        String itemStr = getParam("block",getParam("item"));
        if (!itemStr.isEmpty()){
        	ItemStack item = PlayEffect.instance.u.parseItemStack(itemStr);
            id=item.getType().getId();
            data=item.getDurability();
        }
        number = getParam("num", number);
        speed = getParam("speed",0.1f);
        offsetX = getParam("offsetX",getParam("offset",0f));
        offsetY = getParam("offsetY",getParam("offset",0f));
        offsetZ = getParam("offsetZ",getParam("offset",0f));
    }

    @Override
    protected void play(Location l) {
    	if (disabled) return;
    	if (l==null) return;
        Location loc = l;
        loc.setX(loc.getBlockX()+0.5);
        loc.setY(loc.getBlockY()+0.5);
        if (effectname.equalsIgnoreCase("footstep")) {
            loc.setY(loc.getBlockY());
            offsetY= 0f;
        } else if (effectname.equalsIgnoreCase("drip_lava")||effectname.equalsIgnoreCase("drip_water")) {
            loc.setY(loc.getBlockY()+0.9);
            offsetY= 0f;
        }
        loc.setZ(loc.getBlockZ()+0.5);
        AdditionalEffects.sendParticlesPacket (loc, effectname, id, data, offsetX, offsetY, offsetZ, speed, number);
    }

}

