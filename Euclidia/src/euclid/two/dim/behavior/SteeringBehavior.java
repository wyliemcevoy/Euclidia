package euclid.two.dim.behavior;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;

public abstract class SteeringBehavior
{
	private static final double maxSteeringForce = 50;
	protected GameSpaceObject self;

	public abstract EuVector calculate();
}
