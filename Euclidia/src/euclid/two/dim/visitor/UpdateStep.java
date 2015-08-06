package euclid.two.dim.visitor;

import euclid.two.dim.VectorMath;
import euclid.two.dim.map.Segment;
import euclid.two.dim.model.ConvexPoly;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.NavMesh;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.world.WorldState;

public abstract class UpdateStep implements UpdateVisitor {
	protected WorldState worldState;
	protected double timeStep;
	protected NavMesh navMesh;

	public void runStep(WorldState worldState, double timeStep) {
		this.worldState = worldState;
		this.timeStep = timeStep;
		this.navMesh = worldState.getGameMap().getNavMesh();

		for (GameSpaceObject gso : worldState.getGsos()) {
			gso.acceptUpdateVisitor(this);
		}

		for (GameSpaceObject gso : worldState.getGsos()) {
			ConvexPoly poly = navMesh.getPoly(gso.getPosition());
			if (poly != null) {
				for (Segment segment : poly.getWalls()) {
					EuVector collision = VectorMath.getIntersectionPointOfTwoLineSegments(gso.getPosition(), gso.getFuturePosition(), segment.getOne(), segment.getTwo());
					if (collision != null) {

						// EuVector changeOfPosition = gso.getFuture().subtract(gso.getPosition());
						// EuVector wallVector = segment.getTwo().subtract(segment.getOne());
						// System.out.println(gso.getPosition() + " " + gso.getFuture() + " change " + changeOfPosition + " " + wallVector);
						// double dotProduct = VectorMath.dot(changeOfPosition, wallVector);
						// EuVector correctedChangeOfPosition = wallVector.normalize(); // .multipliedBy(dotProduct);
						// System.out.println(correctedChangeOfPosition + " " + dotProduct);
						gso.setFuturePosition(gso.getPosition());
						break;
					}
				}
			}
		}

		for (GameSpaceObject gso : worldState.getGsos()) {
			gso.travelToTheFuture();
		}
	}
}
