package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.map.Segment;

public class ConvexPoly {

	private ArrayList<Door> doors;
	private ArrayList<EuVector> points;
	private ArrayList<Segment> segments;
	private EuVector center;
	private int id;

	public ConvexPoly(ArrayList<EuVector> points) {
		this.points = points;
		this.doors = new ArrayList<Door>();
		this.setId(-100);
	}

	public boolean contains(EuVector test) {
		boolean result = false;
		for (int i = 0, j = points.size() - 1; i < points.size(); j = i++) {
			if ((points.get(i).getY() > test.getY()) != (points.get(j).getY() > test.getY()) && (test.getX() < (points.get(j).getX() - points.get(i).getX()) * (test.getY() - points.get(i).getY()) / (points.get(j).getY() - points.get(i).getY()) + points.get(i).getX())) {
				result = !result;
			}
		}
		return result;
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

	public EuVector getClosestPointToTarget(EuVector target) {
		if (this.contains(target)) {
			return target;
		}
		else {
			Door first = doors.get(0);
			EuVector closest = first.getPointOne();
			double minDist = target.subtract(closest).getMagnitude();

			ArrayList<EuVector> points = new ArrayList<EuVector>();

			for (Door door : doors) {
				points.add(door.getPointOne());
				points.add(door.getPointTwo());
			}

			for (EuVector point : points) {

				double currentDist = point.subtract(target).getMagnitude();
				if (currentDist < minDist) {
					closest = point;
					minDist = currentDist;
				}

			}

			return closest;
		}

	}

	public ConvexPoly(double x, double y, double width, double height) {

		this.points = new ArrayList<EuVector>();
		points.add(new EuVector(x, y));
		points.add(new EuVector(x + width, y));
		points.add(new EuVector(x + width, y + height));
		points.add(new EuVector(x, y + height));
		this.center = new EuVector((int) (x + (width / 2)), (int) (y + (height / 2)));
		this.doors = new ArrayList<Door>();
	}

	public void addDoor(Door door) {

		this.doors.add(door);
	}

	public ArrayList<Door> getDoors() {
		return this.doors;
	}

	public ArrayList<ConvexPoly> getAttachedRooms() {
		ArrayList<ConvexPoly> result = new ArrayList<ConvexPoly>();

		for (Door door : doors) {
			result.add(door.goThroughFrom(this));
		}

		return result;
	}

	public EuVector getCenter() {
		if (center == null) {
			double minX = points.get(0).getX();
			double minY = points.get(0).getY();
			double maxX = points.get(0).getX();
			double maxY = points.get(0).getY();

			// not correct way to calculate centroid, but fast to calculate

			for (EuVector point : points) {
				if (point.getX() > maxX) {
					maxX = point.getX();
				}
				else if (point.getX() < minX) {
					minX = point.getX();
				}

				if (point.getY() > maxY) {
					maxY = point.getY();
				}
				else if (point.getY() < minY) {
					minY = point.getY();
				}
			}

			center = new EuVector((minX + maxX) / 2, (minY + maxY) / 2);
		}

		return center;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
