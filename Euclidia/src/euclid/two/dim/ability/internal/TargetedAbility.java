package euclid.two.dim.ability.internal;

import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.ability.request.BasicAbilityRequest;
import euclid.two.dim.ability.request.LocationAbilityRequest;
import euclid.two.dim.ability.request.TargetedAbilityRequest;

public abstract class TargetedAbility extends Ability
{
	protected TargetedAbilityRequest request;
	
	public TargetedAbility(TargetedAbility copy)
	{
		//super(copy);
	}
	
	@Override
	public void visit(BasicAbilityRequest basicAbilityRequest)
	{
		// DO NOTHING
	}
	
	@Override
	public void visit(TargetedAbilityRequest request)
	{
		this.request = request;
	}
	
	@Override
	public void visit(LocationAbilityRequest locationAbilityRequest)
	{
		// DO NOTHING
	}
	
	@Override
	public boolean isValidRequest(AbilityRequest abilityRequest)
	{
		abilityRequest.accept(this);
		return (this.abilityType == abilityRequest.getAbilityType() && request != null && this.isOffCooldown());
	}
	
	@Override
	public void closeRequest()
	{
		this.request = null;
	}
}
