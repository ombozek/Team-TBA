package universeTests;

import static org.junit.Assert.*;

import java.awt.Color;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;

import GalacticTBA.Planet;

public class PlanetTest {
	Planet p1;
	Planet p2;
	@Before
	public void setUp() throws Exception {
		
		p1 = new Planet(new Vector3f(0,0,1),(float) 10,100,new Color3f(Color.BLUE));
		p2 = new Planet(new Vector3f(1,1,1),(float) 5,0,new Color3f(Color.GREEN),p1.getTg_trans());
		
	}

	@SuppressWarnings("deprecation")
	@Test
	public void test() {
		assertEquals(p1.getColor() , new Color3f(Color.BLUE));
		assertEquals(p2.getColor(),new Color3f(Color.GREEN));
		assertEquals(p1.getOrbit_radius(),100.0);
		assertEquals(p2.getOrbit_radius(),0.0);
		assertEquals(p1.getRadius(),10);
		assertEquals(p2.getRadius(),5);
		assertEquals(p1.getAxis(), new Vector3f(0,0,1));
		assertEquals(p2.getAxis(), new Vector3f(1,1,1));
		assertEquals(p2.getTg_parent(),p1.getTg_rot());
	}
	@Test
	public void testColor(){
		p1.setColor(new Color3f(Color.GREEN));
		Color3f color = null;
		p1.getSphere().getAppearance().getColoringAttributes().getColor(color);
		assertEquals(color,new Color3f(Color.GREEN));
	}
	@Test
	public void testTranslate(){
		TransformGroup tg = new TransformGroup();
		tg = p1.translate(p1.getSphere(),new Vector3f(10,0,0));
		assertEquals(tg,p1.getSphere().getParent());
		Transform3D t3d = new Transform3D();
		p1.getSphere().getLocalToVworld(t3d);
		Vector3f location = new Vector3f();
		t3d.get(location);
		
		assertEquals(location.x,10);
		assertEquals(location.y,0);
		assertEquals(location.z,0);
	}

}
