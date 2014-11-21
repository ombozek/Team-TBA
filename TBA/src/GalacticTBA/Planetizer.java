package GalacticTBA;

import static GalacticTBA.ETConst.*;

import java.awt.Color;
import java.util.Random;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.LineArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import GalacticTBA.ETConst.Range;
import ParserTBA.Codebase;
import ParserTBA.Codebase.Clazz;
import ParserTBA.Codebase.Methodz;
import ParserTBA.Codebase.VarTable;

import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Planetizer {
	public final Codebase codebase;
	public final Range importRange, paramRange, slocRange, commitRange;
	public final int showAxes;

	public Planetizer(Codebase codebase) {
		this.codebase = codebase;
		importRange = codebase.getImportRange();
		paramRange = codebase.getParamRange();
		slocRange = codebase.getSlocRange();
		commitRange = codebase.getCommitRange();

		Object[] options = { "Yes, show stellar axes",
				"No, don't show stellar axes", "Only stellar axes",
				"Only show central axis" };

		JFrame frame = new JFrame();
		showAxes = JOptionPane.showOptionDialog(frame,
				"Would you like to see a central axis and axes on stars?",
				"GalacticTBA", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[1]);

	}

	/**
	 * Makes the base level stars that surround the blackhole
	 * 
	 * @throws Exception
	 */
	public void celestialize() throws Exception {
		SimpleUniverse universe = new SimpleUniverse();
		universe.getViewer().getView().setBackClipDistance(10000);
		BranchGroup maingroup = new BranchGroup();
		TransformGroup viewgroup = new TransformGroup();
		maingroup.addChild(viewgroup);

		Planet blackhole = new Planet(BASE_AXIS, (float) 0, 0, new Color3f(
				Color.YELLOW), viewgroup);
		new Sun(BASE_AXIS, DG_HOLE_SIZE, BLACK_HOLE_DISTANCE, new Color3f(
				new Color(DG_HOLE, DG_HOLE, DG_HOLE, 100)), blackhole.tg_trans);
		new Sun(BASE_AXIS, LG_HOLE_SIZE, BLACK_HOLE_DISTANCE, new Color3f(
				new Color(LG_HOLE, LG_HOLE, LG_HOLE, 100)), blackhole.tg_trans);
		if (showAxes == 0 || showAxes == 3)
			createViewableAxis(blackhole.tg_trans, false);

		Planet p;
		float starRadius;
		for (Clazz clazz : codebase.getClasses().values()) {

			starRadius = starRadius(clazz.getSloc());
			p = new Sun(generateAxis(0), starRadius, starDistance(
					clazz.getSloc(), true), starColor(clazz.getNumCommits()),
					blackhole.tg_trans);

			if (showAxes == 0 || showAxes == 2)
				createViewableAxis(p.tg_trans, true);

			asteroidize(clazz.varTable, p.tg_trans, starRadius);
			planetize(clazz, p.tg_trans, starRadius);
			if (clazz.getSubclasses() != null
					&& !clazz.getSubclasses().isEmpty())
				celestialize(clazz, p, 1);
		}

		OrbitBehavior orbit = new OrbitBehavior(universe.getCanvas(),
				OrbitBehavior.REVERSE_ALL);
		orbit.setSchedulingBounds(new BoundingSphere());
		MouseZoom myMouse = new MouseZoom();
		myMouse.setTransformGroup(viewgroup);
		myMouse.setSchedulingBounds(new BoundingSphere());
		maingroup.addChild(myMouse);

		universe.getViewingPlatform().setViewPlatformBehavior(orbit);
		universe.addBranchGraph(maingroup);
	}

	/**
	 * Creates subclass stars dubbed ("substars") which will orbit their
	 * superclass'star (dubbed "superstars")
	 * 
	 * @param superclazz
	 *            the superclass of this subclass
	 * @param p
	 *            the superstar which this substar will orbit
	 * @param depth
	 *            the depth in subclasses with respect to the hierarchy
	 */
	private void celestialize(Clazz superclazz, Planet p, int depth) {
		float starRadius;
		for (Clazz subclazz : superclazz.getSubclasses()) {
			starRadius = starRadius(subclazz.getSloc());
			p = new Sun(generateAxis(depth), starRadius, starDistance(
					subclazz.getSloc(), false),
					starColor(subclazz.getNumCommits()), p.tg_trans);

			if (showAxes == 0 || showAxes == 2)
				createViewableAxis(p.tg_trans, true);

			asteroidize(subclazz.varTable, p.tg_trans, starRadius);
			planetize(subclazz, p.tg_trans, starRadius);
			if (subclazz.getSubclasses() != null
					&& !subclazz.getSubclasses().isEmpty())
				celestialize(subclazz, p, depth + 1);
		}
	}

	/**
	 * Creates planets that orbit stars that represent methods
	 * 
	 * @param clazz
	 *            the class that contains the methods from which to create
	 *            planets
	 * @param trans
	 *            the TransformationGroup of the owning star
	 * @param starRadius
	 *            the radius of the owning star
	 */
	public void planetize(Clazz clazz, TransformGroup trans, float starRadius) {
		int w = INITIAL_PLANET_DISTANCE + (int) starRadius;
		Random rg = new Random();
		float planetRadius;
		for (Methodz method : clazz.getMethods()) {
			planetRadius = planetRadius(method.sloc);
			new Planet(new Vector3f(rg.nextFloat(), rg.nextFloat(),
					rg.nextFloat()), planetRadius(method.sloc), planetRadius
					+ w, planetColor(method.parameters), trans);
			w += PLANET_SPACING;
		}
	}

	/**
	 * Creates asteroids which represent global variables
	 * 
	 * @param vars
	 *            the collection of counted variables from the class
	 * @param trans
	 *            the TransformGroup of the owning star
	 * @param sunRadius
	 *            the radius of the owning star
	 */
	public void asteroidize(VarTable vars, TransformGroup trans, float sunRadius) {
		Random rg = new Random();
		Range varRange = vars.getVarRange();
		float asteroidRadius;
		for (int varCount : vars.getTable()) {
			if (varCount > 0) {
				asteroidRadius = asteroidRadius(varRange, varCount);
				new Planet(new Vector3f(rg.nextFloat(), rg.nextFloat(),
						rg.nextFloat()), asteroidRadius, asteroidRadius
						+ sunRadius + ASTEROID_DISTANCE, asteroidColor(
						varRange, varCount), trans);
			}
		}

	}

	/**
	 * Calculates the size of the star given by the number of lines of code in
	 * the file
	 * 
	 * @param sloc
	 *            the source lines of code in the java file
	 * @return the size of the radius of the star
	 */
	public float starRadius(int sloc) {
		float starRadius = STAR_SCALAR
				* ((float) (sloc - slocRange.MIN) / (float) slocRange
						.getRange());
		return (starRadius > MIN_STAR_SIZE ? starRadius : MIN_STAR_SIZE);
	}

	/**
	 * Generates star color based on number of commits. The color is on a scale
	 * from red to yellow. Redder stars have fewer commits and yellower stars
	 * have more commits
	 * 
	 * @param commits
	 *            the number of commits for this given java file
	 * @return the generated color (from red to yellow)
	 */
	public Color3f starColor(int commits) {
		double idx = (commits - commitRange.MIN)
				/ (double) (commitRange.getRange() + 1);
		return new Color3f(new Color(STAR_R,
				(int) ((STAR_G_RANGE * idx) + STAR_G_BASE), STAR_B, A));
	}

	/**
	 * Calculates the distance a star will exist from the black hole or
	 * superstar - relative to it's number of source lines of code
	 * 
	 * @param sloc
	 *            the number of source lines of code
	 * @param baseLevel
	 *            true if this is not a subclass, false otherwise
	 * @return the distance of a star to the blackhole/superstar
	 */
	public int starDistance(int sloc, boolean baseLevel) {
		int spacing = (baseLevel ? MAX_STAR_SPACING : MAX_SUBSTAR_SPACING);
		int minDist = (baseLevel ? MIN_STAR_SPACING : MIN_SUBSTAR_SPACING);
		double idx = ((double) (sloc - slocRange.MIN) / slocRange.getRange());
		int dist = (int) ((idx * spacing) * ((double) (VARIANCE + 1 - new Random()
				.nextInt(3)) / VARIANCE));
		return (dist > minDist ? dist : minDist);
	}

	/**
	 * Generates an axis with a random wobble to make it look a bit prettier -
	 * and a bit more realistic
	 * 
	 * @param depth
	 *            the depth in subclasses with respect to the hierarchy
	 * @return the vector3f that represents the orbital plane of the celestial
	 *         body
	 */
	public Vector3f generateAxis(int depth) {
		float xVal, yVal;
		Random rg = new Random();
		if (depth == 0) {
			xVal = (float) (0.1 - (0.1 * rg.nextInt(3)));
			yVal = (float) (0.2 - (0.1 * rg.nextInt(5)));
		} else {
			xVal = (float) ((depth % 2) + 0.1 - (0.1 * rg.nextInt(3)));
			yVal = (float) (((depth + 1) % 2) + 0.1 - (0.1 * rg.nextInt(3)));
		}

		return new Vector3f(xVal, yVal, Z_VAL);

	}

	/**
	 * Calculates the size of a planet based on the number of lines of code in
	 * the method it represents
	 * 
	 * @param sloc
	 *            the source lines of code in the method
	 * @return the size of the radius of the planet
	 */
	public float planetRadius(int sloc) {
		return PLANET_SCALAR
				* ((float) (sloc + MIN_PLANET_SIZE) / (float) slocRange
						.getRange());
	}

	/**
	 * Generates planet color based on the number of parameters defined in the
	 * method signature. The color is on a scale of blue to green. Methods with
	 * fewer parameters are bluer and with more parameters are greener.
	 * 
	 * @param numParam
	 *            the number of parameters for the method
	 * @return the generated color (from blue to green)
	 */
	public Color3f planetColor(int numParam) {
		double idx = (numParam - paramRange.MIN)
				/ (double) (paramRange.getRange() + 1);
		if (idx < 0.5) {
			// This means we max out Blue and scale up Green
			return new Color3f(new Color(PLANET_R,
					(int) (PLANET_G_BASE + (idx * PLANET_G_RANGE)),
					PLANET_B_BASE, A));
		} else {
			// This means we max out Green and scale down Blue
			return new Color3f(new Color(PLANET_R, PLANET_G_RANGE,
					(int) (PLANET_B_BASE - (idx * PLANET_B_RANGE)), A));
		}
	}

	/**
	 * Calculates the size of an asteroid, based on the number of variables of
	 * the type it represents
	 * 
	 * @param varRange
	 *            The numeric range of instances of variables in the class
	 * @param varCount
	 *            the number of the specific type we're representing as an
	 *            asteroid
	 * @return the radius of the asteroid
	 */
	public float asteroidRadius(Range varRange, int varCount) {
		float idx = (varCount - varRange.MIN)
				/ (float) (varRange.getRange() + 1);
		return idx * ASTEROID_MAX_SIZE;
	}

	/**
	 * Generates a color for the asteroid based on the number of the instances
	 * of a type relative to it's own class from Dark Brown - having fewer
	 * instances - to Light Brown - having more instances
	 * 
	 * @param varRange
	 *            The numeric range of instances of variables in the class
	 * @param varCount
	 *            the number of the specific type we're representing as an
	 *            asteroid
	 * @return the generated color (from dark to light brown)
	 */
	public Color3f asteroidColor(Range varRange, int varCount) {
		double idx = (varCount - varRange.MIN)
				/ (double) (varRange.getRange() + 1);
		int diff = (int) (AST_RANGE * idx);
		return new Color3f(new Color(AST_R - diff, AST_G - diff, AST_B - diff,
				A));

	}

	public Transform3D lookTowardsOriginFrom(Point3d point) {
		Transform3D move = new Transform3D();

		Vector3d up = new Vector3d(0, 1, 0);
		move.lookAt(point, new Point3d(0.0d, 0.0d, 0.0d), up);

		return move;
	}

	/**
	 * Generates a viewable axis for any planetary body
	 * 
	 * @param tg
	 *            the TransformationGroup of the body on which we want an axis
	 * @param smallAxes
	 *            true if axis for celestial body, false if axis for black hole
	 */
	public void createViewableAxis(TransformGroup tg, boolean smallAxes) {
		float axisLength;
		if (smallAxes) {
			axisLength = SMALL_AXIS;
		} else {
			axisLength = BIG_AXIS;
		}
		// Create X axis
		LineArray axisXLines = new LineArray(2, LineArray.COORDINATES);
		tg.addChild(new Shape3D(axisXLines));

		axisXLines.setCoordinate(0, new Point3f(-1 * axisLength, 0.0f, 0.0f));
		axisXLines.setCoordinate(1, new Point3f(axisLength, 0.0f, 0.0f));

		// Create Y axis
		LineArray axisYLines = new LineArray(2, LineArray.COORDINATES);
		tg.addChild(new Shape3D(axisYLines));

		axisYLines.setCoordinate(0, new Point3f(0.0f, -1 * axisLength, 0.0f));
		axisYLines.setCoordinate(1, new Point3f(0.0f, axisLength, 0.0f));

		// Create Z axis with arrow

		LineArray axisZLines = new LineArray(2, LineArray.COORDINATES);
		tg.addChild(new Shape3D(axisZLines));

		axisZLines.setCoordinate(0, new Point3f(0.0f, 0.0f, axisLength));
		axisZLines.setCoordinate(1, new Point3f(0.0f, 0.0f, -1 * axisLength));

	}

}
