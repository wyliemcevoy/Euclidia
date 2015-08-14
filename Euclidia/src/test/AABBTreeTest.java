package test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import euclid.two.dim.datastructure.AABBNode;
import euclid.two.dim.datastructure.AxisAlignedBoundingBox;
import euclid.two.dim.model.EuVector;

public class AABBTreeTest {
	private AxisAlignedBoundingBox one, two, three, four;
	private AABBNode nodeOne, nodeTwo, nodeThree, nodeFour;

	@Before
	public void setup() {
		one = new AxisAlignedBoundingBox(new EuVector(0, 0), new EuVector(10, 10));
		two = new AxisAlignedBoundingBox(new EuVector(20, 0), new EuVector(30, 10));
		three = new AxisAlignedBoundingBox(new EuVector(0, 20), new EuVector(10, 30));
		four = new AxisAlignedBoundingBox(new EuVector(20, 20), new EuVector(30, 30));
		nodeOne = new AABBNode(one);
		nodeTwo = new AABBNode(two);
		nodeThree = new AABBNode(three);
		nodeFour = new AABBNode(four);
	}

	@Test
	public void depthOneTest() {
		AABBNode root = nodeOne;

		root.add(nodeTwo);

		assertTrue("Incorrect node count", root.getNodeCount() == 3);
		assertTrue("Incorrect root area", root.getArea() == 300);
	}

	@Test
	public void depthTwoTest() {
		AABBNode root = nodeOne;

		root.add(nodeTwo);
		root.add(nodeThree);
		root.add(nodeFour);

		assertTrue("Incorrect node count", root.getNodeCount() == 7);
		assertTrue("Incorrect root area " + root.getArea(), root.getArea() == 900);
		assertTrue("Incorrect tree structure", root.getLeft().getArea() == 300);
		assertTrue("Incorrect tree structure", root.getRight().getArea() == 300);
	}

}
