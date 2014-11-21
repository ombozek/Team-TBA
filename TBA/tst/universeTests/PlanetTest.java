package universeTests;

import static GalacticTBA.ETConst.PLANET_RADIUS_DIVISOR;
import static GalacticTBA.ETConst.PLANET_SIZE_DIVISOR;
import static org.junit.Assert.assertEquals;

import java.awt.Color;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;

import org.junit.Before;
import org.junit.Test;

import GalacticTBA.Planet;

import com.sun.j3d.utils.geometry.Sphere;

public class PlanetTest {
	Planet p1;
	Vector3f p1Axis = new Vector3f(0, 0, 1);
	float p1Radius = 10f;
	float p1Distance = 100f;
	Color3f p1Color = new Color3f(Color.BLUE);

	Planet p2;
	Vector3f p2Axis = new Vector3f(1, 1, 1);
	float p2Radius = 5f;
	float p2Distance = 0f;
	Color3f p2Color = new Color3f(Color.GREEN);

	@Before
	public void setUp() throws Exception {

		p1 = new Planet(p1Axis, p1Radius, p1Distance, p1Color);
		p2 = new Planet(p2Axis, p2Radius, p2Distance, p2Color, p1.getTg_trans());

	}

	@Test
	public void test() {
		assertEquals(p1Color, p1.getColor());
		assertEquals(p2Color, p2.getColor());
		assertEquals(p1Distance / PLANET_RADIUS_DIVISOR, p1.getOrbit_radius(),
				0.0);
		assertEquals(0.0 / PLANET_RADIUS_DIVISOR, p2.getOrbit_radius(), 0.0);
		assertEquals(10 / PLANET_SIZE_DIVISOR, p1.getRadius(), 0.0);
		assertEquals(5 / PLANET_SIZE_DIVISOR, p2.getRadius(), 0.0);
		assertEquals(p1Axis, p1.getAxis());
		assertEquals(p2Axis, p2.getAxis());
		assertEquals(p1.getTg_trans(), p2.getTg_parent());
	}

	@Test
	public void testColor() {
		p1.setColor(p2Color);
		Color3f color = null;
		p1.getSphere().getAppearance().getColoringAttributes().getColor(color);
		assertEquals(p2Color, color);
	}

	@Test
	public void testTranslate() {
		Sphere s = new Sphere(1);
		TransformGroup tg = new TransformGroup();
		tg = p1.translate(s, new Vector3f(10, 0, 0));
		Transform3D t3d = new Transform3D();
		s.getLocalToVworld(t3d);
		Vector3f location = new Vector3f();
		t3d.get(location);
		assertEquals(10, location.x, 0);
		assertEquals(0, location.y, 0);
		assertEquals(0, location.z, 0);

	}

}
