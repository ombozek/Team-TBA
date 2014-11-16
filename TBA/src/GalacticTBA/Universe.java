package GalacticTBA;
import com.sun.j3d.utils.universe.SimpleUniverse;
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
	   Color3f blue = new Color3f(0.1f,0.1f,1.0f);
	  
	   //Planet( Vector3f axis, float radius, float orbit_radius, Color3f color)
	   Planet sun = new Planet(new Vector3f(0,0,1),(float) 0.5,25,blue);
	   maingroup.addChild(sun.sphere);
	   
	   
	   createAxis(maingroup);
	   
	   Color3f light1Color = new Color3f(1.8f,0.1f,0.1f);
	   BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	   Vector3f light1Direction = new Vector3f(4.0f, -7.0f, 12.0f);
	   DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
	   light1.setInfluencingBounds(bounds);
	   maingroup.addChild(light1);


	   OrbitBehavior orbit = new OrbitBehavior(universe.getCanvas(), OrbitBehavior.REVERSE_ROTATE);
	   orbit.setSchedulingBounds(new BoundingSphere());
	   universe.getViewingPlatform().setViewPlatformBehavior(orbit);
	   
	   universe.getViewingPlatform().setNominalViewingTransform();
	   /*Transform3D move = lookTowardsOriginFrom(new Point3d(5, 5, 5));
       universe.getViewingPlatform().getViewPlatformTransform().setTransform(move);*/
	   universe.addBranchGraph(maingroup);

	}
	public Transform3D lookTowardsOriginFrom(Point3d point)
    {
        Transform3D move = new Transform3D();
        
        Vector3d up = new Vector3d(point.x, point.y + 1, point.z);
        move.lookAt(point, new Point3d(0.0d, 0.0d, 0.0d), up);

        return move;
    }
	public void createAxis(BranchGroup g){
		
		// Create X axis
	    LineArray axisXLines=new LineArray(2,LineArray.COORDINATES);
	    g.addChild(new Shape3D(axisXLines));
	      
	    axisXLines.setCoordinate(0,new Point3f(-10.0f,0.0f,0.0f));
	    axisXLines.setCoordinate(1,new Point3f(10.0f,0.0f,0.0f));        
	 
	    // Create Y axis  
	    LineArray axisYLines=new LineArray(2,LineArray.COORDINATES);
	    g.addChild(new Shape3D(axisYLines));
	      
	    axisYLines.setCoordinate(0,new Point3f(0.0f,-10.0f,0.0f));
	    axisYLines.setCoordinate(1,new Point3f(0.0f,10.0f,0.0f));
	   
	    // Create Z axis with arrow

	    LineArray axisZLines=new LineArray(2,LineArray.COORDINATES);
	    g.addChild(new Shape3D(axisZLines));
	    
	    axisZLines.setCoordinate(0,new Point3f(0.0f,0.0f,10.0f));
	    axisZLines.setCoordinate(1,new Point3f(0.0f,0.0f,-10.0f));
	   
	    
	}
	public static void main( String[] args ) {
	   new Universe();

	}

} // end of class
	


