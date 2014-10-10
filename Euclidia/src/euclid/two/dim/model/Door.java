package euclid.two.dim.model;

public class Door
{
	private Room roomOne, roomTwo;
	private EuVector pointOne, pointTwo;
	private static final double threshold = .000001;

	public Door(Room roomOne, Room roomTwo)
	{
		this.roomOne = roomOne;
		this.roomTwo = roomTwo;
		roomOne.addDoor(this);
		roomTwo.addDoor(this);
		//findPoints();
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

	public void setPointOne(EuVector pointOne)
	{
		this.pointOne = pointOne;
	}

	public void setPointTwo(EuVector pointTwo)
	{
		this.pointTwo = pointTwo;
	}

	public EuVector getPointOne()
	{
		return pointOne;
	}

	public EuVector getPointTwo()
	{
		return pointTwo;
	}

	public EuVector getMidPoint()
	{
		return new EuVector((pointOne.getX() + pointTwo.getX()) / 2, (pointOne.getY() + pointTwo.getY()) / 2);

	}

	private void findPoints()
	{
		if (Math.abs(roomOne.getX() + roomOne.getWidth() - roomTwo.getX()) < threshold)
		{
			if (roomOne.getHeight() < roomTwo.getHeight())
			{
				//this.pointOne = new Point(roomOne.getX() + roomOne.getWidth(), roomOne.getY());
				//this.pointTwo = new Point(roomOne.getX() + roomOne.getWidth(), roomOne.getY() + roomOne.getHeight());
			} else
			{
				//this.pointOne = new Point(roomTwo.getX() + roomTwo.getWidth(), roomTwo.getY());
				//this.pointTwo = new Point(roomTwo.getX() + roomTwo.getWidth(), roomTwo.getY() + roomTwo.getHeight());
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
