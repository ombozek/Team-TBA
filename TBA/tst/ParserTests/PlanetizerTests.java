package ParserTests;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockSettings;
import org.mockito.Mockito;

import GalacticTBA.ETConst.Range;
import GalacticTBA.Planetizer;
import ParserTBA.Codebase;

public class PlanetizerTests {

	Planetizer planetizer;
	public final Range importRange = new Range(2, 12);
	public final Range paramRange = new Range(0, 6);
	public final Range slocRange = new Range(27, 378);
	public final Range commitRange = new Range(3, 17);

	@Before
	public void setUp() {
		Codebase codebase = Mockito.mock(Codebase.class);
		Mockito.when(codebase.getImportRange()).thenReturn(importRange);
		Mockito.when(codebase.getParamRange()).thenReturn(paramRange);
		Mockito.when(codebase.getSlocRange()).thenReturn(slocRange);
		Mockito.when(codebase.getCommitRange()).thenReturn(commitRange);

		planetizer = Mockito.spy(new Planetizer(codebase));
		// planetizer = Mockito.mock(Planetizer.class);
	}

	@Test
	public void testStarRadius() {
		// Mockito.when(planetizer.starRadius(Mockito.anyInt())).thenCallRealMethod();
		int sloc = new Random().nextInt(slocRange.getRange()) + slocRange.MIN;
		float radius = planetizer.starRadius(sloc);

	}

	@Test
	public void testStarColor() {
		Mockito.when(planetizer.starColor(Mockito.anyInt()))
				.thenCallRealMethod();

	}

	@Test
	public void testStarDistance() {
		Mockito.when(
				planetizer.starDistance(Mockito.anyInt(), Mockito.anyBoolean()))
				.thenCallRealMethod();

	}

	@Test
	public void testPlanetRadius() {
		Mockito.when(planetizer.starColor(Mockito.anyInt()))
				.thenCallRealMethod();

	}

	@Test
	public void testAsteroidRadius() {
		Mockito.when(planetizer.starColor(Mockito.anyInt()))
				.thenCallRealMethod();

	}

	@Test
	public void testAsteroidColor() {
		Mockito.when(planetizer.starColor(Mockito.anyInt()))
				.thenCallRealMethod();

	}

	@Test
	public void testPlanetColor() {
		Mockito.when(planetizer.starColor(Mockito.anyInt()))
				.thenCallRealMethod();

	}

}
