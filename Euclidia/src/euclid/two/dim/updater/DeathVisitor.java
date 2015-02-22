package euclid.two.dim.updater;

import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Fish;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.Unit;

public class DeathVisitor implements UpdateVisitor
{
	private boolean dead;
	
	public DeathVisitor()
	{
		this.dead = false;
	}
	
	@Override
	public void visit(Unit unit)
	{
		dead = unit.getHealth().isDead();
	}
	
	@Override
	public void visit(Fish fish)
	{
		dead = false;
		
	}
	
	@Override
	public void visit(Boid boid)
	{
		dead = false;
	}
	
	@Override
	public void visit(Obstacle obstacle)
	{
		dead = false;
	}
	
	public boolean isDead()
	{
		return dead;
	}
	
}
