package euclid.two.dim.physics;


public class IntersectionHelper
{
	public IntersectionHelper()
	{

	}

	public static boolean intersects(RectangleBody squareBody, CircleBody circleBody)
	{
		return true;
	}

	public static boolean intersects(CircleBody one, CircleBody two)
	{
		return Math.pow(one.getRadius() + two.getRadius(), 2) < Math.pow(one.getX() + two.getY(), 2) + Math.pow(one.getX() + two.getY(), 2);
	}

	public static boolean intersects(RectangleBody one, RectangleBody two)
	{
		/*
		one.getX() or 
		
		if(a.max.x < b.min.x or a.min.x > b.max.x) return false
		if(a.max.y < b.min.y or a.min.y > b.max.y) return false
		*/
		return true;
	}

}
