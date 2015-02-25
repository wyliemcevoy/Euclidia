package euclid.two.dim.behavior;

import euclid.two.dim.Configuration;
import euclid.two.dim.Path;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;

public class Flock extends SteeringType
{
	
	public Flock(Path path, GameSpaceObject self)
	{
		this.path = path;
		this.self = self;
	}
	
	@Override
	public EuVector calculate()
	{
		path.haveArrived(self.getPosition());
		if (path.size() == 0)
		{
			self.setVelocity(new EuVector(0, 0));
			return new EuVector(0, 0);
		}
		
		EuVector desiredVelocity = (path.getTarget().subtract(self.getPosition())).normalize().multipliedBy(self.getMaxSpeed());
		desiredVelocity = desiredVelocity.subtract(self.getVelocity());
		double distToTarget = path.getTarget().subtract(self.getPosition()).getMagnitude();
		
		EuVector averageVelocity = new EuVector(0, 0);
		int i = 0;
		/*
		for (GameSpaceObject gso : worldState.getGsos())
		{
			EuVector dist = gso.getPosition().subtract(self.getPosition());
			double magnitude = dist.getMagnitude();
			if (magnitude > .1 && magnitude < 10)
			{
				// repulsion = repulsion.add(dist.dividedBy(-1 / (magnitude *
				// 10)));
				averageVelocity = averageVelocity.add(gso.getVelocity());
				i++;
			}
		}
		*/
		if (i > 0)
		{
			averageVelocity = averageVelocity.dividedBy(i);
		}
		
		// desiredVelocity.add(repulsion);
		desiredVelocity.add(averageVelocity);
		
		if (distToTarget < 50 && distToTarget > 0)
		{
			desiredVelocity = desiredVelocity.dividedBy(distToTarget / (Configuration.maxSpeed * 4));
		}
		
		// EuVector targetDisplacement =
		// self.getVelocity().add(desiredVelocity.dividedBy(10));
		// System.out.println(targetDisplacement.getMagnitude() / 3);
		
		return desiredVelocity;
		/*
		 * 
		 * System.out.println(desiredVelocity); desiredVelocity =
		 * desiredVelocity
		 * .multipliedBy(target.getPosition().subtract(self.getPosition
		 * ()).getMagnitude() / 25000);
		 * 
		 * if (i > 0) { averageVelocity = averageVelocity.dividedBy(i);
		 * System.out.println("averageVelocity " + averageVelocity);
		 * 
		 * return
		 * desiredVelocity.add(repulsion);//.dividedBy(averageVelocity.getMagnitude
		 * ()); } else {
		 * 
		 * }
		 * 
		 * return desiredVelocity.add(repulsion);
		 */
	}
}
