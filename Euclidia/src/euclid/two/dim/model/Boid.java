package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.Configuration;
import euclid.two.dim.Path;
import euclid.two.dim.behavior.Flock;
import euclid.two.dim.world.WorldState;

public class Boid extends GameSpaceObject
{
	private ArrayList<Fish> fishes;
	private WorldState worldState;
	
	public Boid(Fish fish, WorldState worldState, Path path)
	{
		radius = fish.getRadius() + 10; //9
		this.position = fish.getPosition();
		
		this.worldState = worldState;
		this.fishes = new ArrayList<Fish>();
		fishes.add(fish);
		
		this.velocity = new EuVector(0, 0);
		this.mass = 15;
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
	public void specificUpdate(EuVector steeringForce, double timeStep)
	{
		
		for (Fish fish : fishes)
		{
			EuVector acceleration = steeringForce.dividedBy(mass);
			fish.setFutureVelocity(fish.getVelocity().add(acceleration.multipliedBy(timeStep)).truncate(maxSpeed));
			fish.setFuturePosition(fish.getPosition().add(fish.getFutureVelocity().multipliedBy(timeStep / 100)));
		}
		
		for (Fish fish : fishes)
		{
			fish.setPosition(new EuVector(fish.getFuturePosition()));
			fish.setVelocity(new EuVector(fish.getFutureVelocity()));
		}
		
		timeStep = timeStep / 35;
		
		for (Fish fish : fishes)
		{
			/* pull towards center */
			
			EuVector dist = futurePosition.subtract(fish.getPosition());
			EuVector force = dist.multipliedBy(Configuration.springConstant);
			EuVector springVelocity = force.dividedBy(fish.getMass());
			
			// damping
			EuVector dampingForce = springVelocity.multipliedBy(Configuration.dampingCoefficient);
			EuVector dampingVelocity = dampingForce.dividedBy(fish.getMass());
			
			//System.out.println()
			
			fish.setPosition(fish.getPosition().add(springVelocity.add(dampingVelocity).multipliedBy(timeStep)));
			
			//fish.setVelocity(fish.getVelocity().add(springVelocity).add(dampingVelocity));
			//fish.setFuturePosition(fish.getPosition());
			
			fish.setForce(new EuVector(0, 0));
			for (Fish fishi : fishes)
			{
				// apply spring force 
				
				if (!fishi.equals(fish))
				{
					
					EuVector appart = fishi.getPosition().subtract(fish.getPosition());
					if (appart.getMagnitude() < 50)
					{
						EuVector sForce = appart.normalize().multipliedBy(Configuration.innerSpringConstant * -1 * (appart.getMagnitude() - 50));
						fish.setForce(fish.getForce().add(sForce.multipliedBy(-1)));
						EuVector sVeloc = sForce.dividedBy(fish.getMass());
						//fishi.setPosition(fishi.getPosition().add(sVeloc.multipliedBy(timeStep)));
					}
				}
			}
		}
		
		for (Fish fish : fishes)
		{
			EuVector fishV = fish.getForce().dividedBy(fish.getMass());
			
			//EuVector dampingForce = fishV.multipliedBy(Configuration.dampingCoefficient);
			//EuVector dampingVelocity = dampingForce.dividedBy(fish.getMass());
			
			//System.out.println()
			
			//fish.setPosition(fish.getPosition().add(fishV.add(dampingVelocity).multipliedBy(timeStep)));
			System.out.println("Force " + fishV.multipliedBy(timeStep).getMagnitude());
			
			fish.setPosition(fish.getPosition().add(fishV.multipliedBy(timeStep)));
			
		}
		
		/*
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
		*/
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
