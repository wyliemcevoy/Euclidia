package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.Path;
import euclid.two.dim.behavior.SteeringBehavior;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.world.WorldState;

public class Boid extends GameSpaceObject
{
	private ArrayList<Minion> fishes;
	private WorldState worldState;
	
	public Boid(Minion fish, WorldState worldState, Path path)
	{
		radius = fish.getRadius() + 9;
		this.position = fish.getPosition();
		
		this.worldState = worldState;
		this.fishes = new ArrayList<Minion>();
		fishes.add(fish);
		
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.steeringBehavior = SteeringBehavior.Flock;
	}
	
	public Boid(GameSpaceObject copy)
	{
		super(copy);
	}
	
	public void ingest(Minion fish)
	{
		fishes.add(fish);
	}
	
	public ArrayList<Minion> explode()
	{
		return fishes;
	}
	
	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor)
	{
		updateVisitor.visit(this);
	}
	
	@Override
	public Updatable deepCopy()
	{
		// TODO Auto-generated method stub
		return new Boid(this);
	}
	
	@Override
	public boolean hasExpired()
	{
		// TODO Auto-generated method stub
		return false;
	}
}
