package euclid.two.dim.model;


public abstract class SteeringBehavior
{
	private static final double maxSteeringForce = 50;

	public abstract EuVector calculate();
}
