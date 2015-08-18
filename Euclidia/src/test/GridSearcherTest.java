package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import euclid.two.dim.datastructure.GridNode;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Minion;
import euclid.two.dim.team.Team;

public class GridSearcherTest {

	private GridNode gs;

	@Before
	public void setUpBefore() {
		gs = new GridNode(new EuVector(0, 0), new EuVector(100, 100));
		gs.add(new Minion(Team.Black, new EuVector(10, 10)));
		gs.add(new Minion(Team.Red, new EuVector(30, 30)));
	}

	@Test
	public void test() {

		ArrayList<GameSpaceObject> queryResults = gs.getGsosInRange(new EuVector(15, 15), 300);

		assertTrue("Incorrectly retrieves elements from the grid.", queryResults.size() == 2);
		System.out.println("\n\n");

		queryResults = gs.getGsosInRange(new EuVector(10, 10), 1);
		System.out.println(queryResults.size());
		assertTrue("Incorrectly retrieves elements from the grid.", queryResults.size() == 1);
	}
}
