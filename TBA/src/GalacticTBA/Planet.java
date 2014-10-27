package GalacticTBA;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import javax.media.j3d.BranchGroup;
public class Planet {
	
	Color3f color;
	int posx,posy,posz;
	float radius;
	public Planet(){
		//Empty Contructor
	}
	public Planet(BranchGroup g,int x, int y, int z, float radius, Color3f color){
		this.setColor(color);
		this.setPosx(x);
		this.setPosy(y);
		this.setPosz(z);
		this.radius = radius;
		g.addChild(new Sphere(radius));
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
