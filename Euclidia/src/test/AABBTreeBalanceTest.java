package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import euclid.two.dim.datastructure.AABBNode;
import euclid.two.dim.datastructure.AxisAlignedBoundingBox;
import euclid.two.dim.model.EuVector;

public class AABBTreeBalanceTest {

	Random rand;
	AxisAlignedBoundingBox[] boxes;
	ArrayList<AABBNode> nodes;

	@Before
	public void setUpNodes() {
		Random rand = new Random();
		boxes = new AxisAlignedBoundingBox[100];
		for (int i = 0; i < boxes.length; i++) {
			int x = rand.nextInt(1000);
			int y = rand.nextInt(1000);

			boxes[i] = new AxisAlignedBoundingBox(new EuVector(x, y), new EuVector(x + 10, y + 10));
		}

	}

	public void resetNodes() {
		nodes = new ArrayList<AABBNode>();

		for (int i = 0; i < boxes.length; i++) {
			nodes.add(new AABBNode(boxes[i]));
		}

		Collections.shuffle(nodes, new Random(System.currentTimeMillis()));
	}

	@Test
	public void test() {

		for (int i = 0; i < 100; i++) {
			runRandomOrderInsertTest();
		}

		assertTrue("Incorrect node count ", nodes.get(0).getNodeCount() == 199);
	}

	public void runRandomOrderInsertTest() {
		resetNodes();

		AABBNode root = nodes.get(0);

		for (int i = 1; i < nodes.size(); i++) {
			root.add(nodes.get(i));
		}

		root.printArea();
	}

}
