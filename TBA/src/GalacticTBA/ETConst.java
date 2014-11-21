package GalacticTBA;

import javax.vecmath.Vector3f;

// CONST FILE FOR UI VALUES
public class ETConst {
	// Star size is determined by their number of lines of code
	// Star color is determined by the relative number of commits
	// Planet size is determined by the number of lines of code
	// Planet color is determined by the relative number of parameters

	// Blackhole constants
	public static final float DG_HOLE_SIZE = 7.1f;
	public static final float LG_HOLE_SIZE = 7f;
	public static final int BLACK_HOLE_DISTANCE = 0;
	public static final int DG_HOLE = 15;
	public static final int LG_HOLE = 50;

	// Star Constants
	public static final float MIN_STAR_SIZE = 1f;
	public static final int STAR_SCALAR = 7;
	public static final int INITIAL_STAR_DISTANCE = 15;
	public static final int MIN_STAR_SPACING = 50;
	public static final int MIN_SUBSTAR_SPACING = 20;
	public static final int MAX_STAR_SPACING = 175;
	public static final int MAX_SUBSTAR_SPACING = 50;
	public static final int VARIANCE = 10;

	// Planet Constants
	public static final int MIN_PLANET_SIZE = 20;
	public static final int PLANET_SCALAR = 7;
	public static final int INITIAL_PLANET_DISTANCE = 2;
	public static final int PLANET_SPACING = 1;

	// Asteroid Constants
	public static final float ASTEROID_MAX_SIZE = 0.8f;
	public static final int ASTEROID_DISTANCE = 1;

	// Star Color Constants
	public static final int STAR_R = 255;
	public static final int STAR_B = 100;
	public static final int STAR_G_RANGE = 255;
	public static final int STAR_G_BASE = 0;
	public static final int A = 255;

	// Planet Color Constants
	public static final int PLANET_R = 0;
	public static final int PLANET_B_RANGE = 255;
	public static final int PLANET_B_BASE = 255;
	public static final int PLANET_G_RANGE = 255;
	public static final int PLANET_G_BASE = 0;
	
	// Asteroid Color Constants
	public static final int AST_R = 240;
	public static final int AST_G = 175;
	public static final int AST_B = 110;
	public static final int AST_RANGE = 60;

	// Axis Color Constants
	public static final float Z_VAL = 1f;
	public static final Vector3f BASE_AXIS = new Vector3f(0, 0, 1);
	public static final float SMALL_AXIS = 0.15f;
	public static final float BIG_AXIS = 10f;

	// Simple class to abstract out ranges
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
