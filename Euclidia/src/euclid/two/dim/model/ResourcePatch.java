package euclid.two.dim.model;

import java.util.UUID;

import euclid.two.dim.team.Team;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;

public class ResourcePatch extends Obstacle {

	private int totalRemaining;
	private boolean inUse;
	private UUID currentlyGathering;
	private Resource type;

	public ResourcePatch(EuVector position, Resource type) {
		super(position);
		this.futurePosition = position.deepCopy();
		this.futureVelocity = new EuVector(0, 0);
		this.radius = 10;
		this.inUse = false;
		this.type = type;
	}

	public ResourcePatch(ResourcePatch clone) {
		super(clone.getPosition().deepCopy());
		this.radius = clone.getRadius();
		this.id = clone.getId();
		this.inUse = clone.isInUse();
		this.type = clone.getType();
		this.totalRemaining = clone.getTotal();
	}

	public Resource getType() {
		return type;
	}

	@Override
	public Updatable deepCopy() {
		return new ResourcePatch(this);
	}

	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor) {
		updateVisitor.visit(this);
	}

	public void setTotal(int minerals) {
		this.totalRemaining = minerals;
	}

	public int getTotal() {
		return totalRemaining;
	}

	@Override
	public Team getTeam() {
		return Team.Neutral;
	}

	public boolean isInUse() {
		return inUse;
	}

	public UUID getCurrentlyGathering() {
		return currentlyGathering;
	}

	public void setWorker(UUID id) {
		currentlyGathering = id;
		inUse = true;
	}

	public void freePatch() {
		currentlyGathering = null;
		inUse = false;
	}

	public void request(int amount) {
		this.totalRemaining -= amount;
	}
}
