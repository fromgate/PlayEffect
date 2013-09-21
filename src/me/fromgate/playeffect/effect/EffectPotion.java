package me.fromgate.playeffect.effect;

import org.bukkit.Effect;
import org.bukkit.Location;

public class EffectPotion extends BasicEffect {
    
    int clrparam = 0;
    
    /*
     * TODO 
     */
    
    @Override
    public void onInit(){
        clrparam = Math.max(0, Math.min(10, getParam("param",0)));
    }
    
    @Override
    protected void play(Location loc) {
        loc.getWorld().playEffect(loc, Effect.POTION_BREAK,clrparam);
        
        
        //int [] potions = {0,1,2,3,4,5,8,9,10,12};
    }
    
    
}
