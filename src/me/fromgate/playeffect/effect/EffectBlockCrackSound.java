package me.fromgate.playeffect.effect;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

public class EffectBlockCrackSound extends BasicEffect{

    private ItemStack block; 

    @Override
    public void onInit (){
        block = u().parseItemStack(getParam ("item","GLASS:0"));
    }

    @Override
    protected void play(Location loc) {
        if ((block == null)||(!block.getType().isBlock())) {
            u().logOnce("blockcracksound"+getParam ("item","GLASS:0"), "Failed to player BLOCKCRACKSOUND effect. Wrong block type: "+getParam ("item","GLASS:0"));
            return;
        }
        World w = loc.getWorld();
        w.playEffect(loc, Effect.STEP_SOUND, block.getType(),32);
    }


}
