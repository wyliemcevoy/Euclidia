package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.map.Segment;

public class Room {
	private double x;
	private double y;
	private double width;
	private double height;

	private ArrayList<Door> doors;
	private ArrayList<EuVector> points;
	private ArrayList<Segment> segments;

	public Room(ArrayList<EuVector> points) {
		this.points = points;
	}

	public boolean containsV(EuVector test) {
		int i;
		int j;
		boolean result = false;
		for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
			if ((points.get(i).getY() > test.getY()) != (points.get(j).getY() > test.getY()) && (test.getX() < (points.get(j).getX() - points.get(i).getX()) * (test.getY() - points.get(i).getY()) / (points.get(j).getY() - points.get(i).getY()) + points.get(i).getX())) {
				result = !result;
			}
		}

		return true; // result;
	}

	public ArrayList<Segment> getSegments() {
		// lazy construction
		if (segments == null) {
			segments = new ArrayList<Segment>();
			for (int i = 0; i < points.size(); i++) {
				if (i < points.size() - 1) {
					segments.add(new Segment(new EuVector(points.get(i)), new EuVector(points.get(i + 1))));
				}
				else {
					segments.add(new Segment(new EuVector(points.get(i)), new EuVector(points.get(0))));
				}
			}
		}
		return segments;
	}

	public Room(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.doors = new ArrayList<Door>();
	}

	public Room(Room room) {
		this.x = room.getX();
		this.y = room.getY();
		this.width = room.getWidth();
		this.height = room.getHeight();
		this.doors = new ArrayList<Door>();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void addDoor(Door door) {

		this.doors.add(door);
	}

	public ArrayList<Door> getDoors() {
		return this.doors;
	}

	public boolean contains(EuVector point) {
		return (((point.getX() > x) && (point.getX()) < x + width) && ((point.getY() > y) && (point.getY() < y + height)));
	}

	public ArrayList<Room> getAttachedRooms() {
		ArrayList<Room> result = new ArrayList<Room>();

		for (Door door : doors) {
			result.add(door.goThroughFrom(this));
		}

		return result;
	}

	public EuVector getCenter() {
		return new EuVector((int) (x + (width / 2)), (int) (y + (height / 2)));
	}

}
