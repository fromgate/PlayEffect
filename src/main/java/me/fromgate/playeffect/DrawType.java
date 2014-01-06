package me.fromgate.playeffect;

public enum DrawType {
    NORMAL,
    LINE,
    CIRCLE,
    PLAIN,
    AREA;
    public static boolean contains(String str){
        for (DrawType dt : DrawType.values())
            if (dt.name().equalsIgnoreCase(str)) return true;
        return false;
    }
    
    public boolean isUsingSecondLoc() {
        if (this == DrawType.NORMAL) return false;
        if (this == DrawType.CIRCLE) return false;
        return true;
    }
}
