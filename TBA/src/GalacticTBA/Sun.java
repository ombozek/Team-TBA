package GalacticTBA;

import java.awt.Color;

import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;

public class Sun extends Planet {
	BoundingSphere light_bounds;
	PointLight light;
	TransformGroup tg_trans_light;
	TransformGroup tg_rot_light;
	public Sun(Vector3f axis, float radius, float orbit_radius, Color3f color){
		//Set Properties of sphere
			//this.setColor(color);
			this.radius = radius/100;
			this.axis = new Vector3f(0,0,1f);
			this.orbit_radius = orbit_radius/50;
		//Create Sphere Object
			
			this.sphere = new Sphere(this.radius);
			
		    
			
			
		//Create Transform Group for just this object			
			this.tg_trans = translate(this.sphere,new Vector3f(this.orbit_radius,0,0));
			this.tg_rot = rotate(this.tg_trans, new Alpha(-1,(long) ((radius+orbit_radius)*ORBIT_SPEED_MODIFIER)));
		//Apply Coloring Attributes
			this.ca = new ColoringAttributes(color,ColoringAttributes.NICEST);
			this.ap.setColoringAttributes(ca);
			
			this.mat = new Material(color, color, color, color, 1.0f);
			this.mat.setColorTarget(Material.EMISSIVE);
			this.mat.setLightingEnable(false);
			this.ap.setMaterial(mat);
			this.sphere.setAppearance(ap);
			
			
			this.light_bounds = new BoundingSphere(new Point3d(0,0,0),100.0); 
			this.light = new PointLight();
			this. light.setEnable(true);
			this.light.setColor(color);
		    //light.setPosition(new Point3f(position.getX(),position.getY(),position.getZ()));
			this.light.setCapability(PointLight.ALLOW_STATE_WRITE);
			this. light.setCapability(PointLight.ALLOW_COLOR_WRITE);
			this.light.setCapability(PointLight.ALLOW_POSITION_WRITE);
			this.light.setCapability(PointLight.ALLOW_ATTENUATION_WRITE);
			this.light.setInfluencingBounds(light_bounds);
		    this.tg_trans_light = translate(this.light,new Vector3f(this.orbit_radius,0,0));
			this.tg_rot_light = rotate(this.tg_trans_light, new Alpha(-1,(long) ((radius+orbit_radius)*ORBIT_SPEED_MODIFIER)));
		    // this.tg_rot.addChild(light);*/
			
			
			
	}
	public Sun(Vector3f axis, float radius, int orbit_radius, Color3f color,
			TransformGroup tg) {
		this(axis,radius,orbit_radius,color);
		tg.addChild(this.tg_rot);
		tg.addChild(this.tg_rot_light);
	}
}
