package euclid.two.dim.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import euclid.two.dim.VectorMath;
import euclid.two.dim.etherial.Etherial;
import euclid.two.dim.etherial.Explosion;
import euclid.two.dim.map.GameMap;
import euclid.two.dim.model.Building;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.Unit;
import euclid.two.dim.team.Team;

public class WorldState {
	private ArrayList<GameSpaceObject> gsos;
	private ArrayList<Explosion> explosions;
	private ArrayList<Etherial> etherials;
	private ArrayList<Etherial> expired;
	private GameMap gameMap;

	public WorldState() {
		this.gsos = new ArrayList<GameSpaceObject>();
		this.explosions = new ArrayList<Explosion>();
		this.etherials = new ArrayList<Etherial>();
		this.expired = new ArrayList<Etherial>();
	}

	public List<GameSpaceObject> getUnfriendliesInRage(Minion unit) {
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

	public void update(long timeStep) {
		Iterator<Explosion> it = explosions.iterator();
		while (it.hasNext()) {
			Explosion explosion = it.next();
			if (explosion.hasExpired()) {
				it.remove();
			}
		}
	}

	public ArrayList<GameSpaceObject> getSelected() {
		ArrayList<GameSpaceObject> build = new ArrayList<GameSpaceObject>();
		for (GameSpaceObject gso : this.gsos) {
			if (gso.isSelected()) {
				build.add(gso);
			}
		}
		return build;
	}

	/**
	 * @return the fish
	 */
	public ArrayList<GameSpaceObject> getGsos() {
		return gsos;
	}

	/**
	 * @param fish the fish to set
	 */
	public void setFish(ArrayList<GameSpaceObject> fish) {
		this.gsos = fish;
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

		}

		copy.setGameMap(gameMap);
		for (Etherial updatable : etherials) {
			copy.addEtherial(updatable);
		}

		return copy;
	}

	public ArrayList<GameSpaceObject> getGameSpaceObjects() {
		return gsos;
	}

	public Unit getUnit(UUID id) {
		// Horrible implementation (change to map)
		for (GameSpaceObject gso : gsos) {
			if (gso.getId().equals(id)) {
				return (Unit) gso;
			}
		}
		return null;
	}

	public ArrayList<Explosion> getExplosions() {
		explosions = new ArrayList<Explosion>();

		for (Etherial updatable : etherials) {
			if (updatable instanceof Explosion) {
				explosions.add((Explosion) updatable);
			}
		}

		return explosions;
	}

	public void addEtherial(Etherial etherial) {
		this.etherials.add(etherial);
	}

	public ArrayList<Etherial> getUpdatable() {
		return etherials;
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

	public Hero getHero(UUID id) {
		// Horrible implementation (change to map)
		for (GameSpaceObject gso : gsos) {
			if (gso.getId().equals(id)) {
				return (Hero) gso;
			}
		}
		return null;
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

}
