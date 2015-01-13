package me.fromgate.playeffect;

import java.util.Random;

import org.bukkit.Color;

public enum EffectColor {
	BLACK  ("&0",   0,   0,   0),
	NAVY   ("&1",   0,   0, 170),
	GREEN  ("&2",   0, 170,   0),
	TEAL   ("&3",   0, 170, 170),
	MAROON ("&4", 170,   0,   0),
	PURPLE ("&5", 170,   0, 170),
	GOLD   ("&6", 255, 170,   0),
	SILVER ("&7", 170, 170, 170),
	GREY   ("&8",  85,  85,  85),
	BLUE   ("&9",  85,  85, 255),
	LIME   ("&a",  85, 255,  85),
	AQUA   ("&b",  85, 255, 255),
	RED    ("&c", 255,  85,  85),
	PINK   ("&d", 255,  85, 255),
	YELLOW ("&e", 255, 255,  85),
	WHITE  ("&f", 255, 255, 255),
	RANDOM ("rnd", -1,-  1, -1);

	EffectColor (String alias, int r, int g, int b){
		this.alias = alias;
		this.r = r;
		this.g = g;
		this.b = b;
	}

	private String alias;
	private int r;
	private int g;
	private int b;

	private static Random random = null;

	private static float[] intToFloatColors (int []rgb){
		return intToFloatColors (rgb[0],rgb[1],rgb[2]);
	}

	private static float[] intToFloatColors (int r, int g, int b){
		float [] f = new float [] {(float) r/255,(float)g/255,(float)b/255};
		PlayEffect.instance.u.BC("rgb: "+r+":"+f[0]+" / "+g+":"+f[1]+" / "+b+":"+f[2]);
			
		return f;
	}

	public static int[] getRGBColor (String colorStr){
		if (random == null) random = new Random ();
		int r = -1;
		int g = -1;
		int b = -1;
		if (colorStr.matches("-?\\d{1,3},-?\\d{1,3},-?\\d{1,3}")){
			String [] ln = colorStr.split(",");
			r = Integer.parseInt(ln[0]);
			g = Integer.parseInt(ln[1]);
			b = Integer.parseInt(ln[2]);
		} else for (EffectColor e : EffectColor.values()){
			if (e.name().equalsIgnoreCase(colorStr)||
					e.alias.equalsIgnoreCase(colorStr)){
				r = e.r;
				g = e.g;
				b = e.b;
			}
		}
		return new int[]{r < 0 ? random.nextInt(255) : (r>255 ? 255 :r),
				g < 0 ? random.nextInt(255) : (g>255 ? 255 :g),
						b < 0 ? random.nextInt(255) :(b>255 ? 255 :b)};
	}

	public static float[] getParticlesColor (String colorStr){
		return intToFloatColors (getRGBColor(colorStr));
	}

	public static Color getBukkitColor (String colorStr){
		int [] rgb = getRGBColor (colorStr);
		return Color.fromRGB(rgb[0],rgb[1],rgb[2]);
	}



}
