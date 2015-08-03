package euclid.two.dim.map;

import java.util.ArrayList;

import euclid.two.dim.model.Door;
import euclid.two.dim.model.EuVector;

public class ConvexPoly {
	private ArrayList<EuVector> points;
	private ArrayList<Door> doors;
	private ArrayList<Segment> segments;

	public ConvexPoly(ArrayList<EuVector> points) {
		this.points = points;
	}

	public boolean contains(EuVector test) {
		int i;
		int j;
		boolean result = false;
		for (i = 0, j = points.size() - 1; i < points.size(); j = i++) {
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
}
