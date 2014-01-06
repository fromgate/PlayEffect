package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.PlayEffect;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;


public class EffectSmoke extends BasicEffect {

    private int param=4;

    @Override
    public void onInit (){
        this.param = parseSmokeDirection (getParam ("wind","all"));
    }

    @Override
    protected void play(Location loc) {
        World w = loc.getWorld();
        if (this.param<9) w.playEffect(loc, Effect.SMOKE, this.param);
        else if (this.param==9) for (int i = 0; i<9;i++) w.playEffect(loc, Effect.SMOKE, i);
        else if (this.param == 10) w.playEffect(loc, Effect.SMOKE, PlayEffect.instance.u.getRandomInt(9));
    }


    private int parseSmokeDirection (String dir_str) {
        if (dir_str.equalsIgnoreCase("n")||dir_str.equalsIgnoreCase("north")) return 7;
        if (dir_str.equalsIgnoreCase("nw")||dir_str.equalsIgnoreCase("northwest")) return 8;
        if (dir_str.equalsIgnoreCase("ne")||dir_str.equalsIgnoreCase("northeast")) return 6;
        if (dir_str.equalsIgnoreCase("s")||dir_str.equalsIgnoreCase("south")) return 1;
        if (dir_str.equalsIgnoreCase("sw")||dir_str.equalsIgnoreCase("southwest")) return 2;
        if (dir_str.equalsIgnoreCase("se")||dir_str.equalsIgnoreCase("southeast")) return 0;
        if (dir_str.equalsIgnoreCase("w")||dir_str.equalsIgnoreCase("west")) return 5;
        if (dir_str.equalsIgnoreCase("e")||dir_str.equalsIgnoreCase("east")) return 3;
        if (dir_str.equalsIgnoreCase("calm")||dir_str.equalsIgnoreCase("up")) return 4;
        if (dir_str.equalsIgnoreCase("all")) return 9;
        if (dir_str.equalsIgnoreCase("rnd")||dir_str.equalsIgnoreCase("random")) return 10;
        return 10;
    }






}
