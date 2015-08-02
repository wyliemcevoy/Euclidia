package euclid.two.dim.world;

import java.util.ArrayList;
import java.util.Random;

import euclid.two.dim.Configuration;
import euclid.two.dim.Path;
import euclid.two.dim.ability.BlinkAbility;
import euclid.two.dim.ability.EplosiveProjectileAbility;
import euclid.two.dim.map.ConvexPoly;
import euclid.two.dim.map.GameMap;
import euclid.two.dim.model.Door;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.NavMesh;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.Room;
import euclid.two.dim.team.Team;

public class WorldStateFactory
{
	private Random rand;
	
	public WorldStateFactory()
	{
		rand = new Random(System.currentTimeMillis());
	}
	
	public WorldState createRandomWorldState()
	{
		WorldState worldState = new WorldState();

		return worldState;
		
	}
	
	public WorldState createURoomsWorldState()
	{
		WorldState worldState = new WorldState();
		Team blueTeam = Team.Blue;
		for (int i = 0; i < 200; i++)
		{
			Path path = new Path(new EuVector(150, 250));
			Minion fish = new Minion(blueTeam, randVect(100, 200, 100, 200));
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
	
	public Hero createHero(Team team)
	{
		Hero hero = new Hero(team, randVect(100, 200, 100, 200));
		hero.addAbility(new EplosiveProjectileAbility());
		hero.addAbility(new BlinkAbility());
		hero.setMass(50);
		hero.setRadius(15);
		hero.getHealth().setMaxHealth(5000);
		hero.setMaxSpeed(20);
		
		return hero;
	}
	
	public WorldState createVsWorldState(Team team)
	{
		WorldState worldState = new WorldState();
		
		for (int i = 0; i < 100; i++)
		{
			Minion unit = new Minion(team, randVect(100, 200, 100, 200));
			worldState.addObject(unit);
		}
		
		Room rTL = new Room(0, 0, 1000, 1000);
		ArrayList<Room> rooms = new ArrayList<Room>();
		rooms.add(rTL);
		worldState.setRooms(rooms);
		Camera camera = new Camera();
		worldState.setCamera(camera);
		
		worldState.setGameMap(createSpacePlatform());
		
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
	
	public GameMap createSpacePlatform()
	{
		
		NavMesh navMesh = new NavMesh();
		
		ArrayList<EuVector> p1 = new ArrayList<EuVector>();
		p1.add(new EuVector(33, 289));
		p1.add(new EuVector(556, 32));
		p1.add(new EuVector(556, 254));
		p1.add(new EuVector(414, 316));
		
		navMesh.addPoly(new ConvexPoly(p1));
		
		ArrayList<EuVector> p2 = new ArrayList<EuVector>();
		p2.add(new EuVector(556, 32));
		p2.add(new EuVector(1160, 32));
		p2.add(new EuVector(1160, 254));
		p2.add(new EuVector(556, 254));
		
		navMesh.addPoly(new ConvexPoly(p2));
		
		ArrayList<EuVector> p3 = new ArrayList<EuVector>();
		p3.add(new EuVector(1160, 32));
		p3.add(new EuVector(1680, 290));
		p3.add(new EuVector(1306, 322));
		p3.add(new EuVector(1160, 254));
		
		ConvexPoly c3 = new ConvexPoly(p3);
		navMesh.addPoly(new ConvexPoly(p3));
		
		ArrayList<EuVector> p4 = new ArrayList<EuVector>();
		p4.add(new EuVector(33, 289));
		p4.add(new EuVector(414, 316));
		p4.add(new EuVector(859, 537));
		p4.add(new EuVector(700, 625));
		
		navMesh.addPoly(new ConvexPoly(p4));
		
		ArrayList<EuVector> p5 = new ArrayList<EuVector>();
		p5.add(new EuVector(700, 460));
		p5.add(new EuVector(859, 390));
		p5.add(new EuVector(1020, 460));
		p5.add(new EuVector(859, 537));
		
		navMesh.addPoly(new ConvexPoly(p5));
		
		ArrayList<EuVector> p6 = new ArrayList<EuVector>();
		p6.add(new EuVector(859, 537));
		p6.add(new EuVector(1306, 322));
		p6.add(new EuVector(1680, 290));
		p6.add(new EuVector(1020, 625));
		
		navMesh.addPoly(new ConvexPoly(p6));
		
		return new GameMap(navMesh);
		
	}
	
}
