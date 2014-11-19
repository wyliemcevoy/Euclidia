package euclid.two.dim.render;

import java.awt.Graphics2D;
import java.awt.Image;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Unit;

public class UnitRender implements Renderable
{
	private EuVector location;
	private double theta;
	private int actionIndex;
	private int directionIndex;
	private int action;
	private int index;
	private int width = 39;
	private int height = 38;
	private int rows = 9;
	private int startX = 2;
	private int startY = 2;
	
	public UnitRender(Unit unit)
	{
		this.location = unit.getPosition();
		this.theta = unit.getTheta();
		
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
		
		g.drawImage(source, x, y, x + ((int) width / 2), y + ((int) height / 2), frameX, frameY, frameX + width, frameY + height, null);
	}
	
}
