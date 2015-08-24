package euclid.two.dim.model;

import java.util.UUID;

import euclid.two.dim.combat.Health;
import euclid.two.dim.team.Team;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;

public class Worker extends Unit {
	private int gasCollected;
	private int mineralsCollected;
	private UUID currentResourcePatch;
	private long collectionTime;

	private enum WorkerState {
		collecting, returningHome, travelingToResources, idle
	};

	private WorkerState currentState;

	public Worker(Team team, EuVector position) {
		super(team, position);
		this.gasCollected = 0;
		this.mineralsCollected = 0;
		this.radius = 7;
		this.health = new Health(75);
		this.currentState = WorkerState.idle;

	}

	public Worker(Worker copy) {
		super(copy);
		this.gasCollected = copy.getGasCollected();
		this.mineralsCollected = copy.getMineralsCollected();
		this.radius = copy.getRadius();
	}

	private int getMineralsCollected() {
		return this.mineralsCollected;
	}

	private int getGasCollected() {
		return gasCollected;
	}

	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor) {
		updateVisitor.visit(this);
	}

	@Override
	public Updatable deepCopy() {
		return new Worker(this);
	}

	public void setResourcePatch(UUID targetResource) {
		this.currentResourcePatch = targetResource;
		this.currentState = WorkerState.travelingToResources;
	}

	public UUID getResourceId() {
		return currentResourcePatch;
	}
}
