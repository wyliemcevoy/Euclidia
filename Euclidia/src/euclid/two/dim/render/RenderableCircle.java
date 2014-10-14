package euclid.two.dim.render;

import java.awt.Color;
import java.awt.Graphics2D;

public class RenderableCircle extends Renderable
{
	private Color color;
	private int x, y, radius;

	public RenderableCircle(double x, double y, double radius, Color color)
	{
		this.color = color;
		this.x = (int) x;
		this.y = (int) y;
		this.radius = (int) radius;
	}

	@Override
	public void draw(Graphics2D g)
	{
		g.setColor(color);
		g.drawOval(x, y, radius, radius);
	}

}
