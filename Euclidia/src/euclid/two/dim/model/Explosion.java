package euclid.two.dim.model;

import java.awt.Color;
import java.awt.Graphics;

import euclid.two.dim.visitor.EtherialVisitor;

public class Explosion extends Etherial
{
	private EuVector location;
	private double expireTime;
	private Color color;
	
	public Explosion(EuVector location)
	{
		this.location = location;
		this.color = Color.pink;
		this.expireTime = 2000;
	}
	
	public void update(long timeStep)
	{
		expireTime -= timeStep;
	}
	
	public boolean hasExpired()
	{
		return expireTime < 0;
	}
	
	public void draw(Graphics g)
	{
		g.setColor(color);
		int rad = (int) (expireTime / 500);
		
		g.fillArc((int) location.getX() - rad, (int) location.getY() - rad, 2 * rad, 2 * rad, 0, 360);
	}
	
	public Explosion(Explosion copy)
	{
		location = new EuVector(copy.getLocation());
		color = copy.getColor();
		expireTime = copy.getExpireTime();
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
