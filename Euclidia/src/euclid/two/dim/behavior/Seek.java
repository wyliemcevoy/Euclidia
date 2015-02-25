package euclid.two.dim.behavior;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;

public class Seek extends SteeringType
{
	private GameSpaceObject target;
	private GameSpaceObject self;

	public Seek(GameSpaceObject target, GameSpaceObject self)
	{
		this.target = target;
		this.self = self;
	}

	public void setTarget(GameSpaceObject target)
	{
		this.target = target;
	}

	@Override
	public EuVector calculate()
	{
		EuVector desiredVelocity = (target.getPosition().subtract(self.getPosition())).normalize().multipliedBy(self.getMaxSpeed());
		return desiredVelocity.subtract(self.getVelocity());
	}

}
