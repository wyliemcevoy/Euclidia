package euclid.two.dim.etherial;

import java.awt.Color;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.visitor.EtherialVisitor;

public class Slash extends Etherial
{
	protected EuVector to, from;
	
	@Override
	public void accept(EtherialVisitor etherialVisitor)
	{
		etherialVisitor.visit(this);
		
	}
	
	public EuVector getTo()
	{
		return to;
	}
	
	public EuVector getFrom()
	{
		return from;
	}
	
	@Override
	public Etherial deepCopy()
	{
		// TODO Auto-generated method stub
		return new Slash(this);
	}
	
	public Slash(Slash copy)
	{
		this.location = copy.getLocation();
		this.color = copy.getColor();
		this.expireTime = copy.getExpireTime();
	}
	
	public Slash(EuVector to, EuVector from)
	{
		this.to = to;
		this.from = from;
		this.color = Color.pink;
		this.expireTime = 500;
	}
	
}
