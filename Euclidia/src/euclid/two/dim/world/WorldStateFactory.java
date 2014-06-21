package euclid.two.dim.world;

import java.util.Random;

import euclid.two.dim.Configuration;
import euclid.two.dim.InputManager;
import euclid.two.dim.Path;
import euclid.two.dim.model.Boid;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Fish;
import euclid.two.dim.model.Obstacle;

public class WorldStateFactory
{
	private InputManager inputManager;
	private Random rand;
	
	public WorldStateFactory(InputManager inputManager)
	{
		this.inputManager = inputManager;
		rand = new Random(System.currentTimeMillis());
	}
	
	public WorldState createRandomWorldState()
	{
		
		WorldState worldState = new WorldState();
		
		EuVector one = randVect();
		
		for (int i = 0; i < Configuration.numFish; i++)
		{
			Path path = new Path(new EuVector(one));
			
			inputManager.addPath(path);
			Fish fish = new Fish(worldState, path, new EuVector(rand.nextInt(Configuration.width), rand.nextInt(Configuration.height)));
			worldState.addObject(fish);
		}
		worldState.addObject(new Obstacle(new EuVector(500, 500), worldState));
		worldState.addObject(new Obstacle(new EuVector(300, 500), worldState));
		worldState.addObject(new Obstacle(new EuVector(700, 500), worldState));
		worldState.addObject(new Obstacle(new EuVector(400, 600), worldState));
		worldState.addObject(new Obstacle(new EuVector(600, 600), worldState));
		
		for (int i = 0; i < 40; i++)
		{
			buildBoid(new EuVector(one), worldState, randVect());
		}
		
		return worldState;
		
	}
	
	private EuVector randVect()
	{
		int x = 25 + rand.nextInt(Configuration.width - 50);
		int y = 25 + rand.nextInt(Configuration.height - 50);
		return new EuVector(x, y);
	}
	
	private void buildBoid(EuVector one, WorldState worldState, EuVector v)
	{
		Path path = new Path(new EuVector(randVect()));
		inputManager.addPath(path);
		
		Boid boid = new Boid(new Fish(worldState, path, v), worldState, path);
		
		boid.ingest(new Fish(worldState, path, (v.add(new EuVector(4, 4)))));
		boid.ingest(new Fish(worldState, path, (v.add(new EuVector(4, 0)))));
		boid.ingest(new Fish(worldState, path, (v.add(new EuVector(0, 4)))));
		boid.ingest(new Fish(worldState, path, (v.add(new EuVector(8, 4)))));
		boid.ingest(new Fish(worldState, path, (v.add(new EuVector(4, 8)))));
		boid.ingest(new Fish(worldState, path, (v.add(new EuVector(0, 8)))));
		boid.ingest(new Fish(worldState, path, (v.add(new EuVector(8, 8)))));
		boid.ingest(new Fish(worldState, path, (v.add(new EuVector(8, 0)))));
		worldState.addObject(boid);
		
	}
}
