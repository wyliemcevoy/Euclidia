package euclid.two.dim.command;

public class UseTargetedAbilityCommand extends UseAbilityCommand
{
	
	@Override
	public void accept(CommandVisitor commandVisitor)
	{
		commandVisitor.visit(this);
	}
}
