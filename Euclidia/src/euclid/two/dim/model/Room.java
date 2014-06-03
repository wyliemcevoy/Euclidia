package euclid.two.dim.model;

import java.util.ArrayList;

public class Room
{
	private double x;
	private double y;
	private double width;
	private double height;

	private ArrayList<Door> doors;

	public Room(double x, double y, double width, double height)
	{
		this.x = x;
		this.y = y;
		this.width = x;
		this.height = height;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getWidth()
	{
		return width;
	}

	public void setWidth(double width)
	{
		this.width = width;
	}

	public double getHeight()
	{
		return height;
	}

	public void setHeight(double height)
	{
		this.height = height;
	}

}
