package euclid.two.dim.model;

public class EuVector
{
	private double x, y;
	
	public EuVector(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public EuVector(EuVector copy)
	{
		this.x = copy.getX();
		this.y = copy.getY();
	}
	
	/**
	 * @return the x
	 */
	public double getX()
	{
		return x;
	}
	
	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(double x)
	{
		this.x = x;
	}
	
	/**
	 * @return the y
	 */
	public double getY()
	{
		return y;
	}
	
	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(double y)
	{
		this.y = y;
	}
	
	public EuVector multipliedBy(double constant)
	{
		return new EuVector(this.x * constant, this.y * constant);
	}
	
	public EuVector dividedBy(double constant)
	{
		return new EuVector(this.x / constant, this.y / constant);
	}
	
	public EuVector add(EuVector euVector)
	{
		return new EuVector(this.x + euVector.getX(), this.y + euVector.getY());
	}
	
	public EuVector subtract(EuVector euVector)
	{
		return new EuVector(this.x - euVector.getX(), this.y - euVector.getY());
	}
	
	public EuVector truncate(double constant)
	{
		double ratio = (Math.pow(x, 2) + Math.pow(y, 2)) / Math.pow(constant, 2);
		if (ratio > 1)
		{
			this.x /= ratio;
			this.y /= ratio;
		}
		return this;
	}
	
	public EuVector normalize()
	{
		return dividedBy(Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)));
	}
	
	@Override
	public String toString()
	{
		return "(" + x + "," + y + ")";
	}
	
	public double getMagnitude()
	{
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	public double getMagnitudeSquared()
	{
		return Math.pow(x, 2) + Math.pow(y, 2);
	}
	
	public double getTaxiCabMagnitude()
	{
		return Math.max(Math.abs(x), Math.abs(y));
	}
	
	public void rotate(double radians)
	{
		double px = x * Math.cos(radians) - y * Math.sin(radians);
		double py = x * Math.sin(radians) + y * Math.cos(radians);
		this.x = px;
		this.y = py;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof EuVector)
		{
			return equals((EuVector) o);
		} else
		{
			return false;
		}
	}
	
	private boolean equals(EuVector vect)
	{
		return x == vect.getX() && y == vect.getY();
	}
	
	public EuVector deepCopy()
	{
		return new EuVector(this);
	}
}
