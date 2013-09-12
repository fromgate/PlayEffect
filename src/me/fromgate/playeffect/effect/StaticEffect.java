package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.PlayEffect;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class StaticEffect {

    private BasicEffect effect;
    private long repeat_ticks=5L;
    private boolean enabled  = true;
    
    ////////////////////////////////////////////////////////////////////////////////
    private String id="";
    private BukkitTask task;;
    
    
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
        task = Bukkit.getScheduler().runTaskTimerAsynchronously(PlayEffect.instance, new Runnable(){
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
    
    
    
    
}
