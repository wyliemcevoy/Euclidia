package euclid.two.dim.ability.request;

import euclid.two.dim.model.EuVector;

public class LocationAbilityRequest extends AbilityRequest
{
	private EuVector location;
	
	/**
	 * @return the location
	 */
	public EuVector getLocation()
	{
		return location;
	}
	
	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(EuVector location)
	{
		this.location = location;
	}
	
	@Override
	public void accept(AbilityRequestVisitor visitor)
	{
		visitor.visit(this);
		
	}
	
}
