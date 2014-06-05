package euclid.two.dim.model;

import euclid.two.dim.behavior.Flock;
import euclid.two.dim.behavior.StandStill;
import euclid.two.dim.world.WorldState;

public class Fish extends GameSpaceObject
{
	public Fish(WorldState worldState, GameSpaceObject target, EuVector position)
	{
		this.position = position;
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.sb = new Flock(worldState, target, this);
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

}
