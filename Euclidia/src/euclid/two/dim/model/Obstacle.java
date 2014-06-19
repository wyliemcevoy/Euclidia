package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.world.WorldState;

public class Obstacle extends Fish
{

	public Obstacle(EuVector position, WorldState worldState)
	{
		super(position, worldState);
		this.radius = 100;
	}

	@Override
	public void separate()
	{
		ArrayList<GameSpaceObject> fishes = worldState.getFish();
		futurePosition = new EuVector(position);
		for (GameSpaceObject fish : fishes)
		{
			double rad = radius + fish.getRadius() * 3;
			EuVector distTo = position.subtract(fish.getPosition());
			double mag = distTo.getMagnitude();
			if (!this.equals(fish) && mag < rad)
			{
				/*EuVector plus = distTo.normalize().dividedBy(mag * mag / (radius * radius));
				plus = plus.normalize().multipliedBy(radius * -1);
				fish.setFuturePosition(fish.getFuturePosition().add(plus));
				*/
				EuVector plus = distTo.normalize().multipliedBy(-1 * Math.abs(mag - rad));
				fish.setFuturePosition(fish.getFuturePosition().add(plus));
			}
		}

	}

}
