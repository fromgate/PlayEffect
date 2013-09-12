package me.fromgate.playeffect;

public enum DrawType {
    NORMAL,
    LINE,
    CIRCLE,
    PLAIN,
    REGION;
    public static boolean contains(String str){
        for (DrawType dt : DrawType.values())
            if (dt.name().equalsIgnoreCase(str)) return true;
        return false;
    }
}
