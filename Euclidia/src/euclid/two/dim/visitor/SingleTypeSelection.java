package euclid.two.dim.visitor;

import euclid.two.dim.model.Building;
import euclid.two.dim.model.CasterUnit;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.updater.UpdateVisitor;

public class SingleTypeSelection implements UpdateVisitor {

	private Minion minion;
	private Hero hero;
	private Building building;
	private Obstacle obstacle;

	public SingleTypeSelection(GameSpaceObject gso) {
		gso.acceptUpdateVisitor(this);
	}

	public Minion getMinion() {
		return minion;
	}

	public Hero getHero() {
		return hero;
	}

	public Building getBuilding() {
		return building;
	}

	public Obstacle getObstacle() {
		return obstacle;
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
	public void visit(Obstacle obstacle) {
		this.obstacle = obstacle;
	}

	@Override
	public void accept(Building building) {
		this.building = building;
	}

	public CasterUnit getCaster() {
		if (building == null) {
			return hero;
		}
		return building;

	}
}
