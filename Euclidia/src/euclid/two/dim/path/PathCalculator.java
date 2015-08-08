package euclid.two.dim.path;

import java.util.ArrayList;
import java.util.PriorityQueue;

import euclid.two.dim.Configuration;
import euclid.two.dim.map.GameMap;
import euclid.two.dim.model.ConvexPoly;
import euclid.two.dim.model.Door;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.NavMesh;
import euclid.two.dim.model.RoomDistanceComparitor;
import euclid.two.dim.model.RoomPath;
import euclid.two.dim.world.WorldState;

public class PathCalculator {

	private static final double bufferRadius = 75;

	public PathCalculator() {

	}

	public static Path calculatePath(WorldState worldState, EuVector start, EuVector stop) {
		GameMap gameMap = worldState.getGameMap();
		// Get rooms associated with input points
		ConvexPoly startRoom = gameMap.getNavMesh().getPoly(start);
		ConvexPoly stopRoom = gameMap.getNavMesh().getPoly(stop);

		if (stopRoom == null || startRoom == null) {
			Path failPath = new Path(start);
			failPath.addTarget(stop);

			return failPath;
		}
		else {
			return calculateRoomPath(gameMap.getNavMesh(), start, stop).toPath();
		}
	}

	public static RoomPath calculateRoomPath(NavMesh navMesh, EuVector start, EuVector stop) {

		ConvexPoly startRoom = navMesh.getPoly(start);
		ConvexPoly stopRoom = navMesh.getPoly(stop);

		// A* using crow's flight distance from center of each room
		RoomDistanceComparitor comparitor = new RoomDistanceComparitor(stop);

		// Build visited room list
		ArrayList<ConvexPoly> visited = new ArrayList<ConvexPoly>();
		visited.add(startRoom);

		// Build open paths list and add path of just the start room
		PriorityQueue<RoomPath> openPaths = new PriorityQueue<RoomPath>(10, comparitor);
		RoomPath path = new RoomPath(startRoom, start);
		openPaths.add(path);

		while (!openPaths.isEmpty()) {
			RoomPath currentPath = openPaths.poll();
			ConvexPoly currentRoom = currentPath.getStop();

			// Mark room as visited
			visited.add(currentRoom);

			// test for goal
			if (currentRoom.equals(stopRoom)) {
				currentPath.addPoint(stop);
				return currentPath;
			}

			ArrayList<Door> doors = currentRoom.getDoors();
			for (Door door : doors) {
				ConvexPoly room = door.goThroughFrom(currentRoom);
				if (!visited.contains(room)) {
					RoomPath newOpenPath = new RoomPath(currentPath);
					newOpenPath.addRoom(room);

					EuVector throughDoor = findPointToTravelThroughDoor(newOpenPath.getLastPoint(), stop, door);

					// throughDoor = door.getMidPoint();

					newOpenPath.addPoint(throughDoor);
					openPaths.add(newOpenPath);
				}
			}

		}
		// No path could be found

		try {
			navMesh.getPoly(start);
		} catch (Exception e) {
			RoomPath failPath = new RoomPath(navMesh.getAllPolygons().get(0), start);
			failPath.addPoint(stop);

			return failPath;
		}

		RoomPath failPath = new RoomPath(navMesh.getAllPolygons().get(0), start);
		return failPath;

	}

	private EuVector getHesseNormalFormForLineWithTheseTwoPoints(EuVector one, EuVector two) {
		double r = 0;
		double theta = 0;

		if (Math.abs(one.getX() - two.getX()) < Configuration.threshold) {
			// line is horizontal
			return new EuVector(one.getX(), Math.PI / 2);
		}
		else {
			// y = mx+b
			double m = (two.getY() - one.getY()) / (one.getX() - one.getX());
			double b = one.getY() - (one.getX() * m);

			return new EuVector(r, theta);
		}
	}

