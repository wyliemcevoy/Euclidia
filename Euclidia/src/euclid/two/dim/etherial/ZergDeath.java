package euclid.two.dim.etherial;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.visitor.EtherialVisitor;

public class ZergDeath extends Etherial
{
	private double radius;
	
	public ZergDeath(ZergDeath copy)
	{
		location = new EuVector(copy.getLocation());
		color = copy.getColor();
		expireTime = copy.getExpireTime();
		
	}
	
	public ZergDeath(EuVector location, double radius)
	{
		this.radius = radius;
		this.location = location;
		this.expireTime = 1000;
	}
	
	@Override
	public void accept(EtherialVisitor etherialVisitor)
	{
		etherialVisitor.visit(this);
		
	}
	
	@Override
	public Etherial deepCopy()
	{
		// TODO Auto-generated method stub
		return new ZergDeath(this);
	}
	
	public double getRadius()
	{
		// TODO Auto-generated method stub
		return radius;
	}
	
}
