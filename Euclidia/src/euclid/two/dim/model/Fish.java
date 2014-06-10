package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.Path;
import euclid.two.dim.behavior.Flock;
import euclid.two.dim.behavior.StandStill;
import euclid.two.dim.world.WorldState;

public class Fish extends GameSpaceObject
{
	private WorldState worldState;

	public Fish(WorldState worldState, Path path, EuVector position)
	{
		this.position = position;
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.sb = new Flock(worldState, path, this);
		this.worldState = worldState;
	}

	public Fish(EuVector position)
	{
		this.position = position;
		this.futurePosition = new EuVector(position);
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.sb = new StandStill();
	}

	public Fish(GameSpaceObject copy)
	{
		super(copy);
	}

	@Override
	public void separate()
	{
		ArrayList<GameSpaceObject> fishes = worldState.getFish();
		futurePosition = new EuVector(position);
		EuVector update = new EuVector(0, 0);
		for (GameSpaceObject fish : fishes)
		{
			EuVector distTo = position.subtract(fish.getPosition());
			double mag = distTo.getMagnitude();
			if (!this.equals(fish) && mag < 20)
			{
				EuVector plus = distTo.normalize().dividedBy(mag * mag / 20);

				update = update.add(plus);
			}
		}
		if (update.getMagnitude() > 2.5)
		{
			update = update.normalize().multipliedBy(2.5);
		}

		futurePosition = futurePosition.add(update);

	}

	@Override
	public void separate2()
	{
		ArrayList<GameSpaceObject> fishes = worldState.getFish();
		for (GameSpaceObject fishOne : fishes)
		{
			for (GameSpaceObject fishTwo : fishes)
			{
				if (!fishOne.equals(fishTwo))
				{
					EuVector one = fishOne.getFuturePosition();
					EuVector two = fishTwo.getFuturePosition();
					EuVector distbetween = one.subtract(two);

					if (distbetween.getMagnitude() < 10)
					{
						fishOne.setFuturePosition(fishOne.getFuturePosition().add(distbetween.normalize().multipliedBy(1)));
					}
				}
			}
		}
	}
}
