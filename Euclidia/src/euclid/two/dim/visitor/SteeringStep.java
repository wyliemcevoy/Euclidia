package euclid.two.dim.visitor;

import euclid.two.dim.Configuration;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.Unit;
import euclid.two.dim.path.Path;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.world.WorldState;

public class SteeringStep implements UpdateVisitor {
	private double timeStep;
	private WorldState worldState;

	public SteeringStep(WorldState worldState, double timeStep) {
		this.timeStep = timeStep;
		this.worldState = worldState;
	}

	public void runStep() {
		for (GameSpaceObject gso : worldState.getGameSpaceObjects()) {
			gso.acceptUpdateVisitor(this);
		}

		for (GameSpaceObject gso : worldState.getGameSpaceObjects()) {
			gso.travelToTheFuture();
		}
	}

	private EuVector calculateSteeringForce(Unit unit) {

		// SteeringType sb = unit.getSteeringType();
		EuVector steeringForce = new EuVector(0, 0);

		Path path = unit.getPath();

		switch (unit.getSteeringBehavior()) {
		case Flock:

			path.haveArrived(unit.getPosition());
			if (path.size() == 0) {
				unit.setVelocity(new EuVector(0, 0));
				return new EuVector(0, 0);
			}

			EuVector desiredVelocity = (path.getTarget().subtract(unit.getPosition())).normalize().multipliedBy(unit.getMaxSpeed());
			desiredVelocity = desiredVelocity.subtract(unit.getVelocity());
			double distToTarget = path.getTarget().subtract(unit.getPosition()).getMagnitude();

			EuVector averageVelocity = new EuVector(0, 0);
			int i = 0;
			/*
			 * for (GameSpaceObject gso : worldState.getGsos()) { EuVector dist
			 * = gso.getPosition().subtract(self.getPosition()); double
			 * magnitude = dist.getMagnitude(); if (magnitude > .1 && magnitude
			 * < 10) { // repulsion = repulsion.add(dist.dividedBy(-1 /
			 * (magnitude * // 10))); averageVelocity =
			 * averageVelocity.add(gso.getVelocity()); i++; } }
			 */
			if (i > 0) {
				averageVelocity = averageVelocity.dividedBy(i);
			}

			// desiredVelocity.add(repulsion);
			desiredVelocity.add(averageVelocity);

			if (distToTarget < 50 && distToTarget > 0) {
				desiredVelocity = desiredVelocity.dividedBy(distToTarget / (Configuration.maxSpeed * 4));
			}

			// EuVector targetDisplacement =
			// self.getVelocity().add(desiredVelocity.dividedBy(10));
			// System.out.println(targetDisplacement.getMagnitude() / 3);

			steeringForce = desiredVelocity;

			break;
		case Seek:
			break;
		case StandStill:
			break;
		default:
			break;

		}

		return steeringForce;
	}

	@Override
	public void visit(Minion unit) {
		visit((Unit) unit);
	}

	private void visit(Unit unit) {
		double mass = unit.getMass();
		EuVector steeringForce = calculateSteeringForce(unit);
		EuVector acceleration = steeringForce.dividedBy(mass);

		EuVector velocity = unit.getVelocity();
		EuVector futureVelocity = new EuVector(unit.getVelocity());
		EuVector futurePosition;
		EuVector position = new EuVector(unit.getPosition());

		futureVelocity = velocity.add(acceleration.multipliedBy(timeStep));
		futureVelocity.truncate(unit.getMaxSpeed());

		futurePosition = position.add(futureVelocity.multipliedBy(timeStep / 100));

		unit.setFuturePosition(futurePosition);
		unit.setVelocity(velocity);
		unit.setFutureVelocity(futureVelocity);

		if (velocity.getMagnitude() > 1) {
			unit.setTheta(Math.atan2(velocity.getX(), -1 * velocity.getY()));
		}
		//
		unit.getRenderComponent().acceptUpdate(unit, timeStep);
	}

	@Override
	public void visit(Obstacle obstacle) {
		// do nothing
	}

	@Override
	public void visit(Hero hero) {
		visit((Unit) hero);
	}
}
