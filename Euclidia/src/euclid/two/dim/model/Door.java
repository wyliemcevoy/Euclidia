package euclid.two.dim.model;

public class Door
{
	private Room roomOne, roomTwo;
	private Point pointOne, pointTwo;
	private static final double threshold = .000001;

	public Door(Room roomOne, Room roomTwo)
	{
		this.roomOne = roomOne;
		this.roomTwo = roomTwo;
		findPoints();
	}

	public Room goThroughFrom(Room room)
	{
		if (room.equals(roomOne))
		{
			return roomTwo;
		} else
		{
			return roomOne;
		}
	}

	public Point getPointOne()
	{
		return pointOne;
	}

	public Point getPointTwo()
	{
		return pointTwo;
	}

	private void findPoints()
	{
		if (Math.abs(roomOne.getX() + roomOne.getWidth() - roomTwo.getX()) < threshold)
		{
			if (roomOne.getHeight() < roomTwo.getHeight())
			{
				this.pointOne = new Point(roomOne.getX() + roomOne.getWidth(), roomOne.getY());
				this.pointTwo = new Point(roomOne.getX() + roomOne.getWidth(), roomOne.getY() + roomOne.getHeight());
			} else
			{
				this.pointOne = new Point(roomTwo.getX() + roomTwo.getWidth(), roomTwo.getY());
				this.pointTwo = new Point(roomTwo.getX() + roomTwo.getWidth(), roomTwo.getY() + roomTwo.getHeight());
			}
		} else if (Math.abs(roomTwo.getX() + roomTwo.getWidth() - roomOne.getX()) < threshold)
		{

		} else if (Math.abs(roomOne.getY() + roomOne.getHeight() - roomTwo.getY()) < threshold)
		{

		} else if (Math.abs(roomTwo.getY() + roomTwo.getHeight() - roomOne.getY()) < threshold)
		{

		}

	}

}
