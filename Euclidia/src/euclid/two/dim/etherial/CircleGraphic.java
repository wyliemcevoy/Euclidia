package euclid.two.dim.etherial;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.visitor.EtherialVisitor;

public class CircleGraphic extends Etherial
{
	private int radius;
	private EuVector location;
	
	public CircleGraphic(EuVector location, double radius)
	{
		this.radius = (int) radius;
		this.location = location;
		this.expireTime = 500;
	}
	
	public CircleGraphic(CircleGraphic copy)
	{
		this.radius = copy.getRadius();
		this.expireTime = copy.getExpireTime();
	}
	
	/**
	 * @return the location
	 */
	public EuVector getLocation()
	{
		return location;
	}
	
	@Override
	public void accept(EtherialVisitor etherialVisitor)
	{
		etherialVisitor.visit(this);
		
	}
	
	@Override
	public Etherial deepCopy()
	{
		return new CircleGraphic(this);
	}
	
	public int getRadius()
	{
		return radius;
	}
	
}
