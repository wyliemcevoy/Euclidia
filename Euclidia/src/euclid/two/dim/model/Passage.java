package euclid.two.dim.model;

import euclid.two.dim.map.ConvexPoly;
import euclid.two.dim.map.Segment;

public class Passage extends Segment
{
	private ConvexPoly polyOne, polyTwo;
	
	public Passage(EuVector one, EuVector two, ConvexPoly polyOne, ConvexPoly polyTwo)
	{
		super(one, two);
		this.polyOne = polyOne;
		this.polyTwo = polyTwo;
		//polyOne.addPassage(this);
		//polyTwo.addPassage(this);
	}
	
	public ConvexPoly goThroughFrom(ConvexPoly poly)
	{
		if (poly.equals(polyOne))
		{
			return polyTwo;
		} else
		{
			return polyOne;
		}
	}
	
}
