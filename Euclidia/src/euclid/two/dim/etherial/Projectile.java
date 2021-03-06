package euclid.two.dim.etherial;

import java.util.UUID;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Unit;
import euclid.two.dim.visitor.EtherialVisitor;

public class Projectile extends Etherial
{
	private UUID target;
	private UUID sender;
	private double maxRange;
	private EuVector location;
	
	public Projectile(Unit target, Unit sender)
	{
		this.target = target.getId();
		this.sender = sender.getId();
		this.maxRange = 30;
		this.location = new EuVector(sender.getPosition());
		this.expireTime = 1500;
	}
	
	public Projectile(Projectile copy)
	{
		this.target = copy.getTarget();
		this.sender = copy.getSender();
		this.maxRange = copy.getMaxRange();
		this.location = new EuVector(copy.getLocation());
	}
	
	public boolean hasExpired(long timeStep)
	{
		expireTime -= timeStep;
		return expireTime < 0;
	}
	
	public UUID getTarget()
	{
		return target;
	}
	
	public void setTarget(UUID target)
	{
		this.target = target;
	}
	
	public UUID getSender()
	{
		return sender;
	}
	
	public void setSender(UUID sender)
	{
		this.sender = sender;
	}
	
	public double getMaxRange()
	{
		return maxRange;
	}
	
	public void setMaxRange(double maxRange)
	{
		this.maxRange = maxRange;
	}
	
	public EuVector getLocation()
	{
		return location;
	}
	
	public void setLocation(EuVector location)
	{
		this.location = location;
	}
	
	@Override
	public Etherial deepCopy()
	{
		return new Projectile(this);
	}
	
	@Override
	public void accept(EtherialVisitor etherialVisitor)
	{
		etherialVisitor.visit(this);
	}
	
	public int getDamage()
	{
		return 5;
	}
	
}
