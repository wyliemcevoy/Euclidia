package euclid.two.dim.etherial;

import java.awt.Color;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.visitor.EtherialVisitor;

public class Explosion extends Etherial
{
	private int radius = 10;
	
	public Explosion(EuVector location)
	{
		this.location = location;
		this.color = Color.pink;
		this.expireTime = 500;
	}
	
	public Explosion(Explosion copy)
	{
		location = new EuVector(copy.getLocation());
		color = copy.getColor();
		expireTime = copy.getExpireTime();
	}
	
	@Override
	public Etherial deepCopy()
	{
		return new Explosion(this);
	}
	
	@Override
	public void accept(EtherialVisitor etherialVisitor)
	{
		etherialVisitor.visit(this);
	}
	
	public int getRadius()
	{
		// TODO Auto-generated method stub
		return radius;
	}
	
}
