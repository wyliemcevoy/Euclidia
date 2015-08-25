package euclid.two.dim.world;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import euclid.two.dim.Player;
import euclid.two.dim.VectorMath;
import euclid.two.dim.datastructure.AABBNode;
import euclid.two.dim.datastructure.AxisAlignedBoundingBox;
import euclid.two.dim.etherial.Etherial;
import euclid.two.dim.map.GameMap;
import euclid.two.dim.model.Building;
import euclid.two.dim.model.CasterUnit;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.ResourcePatch;
import euclid.two.dim.model.Unit;
import euclid.two.dim.model.Worker;
import euclid.two.dim.team.Team;
import euclid.two.dim.visitor.SingleTypeSelection;
import euclid.two.dim.visitor.TypedSelection;

public class WorldState {
	private ArrayList<GameSpaceObject> gsos;
	private ArrayList<Etherial> etherials;
	private ArrayList<Etherial> expired;
	private GameMap gameMap;
	private AABBNode root;
	private ArrayList<Player> players;

	public WorldState() {
		this.gsos = new ArrayList<GameSpaceObject>();
		this.etherials = new ArrayList<Etherial>();
		this.expired = new ArrayList<Etherial>();
		this.players = new ArrayList<Player>();
	}

	public ArrayList<GameSpaceObject> getUnfriendliesInRage(Unit unit) {
		ArrayList<GameSpaceObject> targets = new ArrayList<GameSpaceObject>();

		EuVector unitLocation = new EuVector(unit.getPosition());

		for (GameSpaceObject gso : gsos) {
			EuVector dist = VectorMath.subtract(gso.getPosition(), unitLocation);

			if (dist.getMagnitude() < unit.getDetectionRange()) {
				targets.add(gso);
			}
		}

		return targets;
	}

	public GameSpaceObject getNextTarget(Unit unit) {
		ArrayList<GameSpaceObject> targets = getUnfriendliesInRage(unit);

		GameSpaceObject target = null;

		if (targets.size() > 0) {

			double minDistSqrd = unit.getPosition().subtract(targets.get(0).getPosition()).getMagnitudeSquared();
			target = targets.get(0);

			for (int i = 0; i < targets.size(); i++) {
				GameSpaceObject gso = targets.get(i);
				double curDistSqrd = unit.getPosition().subtract(gso.getPosition()).getMagnitudeSquared();
				if (curDistSqrd < minDistSqrd) {
					target = gso;
					minDistSqrd = curDistSqrd;
				}
			}
		}
		return target;
	}

	public void recalculateAABBTree() {
		this.root = new AABBNode(getAABB(gsos.get(0)));
		for (int i = 1; i < gsos.size(); i++) {
			root.add(new AABBNode(getAABB(gsos.get(i))));
		}
	}

	private AxisAlignedBoundingBox getAABB(GameSpaceObject gso) {

		EuVector pos = gso.getPosition().deepCopy();
		double radius = gso.getRadius();
		EuVector topLeft = new EuVector(pos.getX() - radius, pos.getY() - radius);
		EuVector bottomRight = new EuVector(pos.getX() + radius, pos.getY() + radius);

		return new AxisAlignedBoundingBox(topLeft, bottomRight);
	}

	public double getDistanceBetween(GameSpaceObject one, GameSpaceObject two) {
		return VectorMath.subtract(one.getPosition(), two.getPosition()).getMagnitude() - (one.getRadius() + two.getRadius());
	}

	public GameSpaceObject getClosestUnfriendly(Unit unit) {
		GameSpaceObject target = null;
		double minDist = Double.MAX_VALUE;

		for (GameSpaceObject gso : gsos) {
			if (gso.getTeam() != unit.getTeam()) {
				double dist = getDistanceBetween(gso, unit);
				if (dist < unit.getDetectionRange() && dist < minDist) {
					target = gso;
					minDist = dist;
				}

			}

		}

		return target;
	}

	public void addObject(GameSpaceObject gso) {
		gsos.add(gso);
	}

	/**
	 * @return the fish
	 */
	public ArrayList<GameSpaceObject> getGsos() {
		return gsos;
	}

	public WorldState deepCopy() {
		WorldState copy = new WorldState();

		for (GameSpaceObject gso : gsos) {

			if (gso instanceof Minion) {
				copy.addObject(new Minion((Minion) gso));
			}
			if (gso instanceof Hero) {
				copy.addObject(new Hero((Hero) gso));
			}
			if (gso instanceof Building) {
				copy.addObject(new Building((Building) gso));
			}

			if (gso instanceof ResourcePatch) {
				copy.addObject(new ResourcePatch((ResourcePatch) gso));
			}
			if (gso instanceof Worker) {
				copy.addObject(new Worker((Worker) gso));
			}

		}

		for (Player player : players) {
			copy.addPlayer(player);
		}

		copy.setGameMap(gameMap);
		for (Etherial updatable : etherials) {
			copy.addEtherial(updatable);
		}

		return copy;
	}

