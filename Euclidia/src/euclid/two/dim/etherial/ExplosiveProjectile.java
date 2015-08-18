package euclid.two.dim.etherial;

import java.util.UUID;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Unit;
import euclid.two.dim.visitor.EtherialVisitor;

public class ExplosiveProjectile extends Etherial {
	private UUID sender;
	private double maxRange;
	private EuVector destination;
	private int explosionRadius;

	public ExplosiveProjectile(EuVector destination, Unit sender) {
		this.sender = sender.getId();
		this.maxRange = 30;
		this.location = new EuVector(sender.getPosition());
		this.expireTime = 1500;
		this.destination = destination;
		this.explosionRadius = 10;
	}

	public ExplosiveProjectile(ExplosiveProjectile copy) {
		this.sender = copy.getSender();
		this.maxRange = copy.getMaxRange();
		this.location = new EuVector(copy.getLocation());
		this.destination = new EuVector(copy.getDestination());
	}

	public boolean hasExpired(long timeStep) {
		expireTime -= timeStep;
		return expireTime < 0;
	}

	public UUID getSender() {
		return sender;
	}

	public void setSender(UUID sender) {
		this.sender = sender;
	}

	public double getMaxRange() {
		return maxRange;
	}

	public void setMaxRange(double maxRange) {
		this.maxRange = maxRange;
	}

	public EuVector getLocation() {
		return location;
	}

	public void setLocation(EuVector location) {
		this.location = location;
	}

	@Override
	public Etherial deepCopy() {
		return new ExplosiveProjectile(this);
	}

	@Override
	public void accept(EtherialVisitor etherialVisitor) {
		etherialVisitor.visit(this);
	}

	public int getDamage() {
		return 5;
	}

	public EuVector getDestination() {
		return destination;
	}

	public void setDestination(EuVector destination) {
		this.destination = destination;
	}

	public int getExplosionRadius() {
		return this.explosionRadius;
	}
}
