package me.fromgate.playeffect.effect;

import org.bukkit.Effect;
import org.bukkit.Location;

public class EffectSong extends BasicEffect {
    SongType st = SongType.DISC13;
    @Override
    public void onInit(){
        String disc = getParam("disc","DISC13");
        if (disc.equalsIgnoreCase("13")) disc="DISC13";
        else if (disc.equalsIgnoreCase("13DISC")) disc="DISC13";
        else if (disc.equalsIgnoreCase("11")) disc="DISC11";
        else if (disc.equalsIgnoreCase("11DISC")) disc="DISC11";
        try{
            st = SongType.valueOf(disc.toUpperCase());
        } catch(Exception e){
            st = SongType.DISC13;
        }
        
    }
    
    @Override
    protected void play(Location loc) {
        loc.getWorld().playEffect(loc, Effect.RECORD_PLAY, st.getId());
    }

    @Override
    public long getRepeatTick(){
        return u().timeToTicks(st.getLength());
    }
    
    enum SongType{
        DISC13 (2256,178000L), // 2:58  - Было 177000 
        CAT(2257,185000L),     // 3:05  - Было 192000  
        BLOCKS(2258,345000L),  // 5:45  - Было 352000 
        CHIRP(2259,185000L),   // 3:05  - Было 186000  
        FAR(2260,174000L),     // 2:54  - Было 169000
        MALL(2261,197000L),    // 3:17  - Было 203000 
        MELLOHI(2262,96000L), // 1:36  - Было 101000 
        STAL(2263,150000L),    // 2:30  - Было 152000 
        STRAD(2264,188000L),   // 3:08  - Было 189000 
        WARD(2265, 251000L),   // 4:11  - Было 249000 
        DISC11(2266,71000L),   // 1:11  - Было 72000 
        WAIT(2267,238000L);    // 3:58 (3:51) - Было 240000 
        private int id;
        private long length;
        SongType(int song_id, long song_length){
            this.id = song_id;
            this.length = song_length;
        }
        public int getId(){
            return this.id;
        }
        public long getLength(){
            return this.length;
        }
        
        public long getLengthTicks(){
            return this.length/50;
        }
        
        
    }
}