	public Unit getUnit(UUID id) {

		// Horrible implementation (change to map)
		for (GameSpaceObject gso : gsos) {
			if (gso.getId() == null) {
				System.out.println(gso);
			}
			if (gso.getId().equals(id)) {
				if (gso instanceof Unit) {

					return (Unit) gso;
				}

			}
		}
		return null;
	}

	public GameSpaceObject getGso(UUID id) {
		// Horrible implementation (change to map)
		for (GameSpaceObject gso : gsos) {
			if (gso.getId().equals(id)) {
				return gso;
			}
		}
		return null;
	}

	public void addEtherial(Etherial etherial) {
		this.etherials.add(etherial);
	}

	public void registerAsExpired(Etherial etherial) {
		this.expired.add(etherial);
	}

	public void purgeExpired() {
		etherials.removeAll(expired);
		expired = new ArrayList<Etherial>();
	}

	public List<Etherial> getEtherials() {
		return etherials;
	}

	public CasterUnit getCaster(UUID id) {
		// Horrible implementation (change to map)
		for (GameSpaceObject gso : gsos) {
			if (gso.getId().equals(id)) {
				return (new SingleTypeSelection(gso)).getCaster();
			}
		}
		return null;
	}

	public ArrayList<Building> getBuildings(ArrayList<UUID> ids) {
		return (new TypedSelection(getGsos(ids))).getBuildings();
	}

	public ArrayList<GameSpaceObject> getGsos(ArrayList<UUID> ids) {
		ArrayList<GameSpaceObject> result = new ArrayList<GameSpaceObject>();

		for (UUID id : ids) {
			GameSpaceObject gso = getGso(id);
			if (gso != null) {
				result.add(gso);
			}
		}
		return result;
	}

	public ArrayList<Unit> getUnitsInRange(EuVector location, int explosionRadius) {
		ArrayList<Unit> result = new ArrayList<Unit>();

		for (GameSpaceObject gso : gsos) {
			EuVector distance = VectorMath.subtract(gso.getPosition(), location);

			if (gso instanceof Unit && distance.getMagnitude() - gso.getRadius() < explosionRadius) {
				result.add((Unit) gso);
			}
		}

		return result;
	}

	public GameMap getGameMap() {
		return gameMap;
	}

	public void setGameMap(GameMap gameMap) {
		this.gameMap = gameMap;
	}

	public ArrayList<UUID> getUnitsInsideRect(Team team, EuVector one, EuVector two) {

		// bad implementation
		ArrayList<UUID> units = new ArrayList<UUID>();
		for (GameSpaceObject gso : gsos) {
			EuVector pos = gso.getPosition();
			double x = pos.getX();
			double y = pos.getY();

			double minX = two.getX();
			double maxX = one.getX();
			if (one.getX() < two.getX()) {
				minX = one.getX();
				maxX = two.getX();
			}

			double minY = two.getY();
			double maxY = one.getY();

			if (one.getY() < two.getY()) {
				minY = one.getY();
				maxY = two.getY();
			}

			if (gso.getTeam() == team && (minX < x && maxX > x) && (minY < y && maxY > y)) {
				units.add(gso.getId());
			}
		}
		return units;
	}

	public TypedSelection getTypedSelectionInRect(Team team, EuVector one, EuVector two) {
		ArrayList<GameSpaceObject> selected = new ArrayList<GameSpaceObject>();

		for (GameSpaceObject gso : gsos) {
			EuVector pos = gso.getPosition();
			double x = pos.getX();
			double y = pos.getY();

			double minX = two.getX();
			double maxX = one.getX();
			if (one.getX() < two.getX()) {
				minX = one.getX();
				maxX = two.getX();
			}

			double minY = two.getY();
			double maxY = one.getY();

			if (one.getY() < two.getY()) {
				minY = one.getY();
				maxY = two.getY();
			}

			if (gso.getTeam() == team && (minX < x && maxX > x) && (minY < y && maxY > y)) {
				selected.add(gso);
			}
		}

		return new TypedSelection(selected);
	}

	public ArrayList<GameSpaceObject> getFriendlyUnits(Team team) {
		ArrayList<GameSpaceObject> friendlies = new ArrayList<GameSpaceObject>();
		for (GameSpaceObject gso : gsos) {
			if (gso.getTeam() == team) {
				friendlies.add(gso);
			}
		}
		return friendlies;
	}

	public AABBNode getAABBRoot() {
		return root;
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public Player getPlayer(Team team) {

		for (Player player : players) {
			if (player.getTeam() == team) {
				return player;
			}
		}
		return null;
	}

	public GameSpaceObject getGsoAt(EuVector location) {
		for (GameSpaceObject gso : gsos) {
			if (gso.getPosition().subtract(location).getMagnitudeSquared() < gso.getRadius() * gso.getRadius()) {
				return gso;
			}
		}
		return null;

	}
}
