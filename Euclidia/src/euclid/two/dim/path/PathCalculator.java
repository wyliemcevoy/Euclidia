package euclid.two.dim.path;

import java.util.ArrayList;
import java.util.PriorityQueue;

import euclid.two.dim.map.GameMap;
import euclid.two.dim.model.ConvexPoly;
import euclid.two.dim.model.Door;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.NavMesh;
import euclid.two.dim.model.RoomDistanceComparitor;
import euclid.two.dim.model.RoomPath;
import euclid.two.dim.world.WorldState;

public class PathCalculator {
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
		RoomDistanceComparitor comparitor = new RoomDistanceComparitor(stopRoom);

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
					newOpenPath.addPoint(door.getMidPoint());
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

}
