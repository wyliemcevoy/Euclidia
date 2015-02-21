package euclid.two.dim.optimizer;

import java.util.UUID;

public class GsoPair
{
	private UUID one, two;
	
	public GsoPair(UUID one, UUID two)
	{
		this.one = one;
		this.two = two;
	}
	
	public UUID getOne()
	{
		return one;
	}
	
	public UUID getTwo()
	{
		return two;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (!(o instanceof GsoPair))
		{
			return false;
		} else
		{
			GsoPair gsoPair = (GsoPair) o;
			if (one == gsoPair.getOne())
			{
				return two == gsoPair.getTwo();
			} else
			{
				if (one == gsoPair.getTwo())
				{
					return two == gsoPair.getOne();
				} else
				{
					return false;
				}
			}
		}
	}
	
}
