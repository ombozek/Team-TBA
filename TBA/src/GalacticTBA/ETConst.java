package GalacticTBA;

import java.awt.Color;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class ETConst {
	// Star size is determined by their number of lines of code
	// Star color is determined by the number of imports
	// Planet size is determined by the number of lines of code
	// Planet color is determined by the number of parameters

	public static final Color3f red = new Color3f(1.0f, 0.0f, 0.0f);
    public static final Color3f green = new Color3f(0.0f, 1.0f, 0.0f);
    public static final Color3f blue = new Color3f(0.0f, 0.0f, 1.0f);
    public static final Color3f yellow = new Color3f(1.0f, 1.0f, 0.0f);
    public static final Color3f cyan = new Color3f(0.0f, 1.0f, 1.0f);
    public static final Color3f magenta = new Color3f(1.0f, 0.0f, 1.0f);
    public static final Color3f white = new Color3f(1.0f, 1.0f, 1.0f);
    public static final Color3f black = new Color3f(0.0f, 0.0f, 0.0f);
    public static final Color3f grey = new Color3f(0.2f, 0.2f, 0.2f);
    public static final Color3f darkred = new Color3f(0.2f, 0.0f, 0.0f);
    public static final Color3f brown = new Color3f(0.35f, 0.29f, 0.0f);
    public static final Color3f darkBrown = new Color3f(0.15f, 0.1f, 0.0f);
    public final static Color3f darkGrey = new Color3f(0.2f, 0.2f, 0.2f);
    public final static Color3f darkYellow = new Color3f(0.3f, 0.3f, 0.0f);
    public final static Color3f darkGreen = new Color3f(0.0f, 0.3f, 0.0f);
    public final static Color3f darkBlue = new Color3f(0.0f, 0.0f, 0.3f);

	public static final int STAR_SCALAR = 20;
	public static final int INITIAL_STAR_DISTANCE = 10;
	public static final int STAR_SPACING = 20;
	
	
	public static final int MIN_PLANET_SIZE = 20;
	public static final int PLANET_SCALAR = 10;
	public static final int INITIAL_PLANET_DISTANCE = 5;
	public static final int PLANET_SPACING = 2;
	
	// Used for sliding color for suns
	public static final int STAR_R = 255;
	public static final int STAR_B = 100;
	public static final int STAR_G_RANGE = 255;
	public static final int STAR_G_BASE = 0;
	public static final int A = 255;
	
	// Used for sliding color for stars
	public static final int PLANET_R = 0;
	public static final int PLANET_B_RANGE = 200;
	public static final int PLANET_B_BASE = 255;
	public static final int PLANET_G_RANGE = 255;
	public static final int PLANET_G_BASE = 0;

	public static final Vector3f defaultAxis = new Vector3f(0, 0, (float) 0.5);

	public static class Range {
		public final int MIN;
		public final int MAX;

		public Range(int min, int max) {
			this.MIN = min;
			this.MAX = max;
		}

		public int getRange() {
			return MAX - MIN;
		}
	}

}
