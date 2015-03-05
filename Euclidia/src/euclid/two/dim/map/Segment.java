package euclid.two.dim.map;

import euclid.two.dim.model.EuVector;

public class Segment
{
	EuVector one, two;
	
	/**
	 * @return the one
	 */
	public EuVector getOne()
	{
		return one;
	}
	
	/**
	 * @return the two
	 */
	public EuVector getTwo()
	{
		return two;
	}
	
	public Segment(EuVector one, EuVector two)
	{
		this.one = one;
		this.two = two;
	}
	
}
