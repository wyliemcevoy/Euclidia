package euclid.two.dim.model;

import java.util.Comparator;

public class RoomDistanceComparitor implements Comparator<RoomPath> {
	private EuVector goal;

	public RoomDistanceComparitor(EuVector goal) {
		this.goal = goal;
	}

	@Override
	public int compare(RoomPath one, RoomPath two) {
		double oneDist = goal.subtract(one.getLastPoint()).getMagnitude() + one.getCost();
		double twoDist = goal.subtract(two.getLastPoint()).getMagnitude() + two.getCost();

		if (oneDist < twoDist) {
			return -1;
		}
		if (oneDist > twoDist) {
			return 1;
		}
		return 0;
	}

}