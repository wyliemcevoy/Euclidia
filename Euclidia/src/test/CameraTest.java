package test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.world.Camera;

public class CameraTest {
	private Camera camera;
	private EuVector hunByHunViewLocation;

	@Before
	public void setup() {
		camera = new Camera();
		camera.zoom(1);
		hunByHunViewLocation = new EuVector(100, 100);
	}

	@Test
	public void testTransformVsInverse() {
		camera.translate(10, 10);
		camera.zoom(2);

		EuVector screenLocation = camera.mapToScreen(hunByHunViewLocation);
		System.out.println(screenLocation);
		EuVector originalLocation = camera.screenToMap(screenLocation);
		assertTrue("Failed to convert coordinates from map-space to view-space correctly", screenLocation.equals(new EuVector(180, 180)));

		assertTrue("Failed to convert coordinates from map-space to view-space correctly", originalLocation.equals(hunByHunViewLocation));
	}
}