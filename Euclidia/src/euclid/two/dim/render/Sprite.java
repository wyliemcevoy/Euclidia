package euclid.two.dim.render;

import java.awt.Graphics2D;
import java.awt.Image;

import euclid.two.dim.model.GameSpaceObject;

public class Sprite
{
	private Image source;
	
	private int width = 39;
	private int height = 38;
	private int rows = 9;
	private int startX = 2;
	private int startY = 2;
	private int count = 0;
	
	private int index;
	
	public Sprite(Image source)
	{
		this.source = source;
		this.index = 0;
	}
	
	public void draw(Graphics2D g, int x, int y, GameSpaceObject gso)
	{
		int frameX = 2;
		int frameY = 2 + (index) * (height + 4);
		
		if (gso.getVelocity().getMagnitude() < 2)
		{
			index = 0;
		}
		
		double theta = gso.getTheta();
		
		if (theta > 0)
		{
			frameX = (int) (2 + (Math.floor(8 * theta / Math.PI)) * (width + 4));
		} else
		{
			frameX = (int) (2 + ((Math.floor(9 + (8 * theta / Math.PI)))) * (width + 4)) + 7 * (width + 4);
		}
		
		g.drawImage(source, x, y, x + ((int) width / 2), y + ((int) height / 2), frameX, frameY, frameX + width, frameY + height, null);
		
		count++;
		if (count > 5000)
		{
			index = (index + 1) % 8;
			count = 0;
		}
		
	}
	
}
