package euclid.two.dim.ability.request;

public interface AcceptsAbilityRequestVisitor
{
	public void accept(AbilityRequestVisitor visitor);
}
