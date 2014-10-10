package euclid.two.dim.model;

import java.util.Comparator;

public class RoomDistanceComparitor implements Comparator<RoomPath>
{
	private Room goal;

	public RoomDistanceComparitor(Room goal)
	{
		this.goal = goal;
	}

	@Override
	public int compare(RoomPath one, RoomPath two)
	{
		double oneDist = goal.getCenter().subtract(one.getStop().getCenter()).getMagnitude();
		double twoDist = goal.getCenter().subtract(two.getStop().getCenter()).getMagnitude();

		if (oneDist < twoDist)
		{
			return -1;
		}
		if (oneDist > twoDist)
		{
			return 1;
		}
		return 0;
	}

}