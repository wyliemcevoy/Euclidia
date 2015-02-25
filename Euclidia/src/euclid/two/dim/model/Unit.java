package euclid.two.dim.model;

import java.util.UUID;

import euclid.two.dim.render.Renderable;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;

public abstract class Unit extends GameSpaceObject
{
	private UUID target;
	
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
	public boolean hasExpired()
	{
		// TODO Auto-generated method stub
		return false;
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
	
	public UUID getTarget()
	{
		return target;
	}
	
	public void setTarget(UUID target)
	{
		this.target = target;
	}
	
}
