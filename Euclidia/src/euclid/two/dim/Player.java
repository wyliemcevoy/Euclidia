package euclid.two.dim;

import java.awt.Color;

public class Player
{
	private Color color;
	
	public Player(Color color)
	{
		this.color = color;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
	
}
