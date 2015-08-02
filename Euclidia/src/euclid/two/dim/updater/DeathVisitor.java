package euclid.two.dim.updater;

import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.Unit;

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
	public void visit(Obstacle obstacle) {
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

}
