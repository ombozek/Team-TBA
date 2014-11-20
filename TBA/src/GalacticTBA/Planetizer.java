package GalacticTBA;

import static GalacticTBA.ETConst.INITIAL_PLANET_DISTANCE;
import static GalacticTBA.ETConst.INITIAL_STAR_DISTANCE;
import static GalacticTBA.ETConst.MIN_PLANET_SIZE;
import static GalacticTBA.ETConst.PLANET_SCALAR;
import static GalacticTBA.ETConst.PLANET_SPACING;
import static GalacticTBA.ETConst.STAR_SCALAR;
import static GalacticTBA.ETConst.STAR_SPACING;
import static GalacticTBA.ETConst.defaultAxis;
import static GalacticTBA.ETConst.planetColors;
import static GalacticTBA.ETConst.starColors;

import java.awt.Color;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
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
				"No, don't show stellar axes", "Only show central axis" };

		JFrame frame = new JFrame();
		showAxes = JOptionPane.showOptionDialog(frame,
				"Would you like to see axes on stars?", "GalacticTBA",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				options, options[0]);

	}

	public void celestialize() throws Exception {
		SimpleUniverse universe = new SimpleUniverse();

		BranchGroup maingroup = new BranchGroup();
		TransformGroup viewgroup = new TransformGroup();
		maingroup.addChild(viewgroup);

		Planet blackhole = new Planet(new Vector3f(0, 0, 1), (float) 0, 0,
				new Color3f(Color.YELLOW), viewgroup);
		if (showAxes == 0 || showAxes == 2)
			createAxis(blackhole.tg_trans, false);

		Planet p;
		int w = INITIAL_STAR_DISTANCE;
		for (Clazz clazz : codebase.getClasses().values()) {
			w += STAR_SPACING;
			p = new Sun(new Vector3f(0, 0, 1), starRadius(clazz.getSloc()), w,
					starColor(clazz.getNumCommits()), blackhole.tg_trans);

			if (showAxes == 0)
				createAxis(p.tg_trans, true);

			planetize(clazz, p.tg_trans);
			if (clazz.getSubclasses() != null
					&& !clazz.getSubclasses().isEmpty())
				celestialize(clazz, p);
		}

		Color3f light1Color = new Color3f(1.8f, 0.1f, 0.1f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),
				500.0);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, 12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color,
				light1Direction);
		Vector3f light2Direction = new Vector3f(4.0f, 7.0f, 12.0f);
		DirectionalLight light2 = new DirectionalLight(light1Color,
				light2Direction);
		light2.setBoundsAutoCompute(true);
		light1.setInfluencingBounds(bounds);

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

	private void celestialize(Clazz superclazz, Planet p) {
		int w = INITIAL_STAR_DISTANCE;
		for (Clazz subclazz : superclazz.getSubclasses()) {
			w += STAR_SPACING;
			p = new Sun(new Vector3f(0, 0, 1), starRadius(subclazz.getSloc()),
					w, starColor(subclazz.getNumCommits()), p.tg_trans);

			if (showAxes == 0)
				createAxis(p.tg_trans, true);

			planetize(subclazz, p.tg_trans);
			if (subclazz.getSubclasses() != null
					&& !subclazz.getSubclasses().isEmpty())
				celestialize(subclazz, p);
		}

	}

	public void planetize(Clazz clazz, TransformGroup trans) {
		int w = INITIAL_PLANET_DISTANCE;
		for (Methodz method : clazz.getMethods()) {
			w += PLANET_SPACING;
			new Planet(defaultAxis, planetRadius(method.sloc), w,
					planetColor(method.parameters), trans);
		}
	}

	public float starRadius(int sloc) {
		return STAR_SCALAR
				* ((float) (sloc - slocRange.MIN) / (float) slocRange
						.getRange());
	}

	public Color3f starColor(int commits) {
		double idx = (commits - commitRange.MIN)
				/ (double) (commitRange.getRange() + 1);
		return starColors[(int) (idx * (starColors.length - 1))];
	}

	public float planetRadius(int sloc) {
		return PLANET_SCALAR
				* ((float) (sloc + MIN_PLANET_SIZE) / (float) slocRange
						.getRange());
	}

	public Color3f planetColor(int numParam) {
		double idx = (numParam - paramRange.MIN)
				/ (double) (paramRange.getRange() + 1);
		return planetColors[(int) (idx * (planetColors.length - 1))];
	}

	public Transform3D lookTowardsOriginFrom(Point3d point) {
		Transform3D move = new Transform3D();

		Vector3d up = new Vector3d(0, 1, 0);
		move.lookAt(point, new Point3d(0.0d, 0.0d, 0.0d), up);

		return move;
	}

	public void createAxis(TransformGroup tg, boolean smallAxes) {
		float axisLength;
		if (smallAxes) {
			axisLength = 0.5f;
		} else {
			axisLength = 10f;
		}
		// Create X axis
		LineArray axisXLines = new LineArray(2, LineArray.COORDINATES);
		tg.addChild(new Shape3D(axisXLines));

		axisXLines.setCoordinate(0, new Point3f(-1*axisLength, 0.0f, 0.0f));
		axisXLines.setCoordinate(1, new Point3f(axisLength, 0.0f, 0.0f));

		// Create Y axis
		LineArray axisYLines = new LineArray(2, LineArray.COORDINATES);
		tg.addChild(new Shape3D(axisYLines));

		axisYLines.setCoordinate(0, new Point3f(0.0f, -1*axisLength, 0.0f));
		axisYLines.setCoordinate(1, new Point3f(0.0f, axisLength, 0.0f));

		// Create Z axis with arrow

		LineArray axisZLines = new LineArray(2, LineArray.COORDINATES);
		tg.addChild(new Shape3D(axisZLines));

		axisZLines.setCoordinate(0, new Point3f(0.0f, 0.0f, axisLength));
		axisZLines.setCoordinate(1, new Point3f(0.0f, 0.0f, -1*axisLength));

	}

}
