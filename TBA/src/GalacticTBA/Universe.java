package GalacticTBA;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import javax.media.j3d.BranchGroup;

public class Universe {

	public Universe() {
		// TODO Auto-generated constructor stub

	   SimpleUniverse universe = new SimpleUniverse();

	   BranchGroup group = new BranchGroup();
	   Planet p = new Planet();

	   Color3f light1Color = new Color3f(1.8f,0.1f,0.1f);
	   BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
	   Vector3f light1Direction = new Vector3f(4.0f,-7.0f,-12.0f);
	   DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
	   light1.setInfluencingBounds(bounds);
	   group.addChild(light1);
	   universe.getViewingPlatform().setNominalViewingTransform();

	   universe.addBranchGraph(group);

	}

	public static void main( String[] args ) {

	   new Universe();

	}

} // end of class
	


