package euclid.two.dim.command;

public interface CommandVisitor
{
	void visit(MoveCommand moveCommand);
	
	void visit(UseLocationAbilityCommand useLocationAbilityCommand);
	
	void visit(UseTargetedAbilityCommand useTargetedAbilityCommand);
	
	void visit(AttackCommand attackCommand);
	
}
