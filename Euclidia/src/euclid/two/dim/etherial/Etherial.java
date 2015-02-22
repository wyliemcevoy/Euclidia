package euclid.two.dim.etherial;

import java.awt.Color;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.visitor.AcceptsEtherialVisitor;

public abstract class Etherial implements AcceptsEtherialVisitor
{
	
	protected EuVector location;
	protected double expireTime;
	protected Color color;
	
	public void update(long timeStep)
	{
		if (!hasExpired())
		{
			expireTime -= timeStep;
		}
	}
	
	public boolean hasExpired()
	{
		return expireTime < 0;
	}
	
	public EuVector getLocation()
	{
		return location;
	}
	
	public double getExpireTime()
	{
		return expireTime;
	}
	
	public Color getColor()
	{
		return this.color;
	}
	
	public abstract Etherial deepCopy();
}
