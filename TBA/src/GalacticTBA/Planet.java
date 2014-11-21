package GalacticTBA;

import static GalacticTBA.ETConst.ORBIT_SPEED_MODIFIER;
import static GalacticTBA.ETConst.PLANET_RADIUS_DIVISOR;
import static GalacticTBA.ETConst.PLANET_SIZE_DIVISOR;

import java.awt.Color;

import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;

public class Planet {
	Sphere sphere;

	// Appearance Attributes
	Appearance ap = new Appearance();
	ColoringAttributes ca;
	Material mat;
	Color3f color;
	float radius;
	//Positioning attributes
	Vector3f axis;
	TransformGroup tg_axis_rot;
	TransformGroup tg_trans;
	TransformGroup tg_parent;
	TransformGroup tg_rot;
	float orbit_radius;
	public Planet() {
		// Empty Constructor
	}

	public Planet(Vector3f axis, float radius, float orbit_radius, Color3f color) {
		// Set Properties of sphere
		this.color = color;
		this.radius = radius / PLANET_SIZE_DIVISOR;
		this.axis = axis;// new Vector3f(0, 0, 1f);
		this.orbit_radius = orbit_radius / PLANET_RADIUS_DIVISOR;
		// Create Sphere Object
		this.sphere = new Sphere(this.radius);

		// Create Transform Group for just this object

		this.tg_trans = translate(this.sphere, new Vector3f(this.orbit_radius,
				0, 0));
		this.tg_rot = rotate(this.tg_trans, new Alpha(-1,
				(long) ((radius + orbit_radius) * ORBIT_SPEED_MODIFIER)));
		this.tg_axis_rot = rotate(this.tg_rot, axis);
		// Apply Coloring Attributes
		this.ca = new ColoringAttributes(color, ColoringAttributes.NICEST);
		this.ap.setColoringAttributes(ca);
		this.ap.setMaterial(new Material(color, new Color3f(Color.black),
				color, new Color3f(Color.YELLOW), 1.0f));
		this.mat = new Material(color, new Color3f(Color.black), color, color,
				1.0f);
		// mat.setColorTarget(Material.AMBIENT_AND_DIFFUSE);
		this.mat.setLightingEnable(true);
		this.ap.setMaterial(mat);
		this.sphere.setAppearance(ap);
	}

	// Constructor to add to main branchgroup
	public Planet(Vector3f axis, float radius, float orbit_radius,
			Color3f color, BranchGroup g) {
		this(axis, radius, orbit_radius, color);
		g.addChild(this.tg_axis_rot);
	}

	// Constructor to add to a parent transformgroup
	public Planet(Vector3f axis, float radius, float orbit_radius,
			Color3f color, TransformGroup tg) {
		this(axis, radius, orbit_radius, color);
		this.tg_parent = tg;
		tg.addChild(this.tg_axis_rot);
	}

	public Sphere getSphere() {
		return sphere;
	}

	public TransformGroup getTg_parent() {
		return tg_parent;
	}

	public void setTg_parent(TransformGroup tg_parent) {
		this.tg_parent.removeChild(this.tg_axis_rot);
		this.tg_parent = tg_parent;
		this.tg_parent.addChild(this.tg_axis_rot);
	}

	public void setColor(Color3f c) {
		this.color=c;
		this.mat = new Material(color, new Color3f(Color.black), color, color, 1.0f);
		this.ap.setMaterial(mat );
		this.sphere.setAppearance(ap);
		this.color = c;
	}

	public double getRadius() {
		return radius;
	}

	public Color3f getColor() {
		return color;
	}
	public Vector3f getAxis() {
		return axis;
	}

	public void setAxis(Vector3f axis) {
		this.axis = axis;
	}

	public TransformGroup getTg_trans() {
		return tg_trans;
	}

	public void setTg_trans(TransformGroup tg_trans) {
		this.tg_trans = tg_trans;
	}

	public TransformGroup getTg_rot() {
		return tg_rot;
	}

	public void setTg_rot(TransformGroup tg_rot) {
		this.tg_rot = tg_rot;
	}

	public float getOrbit_radius() {
		return orbit_radius;
	}

	public void setOrbit_radius(float orbit_radius) {
		this.orbit_radius = orbit_radius;
	}

	public TransformGroup rotate(Node node, Alpha alpha) {

		TransformGroup xformGroup = new TransformGroup();
		xformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		// Create an interpolator for rotating the node.
		RotationInterpolator interpolator = new RotationInterpolator(alpha,
				xformGroup);

		// Establish the animation region for this
		// interpolator.
		interpolator.setSchedulingBounds(new BoundingSphere(new Point3d(0.0,
				0.0, 0.0), 1.0));

		// Populate the xform group.
		xformGroup.addChild(interpolator);
		xformGroup.addChild(node);

		return xformGroup;

	}// end rotate

	public TransformGroup rotate(Node node, Vector3f vector) {

		TransformGroup xformGroup = new TransformGroup();
		xformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		Transform3D tx = new Transform3D();
		Transform3D ty = new Transform3D();
		Transform3D tz = new Transform3D();
		tx.rotX(vector.angle(new Vector3f(1, 0, 0)));
		ty.rotY(vector.angle(new Vector3f(0, 1, 0)));
		tz.rotZ(vector.angle(new Vector3f(0, 0, 1)));
		ty.mul(tz);
		tx.mul(ty);

		// Populate the xform group.
		/* xformGroup.addChild(t3d); */
		xformGroup.setTransform(tx);
		xformGroup.addChild(node);

		return xformGroup;

	}// end rotate
		// --------------------------------------------------//

	// Given an incoming node object and a vector object,
	// this method will return a transform group designed
	// to translate that node according to that vector.
	public TransformGroup translate(Node node, Vector3f vector) {

		Transform3D transform3D = new Transform3D();
		transform3D.setTranslation(vector);
		TransformGroup transformGroup = new TransformGroup();
		transformGroup.setTransform(transform3D);

		transformGroup.addChild(node);
		return transformGroup;
	}// end translate

}
