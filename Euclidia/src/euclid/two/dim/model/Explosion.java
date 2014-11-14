package euclid.two.dim.model;

import java.awt.Color;
import java.awt.Graphics;

import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;

public class Explosion implements Updatable
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

	public boolean hasExpired(long timeStep)
	{
		expireTime -= timeStep;
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

	private double getExpireTime()
	{
		return expireTime;
	}

	public Color getColor()
	{
		return this.color;
	}

	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor)
	{
		updateVisitor.visit(this);
	}

	@Override
	public Updatable deepCopy()
	{
		// TODO Auto-generated method stub
		return new Explosion(this);
	}

}
