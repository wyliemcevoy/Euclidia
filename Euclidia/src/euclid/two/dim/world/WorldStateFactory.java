package euclid.two.dim.world;

import java.util.ArrayList;
import java.util.Random;

import euclid.two.dim.Configuration;
import euclid.two.dim.Path;
import euclid.two.dim.input.InputManager;
import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Door;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.Room;
import euclid.two.dim.model.Unit;
import euclid.two.dim.render.Camera;

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
			
			Unit fish = new Unit(worldState, path, new EuVector(rand.nextInt(Configuration.width), rand.nextInt(Configuration.height)));
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
	
	public WorldState createURoomsWorldState()
	{
		WorldState worldState = new WorldState();
		
		//inputManager.addPath(path);
		for (int i = 0; i < 200; i++)
		{
			Path path = new Path(new EuVector(150, 250));
			Unit fish = new Unit(worldState, path, randVect(100, 200, 100, 200));
			worldState.addObject(fish);
		}
		
		Room rTL = new Room(100, 100, 100, 100);
		Room rBL = new Room(100, 200, 100, 100);
		Door d1 = new Door(rTL, rBL);
		d1.setPointOne(new EuVector(100, 200));
		d1.setPointTwo(new EuVector(200, 200));
		worldState.addDoor(d1);
		
		Room rBC = new Room(200, 200, 100, 100);
		Door d2 = new Door(rBL, rBC);
		d2.setPointOne(new EuVector(200, 200));
		d2.setPointTwo(new EuVector(200, 300));
		worldState.addDoor(d2);
		
		Room rBR = new Room(300, 200, 100, 100);
		Door d3 = new Door(rBC, rBR);
		d3.setPointOne(new EuVector(300, 200));
		d3.setPointTwo(new EuVector(300, 300));
		worldState.addDoor(d3);
		
		Room rTR = new Room(300, 100, 100, 100);
		Door d4 = new Door(rBR, rTR);
		d4.setPointOne(new EuVector(300, 200));
		d4.setPointTwo(new EuVector(400, 200));
		worldState.addDoor(d4);
		
		ArrayList<Room> rooms = new ArrayList<Room>();
		rooms.add(rTL);
		rooms.add(rBL);
		rooms.add(rBC);
		rooms.add(rBR);
		rooms.add(rTR);
		worldState.setRooms(rooms);
		
		worldState.addObject(new Obstacle(new EuVector(250, 150), worldState));
		worldState.addObject(new Obstacle(new EuVector(50, 50), worldState));
		worldState.addObject(new Obstacle(new EuVector(50, 150), worldState));
		worldState.addObject(new Obstacle(new EuVector(50, 250), worldState));
		worldState.addObject(new Obstacle(new EuVector(50, 350), worldState));
		worldState.addObject(new Obstacle(new EuVector(150, 50), worldState));
		worldState.addObject(new Obstacle(new EuVector(350, 50), worldState));
		worldState.addObject(new Obstacle(new EuVector(250, 50), worldState));
		worldState.addObject(new Obstacle(new EuVector(150, 350), worldState));
		worldState.addObject(new Obstacle(new EuVector(250, 350), worldState));
		worldState.addObject(new Obstacle(new EuVector(350, 350), worldState));
		worldState.setCamera(new Camera());
		return worldState;
	}
	
	private EuVector randVect()
	{
		int x = 25 + rand.nextInt(Configuration.width - 50);
		int y = 25 + rand.nextInt(Configuration.height - 50);
		return new EuVector(x, y);
	}
	
	private EuVector randVect(int lowX, int highX, int lowY, int highY)
	{
		int x = lowX + rand.nextInt(highX - lowX);
		int y = lowY + rand.nextInt(highY - lowY);
		return new EuVector(x, y);
	}
	
	private void buildBoid(EuVector one, WorldState worldState, EuVector v)
	{
		Path path = new Path(new EuVector(randVect()));
		//inputManager.addPath(path);
		
		Boid boid = new Boid(new Unit(worldState, path, v), worldState, path);
		
		boid.ingest(new Unit(worldState, path, (v.add(new EuVector(4, 4)))));
		boid.ingest(new Unit(worldState, path, (v.add(new EuVector(4, 0)))));
		boid.ingest(new Unit(worldState, path, (v.add(new EuVector(0, 4)))));
		boid.ingest(new Unit(worldState, path, (v.add(new EuVector(8, 4)))));
		boid.ingest(new Unit(worldState, path, (v.add(new EuVector(4, 8)))));
		boid.ingest(new Unit(worldState, path, (v.add(new EuVector(0, 8)))));
		boid.ingest(new Unit(worldState, path, (v.add(new EuVector(8, 8)))));
		boid.ingest(new Unit(worldState, path, (v.add(new EuVector(8, 0)))));
		worldState.addObject(boid);
		
	}
}
