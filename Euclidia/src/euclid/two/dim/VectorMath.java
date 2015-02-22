package euclid.two.dim;

import euclid.two.dim.model.EuVector;

public class VectorMath
{
	public static EuVector multiplyBy(EuVector vect, double constant)
	{
		return new EuVector(vect.getX() * constant, vect.getY() * constant);
	}
	
	public static EuVector divideBy(EuVector vect, double constant)
	{
		return new EuVector(vect.getX() / constant, vect.getY() / constant);
	}
	
	public static EuVector add(EuVector vect, EuVector euVector)
	{
		return new EuVector(vect.getX() + euVector.getX(), vect.getY() + euVector.getY());
	}
	
	public static EuVector subtract(EuVector vect, EuVector euVector)
	{
		return new EuVector(vect.getX() - euVector.getX(), vect.getY() - euVector.getY());
	}
	
	public static EuVector normalize(EuVector vect)
	{
		return divideBy(vect, Math.sqrt(Math.pow(vect.getX(), 2) + Math.pow(vect.getY(), 2)));
	}
	
	public static double getMagnitude(EuVector vect)
	{
		return Math.sqrt(Math.pow(vect.getX(), 2) + Math.pow(vect.getY(), 2));
	}
	
	public static double getMagnintudeSquared(EuVector vect)
	{
		return Math.pow(vect.getX(), 2) + Math.pow(vect.getY(), 2);
	}
	
	public static double getTaxiCabMagnitude(EuVector vect)
	{
		return Math.max(Math.abs(vect.getX()), Math.abs(vect.getY()));
	}
	
	public static EuVector rotate(EuVector vect, double radians)
	{
		double px = vect.getX() * Math.cos(radians) - vect.getY() * Math.sin(radians);
		double py = vect.getX() * Math.sin(radians) + vect.getY() * Math.cos(radians);
		return new EuVector(px, py);
	}
	
	public static double getDistanceSquared(EuVector one, EuVector two)
	{
		return getMagnintudeSquared(subtract(one, two));
	}
	
}
