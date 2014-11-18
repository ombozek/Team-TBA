package GalacticTBA;

import java.awt.Color;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;

public class Sun extends Planet {
	BoundingSphere light_bounds;
	Material mat;
	public Sun(Vector3f axis, float radius, float orbit_radius, Color3f color){
		//Set Properties of sphere
			//this.setColor(color);
			this.radius = radius/100;
			this.axis = new Vector3f(0,0,1f);
			this.orbit_radius = orbit_radius/50;
		//Create Sphere Object
			light_bounds = new BoundingSphere(new Point3d(0.0, 0.0,
			        0.0), // Center
			        1000.0); // Extent
			this.sphere = new Sphere(this.radius);
	
		//Create Transform Group for just this object			
			
			this.tg_trans = translate(this.sphere,new Vector3f(this.orbit_radius,0,0));
			this.tg_rot = rotate(this.tg_trans, new Alpha(-1,(long) ((radius+orbit_radius)*ORBIT_SPEED_MODIFIER)));
		//Apply Coloring Attributes
			this.ca = new ColoringAttributes(color,ColoringAttributes.NICEST);
			this.ap.setColoringAttributes(ca);
			mat = new Material(color, color, color, new Color3f(Color.black), 1.0f);
			mat.setLightingEnable(true);
			this.ap.setMaterial(mat );
			this.sphere.setAppearance(ap);
	}
	public Sun(Vector3f axis, float radius, int orbit_radius, Color3f color,
			TransformGroup tg) {
		this(axis,radius,orbit_radius,color);
		tg.addChild(this.tg_rot);
	}
}
