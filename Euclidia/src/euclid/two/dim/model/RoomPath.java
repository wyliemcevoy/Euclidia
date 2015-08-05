package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.path.Path;

public class RoomPath {
	private ArrayList<ConvexPoly> theRooms;
	private ArrayList<EuVector> thePoints;
	private double cost;

	public RoomPath(ConvexPoly startRoom, EuVector startPoint) {
		this.theRooms = new ArrayList<ConvexPoly>();
		this.theRooms.add(startRoom);
		this.cost = 0;
		this.thePoints = new ArrayList<EuVector>();
		this.thePoints.add(startPoint);
	}

	public RoomPath(RoomPath copy) {
		this.theRooms = new ArrayList<ConvexPoly>();
		this.thePoints = new ArrayList<EuVector>();
		for (ConvexPoly room : copy.getRooms()) {
			this.theRooms.add(room);
		}

		for (EuVector point : copy.getPoints()) {
			this.thePoints.add(point.deepCopy());
		}
	}

	public double getCost() {
		return cost;
	}

	public Path toPath() {
		Path path = new Path(thePoints.get(0));

		for (int i = 1; i < thePoints.size(); i++) {
			path.addTarget(thePoints.get(i));
		}
		return path;
	}

	public ArrayList<EuVector> getPoints() {
		return thePoints;
	}

	public ConvexPoly getStart() {
		return theRooms.get(0);
	}

	public ConvexPoly getStop() {
		return theRooms.get(theRooms.size() - 1);
	}

	public void addRoom(ConvexPoly room) {
		theRooms.add(room);
	}

	public int size() {
		return theRooms.size();
	}

	private ArrayList<ConvexPoly> getRooms() {
		return theRooms;
	}

	public void addPoint(EuVector point) {
		cost += point.subtract(getLastPoint()).getMagnitude();
		thePoints.add(point);
	}

	public EuVector getLastPoint() {
		return thePoints.get(thePoints.size() - 1);
	}

}
