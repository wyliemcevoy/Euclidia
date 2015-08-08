package euclid.two.dim.model;

import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;

public class Obstacle extends GameSpaceObject {
	private int hitPoints;

	public Obstacle(EuVector position) {
		this.position = position;
		this.futurePosition = new EuVector(position);
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.isSelected = true;
		this.hitPoints = 100;
		this.radius = 50;
	}

	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor) {
		updateVisitor.visit(this);
	}

	@Override
	public Updatable deepCopy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasExpired() {
		// TODO Auto-generated method stub
		return false;
	}

}
