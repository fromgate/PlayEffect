package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.Util;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class EffectBlockCrackSound extends BasicEffect{
    private ItemStack block;

    @Override
    public void onInit (){
    	String blockStr = getParam ("item","");
    	if (blockStr.isEmpty()) blockStr = getParam ("block","GLASS:0");
        block = u().parseItemStack(blockStr);
    }

    @Override
    protected void play(Location loc) {
        if ((block == null)||(!block.getType().isBlock())) {
            u().logOnce("blockcracksound"+Util.locationToString(loc), "Failed to play BLOCKCRACKSOUND effect. Wrong block type: "+(getParam ("item","").isEmpty() ? getParam ("block","N/A").isEmpty() : getParam ("item","")));
            return;
        }
        World w = loc.getWorld();
        w.playEffect(loc, Effect.STEP_SOUND, block.getType(),32);
    }


}
