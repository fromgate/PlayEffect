package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.PlayEffect;

import org.bukkit.Location;
import org.bukkit.World;

public class EffectLightning extends BasicEffect{

    private String lightmode = "anytime,day,night,day-storm,night-storm,storm";
    private int chance = 50;
    private String mode = "storm";


    @Override
    public void onInit(){
        chance = getParam("lchance",100);
        mode = getParam("mode","anytime");
        if (!PlayEffect.instance.u.isWordInList(mode, lightmode)) mode = "storm";
    }


    @Override
    protected void play(Location loc) {
        if (isTimeToBolt(loc.getWorld()))  loc.getWorld().strikeLightningEffect(loc);
    }

    private boolean isTimeToBolt(World w){
        if (!PlayEffect.instance.u.rollDiceChance(chance)) return false;
        if (mode.contains("night")&&day(w)) return false;
        if (mode.contains("day")&&(!day(w))) return false;
        if (mode.contains("storm")&&(!w.hasStorm())) return false;
        return true;
    }

    private boolean day(World w) {
        long time = w.getTime();
        return time < 12300 || time > 23850;
    }

}
