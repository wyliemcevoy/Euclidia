package euclid.two.dim.physics;

public abstract class PhysicalBody
{
	public abstract boolean intersectsVisit(PhysicalBody physicalBody);

	public abstract boolean intersects(CircleBody circleBody);

	public abstract boolean intersects(RectangleBody rectangelBody);

}
