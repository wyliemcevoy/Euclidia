package euclid.two.dim.model;

import java.util.ArrayList;

public class Door {
	private ConvexPoly roomOne, roomTwo;
	private EuVector pointOne, pointTwo;

	private static final double threshold = .000001;

	public Door(ConvexPoly roomOne, ConvexPoly roomTwo) {
		this.roomOne = roomOne;
		this.roomTwo = roomTwo;

		roomOne.addDoor(this);
		roomTwo.addDoor(this);
	}

	public ConvexPoly goThroughFrom(ConvexPoly room) {
		if (room.equals(roomOne)) {
			return roomTwo;
		}
		else {
			return roomOne;
		}
	}

	public void setPointOne(EuVector pointOne) {
		this.pointOne = pointOne;
	}

	public void setPointTwo(EuVector pointTwo) {
		this.pointTwo = pointTwo;
	}

	public EuVector getPointOne() {
		return pointOne.deepCopy();
	}

	public EuVector getPointTwo() {
		return pointTwo.deepCopy();
	}

	public EuVector getMidPoint() {
		return new EuVector((pointOne.getX() + pointTwo.getX()) / 2, (pointOne.getY() + pointTwo.getY()) / 2);
	}

	@Override
	public String toString() {
		return "Door [" + pointOne.toString() + "  " + pointTwo.toString() + "]";
	}

	public ArrayList<EuVector> getGuidePointsOne(int radius) {
		ArrayList<EuVector> build = new ArrayList<EuVector>();

		return null;
	}
}
