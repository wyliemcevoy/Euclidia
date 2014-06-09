package euclid.two.dim.behavior;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.SteeringBehavior;
import euclid.two.dim.world.WorldState;

public class Flock extends SteeringBehavior
{
	private WorldState worldState;
	private GameSpaceObject target;
	private GameSpaceObject self;

	public Flock(WorldState worldState, GameSpaceObject target, GameSpaceObject self)
	{
		this.worldState = worldState;
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
		desiredVelocity = desiredVelocity.subtract(self.getVelocity());
		double distToTarget = target.getPosition().subtract(self.getPosition()).getMagnitude();

		EuVector repulsion = new EuVector(0, 0);
		EuVector averageVelocity = new EuVector(0, 0);
		int i = 0;
		for (GameSpaceObject gso : worldState.getFish())
		{
			EuVector dist = gso.getPosition().subtract(self.getPosition());
			double magnitude = dist.getMagnitude();
			if (magnitude > .1 && magnitude < 10)
			{
				repulsion = repulsion.add(dist.dividedBy(-1 / (magnitude * 10)));
				averageVelocity = averageVelocity.add(gso.getVelocity());
				i++;
			}
		}
		System.out.println(repulsion);

		if (i > 0)
		{
			averageVelocity = averageVelocity.dividedBy(i);
		}

		//desiredVelocity.add(repulsion);
		desiredVelocity.add(averageVelocity);
		if (distToTarget < 50 && distToTarget > 0)
		{
			desiredVelocity = desiredVelocity.dividedBy(distToTarget / 50);
		}

		return desiredVelocity;
		/*
		
		System.out.println(desiredVelocity);
		desiredVelocity = desiredVelocity.multipliedBy(target.getPosition().subtract(self.getPosition()).getMagnitude() / 25000);

		if (i > 0)
		{
			averageVelocity = averageVelocity.dividedBy(i);
			System.out.println("averageVelocity " + averageVelocity);

			return desiredVelocity.add(repulsion);//.dividedBy(averageVelocity.getMagnitude());
		} else
		{

		}
		
		return desiredVelocity.add(repulsion);*/
	}
}
