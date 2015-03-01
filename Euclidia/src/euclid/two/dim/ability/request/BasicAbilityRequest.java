package euclid.two.dim.ability.request;

public class BasicAbilityRequest extends AbilityRequest
{
	
	@Override
	public void accept(AbilityRequestVisitor visitor)
	{
		visitor.visit(this);
		
	}
	
}
