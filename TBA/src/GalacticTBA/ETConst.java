package GalacticTBA;

import java.awt.Color;

import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

public class ETConst {
	// Star size is determined by their number of lines of code
	// Star color is determined by the number of imports
	// Planet size is determined by the number of lines of code
	// Planet color is determined by the number of parameters

	public static final Color3f[] starColors = { new Color3f(Color.RED),
			new Color3f(Color.ORANGE), new Color3f(Color.YELLOW),
			/*new Color3f(Color.BLUE),*/ new Color3f(Color.WHITE) };

	public static final Color3f[] planetColors = {
			new Color3f(Color.DARK_GRAY), new Color3f(Color.GRAY),
			new Color3f(Color.CYAN), new Color3f(Color.GREEN),
			new Color3f(Color.BLUE), new Color3f(Color.LIGHT_GRAY) };

	public static final int STAR_SCALAR = 20;
	public static final int INITIAL_STAR_DISTANCE = 25;
	public static final int STAR_SPACING = 17;
	
	
	public static final int MIN_PLANET_SIZE = 10;
	public static final int PLANET_SCALAR = 20;
	public static final int INITIAL_PLANET_DISTANCE = 5;
	public static final int PLANET_SPACING = 3;
	
	// Used for sliding color for suns
	public static final int STAR_R = 255;
	public static final int STAR_B = 100;
	public static final int STAR_G_RANGE = 200;
	public static final int STAR_G_BASE = 50;
	public static final int A = 100;
	
	// Used for sliding color for stars
	public static final int PLANET_R = 0;
	public static final int PLANET_B_RANGE = 247;
	public static final int PLANET_B_BASE = 247;
	public static final int PLANET_G_RANGE = 247;
	public static final int PLANET_G_BASE = 0;

	public static final Vector3f defaultAxis = new Vector3f(0, 0, 1);

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
