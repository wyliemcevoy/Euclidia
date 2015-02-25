package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import euclid.two.dim.Path;
import euclid.two.dim.Player;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Minion;

public class MinionTest
{
	Minion unit;
	
	@Before
	public void before()
	{
		Path path = new Path(new EuVector(0, 0));
		Player player = new Player(Color.BLACK);
		this.unit = new Minion(path, new EuVector(10, 10), player);
	}
	
	@Test
	public void testCloneConstructor()
	{
		Minion clone = new Minion(unit);
		
		testValidityOfClone("Id", clone, unit);
		testValidityOfClone("position", clone.getPosition(), unit.getPosition());
		testValidityOfClone("futurePosition", clone.getFuturePosition(), unit.getFuturePosition());
		testValidityOfClone("Attack", clone.getAttack(), unit.getAttack());
		testValidityOfClone("SteeringBehavior", clone.getSteeringType(), unit.getSteeringType());
		
		assertEquals("SteeringBehavior", clone.getSteeringBehavior(), unit.getSteeringBehavior());
		
		//validClone(clone.getpath());
		
	}
	
	private void testValidityOfClone(String message, Object one, Object two)
	{
		assertFalse(message + " is null", one == null);
		assertFalse(message + " is null", two == null);
		
		assertFalse(message, one == two);
		assertTrue(message, one.equals(two));
	}
	
	private void testValidityOfCopy(String message, Object one, Object two)
	{
		
	}
	
}
