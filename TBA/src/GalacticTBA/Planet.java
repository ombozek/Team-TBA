package GalacticTBA;
import com.sun.j3d.utils.geometry.*;
import javax.vecmath.*;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.ColoringAttributes;
public class Planet {
	Sphere sphere;
	Appearance ap = new Appearance();
	ColoringAttributes ca;
	Color3f color;
	int posx,posy,posz;
	float radius;
	public Planet(){
		//Empty Constructor
	}
	public Planet(BranchGroup g,int x, int y, int z, float radius, Color3f color){
		//Set Properties of sphere
		this.setColor(color);
		this.setPosx(x);
		this.setPosy(y);
		this.setPosz(z);
		this.radius = radius;
		
		//TODO: Transform position of sphere before creation
		
		//Create Sphere Object
		this.sphere = new Sphere(radius);
		
		//Apply Coloring Attributes
		this.ca = new ColoringAttributes(color,ColoringAttributes.NICEST);
		this.ap.setColoringAttributes(ca);
		this.sphere.setAppearance(ap);
		
		//Add Sphere to group
		g.addChild(this.sphere);
	}
	private void setColor(Color3f c) {
		this.color=c;
	}
	public int getPosx() {
		return posx;
	}
	public void setPosx(int posx) {
		this.posx = posx;
	}
	public int getPosy() {
		return posy;
	}
	public void setPosy(int posy) {
		this.posy = posy;
	}

	public int getPosz() {
		return posz;
	}
	public void setPosz(int posz) {
		this.posz = posz;
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
	  
}
