package euclid.two.dim.world;

import java.util.ArrayList;
import java.util.Random;

import euclid.two.dim.Configuration;
import euclid.two.dim.ability.BlinkAbility;
import euclid.two.dim.ability.EplosiveProjectileAbility;
import euclid.two.dim.map.GameMap;
import euclid.two.dim.model.ConvexPoly;
import euclid.two.dim.model.Door;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.NavMesh;
import euclid.two.dim.path.Path;
import euclid.two.dim.team.Team;

public class WorldStateFactory {
	private Random rand;

	public WorldStateFactory() {
		rand = new Random(System.currentTimeMillis());
	}

	public WorldState createRandomWorldState() {
		WorldState worldState = new WorldState();

		return worldState;

	}

	public WorldState createURoomsWorldState() {
		WorldState worldState = new WorldState();
		Team blueTeam = Team.Blue;
		for (int i = 0; i < 200; i++) {
			Path path = new Path(new EuVector(150, 250));
			Minion fish = new Minion(blueTeam, randVect(100, 200, 100, 200));
			worldState.addObject(fish);
		}

		ConvexPoly rTL = new ConvexPoly(100, 100, 100, 100);
		ConvexPoly rBL = new ConvexPoly(100, 200, 100, 100);
		Door d1 = new Door(rTL, rBL);
		d1.setPointOne(new EuVector(100, 200));
		d1.setPointTwo(new EuVector(200, 200));

		ConvexPoly rBC = new ConvexPoly(200, 200, 100, 100);
		Door d2 = new Door(rBL, rBC);
		d2.setPointOne(new EuVector(200, 200));
		d2.setPointTwo(new EuVector(200, 300));

		ConvexPoly rBR = new ConvexPoly(300, 200, 100, 100);
		Door d3 = new Door(rBC, rBR);
		d3.setPointOne(new EuVector(300, 200));
		d3.setPointTwo(new EuVector(300, 300));

		ConvexPoly rTR = new ConvexPoly(300, 100, 100, 100);
		Door d4 = new Door(rBR, rTR);
		d4.setPointOne(new EuVector(300, 200));
		d4.setPointTwo(new EuVector(400, 200));

		ArrayList<ConvexPoly> rooms = new ArrayList<ConvexPoly>();
		rooms.add(rTL);
		rooms.add(rBL);
		rooms.add(rBC);
		rooms.add(rBR);
		rooms.add(rTR);

		worldState.setCamera(new Camera());
		return worldState;
	}

	public Hero createHero(Team team) {
		Hero hero = new Hero(team, randVect(100, 200, 100, 200));
		hero.addAbility(new EplosiveProjectileAbility());
		hero.addAbility(new BlinkAbility());
		hero.setMass(50);
		hero.setRadius(15);
		hero.getHealth().setMaxHealth(5000);
		hero.setMaxSpeed(20);

		return hero;
	}

	public WorldState createVsWorldState(Team team) {
		WorldState worldState = new WorldState();

		for (int i = 0; i < 100; i++) {
			Minion unit = new Minion(team, randVect(100, 200, 100, 200));
			worldState.addObject(unit);
		}

		Camera camera = new Camera();
		worldState.setCamera(camera);

		worldState.setGameMap(createSpacePlatform());

		return worldState;
	}

	private EuVector randVect() {
		int x = 25 + rand.nextInt(Configuration.width - 50);
		int y = 25 + rand.nextInt(Configuration.height - 50);
		return new EuVector(x, y);
	}

	private EuVector randVect(int lowX, int highX, int lowY, int highY) {
		int x = lowX + rand.nextInt(highX - lowX);
		int y = lowY + rand.nextInt(highY - lowY);
		return new EuVector(x, y);
	}

	public GameMap createSpacePlatform() {

		NavMesh navMesh = new NavMesh();

		ArrayList<EuVector> p1 = new ArrayList<EuVector>();
		p1.add(new EuVector(33, 289));
		p1.add(new EuVector(556, 32));
		p1.add(new EuVector(556, 254));
		p1.add(new EuVector(414, 316));
		ConvexPoly cp1 = new ConvexPoly(p1);

		navMesh.addPoly(cp1);

		ArrayList<EuVector> p2 = new ArrayList<EuVector>();
		p2.add(new EuVector(556, 32));
		p2.add(new EuVector(1160, 32));
		p2.add(new EuVector(1160, 254));
		p2.add(new EuVector(556, 254));
		ConvexPoly cp2 = new ConvexPoly(p2);

		navMesh.addPoly(new ConvexPoly(p2));

		ArrayList<EuVector> p3 = new ArrayList<EuVector>();
		p3.add(new EuVector(1160, 32));
		p3.add(new EuVector(1680, 290));
		p3.add(new EuVector(1306, 322));
		p3.add(new EuVector(1160, 254));

		ConvexPoly cp3 = new ConvexPoly(p3);
		navMesh.addPoly(cp3);

		ArrayList<EuVector> p4 = new ArrayList<EuVector>();
		p4.add(new EuVector(33, 289));
		p4.add(new EuVector(414, 316));
		p4.add(new EuVector(859, 537));
		p4.add(new EuVector(700, 625));

		ConvexPoly cp4 = new ConvexPoly(p4);
		navMesh.addPoly(cp4);

		ArrayList<EuVector> p5 = new ArrayList<EuVector>();
		p5.add(new EuVector(700, 460));
		p5.add(new EuVector(859, 390));
		p5.add(new EuVector(1020, 460));
		p5.add(new EuVector(859, 537));

		ConvexPoly cp5 = new ConvexPoly(p5);
		navMesh.addPoly(cp5);

		ArrayList<EuVector> p6 = new ArrayList<EuVector>();
		p6.add(new EuVector(859, 537));
		p6.add(new EuVector(1306, 322));
		p6.add(new EuVector(1680, 290));
		p6.add(new EuVector(1020, 625));

		ConvexPoly cp6 = new ConvexPoly(p6);
		navMesh.addPoly(cp6);

		Door d1 = new Door(cp1, cp2);
		d1.setPointTwo(new EuVector(33, 289));
		d1.setPointOne(new EuVector(414, 316));

		Door d2 = new Door(cp2, cp3);
		d2.setPointTwo(new EuVector(556, 254));
		d2.setPointOne(new EuVector(556, 32));

		Door d3 = new Door(cp3, cp4);
		d3.setPointTwo(new EuVector(1160, 32));
		d3.setPointOne(new EuVector(1160, 254));

		Door d4 = new Door(cp3, cp6);
		d4.setPointTwo(new EuVector(1306, 322));
		d4.setPointOne(new EuVector(1680, 290));

		Door d5 = new Door(cp4, cp5);
		d5.setPointTwo(new EuVector(700, 460));
		d5.setPointOne(new EuVector(859, 537));

		Door d6 = new Door(cp5, cp6);
		d6.setPointTwo(new EuVector(1020, 460));
		d6.setPointOne(new EuVector(859, 537));

		return new GameMap(navMesh);
	}

	public GameMap buildRtsBoard() {

		NavMesh navMesh = new NavMesh();

		return new GameMap(navMesh);
	}

}
