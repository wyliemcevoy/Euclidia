package euclid.two.dim.world;

import java.util.Random;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Fish;

public class WorldStateFactory
{

	public WorldState createRandomWorldState()
	{
		WorldState worldState = new WorldState();
		Fish target = new Fish(new EuVector(250, 250));
		Random rand = new Random();
		for (int i = 0; i < 50; i++)
		{
			Fish fish = new Fish(worldState, target, new EuVector(rand.nextInt(500), rand.nextInt(500)));
			worldState.addObject(fish);
		}
		return worldState;
	}
}
