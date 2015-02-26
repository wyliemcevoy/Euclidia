package euclid.two.dim;

import java.awt.Color;

public class Player
{
	private Color color;
	private int id;
	
	public Player(int id, Color color)
	{
		this.color = color;
		this.id = id;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public boolean equals(Player player)
	{
		return player.getId() == id;
	}
	
	private int getId()
	{
		return id;
	}
	
}
