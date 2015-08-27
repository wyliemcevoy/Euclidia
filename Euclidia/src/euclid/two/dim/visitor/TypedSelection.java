package euclid.two.dim.visitor;

import java.util.ArrayList;
import java.util.UUID;

import euclid.two.dim.model.Building;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.ResourcePatch;
import euclid.two.dim.model.Unit;
import euclid.two.dim.model.Worker;
import euclid.two.dim.updater.UpdateVisitor;

public class TypedSelection implements UpdateVisitor {

	private ArrayList<Hero> heros;
	private ArrayList<Building> buildings;
	private ArrayList<Minion> minions;
	private ArrayList<ResourcePatch> resourcePatchs;
	private ArrayList<Worker> workers;

	public TypedSelection(ArrayList<GameSpaceObject> gsos) {
		this.heros = new ArrayList<Hero>();
		this.buildings = new ArrayList<Building>();
		this.minions = new ArrayList<Minion>();
		this.resourcePatchs = new ArrayList<ResourcePatch>();
		this.workers = new ArrayList<Worker>();

		for (GameSpaceObject gso : gsos) {
			gso.acceptUpdateVisitor(this);
		}
	}

	public ArrayList<Hero> getHeros() {
		return heros;
	}

	public ArrayList<Building> getBuildings() {
		return buildings;
	}

	public ArrayList<Minion> getMinions() {
		return minions;
	}

	public ArrayList<ResourcePatch> getResourcePatches() {
		return resourcePatchs;
	}

	public ArrayList<Unit> getUnits() {
		ArrayList<Unit> units = new ArrayList<Unit>();
		units.addAll(minions);
		units.addAll(heros);
		return units;
	}

	public ArrayList<UUID> getIds() {
		ArrayList<UUID> ids = new ArrayList<UUID>();

		for (GameSpaceObject gso : minions) {
			ids.add(gso.getId());
		}

		for (GameSpaceObject gso : heros) {
			ids.add(gso.getId());
		}

		for (GameSpaceObject gso : workers) {
			ids.add(gso.getId());
		}
		return ids;
	}

	@Override
	public void visit(Minion minion) {
		minions.add(minion);
	}

	@Override
	public void visit(Hero hero) {
		heros.add(hero);
	}

	@Override
	public void visit(ResourcePatch resourcePatch) {
		resourcePatchs.add(resourcePatch);
	}

	@Override
	public void visit(Building building) {
		buildings.add(building);
	}

	public ArrayList<UUID> getBuildingIds() {
		ArrayList<UUID> ids = new ArrayList<UUID>();

		for (GameSpaceObject gso : buildings) {
			ids.add(gso.getId());
		}

		return ids;
	}

	public ArrayList<UUID> getHeroIds() {
		ArrayList<UUID> ids = new ArrayList<UUID>();

		for (GameSpaceObject gso : heros) {
			ids.add(gso.getId());
		}

		return ids;
	}

	@Override
	public void visit(Worker worker) {
		workers.add(worker);

	}

	public ArrayList<Worker> getWorkers() {
		return workers;
	}

	public ArrayList<UUID> getWorkerIds() {
		ArrayList<UUID> ids = new ArrayList<UUID>();

		for (GameSpaceObject gso : workers) {
			ids.add(gso.getId());
		}

		return ids;
	}

	public ArrayList<UUID> getMinionIds() {
		ArrayList<UUID> ids = new ArrayList<UUID>();

		for (GameSpaceObject gso : minions) {
			ids.add(gso.getId());
		}

		return ids;
	}

}
