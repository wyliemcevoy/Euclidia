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
	
	public WorldState createRandomWorldState() {
		Random rand = new Random(System.currentTimeMillis());
		WorldState worldState = new WorldState();
		// Fish target = new Fish(new
		// EuVector(rand.nextInt(Configuration.width),
		// rand.nextInt(Configuration.height)));
		
		EuVector one = randVect();
		/*
		 * EuVector two = randVect(); EuVector three = randVect(); EuVector four
		 * = randVect(); EuVector five = randVect(); EuVector six = new
		 * EuVector(50, 50); EuVector seven = new EuVector(900, 900); EuVector
		 * eight = new EuVector(50, 900); EuVector nine = new EuVector(500,
		 * 500);
		 */
		for (int i = 0; i < Configuration.numFish; i++)
		{
			Path path = new Path(new EuVector(one));
			/*
			 * path.addTarget(new EuVector(two)); path.addTarget(new
			 * EuVector(three)); path.addTarget(new EuVector(four));
			 * path.addTarget(new EuVector(five)); path.addTarget(new
			 * EuVector(six)); path.addTarget(new EuVector(seven));
			 * path.addTarget(new EuVector(eight)); path.addTarget(new
			 * EuVector(nine));
			 */
			
			inputManager.addPath(path);
			Fish fish = new Fish(worldState, path, new EuVector(rand.nextInt(Configuration.width), rand.nextInt(Configuration.height)));
			worldState.addObject(fish);
		}
		return worldState;
	}
	
	private EuVector randVect() {
		Random rand = new Random(System.currentTimeMillis());
		int x = 25 + rand.nextInt(Configuration.width - 50);
		int y = 25 + rand.nextInt(Configuration.height - 50);
		return new EuVector(x, y);
	}
}
