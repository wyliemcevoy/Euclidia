package euclid.two.dim.model;

import java.util.ArrayList;

public class Room
{
	private double x;
	private double y;
	private double width;
	private double height;

	private ArrayList<Door> doors;

	public Room(double x, double y, double width, double height)
	{
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.doors = new ArrayList<Door>();
	}

	public Room(Room room)
	{
		this.x = room.getX();
		this.y = room.getY();
		this.width = room.getWidth();
		this.height = room.getHeight();
		this.doors = new ArrayList<Door>();
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

	public void addDoor(Door door)
	{

		this.doors.add(door);
	}

	public ArrayList<Door> getDoors()
	{
		return this.doors;
	}

	public boolean contains(EuVector point)
	{
		return (((point.getX() > x) && (point.getX()) < x + width) && ((point.getY() > y) && (point.getY() < y + height)));
	}

	public ArrayList<Room> getAttachedRooms()
	{
		ArrayList<Room> result = new ArrayList<Room>();

		for (Door door : doors)
		{
			result.add(door.goThroughFrom(this));
		}

		return result;
	}

	public EuVector getCenter()
	{
		return new EuVector((int) (x + (width / 2)), (int) (y + (height / 2)));
	}
}
