package euclid.two.dim.ability.internal;

import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.ability.request.BasicAbilityRequest;
import euclid.two.dim.ability.request.LocationAbilityRequest;
import euclid.two.dim.ability.request.TargetedAbilityRequest;

public abstract class BasicAbility extends Ability
{
	protected BasicAbilityRequest request;
	
	@Override
	public Ability deepCopy()
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void visit(BasicAbilityRequest request)
	{
		this.request = request;
	}
	
	@Override
	public void visit(TargetedAbilityRequest request)
	{
	}
	
	@Override
	public void visit(LocationAbilityRequest request)
	{
		
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
