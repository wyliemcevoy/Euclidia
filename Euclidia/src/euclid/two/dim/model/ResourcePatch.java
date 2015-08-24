package euclid.two.dim.model;

import euclid.two.dim.team.Team;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;

public class ResourcePatch extends Obstacle {

	private int minerals;

	public ResourcePatch(EuVector position) {
		super(position);
		this.futurePosition = position.deepCopy();
		this.futureVelocity = new EuVector(0, 0);
		this.radius = 20;
	}

	public ResourcePatch(ResourcePatch resourcePatch) {
		super(resourcePatch.getPosition().deepCopy());
		this.radius = resourcePatch.getRadius();
		this.id = resourcePatch.getId();
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
}
