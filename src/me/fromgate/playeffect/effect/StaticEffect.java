package me.fromgate.playeffect.effect;

import java.util.ArrayList;
import java.util.List;

import me.fromgate.playeffect.PlayEffect;
import me.fromgate.playeffect.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitTask;

public class StaticEffect {

    private String id="";
    private long repeat_ticks=5L;
    private boolean enabled  = true;

    ////////////////////////////////////////////////////////////////////////////////    
    private BasicEffect effect;
    private BukkitTask task;


    public StaticEffect(String id, BasicEffect effect, long rpt_interval){
        this.effect = effect;
        this.id = id;
        this.repeat_ticks = Math.max(rpt_interval, effect.getRepeatTick());
        if (enabled) startRepeater();
    }

    public String getId(){
        return id;
    }


    public void startRepeater(){
        if (!enabled) return;
        task = Bukkit.getScheduler().runTaskTimer(PlayEffect.instance, new Runnable(){
            @Override
            public void run() {
                effect.playEffect();
            }
        }, 10L, repeat_ticks);
    }

    public void stopRepeater(){
        task.cancel();
        task = null;
    }

    public void setEnabled(boolean enable){
        if(enabled&&(!enable)) stopRepeater();
        if ((!enabled)&&enable) startRepeater();
        this.enabled = enable;
    }

    public boolean isEnabled(){
        return enabled;
    }

    public void saveEffect(YamlConfiguration cfg){
        String uid = id+"@"+this.effect.getLocation().hashCode()+".";
        cfg.set(uid+"id", getId());
        cfg.set(uid+"effect", effect.type.name());
        for (String key : effect.params.keySet()){
            if (key.equalsIgnoreCase("id")) continue;
            if (key.equalsIgnoreCase("loc2")&&(!effect.getDrawType().isUsingSecondLoc())) continue;
            if (!effect.getType().isValidParam(key)) continue;
            cfg.set(uid+key, effect.params.get(key));
        }
    }

    @Override
    public String toString(){
        return id+" : "+this.effect.toString();
    }

    public List<String> getInfo(){
        List<String> info = new ArrayList<String>();
        info.add(ChatColor.translateAlternateColorCodes('&',"&3"+ getId() +" &e"+Util.locationToString(effect.getLocation())));
        for (String key : effect.params.keySet()){
            if (key.equalsIgnoreCase("id")) continue;
            if (key.equalsIgnoreCase("loc")) continue;
            if (key.equalsIgnoreCase("param")) continue;
            String param = effect.params.get(key);
            if (key.equalsIgnoreCase("loc2")) param = Util.locationToString(Util.parseLocation(param));
            info.add("&2"+key + " &e= &a"+param);
        }
        return info;
    }

    public String getParam(){
        //this.effect.params
        return null;
    }

    public boolean inLocation(Location loc){
        if (!effect.getLocation().getWorld().equals(loc.getWorld())) return false;
        if (effect.getLocation().getBlockX() != loc.getBlockX()) return false;
        if (effect.getLocation().getBlockY() != loc.getBlockY()) return false;
        return (effect.getLocation().getBlockY() == loc.getBlockY());
    }

}
