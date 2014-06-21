package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.Path;
import euclid.two.dim.behavior.Flock;
import euclid.two.dim.world.WorldState;

public class Boid extends GameSpaceObject
{
	private ArrayList<Fish> fishes;
	private WorldState worldState;
	
	public Boid(Fish fish, WorldState worldState, Path path)
	{
		radius = fish.getRadius() + 9;
		this.position = fish.getPosition();
		
		this.worldState = worldState;
		this.fishes = new ArrayList<Fish>();
		fishes.add(fish);
		
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.sb = new Flock(worldState, path, this);
		
	}
	
	public Boid(GameSpaceObject copy)
	{
		super(copy);
	}
	
	public void ingest(Fish fish)
	{
		fishes.add(fish);
	}
	
	@Override
	public void specificUpdate(EuVector displacement)
	{
		
		//System.out.println("pre update : " + this.toString());
		for (Fish fish : fishes)
		{
			fish.setPosition(fish.getPosition().add(displacement));
			if (fish.getPosition().subtract(futurePosition).getMagnitude() >= radius)
			{
				System.out.println("PROBLEM " + (fish.getPosition().subtract(futurePosition).getMagnitude() - radius));
			}
			
		}
		
		for (Fish fish : fishes)
		{
			EuVector update = new EuVector(0, 0);
			for (GameSpaceObject innerFish : fishes)
			{
				EuVector distTo = innerFish.getPosition().subtract(fish.getPosition());
				double mag = distTo.getMagnitude();
				if (!fish.equals(innerFish) && mag < 20)
				{
					EuVector plus = distTo.normalize().dividedBy(mag * mag / (fish.getRadius() * 10));
					update = update.add(plus);
				}
			}
			
			update = update.multipliedBy(.15);
			
			if (update.getMagnitude() > 8)
			{
				update = update.normalize().multipliedBy(3);
			}
			if (update.getMagnitude() < .000001)
			{
				update = new EuVector(0, 0);
			}
			
			fish.setPosition(fish.getPosition().subtract(update));
			
			// Dist away from center
			EuVector fromCenter = futurePosition.subtract(fish.getPosition());
			if (fromCenter.getMagnitude() > radius - 1.5)
			{
				double d = Math.min(Math.max(fromCenter.getMagnitude() - .5 * radius, .4), 6);
				d = d * .75;
				if (d > .0)
				{
					fish.setPosition(fish.getPosition().add(fromCenter.normalize().multipliedBy(d)));
				}
			}
			
			if (fish.getPosition().subtract(futurePosition).getMagnitude() > radius)
			{
				System.out.println("PROBLEM " + (fish.getPosition().subtract(futurePosition).getMagnitude() - radius));
			}
		}
		//System.out.println(" update : " + this.toString());
		
	}
	
	public ArrayList<Fish> explode()
	{
		return fishes;
	}
	
	@Override
	public void separate()
	{
		//System.out.println(this.toString());
		
		ArrayList<GameSpaceObject> fishes = worldState.getFish();
		futurePosition = new EuVector(position);
		EuVector update = new EuVector(0, 0);
		for (GameSpaceObject fish : fishes)
		{
			EuVector distTo = position.subtract(fish.getPosition());
			double mag = distTo.getMagnitude();
			if (!this.equals(fish) && mag < 20 && mag > .000001)
			{
				EuVector plus = distTo.normalize().dividedBy(mag * mag / (fish.getRadius() * 10));
				//System.out.println(plus);
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
		//futureVelocity = futureVelocity.add(update);
		futurePosition = futurePosition.add(update);
		
		for (Fish fish : this.fishes)
		{
			//fish.setPosition(fish.getPosition().subtract(update));
			
		}
		//System.out.println(update + " POST Separate " + this.toString());
		
	}
	
	@Override
	protected void specificConstructor(GameSpaceObject gso)
	{
		this.radius = gso.getRadius();
		//this.position = gso.getPosition();
		this.fishes = new ArrayList<Fish>();
		//this.velocity = new EuVector(0, 0);
		Boid boid = ((Boid) gso);
		for (Fish fish : boid.explode())
		{
			fishes.add(new Fish(fish));
		}
		
	}
}
