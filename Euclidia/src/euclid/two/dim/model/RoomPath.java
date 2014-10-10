package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.Path;

public class RoomPath
{
	private ArrayList<Room> theRooms;
	private ArrayList<EuVector> thePoints;

	public RoomPath(Room startRoom, EuVector startPoint)
	{
		this.theRooms = new ArrayList<Room>();
		this.theRooms.add(startRoom);

		this.thePoints = new ArrayList<EuVector>();
		this.thePoints.add(startPoint);
	}

	public RoomPath(RoomPath copy)
	{
		this.theRooms = new ArrayList<Room>();
		this.thePoints = new ArrayList<EuVector>();

		for (Room room : copy.getRooms())
		{
			this.theRooms.add(room);
		}

		for (EuVector point : copy.getPoints())
		{
			this.thePoints.add(point);
		}

	}

	public Path toPath()
	{
		Path path = new Path(thePoints.get(0));

		for (int i = 1; i < thePoints.size(); i++)
		{
			path.addTarget(thePoints.get(i));
		}
		return path;
	}

	public ArrayList<EuVector> getPoints()
	{
		return thePoints;
	}

	public Room getStart()
	{
		return theRooms.get(0);
	}

	public Room getStop()
	{
		return theRooms.get(theRooms.size() - 1);
	}

	public void addRoom(Room room)
	{
		theRooms.add(room);
	}

	public int size()
	{
		return theRooms.size();
	}

	private ArrayList<Room> getRooms()
	{
		return theRooms;
	}

	public void addPoint(EuVector point)
	{
		thePoints.add(point);
	}

}
