package euclid.two.dim.model;

import java.util.UUID;

import euclid.two.dim.combat.Health;
import euclid.two.dim.team.Team;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;

public class Worker extends Unit {
	private int gasCollected;
	private int mineralsCollected;
	private WorkerState currentState, collecting, returningHome, returningToResources, idle;
	private UUID currentResourcePatch;

	public Worker(Team team, EuVector position) {
		super(team, position);
		this.gasCollected = 0;
		this.mineralsCollected = 0;
		this.collecting = new Collecting();
		this.returningHome = new ReturningHome();
		this.returningToResources = new ReturningToResources();
		this.idle = new Idle();
		this.radius = 35;
		this.health = new Health(500);
		this.currentState = idle;

	}

	public Worker(Worker copy) {
		super(copy);
	}

	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor) {
		updateVisitor.visit(this);
	}

	@Override
	public Updatable deepCopy() {
		return new Worker(this);
	}

	private abstract class WorkerState {

	}

	private class Collecting extends WorkerState {

	}

	private class ReturningHome extends WorkerState {

	}

	private class ReturningToResources extends WorkerState {

	}

	private class Idle extends WorkerState {

	}

}
