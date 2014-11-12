package euclid.two.dim.physics;

public class RectangleBody extends PhysicalBody
{
	private double x, y, width, height;

	public double getBottomX()
	{
		return x + width;
	}

	public double getBottomY()
	{
		return y + height;
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

	/**
	 * @return the width
	 */
	public double getWidth()
	{
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(double width)
	{
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public double getHeight()
	{
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(double height)
	{
		this.height = height;
	}

	@Override
	public boolean intersectsVisit(PhysicalBody physicalBody)
	{
		return physicalBody.intersects(this);
	}

	@Override
	public boolean intersects(CircleBody circleBody)
	{
		return IntersectionHelper.intersects(this, circleBody);
	}

	@Override
	public boolean intersects(RectangleBody rectangelBody)
	{
		return IntersectionHelper.intersects(this, rectangelBody);
	}

}
