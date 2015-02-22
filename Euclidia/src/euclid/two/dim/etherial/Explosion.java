package euclid.two.dim.etherial;

import java.awt.Color;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.visitor.EtherialVisitor;

public class Explosion extends Etherial
{
	
	public Explosion(EuVector location)
	{
		this.location = location;
		this.color = Color.pink;
		this.expireTime = 2000;
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
	
}
