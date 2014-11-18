package GalacticTBA;
import java.awt.Color;

import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.behaviors.mouse.MouseZoom;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import javax.media.j3d.BranchGroup;

public class Universe {

	public Universe() {
		// TODO Auto-generated constructor stub

	   SimpleUniverse universe = new SimpleUniverse();
	   
	   BranchGroup maingroup = new BranchGroup();
	   TransformGroup viewgroup = new TransformGroup();
	   maingroup.addChild(viewgroup);
	   //Color3f yellow = new Color3f(0.85f,0.5f,0.2f);
	   Color3f blue = new Color3f(0.1f,0.1f,1.0f);
	   Color3f red = new Color3f(0.9f,0.1f,0.1f);
	   Color3f yellow = new Color3f(Color.YELLOW);
	   Color3f white = new Color3f(Color.WHITE);
	   Color3f orange = new Color3f(Color.ORANGE);
	   //Planet( Vector3f axis, float radius, float orbit_radius, Color3f color)
	   Planet hole = new Planet(new Vector3f(0,0,1),(float) 0,0,yellow,viewgroup);
	   Planet sun = new Sun(new Vector3f(0,0,1),(float) 50,300,yellow,hole.tg_trans);
	   
	   
	   Planet earth = new Planet(new Vector3f(0,0,1),(float) 10,100,blue,sun.tg_trans);
	   Planet moon = new Planet(new Vector3f(0,0,1),(float) 1,10,white,earth.tg_trans);
	   Planet mercury = new Planet(new Vector3f(0,0,1),(float) 5,50,red,sun.tg_trans);
	   Planet venus = new Planet(new Vector3f(0,0,1),(float) 7,60,blue,sun.tg_trans);
	   Planet jupiter = new Planet(new Vector3f(0,0,1),(float) 15,250,red,sun.tg_trans);
	   for(int i = 0; i < 5; i++){
		   new Planet(new Vector3f(0,0,1),(float) i,10+5*i,white,jupiter.tg_trans);
	   }
	   Planet sun2 = new Sun(new Vector3f(0,0,1),(float) 50,500,white,sun.tg_trans);
	   
	   
	   Planet earth2 = new Planet(new Vector3f(0,0,1),(float) 10,100,blue,sun2.tg_trans);
	   Planet moon2 = new Planet(new Vector3f(0,0,1),(float) 1,10,white,earth2.tg_trans);
	   Planet mercury2 = new Planet(new Vector3f(0,0,1),(float) 5,50,red,sun2.tg_trans);
	   Planet venus2 = new Planet(new Vector3f(0,0,1),(float) 7,60,blue,sun2.tg_trans);
	   createAxis(hole.tg_trans);
	   createAxis(sun.tg_trans);
	   createAxis(sun2.tg_trans);
	   
	   Color3f light1Color = new Color3f(1.8f,0.1f,0.1f);
	   BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 500.0);
	   Vector3f light1Direction = new Vector3f(4.0f, -7.0f, 12.0f);
	   DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
	   Vector3f light2Direction = new Vector3f(4.0f, 7.0f, 12.0f);
	   DirectionalLight light2 = new DirectionalLight(light1Color, light2Direction);
	   light2.setBoundsAutoCompute(true);
	   //maingroup.addChild(light2);
	   light1.setInfluencingBounds(bounds);
	   //maingroup.addChild(light1);


	   OrbitBehavior orbit = new OrbitBehavior(universe.getCanvas(), OrbitBehavior.REVERSE_ALL);			   
	   orbit.setSchedulingBounds(new BoundingSphere());
	  MouseZoom myMouse = new MouseZoom();
	  myMouse.setTransformGroup(viewgroup);
	  myMouse.setSchedulingBounds(new BoundingSphere());
	  maingroup.addChild(myMouse);
	   
	   universe.getViewingPlatform().setViewPlatformBehavior(orbit);
	   universe.addBranchGraph(maingroup);
	   universe.getViewingPlatform().setNominalViewingTransform();
	   //Transform3D move = lookTowardsOriginFrom(new Point3d(5, 5, 200));
       //universe.getViewingPlatform().getViewPlatformTransform().setTransform(move);
	  

	}
	public Transform3D lookTowardsOriginFrom(Point3d point)
    {
        Transform3D move = new Transform3D();
        
        Vector3d up = new Vector3d(0, 1, 0);
        move.lookAt(point, new Point3d(0.0d, 0.0d, 0.0d), up);

        return move;
    }
	public void createAxis(TransformGroup tg){
		
		// Create X axis
	    LineArray axisXLines=new LineArray(2,LineArray.COORDINATES);
	    tg.addChild(new Shape3D(axisXLines));
	      
	    axisXLines.setCoordinate(0,new Point3f(-10.0f,0.0f,0.0f));
	    axisXLines.setCoordinate(1,new Point3f(10.0f,0.0f,0.0f));        
	 
	    // Create Y axis  
	    LineArray axisYLines=new LineArray(2,LineArray.COORDINATES);
	    tg.addChild(new Shape3D(axisYLines));
	      
	    axisYLines.setCoordinate(0,new Point3f(0.0f,-10.0f,0.0f));
	    axisYLines.setCoordinate(1,new Point3f(0.0f,10.0f,0.0f));
	   
	    // Create Z axis with arrow

	    LineArray axisZLines=new LineArray(2,LineArray.COORDINATES);
	    tg.addChild(new Shape3D(axisZLines));
	    
	    axisZLines.setCoordinate(0,new Point3f(0.0f,0.0f,10.0f));
	    axisZLines.setCoordinate(1,new Point3f(0.0f,0.0f,-10.0f));
	   
	    
	}
	public static void main( String[] args ) {
	   new Universe();

	}

} // end of class
	


