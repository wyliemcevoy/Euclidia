package euclid.two.dim.model;

import java.util.ArrayList;

public class NavMesh
{
	private ArrayList<Room> rooms;
	
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
	
	public Room getRoom(EuVector point)
	{
		// Bad implementation (fix with a grid and then store rooms inside)
		for (Room room : rooms)
		{
			if (room.contains(point))
				return room;
		}
		return null;
	}
}
