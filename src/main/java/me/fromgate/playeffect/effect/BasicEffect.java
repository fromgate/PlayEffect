package me.fromgate.playeffect.effect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitTask;

import me.fromgate.playeffect.DrawType;
import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.Util;
import me.fromgate.playeffect.VisualEffect;


public abstract class BasicEffect {
    VisualEffect type =VisualEffect.BASIC;
    DrawType drawtype = DrawType.NORMAL;
    private Location loc;
    private Location loc2;
    private Long dur = 0L;
    private Long ttl = 0L;
    private Long freq = 0L;
    private BukkitTask task;
    Map<String,String> params = new HashMap<String,String>();
    private int radius=0; // radius:X
    private int amount=-1;
    private int chance=100;
    private boolean land = false; // land:true
    private List<Location> cache  = new ArrayList<Location>();

    Util u(){
        return PlayEffect.instance.u;
    }
    
    public void initEffect(VisualEffect effecttype, Location loc, Map<String,String> params){
        this.type = effecttype;
        this.params = params;
        this.loc = loc;
        this.land = getParam("land",false);
        dur =  u().parseTime(getParam("dur", "0"));
        if (dur>0) freq = Math.max(type.getRepeatTicks(), u().timeToTicks(u().parseTime(getParam("freq", type.getRepeatTicks()+"t"))));
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

    public VisualEffect getType(){
        return this.type;
    }


    private List<Location> getDrawLocations(){
        List<Location> locs = new ArrayList<Location>();
        switch (drawtype){
        case NORMAL:
            locs.add(this.randomizeLocation());
            break;
        case CIRCLE:
            if (radius>0) locs = buildCircle(loc, radius);
            else locs.add(loc);
            break;
        case LINE:
            if (loc2 != null)  locs = buildLine(loc, loc2);
            else locs.add(loc);
            break;
        case AREA:
            if (loc2 != null) locs = buildCuboid(loc, loc2, this.land);
            else locs.add(loc);
            break;
        case PLAIN:
            if (loc2 != null) locs = buildPlain(loc, loc2);
            else locs.add(loc);
            break;
        }
        return locs;
    }

    private List<Location> buildPlain(Location loc1, Location loc2) {
        if (cache.isEmpty()) cache = Util.buildPlain(loc1, loc2);
        return cache;
    }

    private List<Location> buildCuboid(Location loc1, Location loc2,boolean land) {
        if (cache.isEmpty()) cache = Util.buildCuboid(loc, loc2, land);
        return cache;
    }

    private List<Location> buildCircle(Location loc, int radius) {
        if (cache.isEmpty()) cache = Util.buildCircle(loc, radius);
        return cache;
    }

    private boolean rollDice(){
        return PlayEffect.instance.u.rollDiceChance(this.chance);
    }

    public void playEffect(){
        if (dur>0){
            Long ct = System.currentTimeMillis();
            if ((ttl!=0)&&(ct<ttl)) return;
            ttl = dur+ct;
            playMultipleEffect();
        } else playSingleEffect();
    }

    public void playMultipleEffect(){
        if (dur<=0) return;
        if (System.currentTimeMillis()>ttl) return;
        playSingleEffect();
        if ((task==null)||(!Bukkit.getScheduler().isCurrentlyRunning(task.getTaskId()))){
            task = Bukkit.getScheduler().runTaskLater(PlayEffect.instance, new Runnable(){
                @Override
                public void run(){
                    playMultipleEffect();
                }
            }, this.freq);
        }
    }

    public void playSingleEffect(){
        List<Location> locs = getDrawLocations();
        locs = Util.refilterLocations(locs, amount);
        if ((locs == null)||locs.isEmpty()) return;
        for (Location effectlocation : locs)
            if (rollDice()) play(effectlocation);
    }


    protected abstract void play(Location loc);


    public void setLocation(Location loc){
        this.loc = loc;
    }

    public Location getLocation(){
        return this.loc;
    }

    private List<Location> buildLine(Location loc1, Location loc2){
        if (cache.isEmpty()) cache = Util.buildLine(loc, loc2);
        return cache;
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
        String param = "";
        for (String pstr : this.params.keySet()){
            if (pstr.equalsIgnoreCase("id")) continue;
            if (pstr.equalsIgnoreCase("effect")) continue;
            if (!type.isValidParam(pstr)) continue;
            if (param.isEmpty()) param = pstr+":"+params.get(pstr);
            else param = param+", "+pstr+":"+params.get(pstr);
        }
        return this.type.name()+" "+Util.locationToString(loc);
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
        return Math.max(type.getRepeatTicks(), u().timeToTicks(this.dur));
    }

    public DrawType getDrawType(){
        return this.drawtype;
    }

    public void setParam(String key, String value){
        params.put(key, value);
    }

    public void stopEffect(){
        if (task==null) return;
        if (Bukkit.getScheduler().isCurrentlyRunning(task.getTaskId()))
            task.cancel();
    }

}


