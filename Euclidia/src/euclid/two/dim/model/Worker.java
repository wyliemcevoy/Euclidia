package euclid.two.dim.model;

import java.util.UUID;

import euclid.two.dim.combat.Health;
import euclid.two.dim.path.Path;
import euclid.two.dim.path.PathCalculator;
import euclid.two.dim.team.Team;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.visitor.TypedSelection;
import euclid.two.dim.world.WorldState;

public class Worker extends Unit {
	private int gasCollected;
	private int mineralsCollected;
	private UUID currentResourcePatch;
	private long collectionTime;
	private long timeToCollectResource;

	private enum WorkerState {
		collecting, returningHome, travelingToResources, idle
	};

	private WorkerState currentState;

	public Worker(Team team, EuVector position) {
		super(team, position);
		this.gasCollected = 0;
		this.mineralsCollected = 0;
		this.timeToCollectResource = 1000;
		this.radius = 7;
		this.maxSpeed = 20;
		this.health = new Health(75);
		this.currentState = WorkerState.idle;

	}

	public Worker(Worker copy) {
		super(copy);
		this.gasCollected = copy.getGasCollected();
		this.mineralsCollected = copy.getMineralsCollected();
		this.radius = copy.getRadius();
		this.timeToCollectResource = 1000;
	}

	public int getMineralsCollected() {
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

	public void update(WorldState worldState) {
		switch (currentState) {
		case idle:
			// Do nothing
			break;
		case collecting:
			if (System.currentTimeMillis() - collectionTime > timeToCollectResource) {
				// Finished harvesting
				this.mineralsCollected = 10;
				collectionTime = -1;
				this.currentState = WorkerState.returningHome;

				Building home = null;
				double minDistSqrd = Double.MAX_VALUE;

				for (Building building : (new TypedSelection(worldState.getGsos())).getBuildings()) {
					double curDistSqrd = building.getPosition().subtract(position).getMagnitudeSquared();
					if (building.getTeam() == team && curDistSqrd < minDistSqrd) {
						home = building;
						minDistSqrd = curDistSqrd;
					}
				}

				if (home != null) {
					this.path = PathCalculator.calculatePath(worldState, position.deepCopy(), home.getPosition());
				}
			}
			break;
		case returningHome:
			for (Building building : (new TypedSelection(worldState.getGsos())).getBuildings()) {
				if (building.getTeam() == team && building.getPosition().subtract(position).getMagnitude() < building.getRadius() + radius) {
					// Arrived home (deposit)
					worldState.getPlayer(team).addMinerals(mineralsCollected);
					this.mineralsCollected = 0;
					this.currentState = WorkerState.travelingToResources;
					this.path = PathCalculator.calculatePath(worldState, position, worldState.getGso(currentResourcePatch).getPosition());
				}
			}
			break;
		case travelingToResources:
			GameSpaceObject resource = worldState.getGso(currentResourcePatch);
			if (resource != null) {
				if (resource.getPosition().subtract(position).getMagnitudeSquared() < (radius + resource.getRadius()) * (radius + resource.getRadius())) {
					// Arrived at resources
					this.collectionTime = System.currentTimeMillis();
					this.currentState = WorkerState.collecting;
					this.path = new Path(position);
				}
			}

			break;

		default:
			// Do nothing
			break;
		}

	}

	public void gather(UUID targetResource) {
		this.currentResourcePatch = targetResource;
		this.currentState = WorkerState.travelingToResources;
	}

	@Override
	public void setPath(Path path) {
		this.currentState = WorkerState.idle;
		this.path = path;
	}

}
