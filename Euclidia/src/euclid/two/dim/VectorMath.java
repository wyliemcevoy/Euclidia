package euclid.two.dim;

import euclid.two.dim.model.EuVector;

public class VectorMath {
	public static EuVector multiplyBy(EuVector vect, double constant) {
		return new EuVector(vect.getX() * constant, vect.getY() * constant);
	}

	public static EuVector divideBy(EuVector vect, double constant) {
		return new EuVector(vect.getX() / constant, vect.getY() / constant);
	}

	public static EuVector add(EuVector vect, EuVector euVector) {
		return new EuVector(vect.getX() + euVector.getX(), vect.getY() + euVector.getY());
	}

	public static EuVector subtract(EuVector vect, EuVector euVector) {
		return new EuVector(vect.getX() - euVector.getX(), vect.getY() - euVector.getY());
	}

	public static EuVector normalize(EuVector vect) {
		return divideBy(vect, Math.sqrt(Math.pow(vect.getX(), 2) + Math.pow(vect.getY(), 2)));
	}

	public static double getMagnitude(EuVector vect) {
		return Math.sqrt(Math.pow(vect.getX(), 2) + Math.pow(vect.getY(), 2));
	}

	public static double getMagnintudeSquared(EuVector vect) {
		return Math.pow(vect.getX(), 2) + Math.pow(vect.getY(), 2);
	}

	public static double getTaxiCabMagnitude(EuVector vect) {
		return Math.max(Math.abs(vect.getX()), Math.abs(vect.getY()));
	}

	public static EuVector rotate(EuVector vect, double radians) {
		double px = vect.getX() * Math.cos(radians) - vect.getY() * Math.sin(radians);
		double py = vect.getX() * Math.sin(radians) + vect.getY() * Math.cos(radians);
		return new EuVector(px, py);
	}

	public static EuVector getVectFromAToB(EuVector a, EuVector b) {
		return new EuVector(b.getX() - a.getX(), b.getY() - a.getY());
	}

	public static double getDistanceSquared(EuVector one, EuVector two) {
		return getMagnintudeSquared(subtract(one, two));
	}

	public static EuVector getIntersectionPointOfTwoLineSegments(EuVector p0, EuVector p1, EuVector p2, EuVector p3) {
		double p0_x = p0.getX();
		double p0_y = p0.getY();
		double p1_x = p1.getX();
		double p1_y = p1.getY();
		double p2_x = p2.getX();
		double p2_y = p2.getY();
		double p3_x = p3.getX();
		double p3_y = p3.getY();

		double s1_x, s1_y, s2_x, s2_y;
		s1_x = p1_x - p0_x;
		s1_y = p1_y - p0_y;
		s2_x = p3_x - p2_x;
		s2_y = p3_y - p2_y;

		double s, t;
		s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
		t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

		if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
			// Collision detected
			return new EuVector(p0_x + (t * s1_x), p0_y + (t * s1_y));

		}
		// No collision

		return null;
	}

	public static double dot(EuVector one, EuVector two) {
		return (one.getX() * two.getX()) + (one.getY() * two.getY());
	}

}
