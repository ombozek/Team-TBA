package GalacticTBA;
import com.sun.j3d.utils.geometry.*;

import javax.vecmath.*;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.Node;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
public class Planet {
	Sphere sphere;
	
	//Appearance Attributes
	Appearance ap = new Appearance();
	ColoringAttributes ca;
	Color3f color;
	float radius;
	//Positioning attributes
	Vector3f pos;
	Vector3f axis;
	TransformGroup tg_trans;
	TransformGroup tg_rot;
	float orbit_radius;
	public Planet(){
		//Empty Constructor
	}
	public Planet(Vector3f axis, float radius, float orbit_radius, Color3f color){
		//Set Properties of sphere
			this.setColor(color);
			this.radius = radius;
			this.axis = new Vector3f(0,0,1f);
			this.orbit_radius = orbit_radius;
		//Create Sphere Object
			this.sphere = new Sphere(radius);
	
		//Create Transform Group for just this object			
			/*this.tg_rot = rotate(this.sphere, new Alpha(1,1000));
			this.tg_trans = translate(this.tg_rot,new Vector3f(this.orbit_radius,0,0));*/
			
		//Apply Coloring Attributes
			this.ca = new ColoringAttributes(color,ColoringAttributes.NICEST);
			this.ap.setColoringAttributes(ca);
			this.sphere.setAppearance(ap);
	}
	//Constructor to add to main branchgroup
	public Planet(Vector3f axis, float radius, float orbit_radius, Color3f color,BranchGroup g){
		//Set Properties of sphere
		this.setColor(color);
		this.radius = radius;
		this.axis = new Vector3f(0,0,1f);
		this.orbit_radius = orbit_radius;
	//Create Sphere Object
		this.sphere = new Sphere(1);
		
	//Create Transform Group for just this object			
		/*this.tg_rot = rotate(this.sphere, new Alpha(1,1000));
		this.tg_trans = translate(this.tg_rot,new Vector3f(this.orbit_radius,0,0));*/
		g.addChild(this.sphere);
	//Apply Coloring Attributes
		/*this.ca = new ColoringAttributes(color,ColoringAttributes.NICEST);
		this.ap.setColoringAttributes(ca);
		this.sphere.setAppearance(ap);*/
		//Add Sphere to main branchgroup
		
	}
	//Constructor to add to a parent transformgroup
	public Planet(int x, int y, int z, Vector3f axis, float radius,float orbit_radius, Color3f color, TransformGroup tg){
		this(axis,radius,orbit_radius,color);
		tg.addChild(this.tg_trans);
	}
	private void setColor(Color3f c) {
		this.color=c;
	}
	public double getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public Color3f getColor() {
		return color;
	}
	TransformGroup rotate(Node node,Alpha alpha){

	      TransformGroup xformGroup = new TransformGroup();
	      xformGroup.setCapability(
	                    TransformGroup.ALLOW_TRANSFORM_WRITE);

	      //Create an interpolator for rotating the node.
	      RotationInterpolator interpolator = 
	               new RotationInterpolator(alpha,xformGroup);

	      //Establish the animation region for this
	      // interpolator.
	      interpolator.setSchedulingBounds(new BoundingSphere(
	                           new Point3d(0.0,0.0,0.0),1.0));

	      //Populate the xform group.
	      xformGroup.addChild(interpolator);
	      xformGroup.addChild(node);

	      return xformGroup;

	    }//end rotate
	    //--------------------------------------------------//

	    //Given an incoming node object and a vector object,
	    // this method will return a transform group designed
	    // to translate that node according to that vector.
	    TransformGroup translate(Node node,Vector3f vector){

	        Transform3D transform3D = new Transform3D();
	        transform3D.setTranslation(vector);
	        TransformGroup transformGroup = 
	                                     new TransformGroup();
	        transformGroup.setTransform(transform3D);

	        transformGroup.addChild(node);
	        return transformGroup;
	    }//end translate
	  
}
