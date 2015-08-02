package euclid.two.dim.updater;

import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.Obstacle;

public class DeathVisitor implements UpdateVisitor {
	private boolean dead;

	public DeathVisitor() {
		this.dead = false;
	}

	@Override
	public void visit(Minion unit) {
		dead = unit.getHealth().isDead();
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
		// TODO Auto-generated method stub

	}

}
