package euclid.two.dim.path;

import java.util.ArrayList;
import java.util.PriorityQueue;

import euclid.two.dim.exception.OutOfBoundsException;
import euclid.two.dim.model.Door;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Room;
import euclid.two.dim.model.RoomDistanceComparitor;
import euclid.two.dim.model.RoomPath;
import euclid.two.dim.world.WorldState;

public class PathCalculator
{
	public PathCalculator()
	{

	}

	public static RoomPath calculateRoomPath(WorldState worldState, EuVector start, EuVector stop)
	{

		try
		{
			// Get rooms associated with input points
			Room startRoom = worldState.getRoom(start);
			Room stopRoom = worldState.getRoom(stop);

			// A* using crow's flight distance from center of each room
			RoomDistanceComparitor comparitor = new RoomDistanceComparitor(stopRoom);

			// Build visited room list
			ArrayList<Room> visited = new ArrayList<Room>();
			visited.add(startRoom);

			// Build open paths list and add path of just the start room
			PriorityQueue<RoomPath> openPaths = new PriorityQueue<RoomPath>(10, comparitor);
			RoomPath path = new RoomPath(startRoom, start);
			openPaths.add(path);

			while (!openPaths.isEmpty())
			{
				RoomPath currentPath = openPaths.poll();
				Room currentRoom = currentPath.getStop();

				// Mark room as visited
				visited.add(currentRoom);

				// test for goal
				if (currentRoom.equals(stopRoom))
				{
					currentPath.addPoint(stop);
					return currentPath;
				}

				ArrayList<Door> doors = currentRoom.getDoors();
				for (Door door : doors)
				{
					Room room = door.goThroughFrom(currentRoom);
					if (!visited.contains(room))
					{
						RoomPath newOpenPath = new RoomPath(currentPath);
						newOpenPath.addRoom(room);
						newOpenPath.addPoint(door.getMidPoint());
						openPaths.add(newOpenPath);
					}
				}

			}
			// No path could be found

		} catch (OutOfBoundsException e)
		{

		}

		try
		{
			worldState.getRoom(start);
		} catch (OutOfBoundsException e)
		{
			RoomPath failPath = new RoomPath(worldState.getRooms().get(0), start);
			failPath.addPoint(stop);

			return failPath;
		}

		RoomPath failPath = new RoomPath(worldState.getRooms().get(0), start);
		return failPath;

	}

}
