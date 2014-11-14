package euclid.two.dim.render;

import java.awt.Color;
import java.awt.Graphics2D;

import euclid.two.dim.model.Explosion;
import euclid.two.dim.model.Projectile;

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
	
	public ExplosionRender(Projectile projectile)
	{
		this.color = Color.LIGHT_GRAY;
		this.rad = 1;
		this.x = (int) projectile.getLocation().getX();
		this.y = (int) projectile.getLocation().getY();
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(color);
		g.fillArc(x, y, 2 * rad, 2 * rad, 0, 360);
	}
	
}
