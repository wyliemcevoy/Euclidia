package euclid.two.dim.behavior;

import euclid.two.dim.model.EuVector;

public class StandStill extends SteeringBehavior
{

	@Override
	public EuVector calculate()
	{
		if (self != null)
		{
			this.self.setVelocity(new EuVector(0, 0));

		}
		return new EuVector(0, 0);
	}

}
