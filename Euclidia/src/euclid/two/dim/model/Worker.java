package euclid.two.dim.model;

import java.util.UUID;

import euclid.two.dim.combat.Health;
import euclid.two.dim.path.Path;
import euclid.two.dim.path.PathCalculator;
import euclid.two.dim.team.Team;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.visitor.SingleTypeSelection;
import euclid.two.dim.visitor.TypedSelection;
import euclid.two.dim.world.WorldState;

public class Worker extends Unit {
	private int carryAmount;
	private final int carryCapacity = 10;
	private UUID currentResourcePatch;
	private long collectionTime;
	private long timeToCollectResource;
	private Resource type;

	public enum WorkerState {
		COLLECTING("collecting"), RETURNING_HOME("returningHome"), TRAVELING_TO_RESOURCES("travelingToResources"), IDLE("idle");

		private final String name;

		private WorkerState(String s) {
			name = s;
		}

		public boolean equalsName(String otherName) {
			return (otherName == null) ? false : name.equals(otherName);
		}

		public String toString() {
			return this.name;
		}

	};

	private WorkerState currentState;

	public Worker(Team team, EuVector position) {
		super(team, position);
		this.carryAmount = 0;
		this.timeToCollectResource = 1000;
		this.radius = 7;
		this.maxSpeed = 14;
		this.health = new Health(75);
		this.currentState = WorkerState.IDLE;

	}

	public Worker(Worker copy) {
		super(copy);
		this.carryAmount = copy.getCarryAmount();
		this.radius = copy.getRadius();
		this.timeToCollectResource = 1000;
		this.type = copy.getCarryType();
		this.currentState = copy.getState();
	}

	public int getCarryAmount() {
		return this.carryAmount;
	}

	public Resource getCarryType() {
		return type;
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
		this.currentState = WorkerState.TRAVELING_TO_RESOURCES;
	}

	public UUID getResourceId() {
		return currentResourcePatch;
	}

	public void update(WorldState worldState) {
		switch (currentState) {
		case IDLE:
			// Do nothing
			break;
		case COLLECTING:
			if (System.currentTimeMillis() - collectionTime > timeToCollectResource) {
				// Finished harvesting

				collectionTime = -1;
				this.currentState = WorkerState.RETURNING_HOME;

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
				ResourcePatch resource = (new SingleTypeSelection(worldState.getGso(currentResourcePatch))).getResourcePatch();
				this.carryAmount = carryCapacity;
				resource.request(carryCapacity);
				this.type = resource.getType();
				resource.freePatch();
			}
			break;
		case RETURNING_HOME:
			for (Building building : (new TypedSelection(worldState.getGsos())).getBuildings()) {
				if (building.getTeam() == team && building.getPosition().subtract(position).getMagnitude() < building.getRadius() + radius) {
					// Arrived home (deposit)
					worldState.getPlayer(team).addResource(type, carryAmount);
					this.carryAmount = 0;
					this.currentState = WorkerState.TRAVELING_TO_RESOURCES;
					this.path = PathCalculator.calculatePath(worldState, position, worldState.getGso(currentResourcePatch).getPosition());
				}
			}
			break;
		case TRAVELING_TO_RESOURCES:
			ResourcePatch resource = (new SingleTypeSelection(worldState.getGso(currentResourcePatch))).getResourcePatch();
			if (resource != null) {
				if (resource.getPosition().subtract(position).getMagnitudeSquared() < (radius + resource.getRadius()) * (radius + resource.getRadius())) {
					// Arrived at resources
					if (!resource.isInUse()) {
						this.collectionTime = System.currentTimeMillis();
						this.currentState = WorkerState.COLLECTING;
						this.path = new Path(position);
						resource.setWorker(id);
					}
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
		this.currentState = WorkerState.TRAVELING_TO_RESOURCES;
	}

	@Override
	public void setPath(Path path) {
		this.currentState = WorkerState.IDLE;
		this.path = path;
	}

	public boolean isGathering() {
		return currentState != WorkerState.IDLE;
	}

	public boolean isCollecting() {
		return currentState == WorkerState.COLLECTING;
	}

	public void setIdle() {
		this.currentState = WorkerState.IDLE;
	}

	public WorkerState getState() {
		return currentState;
	}
}