	private static EuVector findPointToTravelThroughDoor(EuVector start, EuVector target, Door door) {

		if (start.equals(target)) {
			// Already arrived (don't add any additional points)
			return start;
		}

		if (Math.abs(start.getX() - target.getX()) < Configuration.threshold) {
			// line between start and target is vertical

			EuVector doorRiseRun = getMXPlusB(door.getPointOne(), door.getPointTwo());
			// solve intersection of two lines, with y guaranteed to be start.getY()
			// (because the line between start and target is horizontal).

			double m = doorRiseRun.getX();
			double b = doorRiseRun.getY();
			double y = start.getX();
			double x = (y - b) / m;
			EuVector intersectionPoint = new EuVector(x, y);

			// Now determine if the intersection point lies on the segment that is the door
			// Since the path line is vertical, just check if the intersection point
			// is less than both x values in the door or greater than both x in the door.

			if ((x < door.getPointOne().getX() && x < door.getPointTwo().getX())) {
				EuVector additionalStop = door.getPointOne();
				if (door.getPointOne().getX() > door.getPointTwo().getX()) {
					additionalStop = door.getPointTwo();
				}
				return additionalStop;
			}

			if (x > door.getPointOne().getX() && x < door.getPointTwo().getX()) {

				EuVector additionalStop = door.getPointOne();
				if (door.getPointOne().getX() > door.getPointTwo().getX()) {
					additionalStop = door.getPointTwo();
				}
				return additionalStop;
			}

			return intersectionPoint;
		}
		else {
			// line between start and target is not vertical so calculate y = mx+b
			double m = (target.getY() - start.getY()) / (target.getX() - start.getX());
			double b = start.getY() - (start.getX() * m);

			// Check to see if door is horizontal
			if (Math.abs(door.getPointOne().getX() - door.getPointTwo().getX()) < Configuration.threshold) {
				// Door is horizontal

				double x = door.getPointOne().getX();
				double y = m * x + b;
				EuVector intersectionPoint = new EuVector(x, y);

				// intersection point below the door
				if (y < door.getPointOne().getY() && y < door.getPointTwo().getY()) {
					// EuVector[] temp = { intersectionPoint, door.getPointOne(), door.getPointTwo() };
					// return getClosestPoint(temp, target);
					EuVector additionalStop = door.getPointOne();
					if (door.getPointOne().getY() > door.getPointTwo().getY()) {
						additionalStop = door.getPointTwo();
					}
					return additionalStop;
				}
				// intersection point above the door
				if (y > door.getPointOne().getY() && y > door.getPointTwo().getY()) {
					// EuVector[] temp = { intersectionPoint, door.getPointOne(), door.getPointTwo() };
					// return getClosestPoint(temp, target);
					EuVector additionalStop = door.getPointOne();
					if (door.getPointOne().getY() < door.getPointTwo().getY()) {
						additionalStop = door.getPointTwo();
					}
					return additionalStop;
				}
				return intersectionPoint;

			}
			else {
				// neither path between start and target nor the door are vertical

				EuVector doorRiseRun = getMXPlusB(door.getPointOne(), door.getPointTwo());
				double mDoor = doorRiseRun.getX();
				double bDoor = doorRiseRun.getY();

				// solve two equations two unknowns
				double x = (bDoor - b) / (m - mDoor);
				double y = m * x + b;
				EuVector intersectionPoint = new EuVector(x, y);

				// Need to determine if (x,y) is inside the door
				double distToD1 = (door.getPointOne().subtract(intersectionPoint)).getMagnitude();
				double distToD2 = (door.getPointTwo().subtract(intersectionPoint)).getMagnitude();
				double distBetween = (door.getPointOne().subtract(door.getPointTwo())).getMagnitude();

				if (Math.abs(distToD1 + distToD2 - distBetween) > 1) {
					if (distToD1 < distToD2) {
						EuVector buffer = door.getPointTwo().subtract(door.getPointOne()).truncate(bufferRadius);
						buffer = new EuVector(0, 0);
						return door.getPointOne().add(buffer);
					}
					else {
						EuVector buffer = door.getPointOne().subtract(door.getPointTwo()).truncate(bufferRadius);
						buffer = new EuVector(0, 0);
						return door.getPointTwo().add(buffer);
					}
				}
				else {
					return intersectionPoint;
				}
			}
		}
	}

	private static EuVector getClosestPoint(EuVector[] points, EuVector target) {
		if (points.length > 0) {
			EuVector closest = points[0];
			double minDistSquared = closest.subtract(target).getMagnitudeSquared();

			for (int i = 1; i < points.length; i++) {
				EuVector current = points[i];
				double currentDistSquared = current.subtract(target).getMagnitudeSquared();
				if (currentDistSquared < minDistSquared) {
					minDistSquared = currentDistSquared;
					closest = current;
				}
			}

			return closest;
		}
		else {
			return null;
		}

	}

	private static EuVector getMXPlusB(EuVector one, EuVector two) {
		// y = mx+b
		double m = (two.getY() - one.getY()) / (two.getX() - one.getX());
		double b = one.getY() - (one.getX() * m);
		return new EuVector(m, b);
	}
}
