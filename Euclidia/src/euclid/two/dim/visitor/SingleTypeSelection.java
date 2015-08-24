package euclid.two.dim.visitor;

import euclid.two.dim.model.Building;
import euclid.two.dim.model.CasterUnit;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.ResourcePatch;
import euclid.two.dim.model.Worker;
import euclid.two.dim.updater.UpdateVisitor;

public class SingleTypeSelection implements UpdateVisitor {

	private Minion minion;
	private Hero hero;
	private Building building;
	private ResourcePatch resourcePatch;
	private Worker worker;

	public SingleTypeSelection(GameSpaceObject gso) {
		gso.acceptUpdateVisitor(this);
	}

	public Minion getMinion() {
		return minion;
	}

	public ResourcePatch getResourcePatch() {
		return resourcePatch;
	}

	public Hero getHero() {
		return hero;
	}

	public Building getBuilding() {
		return building;
	}

	@Override
	public void visit(Minion minion) {
		this.minion = minion;
	}

	@Override
	public void visit(Hero hero) {
		this.hero = hero;
	}

	@Override
	public void visit(ResourcePatch resourcePatch) {
		this.resourcePatch = resourcePatch;
	}

	@Override
	public void visit(Building building) {
		this.building = building;
	}

	public CasterUnit getCaster() {
		if (building == null) {
			return hero;
		}
		return building;

	}

	@Override
	public void visit(Worker worker) {
		this.worker = worker;

	}

	public Worker getWorker() {
		return worker;
	}
}
