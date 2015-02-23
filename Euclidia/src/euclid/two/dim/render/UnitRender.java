package euclid.two.dim.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;

import eucild.two.dim.combat.Health;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Unit;

public class UnitRender implements Renderable
{
	private EuVector location;
	private double theta;
	private int index;
	private int width = 39;
	private int height = 38;
	private int radius;
	private Health health;
	
	public UnitRender(Unit unit)
	{
		this.location = unit.getPosition();
		this.theta = unit.getTheta();
		this.radius = (int) unit.getRadius();
		this.health = unit.getHealth().deepCopy();
		
		if (unit.getVelocity().getMagnitude() > 1)
		{
			this.index = unit.getRenderComponent().getRenderIndex();
		}
	}
	
	@Override
	public void draw(Graphics2D g)
	{
		Image source = SpriteFlyWeight.getInstance().getZergImage();
		int x = (int) location.getX();
		int y = (int) location.getY();
		int frameX = 2;
		int frameY = 2 + (index) * (height + 4);
		
		/*
		if (gso.getVelocity().getMagnitude() < .5)
		{
			index = 0;
		}
		*/
		
		if (theta > 0)
		{
			frameX = (int) (2 + (Math.floor(8 * theta / Math.PI)) * (width + 4));
		} else
		{
			frameX = (int) (2 + ((Math.floor(9 + (8 * theta / Math.PI)))) * (width + 4)) + 7 * (width + 4);
		}
		
		radius = radius + 2;
		
		//g.drawImage(source, x, y, x + ((int) width / 2), y + ((int) height / 2), frameX, frameY, frameX + width, frameY + height, null);
		g.drawImage(source, x - radius, y - radius, x + radius, y + radius, frameX, frameY, frameX + width, frameY + height, null);
		
		/*
		
		g.setColor(Color.BLACK);
		g.drawRect(x - (radius / 2), y - radius, radius, 1);
		
		g.setColor(getHealthColor(health.getHealthPercentage()));
		g.drawRect(x - (radius / 2), y - radius, (int) (radius * health.getHealthPercentage()), 1);
		*/
		
		/*
		g.setColor(Color.WHITE);
		g.drawArc(x - radius, y - radius, 2 * radius, 2 * radius, 0, 360);
		*/
	}
	
	private Color getHealthColor(double percent)
	{
		if (percent < .2)
		{
			return Color.RED;
		} else if (percent < .5)
		{
			return Color.YELLOW;
		} else
		{
			return Color.GREEN;
		}
	}
	
}
