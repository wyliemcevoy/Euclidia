package euclid.two.dim.render;

import java.awt.Color;
import java.awt.Graphics2D;

import euclid.two.dim.etherial.Explosion;

public class ExplosionRender implements Renderable
{
	private Color color;
	private int rad;
	private int x;
	private int y;
	
	public ExplosionRender(Explosion explosion)
	{
		this.rad = (int) explosion.getExpireTime() / 500;
		this.x = (int) explosion.getLocation().getX() - rad;
		this.y = (int) explosion.getLocation().getY() - rad;
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(color);
		g.fillArc(x, y, 2 * rad, 2 * rad, 0, 360);
	}
	
}
