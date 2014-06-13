package euclid.two.dim.behavior;

import euclid.two.dim.Configuration;
import euclid.two.dim.Path;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.world.WorldState;

public class Flock extends SteeringBehavior
{
	private WorldState worldState;
	private Path path;

	public Flock(WorldState worldState, Path path, GameSpaceObject self)
	{
		this.worldState = worldState;
		this.path = path;
		this.self = self;
	}

	public void setPath(Path path)
	{
		this.path = path;
	}

	@Override
	public EuVector calculate()
	{
		if (path.haveArrived(self.getPosition()) && path.size() == 1)
		{
			self.setVelocity(new EuVector(0, 0));
			self.setSteeringBehavior(new StandStill());
			return new EuVector(0, 0);
		}
		EuVector desiredVelocity = (path.getTarget().subtract(self.getPosition())).normalize().multipliedBy(self.getMaxSpeed());
		desiredVelocity = desiredVelocity.subtract(self.getVelocity());
		double distToTarget = path.getTarget().subtract(self.getPosition()).getMagnitude();

		EuVector averageVelocity = new EuVector(0, 0);
		int i = 0;

		for (GameSpaceObject gso : worldState.getFish())
		{
			EuVector dist = gso.getPosition().subtract(self.getPosition());
			double magnitude = dist.getMagnitude();
			if (magnitude > .1 && magnitude < 10)
			{
				//repulsion = repulsion.add(dist.dividedBy(-1 / (magnitude * 10)));
				averageVelocity = averageVelocity.add(gso.getVelocity());
				i++;
			}
		}

		if (i > 0)
		{
			averageVelocity = averageVelocity.dividedBy(i);
		}

		//desiredVelocity.add(repulsion);
		desiredVelocity.add(averageVelocity);

		if (distToTarget < 50 && distToTarget > 0)
		{
			desiredVelocity = desiredVelocity.dividedBy(distToTarget / (Configuration.maxSpeed * 4));
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
