package euclid.two.dim.model;

public abstract class Obstacle extends GameSpaceObject {
	public Obstacle(EuVector position) {
		this.position = position;
		this.futurePosition = new EuVector(position);
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.radius = 50;
	}

	@Override
	public boolean hasExpired() {
		return false;
	}

}
