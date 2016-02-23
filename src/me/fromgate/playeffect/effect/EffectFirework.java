package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.EffectColor;
import me.fromgate.playeffect.PlayEffectPlugin;
import me.fromgate.playeffect.customeffects.AdditionalEffects;
import me.fromgate.playeffect.customeffects.PacketNMS;
import me.fromgate.playeffect.firework.InstantFireworks18R1;
import me.fromgate.playeffect.firework.InstantFireworks18R2;
import me.fromgate.playeffect.firework.InstantFireworks18R3;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

public class EffectFirework extends BasicEffect {
	private FireworkEffect.Type ftype = FireworkEffect.Type.BALL; 
	private Color color = null;
	boolean ftypernd = false;
	boolean colorrnd = false;

	@Override
	public void onInit(){
		String ftstr = this.getParam("type", "random");
		if (ftstr.equalsIgnoreCase("random")) ftypernd = true;
		try{
			if (ftstr.equalsIgnoreCase("random")) ftype = FireworkEffect.Type.values()[PlayEffectPlugin.instance.u.getRandomInt(FireworkEffect.Type.values().length)];
			else ftype = FireworkEffect.Type.valueOf(ftstr.toUpperCase());
		} catch (Exception e){
			ftype = FireworkEffect.Type.BALL;
		}
		if (getParam ("color","random").equalsIgnoreCase("random")) colorrnd = true;
		color = EffectColor.getBukkitColor(getParam ("color","random"));
	}


	@Override
	protected void play(final Location loc) {
		if (ftypernd) ftype = FireworkEffect.Type.values()[PlayEffectPlugin.instance.u.getRandomInt(FireworkEffect.Type.values().length)];
		if (colorrnd) color = EffectColor.getBukkitColor("RANDOM");
		Bukkit.getScheduler().runTaskLater(PlayEffectPlugin.instance, new Runnable(){
			@Override
			public void run() {
				FireworkEffect fe = FireworkEffect.builder().with(ftype).withColor(color).flicker(true).build();
				AdditionalEffects.playFirework(loc, fe);
				if (PacketNMS.getVersion().equalsIgnoreCase("v1_8_R1")) InstantFireworks18R1.spawn(loc, fe);
				else if (PacketNMS.getVersion().equalsIgnoreCase("v1_8_R2")) InstantFireworks18R2.spawn(loc, fe);
				else if (PacketNMS.getVersion().equalsIgnoreCase("v1_8_R3")) InstantFireworks18R3.spawn(loc, fe);
			}
		}, 1);

	}

}
