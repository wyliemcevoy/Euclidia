package euclid.two.dim.world;

import java.util.Random;

import euclid.two.dim.Configuration;
import euclid.two.dim.InputManager;
import euclid.two.dim.Path;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Fish;

public class WorldStateFactory
{
	private InputManager inputManager;

	public WorldStateFactory(InputManager inputManager)
	{
		this.inputManager = inputManager;
	}

	public WorldState createRandomWorldState()
	{
		Random rand = new Random(System.currentTimeMillis());
		WorldState worldState = new WorldState();

		EuVector one = randVect();

		for (int i = 0; i < Configuration.numFish; i++)
		{
			Path path = new Path(new EuVector(one));

			inputManager.addPath(path);
			Fish fish = new Fish(worldState, path, new EuVector(rand.nextInt(Configuration.width), rand.nextInt(Configuration.height)));
			worldState.addObject(fish);
		}
		return worldState;
	}

	private EuVector randVect()
	{
		Random rand = new Random(System.currentTimeMillis());
		int x = 25 + rand.nextInt(Configuration.width - 50);
		int y = 25 + rand.nextInt(Configuration.height - 50);
		return new EuVector(x, y);
	}
}
