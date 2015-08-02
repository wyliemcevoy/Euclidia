package euclid.two.dim.visitor;

import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.world.WorldState;

public abstract class UpdateStep implements UpdateVisitor {
	protected WorldState worldState;
	protected double timeStep;

	public void runStep(WorldState worldState, double timeStep) {
		this.worldState = worldState;
		this.timeStep = timeStep;

		for (GameSpaceObject gso : worldState.getGsos()) {
			gso.acceptUpdateVisitor(this);
		}

		for (GameSpaceObject gso : worldState.getGsos()) {
			gso.travelToTheFuture();
		}
	}
}
