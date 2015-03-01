package euclid.two.dim.ability.request;

import java.util.UUID;

import euclid.two.dim.ability.internal.AbilityType;

public abstract class AbilityRequest implements AcceptsAbilityRequestVisitor
{
	protected AbilityType abilityType;
	protected UUID heroId;
	
	/**
	 * @return the heroId
	 */
	public UUID getHeroId()
	{
		return heroId;
	}
	
	/**
	 * @return the abilityType
	 */
	public AbilityType getAbilityType()
	{
		return abilityType;
	}
	
	/**
	 * @param abilityType
	 *            the abilityType to set
	 */
	public void setAbilityType(AbilityType abilityType)
	{
		this.abilityType = abilityType;
	}
	
}
