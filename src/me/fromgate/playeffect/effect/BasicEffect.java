package me.fromgate.playeffect.effect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import me.fromgate.playeffect.DrawType;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.Util;
import me.fromgate.playeffect.VisualEffect;


public abstract class BasicEffect {
    VisualEffect type =VisualEffect.BASIC;
    DrawType drawtype = DrawType.NORMAL;

    private Location loc;
    private Location loc2;
    

    Map<String,String> params = new HashMap<String,String>();


    private int radius=0; // radius:X
    private int amount=-1;
    private int chance=100;
    private boolean land = false; // land:true
    private List<Location> cache  = new ArrayList<Location>();


    public void initEffect(VisualEffect effecttype, Location loc, Map<String,String> params){
        this.type = effecttype;
        this.params = params;
        this.loc = loc;
        String loc2str = getParam("loc2","");
        if (!loc2str.isEmpty()) loc2 = Util.parseLocation(loc2str);
        radius = getParam("radius",0);
        chance = getParam("chance",100);
        if (DrawType.contains(getParam("draw","NORMAL"))) drawtype = DrawType.valueOf(getParam("draw","NORMAL").toUpperCase()); 
        amount = getParam("amount",-1);
        onInit();
    }

    public void onInit(){
        // переопределять этот метод если эффекту нужна внутренняя инициализация
    }

    public BasicEffect (){

    }

    public VisualEffect getType(){
        return this.type;
    }

    /* параметры:
     *  radius - рэндомизация локации или радиус для круга и сферы
     *  type:line, circle, sphere
     *  loc2: вторая локация для линии
     *  
     * 
     */


    private List<Location> getDrawLocations(){
        List<Location> locs = new ArrayList<Location>();
        switch (drawtype){
        case NORMAL:
            locs.add(this.randomizeLocation());
            break;
        case CIRCLE:
            if (radius>0) locs = Util.buildCircle(loc, radius);
            else locs.add(loc);
            break;
        case LINE:
            if (loc2 != null)  locs = Util.buildLine(loc, loc2);
            else locs.add(loc);
            break;
        case REGION:
            /* TODO
             * 
             */
            break;
        case PLAIN:
            if (loc2 != null) locs = Util.buildPlain(loc, loc2);
            else locs.add(loc);
            break;
        }
        return locs;
    }


    public void playEffect(){
        List<Location> locs = getDrawLocations();
        locs = Util.refilterLocations(locs, amount);
        if ((locs == null)||locs.isEmpty()) return;
        for (Location effectlocation : locs)
            if (PlayEffect.instance.u.rollDiceChance(chance)) play(effectlocation);
    }

    protected abstract void play(Location loc);


    public void setLocation(Location loc){
        this.loc = loc;
    }

    public Location getLocation(){
        return this.loc;
    }

    private Location randomizeLocation(){
        Location l = this.loc;
        if (radius>0){
            if (cache.isEmpty()){
                for (int x = l.getBlockX()-radius; x<=l.getBlockX()+radius;x++)
                    for (int y = l.getBlockY()-radius; y<=l.getBlockY()+radius;y++)
                        for (int z = l.getBlockZ()-radius; z<=l.getBlockZ()+radius;z++){
                            Location t = new Location (l.getWorld(),x,y,z,l.getPitch(),l.getYaw());
                            if ((l.distance(t)<=radius)||(t.getBlock().getType()!=Material.AIR)){
                                if (land){
                                    if (t.getBlock().getRelative(0, -1, 0).getType()!=Material.AIR) cache.add(t);
                                } else cache.add(t);
                            }
                        }
            }

            if (!cache.isEmpty()){
                l = cache.get(PlayEffect.instance.u.getRandomInt(cache.size()));
            }
        }
        return l;
    }

    public String toString() {
        return this.type.name()+" ["+Util.locationToString(loc)+"]";
    }


    public int getParam(String key, int defvalue){
        if (!params.containsKey(key)) return defvalue;
        if (!PlayEffect.instance.u.isInteger(params.get(key))) return defvalue;
        return Integer.parseInt(params.get(key));
    }


    public float getParam(String key, float defvalue){
        if (!params.containsKey(key)) return defvalue;
        if (!params.get(key).matches("[0-9]+\\.?[0-9]*")) return defvalue;
        return Float.parseFloat(params.get(key));
    }



    public String getParam(String key, String defvalue){
        if (params.containsKey(key)) return params.get(key);
        return defvalue;
    }

    public String getParam(String key){
        if (params.containsKey(key)) return params.get(key);
        return "";
    }


    public boolean getParam(String key, boolean defparam){
        if (params.containsKey(key)) return params.get(key).equalsIgnoreCase("true"); 
        return defparam; 
    }

    public long getRepeatTick(){
        return this.type.getRepeatTicks();
    }

}


