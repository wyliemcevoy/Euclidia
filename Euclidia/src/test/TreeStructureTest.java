package test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import euclid.two.dim.datastructure.AABBNode;
import euclid.two.dim.datastructure.AxisAlignedBoundingBox;
import euclid.two.dim.model.EuVector;

public class TreeStructureTest {
	AxisAlignedBoundingBox one, two, three, four;

	@Before
	public void setUp() {

		one = new AxisAlignedBoundingBox(new EuVector(0, 0), new EuVector(10, 10));
		two = new AxisAlignedBoundingBox(new EuVector(20, 0), new EuVector(30, 10));

		three = new AxisAlignedBoundingBox(new EuVector(100, 100), new EuVector(110, 110));
		four = new AxisAlignedBoundingBox(new EuVector(120, 100), new EuVector(130, 110));
	}

	@Test
	public void test() {

		AABBNode root = new AABBNode(one);

		root.add(new AABBNode(two));
		root.add(new AABBNode(three));
		root.printArea();
		root.add(new AABBNode(four));

		root.printArea();
		areaTest(root.getLeft());
		areaTest(root.getRight());
	}

	public void areaTest(AABBNode node) {
		assertTrue("Non optimal structuring of tree.", node.getArea() <= 300);

		if (node.getLeft() != null) {
			areaTest(node.getLeft());
		}
		if (node.getRight() != null) {
			areaTest(node.getRight());
		}
	}

}
