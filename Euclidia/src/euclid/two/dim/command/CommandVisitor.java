package euclid.two.dim.command;

public interface CommandVisitor {
	void visit(MoveCommand moveCommand);

	void visit(UseLocationAbilityCommand useLocationAbilityCommand);

	void visit(UseTargetedAbilityCommand useTargetedAbilityCommand);

	void visit(AbilityCommand abilityCommand);

	void visit(AttackCommand attackCommand);

	void visit(GatherCommand gatherCommand);

}
