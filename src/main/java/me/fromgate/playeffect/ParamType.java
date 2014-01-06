package me.fromgate.playeffect;

// этот энум, всё-таки лишний...

public enum ParamType {
    //BASEEFFECTS
    LOC,
    LOC2,
    RADIUS,
    AMOUNT,
    CHANCE,
    LAND,
    DRAW,
    WIND,         //SMOKE
    PARAM,        //POTIONBREAK
    LCHANCE,      //Lightning
    MODE,         //Lightning
    EFFECTNAME,
    NUM,
    SPEED,
    OFFSET,
    OFFSETX,
    OFFSETY,
    OFFSETZ,
    ITEM,
    TYPE,
    COLOR,
    PITCH,
    VOLUME,
    DISC;
    

    public static boolean isValid(String param){
        for (ParamType pt : ParamType.values())
            if (pt.name().equalsIgnoreCase(param)) return true;
        return false;
    }
}
