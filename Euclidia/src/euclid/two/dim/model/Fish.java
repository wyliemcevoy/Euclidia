package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.Path;
import euclid.two.dim.behavior.Flock;
import euclid.two.dim.behavior.StandStill;
import euclid.two.dim.world.WorldState;

public class Fish extends GameSpaceObject
{
	protected WorldState worldState;
	
	private EuVector force;
	
	public Fish(WorldState worldState, Path path, EuVector position)
	{
		this.position = position;
		this.futurePosition = new EuVector(position);
		this.future = new EuVector(position);
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.sb = new Flock(worldState, path, this);
		this.worldState = worldState;
		this.radius = 1;
	}
	
	public Fish(EuVector position, WorldState worldState)
	{
		this.position = position;
		this.futurePosition = new EuVector(position);
		this.future = new EuVector(position);
		this.worldState = worldState;
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.sb = new StandStill();
		this.radius = 30;
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
				EuVector plus = distTo.normalize().dividedBy(mag * mag / (fish.getRadius() * 10));
				update = update.add(plus);
			}
		}
		
		if (update.getMagnitude() > 2)
		{
			update = update.normalize().multipliedBy(2);
		}
		if (update.getMagnitude() < .15)
		{
			return;
		}
		futureVelocity = futureVelocity.add(update);
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
	
	@Override
	public void specificUpdate(EuVector displacement, double timeStep)
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void specificConstructor(GameSpaceObject gso)
	{
		// TODO Auto-generated method stub
		
	}
	
	public EuVector getForce()
	{
		return force;
	}
	
	public void setForce(EuVector force)
	{
		this.force = force;
	}
	
}
