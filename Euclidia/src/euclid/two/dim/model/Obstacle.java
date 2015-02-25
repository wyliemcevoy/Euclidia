package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.render.Renderable;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.world.WorldState;

public class Obstacle extends GameSpaceObject
{
	private int hitPoints;
	private WorldState worldState;
	
	public Obstacle(EuVector position, WorldState worldState)
	{
		this.position = position;
		this.futurePosition = new EuVector(position);
		this.future = new EuVector(position);
		this.worldState = worldState;
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.isSelected = true;
		this.hitPoints = 100;
		this.radius = 50;
	}
	
	@Override
	public void separate()
	{
		ArrayList<GameSpaceObject> fishes = worldState.getGsos();
		futurePosition = new EuVector(position);
		for (GameSpaceObject fish : fishes)
		{
			double rad = radius + fish.getRadius() * 3;
			EuVector distTo = position.subtract(fish.getPosition());
			double mag = distTo.getTaxiCabMagnitude();
			if (!(fish instanceof Obstacle) && mag < rad)
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
	
	@Override
	public void acceptUpdateVisitor(UpdateVisitor updatevisitor)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Updatable deepCopy()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Renderable toRenderable()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void specificConstructor(GameSpaceObject copy)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void specificUpdate(EuVector displacement)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean hasExpired()
	{
		// TODO Auto-generated method stub
		return false;
	}
	
}
