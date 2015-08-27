package euclid.two.dim.world;

import java.util.ArrayList;
import java.util.Random;

import euclid.two.dim.ability.BlinkAbility;
import euclid.two.dim.ability.BuyUnitAbility;
import euclid.two.dim.ability.EplosiveProjectileAbility;
import euclid.two.dim.factory.MinionFactory;
import euclid.two.dim.factory.WorkerFactory;
import euclid.two.dim.map.GameMap;
import euclid.two.dim.model.Building;
import euclid.two.dim.model.ConvexPoly;
import euclid.two.dim.model.Door;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.NavMesh;
import euclid.two.dim.model.Resource;
import euclid.two.dim.model.ResourcePatch;
import euclid.two.dim.model.Worker;
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
		for (int i = 0; i < 10; i++) {
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

		for (int i = 0; i < 0; i++) {
			Minion unit = new Minion(team, randVect(300, 400, 300, 400));

			worldState.addObject(unit);
		}
		worldState.addObject(createHero(Team.Blue));
		buildBase(worldState, Team.Blue, new EuVector(277, 300));
		buildBase(worldState, Team.Red, new EuVector(1450, 300));

		worldState.setGameMap(createSpacePlatform());

		worldState.addObject(buildGasGeyser(new EuVector(242, 210)));
		worldState.addObject(buildGasGeyser(new EuVector(1475, 210)));
		worldState.addObject(buildMineralPatch(new EuVector(212, 230)));
		worldState.addObject(buildMineralPatch(new EuVector(187, 245)));
		worldState.addObject(buildMineralPatch(new EuVector(1525, 230)));
		worldState.addObject(buildMineralPatch(new EuVector(1530, 245)));
		worldState.addObject(buildMineralPatch(new EuVector(167, 260)));
		worldState.addObject(buildMineralPatch(new EuVector(1560, 260)));
		worldState.addObject(buildMineralPatch(new EuVector(152, 275)));
		worldState.addObject(buildMineralPatch(new EuVector(1575, 275)));
		worldState.addObject(buildMineralPatch(new EuVector(137, 290)));
		worldState.addObject(buildMineralPatch(new EuVector(1590, 290)));

		return worldState;
	}

	public void buildBase(WorldState worldState, Team team, EuVector position) {
		worldState.addObject(buildHatchery(team, position));

		for (int i = 0; i < 5; i++) {
			worldState.addObject(new Worker(team, new EuVector(position.getX() + rand.nextDouble() * 30, position.getY() + rand.nextDouble() * 30)));
		}
	}

	public ResourcePatch buildMineralPatch(EuVector location) {
		ResourcePatch rp = new ResourcePatch(location, Resource.MINERALS);
		rp.setTotal(5000);

		return rp;
	}

	public ResourcePatch buildGasGeyser(EuVector location) {
		ResourcePatch rp = new ResourcePatch(location, Resource.GAS);
		rp.setTotal(5000);

		return rp;
	}

	public Building buildHatchery(Team team, EuVector position) {
		Building hatchery = new Building(team, position);
		hatchery.addAbility(new BuyUnitAbility(new WorkerFactory(), 0, 25));
		hatchery.addAbility(new BuyUnitAbility(new MinionFactory(), 0, 50));
		return hatchery;

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
		cp1.setId(1);
		navMesh.addPoly(cp1);

		ArrayList<EuVector> p2 = new ArrayList<EuVector>();
		p2.add(new EuVector(556, 32));
		p2.add(new EuVector(1160, 32));
		p2.add(new EuVector(1160, 254));
		p2.add(new EuVector(556, 254));
		ConvexPoly cp2 = new ConvexPoly(p2);
		cp2.setId(2);
		navMesh.addPoly(cp2);

		ArrayList<EuVector> p3 = new ArrayList<EuVector>();
		p3.add(new EuVector(1160, 32));
		p3.add(new EuVector(1680, 290));
		p3.add(new EuVector(1306, 322));
		p3.add(new EuVector(1160, 254));

		ConvexPoly cp3 = new ConvexPoly(p3);
		cp3.setId(3);
		navMesh.addPoly(cp3);

		ArrayList<EuVector> p4 = new ArrayList<EuVector>();
		p4.add(new EuVector(33, 289));
		p4.add(new EuVector(414, 316));
		p4.add(new EuVector(700, 460));
		p4.add(new EuVector(859, 537));
		p4.add(new EuVector(700, 625));

		ConvexPoly cp4 = new ConvexPoly(p4);
		cp4.setId(4);
		navMesh.addPoly(cp4);

		ArrayList<EuVector> p5 = new ArrayList<EuVector>();
		p5.add(new EuVector(700, 460));
		p5.add(new EuVector(859, 390));
		p5.add(new EuVector(1020, 460));
		p5.add(new EuVector(859, 537));

		ConvexPoly cp5 = new ConvexPoly(p5);
		cp5.setId(5);
		navMesh.addPoly(cp5);

		ArrayList<EuVector> p6 = new ArrayList<EuVector>();
		p6.add(new EuVector(859, 537));
		p6.add(new EuVector(1020, 460));
		p6.add(new EuVector(1306, 322));
		p6.add(new EuVector(1680, 290));
		p6.add(new EuVector(1020, 625));

		ConvexPoly cp6 = new ConvexPoly(p6);
		cp6.setId(6);
		navMesh.addPoly(cp6);

		Door d1 = new Door(cp1, cp4);
		d1.setPointTwo(new EuVector(33, 289));
		d1.setPointOne(new EuVector(414, 316));

		Door d2 = new Door(cp1, cp2);
		d2.setPointTwo(new EuVector(556, 254));
		d2.setPointOne(new EuVector(556, 32));

		Door d3 = new Door(cp2, cp3);
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
