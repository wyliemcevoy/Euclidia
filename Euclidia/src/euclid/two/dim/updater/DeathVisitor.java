package euclid.two.dim.updater;

import euclid.two.dim.model.Building;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.ResourcePatch;
import euclid.two.dim.model.Unit;
import euclid.two.dim.model.Worker;

public class DeathVisitor implements UpdateVisitor {
	private boolean dead;

	public DeathVisitor() {
		this.dead = false;
	}

	@Override
	public void visit(Minion minion) {
		visit((Unit) minion);
	}

	@Override
	public void visit(ResourcePatch resourcePatch) {
		dead = false;
	}

	public boolean isDead() {
		return dead;
	}

	@Override
	public void visit(Hero hero) {
		visit((Unit) hero);
	}

	private void visit(Unit unit) {
		dead = unit.getHealth().isDead();
	}

	@Override
	public void visit(Building building) {
		visit((Unit) building);
	}

	@Override
	public void visit(Worker worker) {
		visit((Unit) worker);
	}
}
