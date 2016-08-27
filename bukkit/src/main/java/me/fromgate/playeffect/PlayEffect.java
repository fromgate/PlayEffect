package me.fromgate.playeffect;

import me.fromgate.playeffect.effect.BasicEffect;
import org.bukkit.Location;

import java.util.Map;


public class PlayEffect {

    /**
     * @param effect - VisualEffect type. Example: VisualEffect.SMOKE
     * @param loc    - Location where effect will played
     * @param param  - Any effect parameter. Example: "radius:2 num:5"
     */
    public static void play(VisualEffect effect, Location loc, String param) {
        if (effect == null) return;
        if (loc == null) return;
        Effects.playEffect(effect, param + " loc:" + Util.locationToStrLoc(loc));
    }

    /**
     * @param effect - VisualEffect type. Example: VisualEffect.SMOKE
     * @param loc    - Location where effect will played
     * @param params - Any supported effect parameter.
     *               You can create HashMap<String,String> params and add
     *               required parameters to this map
     *               Example:
     *               Map<String,String> params = new HashMap<String,String>();
     *               params.put ("radius","2");
     *               params.put ("num","5");
     */
    public static void play(VisualEffect effect, Location loc, Map<String, String> params) {
        if (effect == null) return;
        if (loc == null) return;
        params.put("loc", Util.locationToStrLoc(loc));
        Effects.playEffect(effect, params);
    }

    /**
     * @param effect - Name of the VisualEffect. Example: "smoke", "FIREWORK", etc...
     * @param loc    - Location where effect will played
     * @param params - Any supported effect parameter.
     *               You can create HashMap<String,String> params and add
     *               required parameters to this map
     *               Example:
     *               Map<String,String> params = new HashMap<String,String>();
     *               params.put ("radius","2");
     *               params.put ("num","5");
     */
    public static void play(String effect, Location loc, Map<String, String> params) {
        if (loc == null) return;
        VisualEffect ve = VisualEffect.getEffectByName(effect);
        if (ve == null) return;
        play(ve, loc, params);
    }

    /**
     * @param effect - VisualEffect type. Example: VisualEffect.SMOKE
     * @param param  - Any effect parameter. Don't forget to
     *               add "loc" parameter to define effect location
     *               Example: "loc:world,10,65,12 radius:2 num:5"
     */
    public static void play(VisualEffect effect, String param) {
        Effects.playEffect(effect, param);
    }

    /**
     * @param effect - Name of the VisualEffect. Example: "smoke", "FIREWORK", etc...
     * @param param  - Any effect parameter. Don't forget to
     *               add "loc" parameter to define effect location
     *               Example: "loc:world,10,65,12 radius:2 num:5"
     */
    public static void play(String effect, String param) {
        VisualEffect ve = VisualEffect.getEffectByName(effect);
        if (ve == null) return;
        Effects.playEffect(ve, param);
    }

    /**
     * @param effect - Name of the VisualEffect. Example: "smoke", "FIREWORK", etc...
     * @param params - Any supported effect parameter.
     *               You can create HashMap<String,String> params and add
     *               required parameters to this map
     *               Example:
     *               Map<String,String> params = new HashMap<String,String>();
     *               params.put ("radius","2");
     *               params.put ("num","5");
     */
    public static void play(String effect, Map<String, String> params) {
        VisualEffect ve = VisualEffect.getEffectByName(effect);
        if (ve == null) return;
        Effects.playEffect(ve, params);
    }

    /**
     * Set static (repeating) effect in defined location
     *
     * @param effect - VisualEffect type. Example: VisualEffect.SMOKE
     * @param param  - Any effect parameter. Don't forget to
     *               add "loc" parameter to define effect location
     *               Example: "loc:world,10,65,12 radius:2 num:5"
     * @return - Return true if effect was set successfully
     */
    public static boolean set(VisualEffect effect, String param) {
        BasicEffect be = Effects.createEffect(effect, param);
        if (be == null) return false;
        return Effects.createStaticEffect(be, true);
    }

    /**
     * Set static (repeating) effect in defined location
     *
     * @param effect - VisualEffect type. Example: VisualEffect.SMOKE
     * @param params - Any supported effect parameter.
     *               You can create HashMap<String,String> params and add
     *               required parameters to this map
     *               Example:
     *               Map<String,String> params = new HashMap<String,String>();
     *               params.put ("loc","world,10,65,20");
     *               params.put ("radius","2");
     *               params.put ("num","5");
     * @return - Return true if effect was set successfully
     */
    public static boolean set(VisualEffect effect, Map<String, String> params) {
        BasicEffect be = Effects.createEffect(effect, params);
        if (be == null) return false;
        return Effects.createStaticEffect(be, true);
    }

    /**
     * Set static (repeating) effect in defined location
     *
     * @param effect - Name of the VisualEffect. Example: "smoke", "FIREWORK", etc...
     * @param param  - Any effect parameter. Don't forget to
     *               add "loc" parameter to define effect location
     *               Example: "loc:world,10,65,12 radius:2 num:5"
     * @return - Return true if effect was set successfully
     */
    public static boolean set(String effect, String param) {
        VisualEffect ve = VisualEffect.getEffectByName(effect);
        if (ve == null) return false;
        return set(ve, param);
    }

    /**
     * Set static (repeating) effect in defined location
     *
     * @param effect - Name of the VisualEffect. Example: "smoke", "FIREWORK", etc...
     * @param params - Any supported effect parameter.
     *               You can create HashMap<String,String> params and add
     *               required parameters to this map
     *               Example:
     *               Map<String,String> params = new HashMap<String,String>();
     *               params.put ("loc","world,10,65,20");
     *               params.put ("radius","2");
     *               params.put ("num","5");
     * @return - Return true if effect was set successfully
     */
    public static boolean set(String effect, Map<String, String> params) {
        VisualEffect ve = VisualEffect.getEffectByName(effect);
        if (ve == null) return false;
        return set(ve, params);
    }
}
