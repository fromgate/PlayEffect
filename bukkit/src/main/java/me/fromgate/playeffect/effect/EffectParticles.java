package me.fromgate.playeffect.effect;

import me.fromgate.playeffect.PlayEffectPlugin;
import me.fromgate.playeffect.VisualEffect;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class EffectParticles extends BasicEffect {
    private int id = 0;
    private int data = 0;
    private float offsetX = 0f;
    private float offsetY = 0f;
    private float offsetZ = 0f;
    private float speed = 0.1f;
    private int number = 10;
    private boolean disabled = false;


    @Override
    @SuppressWarnings("deprecation")
    public void onInit() {
        if (this.type == null) this.type = VisualEffect.CLOUD;
        String itemStr = getParam("block", getParam("item"));
        if (!itemStr.isEmpty()) {
            ItemStack item = PlayEffectPlugin.instance.u.parseItemStack(itemStr);
            id = item.getType().getId();
            data = item.getDurability();
        }
        number = getParam("num", number);
        speed = getParam("speed", 0.1f);
        offsetX = getParam("offsetX", getParam("offset", 0f));
        offsetY = getParam("offsetY", getParam("offset", 0f));
        offsetZ = getParam("offsetZ", getParam("offset", 0f));
    }

    /*
            player.getWorld().spawnParticle(particle,player.getLocation(),
                cfg.particleAmount,
                cfg.particleRadius,
                cfg.particleRadius,
                cfg.particleRadius,
                cfg.particleExtra);
     */

    @SuppressWarnings("deprecation")
    @Override
    protected void play(Location l) {
        if (disabled) return;
        if (l == null) return;
        Location loc = l;
        loc.setX(loc.getBlockX() + 0.5);
        loc.setZ(loc.getBlockZ() + 0.5);
        loc.setY(loc.getBlockY() + 0.5);

        Particle particle = this.type.getParticle();
        if (particle == null) return;

        if (particle == Particle.FOOTSTEP) {
            loc.setY(loc.getBlockY());
            offsetY = 0f;
        } else if (particle == Particle.DRIP_LAVA || particle == Particle.DRIP_WATER) {//
            loc.setY(loc.getBlockY() + 0.9);
            offsetY = 0f;
        }

        switch (particle.name()) {
            case "ITEM_CRACK":
                loc.getWorld().spawnParticle(particle, loc,
                        this.number,
                        offsetX,
                        offsetY,
                        offsetZ,
                        speed, new ItemStack(id, 1, (byte) data));
                break;
            case "BLOCK_CRACK":
            case "BLOCK_DUST":
            case "FALLING_DUST":
                loc.getWorld().spawnParticle(particle, loc,
                        this.number,
                        offsetX,
                        offsetY,
                        offsetZ,
                        speed, new MaterialData(id, (byte) data));
                break;
            default:
                loc.getWorld().spawnParticle(particle, loc,
                        this.number,
                        offsetX,
                        offsetY,
                        offsetZ,
                        speed);
                break;
        }
        //AdditionalEffects.sendParticlesPacket (loc, effectname, id, data, offsetX, offsetY, offsetZ, speed, number);
    }
}

