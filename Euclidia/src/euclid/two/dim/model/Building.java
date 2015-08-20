package euclid.two.dim.model;

import euclid.two.dim.combat.Health;
import euclid.two.dim.team.Team;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;

public class Building extends CasterUnit {

	public Building(Team team, EuVector position) {
		super(team, position);
		this.radius = 35;
		this.health = new Health(500);
	}

	public Building(Building clone) {
		super(clone);
	}

	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor) {
		updateVisitor.visit(this);
	}

	@Override
	public Updatable deepCopy() {
		return new Building(this);
	}

	@Override
	public void travelToTheFuture() {
		// do nothing (don't move)
	}
}
