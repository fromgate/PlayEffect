package me.fromgate.playeffect;

import me.fromgate.playeffect.effect.BasicEffect;
import me.fromgate.playeffect.effect.EffectBlockCrackSound;
import me.fromgate.playeffect.effect.EffectExplosion;
import me.fromgate.playeffect.effect.EffectEye;
import me.fromgate.playeffect.effect.EffectFirework;
import me.fromgate.playeffect.effect.EffectFlame;
import me.fromgate.playeffect.effect.EffectLightning;
import me.fromgate.playeffect.effect.EffectParticles;
import me.fromgate.playeffect.effect.EffectPotion;
import me.fromgate.playeffect.effect.EffectSignal;
import me.fromgate.playeffect.effect.EffectSmoke;
import me.fromgate.playeffect.effect.EffectSong;
import me.fromgate.playeffect.effect.EffectSound;

public enum VisualEffect  {
    BASIC(BasicEffect.class,"",0,"id,time,loc,loc2,radius,amount,chance,land,draw,freq,dur"),
    SMOKE (EffectSmoke.class,"",5,"wind"),
    SIGNAL (EffectSignal.class,"",5,""),
    POTION (EffectPotion.class,"",5,"param"),
    FLAME (EffectFlame.class,"",5,""),
    EXPLOSION (EffectExplosion.class,"",5,""),
    EYE (EffectEye.class,"",5,""),
    LIGHTNING (EffectLightning.class,"",128,5,"lchance,mode"),
    NOTE (EffectParticles.class,"effectname:note num:1",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    PORTAL(EffectParticles.class,"effectname:portal num:30",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    CLOUD(EffectParticles.class,"effectname:cloud num:10",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    HUGEEXPLOSION(EffectParticles.class,"effectname:hugeexplosion num:1",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    LARGEEXPLODE(EffectParticles.class,"effectname:largeexplode num:10 speed:3 offset:1",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SPARK(EffectParticles.class,"effectname:fireworksSpark num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    BUBBLE(EffectParticles.class,"effectname:bubble num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SUSPEND(EffectParticles.class,"effectname:suspended num:25",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    DEPTHSUSPEND(EffectParticles.class,"effectname:depthsuspend num:25",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    TOWNAURA(EffectParticles.class,"effectname:townaura num:25",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    CRIT(EffectParticles.class,"effectname:crit num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    MAGICCRIT(EffectParticles.class,"effectname:magicCrit num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    MOBSPELL(EffectParticles.class,"effectname:mobSpell num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    MOBSPELLAMBIENT(EffectParticles.class,"effectname:mobSpellAmbient num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SPELL(EffectParticles.class,"effectname:spell num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    INSTANTSPELL(EffectParticles.class,"effectname:instantSpell num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    WITCHMAGIC(EffectParticles.class,"effectname:witchMagic num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    RUNES(EffectParticles.class,"effectname:enchantmenttable num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    EXPLODE(EffectParticles.class,"effectname:explode num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    FLAMENEW(EffectParticles.class,"effectname:flame num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    LAVA(EffectParticles.class,"effectname:lava num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    FOOTSTEP(EffectParticles.class,"effectname:footstep num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SPLASH(EffectParticles.class,"effectname:splash num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    LARGESMOKE(EffectParticles.class,"effectname:largesmoke num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    REDDUST(EffectParticles.class,"effectname:reddust num:5 speed:0",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SNOWBALL(EffectParticles.class,"effectname:snowballpoof num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    DRIPWATER(EffectParticles.class,"effectname:dripWater num:5 offsetY:0 offsetX:0.8 offsetZ:0.8",64,5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    DRIPLAVA(EffectParticles.class,"effectname:dripLava num:5 offsetY:0 offsetX:0.8 offsetZ:0.8",64,5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SNOWSHOVEL(EffectParticles.class,"effectname:snowshovel num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    SLIME(EffectParticles.class,"effectname:slime num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    HEART(EffectParticles.class,"effectname:heart num:2",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    ANGRY(EffectParticles.class,"effectname:angryVillager num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    HAPPY(EffectParticles.class,"effectname:happyVillager num:5",5,"num,speed,offset,offsetX,offsetY,offsetZ"),
    ICONCRACK(EffectParticles.class,"effectname:iconcrack_ num:5 item:GLASS:0",5,"num,speed,offset,offsetX,offsetY,offsetZ,item"),
    TILECRACK(EffectParticles.class,"effectname:tilecrack_ num:5 block:GLASS:0",5,"num,speed,offset,offsetX,offsetY,offsetZ,item,block"),
    BLOCKCRACK(EffectParticles.class,"effectname:blockcrack_ num:5 block:GLASS:0",5,"num,speed,offset,offsetX,offsetY,offsetZ,item,block"),
    BLOCKCRACKSOUND(EffectBlockCrackSound.class,"block:GLASS:0",5,"item,block"),
    BLOCKDUST(EffectParticles.class,"effectname:blockdust_ num:5 block:GLASS:0",5,"num,speed,offset,offsetX,offsetY,offsetZ,item,block"),
    FIREWORK(EffectFirework.class,"",128,20,"type,color"),
    SOUND(EffectSound.class,"",128,10,"type,pitch,volume"),
    SONG(EffectSong.class,"",128,5,"disc");

    private Class<? extends BasicEffect > clazz;
    private String param;
    private long min_rpt_time;
    private String paramlist;
    private int distance;
    

    private VisualEffect (Class<? extends BasicEffect> clazz, String param, int distance, long minrpt, String paramlist){
        this.clazz = clazz;
        this.param = param;
        this.min_rpt_time= minrpt;
        this.paramlist=paramlist;
        this.distance = distance;
    }
    
    private VisualEffect (Class<? extends BasicEffect> clazz, String param, long minrpt, String paramlist){
        this.clazz = clazz;
        this.param = param;
        this.min_rpt_time= minrpt;
        this.paramlist=paramlist;
        this.distance = 32;
    }

    
    public int getVisibilityDistance(){
        return this.distance;
    }
    
    public Class<? extends BasicEffect> getClazz(){
        return this.clazz;
    }

    public String getParam(){
        return this.param;
    }

    public String getParamList(){
        return this.paramlist;
    }

    public boolean isValidParam(String param){
        return PlayEffect.instance.u.isWordInList(param, BASIC.getParamList())||PlayEffect.instance.u.isWordInList(param, getParamList());
    }

    public static boolean isValidParameter (String param){
        for (VisualEffect ve :  VisualEffect.values())
            if (PlayEffect.instance.u.isWordInList(param, ve.getParamList())) return true;
        return false;
    }

    public long getRepeatTicks(){
        return this.min_rpt_time;
    }

    public static boolean contains(String id){
        String vn = id;
        if (vn.equalsIgnoreCase("tilecrack")) vn = "blockcrack";
        for (VisualEffect ve :VisualEffect.values()){
            if (ve == VisualEffect.BASIC) continue;
            if (ve.name().equalsIgnoreCase(vn)) return true;
        }
        return false;
    }

    public static VisualEffect getEffectByName(String name){
        String vn = name;
        if (vn.equalsIgnoreCase("tilecrack")) vn = "blockcrack";
        for (VisualEffect ve : VisualEffect.values())
            if (ve.name().equalsIgnoreCase(vn)) return ve;
        return null;
    }
    

}

