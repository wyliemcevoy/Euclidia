package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.exception.OutOfBoundsException;

public class NavMesh
{
	private ArrayList<Room> rooms;
	private int gridSize = 20;

	public NavMesh()
	{
		this.rooms = new ArrayList<Room>();
	}

	public void addRoom(Room room)
	{
		rooms.add(room);
	}

	public void preCalculatePaths()
	{
		// TODO run A* on every possible path and store it in memory?
	}

	public Room getRoom(EuVector point) throws OutOfBoundsException
	{
		// Bad implementation (fix with a grid and then store rooms inside)
		for (Room room : rooms)
		{
			if (room.contains(point))
				return room;
		}
		throw new OutOfBoundsException();
	}
}
