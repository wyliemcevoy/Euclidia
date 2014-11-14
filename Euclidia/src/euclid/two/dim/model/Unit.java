package euclid.two.dim.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.UUID;

import euclid.two.dim.Path;
import euclid.two.dim.Player;
import euclid.two.dim.behavior.Flock;
import euclid.two.dim.behavior.StandStill;
import euclid.two.dim.render.Renderable;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.world.WorldState;

public class Unit extends GameSpaceObject
{
	protected WorldState worldState;
	protected UUID enemyTarget;
	protected Player player;
	protected int hitPoints;
	
	public Unit(WorldState worldState, Path path, EuVector position, Player player)
	{
		this.position = position;
		this.futurePosition = new EuVector(position);
		this.future = new EuVector(position);
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.sb = new Flock(worldState, path, this);
		this.worldState = worldState;
		this.radius = 1;
		this.isSelected = true;
		this.player = player;
		this.hitPoints = 100;
	}
	
	public Unit(EuVector position, WorldState worldState, Player player)
	{
		this.position = position;
		this.futurePosition = new EuVector(position);
		this.future = new EuVector(position);
		this.worldState = worldState;
		this.velocity = new EuVector(0, 0);
		this.mass = 10;
		this.sb = new StandStill();
		this.radius = 30;
		this.isSelected = true;
		this.hitPoints = 100;
	}
	
	public Color getColor()
	{
		return player.getColor();
	}
	
	public Unit(GameSpaceObject copy)
	{
		super(copy);
		this.hitPoints = ((Unit) copy).getHitPoints();
	}
	
	public int getHitPoints()
	{
		return hitPoints;
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
			if (!this.equals(fish) && mag < 15)
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
		//futureVelocity = futureVelocity.add(update);
		futurePosition = futurePosition.add(update);
		
	}
	
	@Override
	public void setPath(Path path)
	{
		this.sb.setPath(path);
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
	public void specificUpdate(EuVector displacement)
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void specificConstructor(GameSpaceObject gso)
	{
		this.player = ((Unit) gso).getPlayer();// TODO Auto-generated method stub
		
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor)
	{
		updateVisitor.visit(this);
	}
	
	@Override
	public Updatable deepCopy()
	{
		return new Unit(this);
	}
	
	@Override
	public Renderable toRenderable()
	{
		// TODO Auto-generated method stub
		return new NullRenderable();
	}
}
