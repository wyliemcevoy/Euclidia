package euclid.two.dim.model;

import java.util.UUID;

import euclid.two.dim.team.Team;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;

public class ResourcePatch extends Obstacle {

	private int minerals;
	private boolean inUse;
	private UUID currentlyGathering;

	public ResourcePatch(EuVector position) {
		super(position);
		this.futurePosition = position.deepCopy();
		this.futureVelocity = new EuVector(0, 0);
		this.radius = 10;
		this.inUse = false;
	}

	public ResourcePatch(ResourcePatch clone) {
		super(clone.getPosition().deepCopy());
		this.radius = clone.getRadius();
		this.id = clone.getId();
		this.inUse = clone.isInUse();
	}

	@Override
	public Updatable deepCopy() {
		return new ResourcePatch(this);
	}

	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor) {
		updateVisitor.visit(this);
	}

	public void setMinerals(int minerals) {
		this.minerals = minerals;
	}

	public int getMinerals() {
		return minerals;
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
}
