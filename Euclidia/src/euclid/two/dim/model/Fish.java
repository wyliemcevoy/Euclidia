package euclid.two.dim.model;

import euclid.two.dim.Path;
import euclid.two.dim.behavior.SteeringBehavior;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.world.WorldState;

public class Fish extends GameSpaceObject
{
	protected WorldState worldState;
	
	public Fish(WorldState worldState, Path path, EuVector position)
	{
		this.position = position;
		this.futurePosition = new EuVector(position);
		this.future = new EuVector(position);
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.path = path;
		this.worldState = worldState;
		this.radius = 2;
	}
	
	public Fish(EuVector position, WorldState worldState)
	{
		this.position = position;
		this.futurePosition = new EuVector(position);
		this.future = new EuVector(position);
		this.worldState = worldState;
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.steeringBehavior = SteeringBehavior.StandStill;
		this.radius = 30;
	}
	
	public Fish(GameSpaceObject copy)
	{
		super(copy);
	}
	
	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor)
	{
		updateVisitor.visit(this);
	}
	
	@Override
	public Updatable deepCopy()
	{
		return new Fish(this);
	}
	
	@Override
	public boolean hasExpired()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
}
