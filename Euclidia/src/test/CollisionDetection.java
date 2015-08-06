package test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import euclid.two.dim.VectorMath;
import euclid.two.dim.model.EuVector;

public class CollisionDetection {

	@Before
	public void setup() {

	}

	@Test
	public void test() {
		EuVector p0 = new EuVector(0, 0);
		EuVector p1 = new EuVector(10, 10);
		EuVector p2 = new EuVector(0, 10);
		EuVector p3 = new EuVector(10, 0);

		EuVector collision = VectorMath.getIntersectionPointOfTwoLineSegments(p0, p1, p2, p3);
		assertTrue("Failed to detect a collision", collision.equals(new EuVector(5, 5)));

		p0 = new EuVector(0, 0);
		p1 = new EuVector(10, 10);
		p2 = new EuVector(10, 0);
		p3 = new EuVector(20, 10);

		EuVector nonCollision = VectorMath.getIntersectionPointOfTwoLineSegments(p0, p1, p2, p3);
		assertNull("False positive for a collision", nonCollision);
	}

}
