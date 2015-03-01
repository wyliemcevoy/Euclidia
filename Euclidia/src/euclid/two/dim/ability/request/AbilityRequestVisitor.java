package euclid.two.dim.ability.request;

public interface AbilityRequestVisitor
{
	void visit(BasicAbilityRequest basicAbilityRequest);
	
	void visit(TargetedAbilityRequest targetedAbilityRequest);
	
	void visit(LocationAbilityRequest locationAbilityRequest);
}
