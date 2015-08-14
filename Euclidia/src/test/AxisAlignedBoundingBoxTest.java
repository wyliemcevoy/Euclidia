package test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import euclid.two.dim.datastructure.AxisAlignedBoundingBox;
import euclid.two.dim.model.EuVector;

public class AxisAlignedBoundingBoxTest {

	private AxisAlignedBoundingBox one, two;

	@Before
	public void setUpBeforeClass() throws Exception {
		one = new AxisAlignedBoundingBox(new EuVector(0, 0), new EuVector(10, 10));
		two = new AxisAlignedBoundingBox(new EuVector(20, 0), new EuVector(30, 10));
	}

	@Test
	public void test() {
		AxisAlignedBoundingBox oneAndTwo = new AxisAlignedBoundingBox(one, two);

		assertTrue(oneAndTwo.getX() == 0);
		assertTrue(oneAndTwo.getY() == 0);
		assertTrue(oneAndTwo.getWidth() == 30);
		assertTrue(oneAndTwo.getHeight() == 10);
	}

}
