package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Minion;
import euclid.two.dim.path.Path;
import euclid.two.dim.team.Team;

public class MinionTest {
	Minion unit;

	@Before
	public void before() {
		Path path = new Path(new EuVector(0, 0));
		path.addTarget(new EuVector(10, 10));
		path.addTarget(new EuVector(50, 50));

		Team blackTeam = Team.Black;
		this.unit = new Minion(blackTeam, new EuVector(10, 10));
	}

	@Test
	public void testCloneConstructor() {
		Minion clone = new Minion(unit);

		testValidityOfClone("Id", clone, unit);
		testValidityOfClone("position", clone.getPosition(), unit.getPosition());
		testValidityOfClone("futurePosition", clone.getFuturePosition(), unit.getFuturePosition());
		testValidityOfClone("Attack", clone.getAttack(), unit.getAttack());

		assertEquals("SteeringBehavior", clone.getSteeringBehavior(), unit.getSteeringBehavior());
		testValidityOfClone("path", clone.getPath(), unit.getPath());
	}

	private void testValidityOfClone(String message, Object one, Object two) {
		assertFalse(message + " is null", one == null);
		assertFalse(message + " is null", two == null);

		assertFalse(message, one == two);
		assertTrue(message, one.equals(two));
	}

}
