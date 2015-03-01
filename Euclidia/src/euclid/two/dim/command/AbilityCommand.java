package euclid.two.dim.command;

import euclid.two.dim.ability.request.AbilityRequest;

public class AbilityCommand extends Command
{
	private AbilityRequest abilityRequest;
	
	/**
	 * @return the abilityRequest
	 */
	public AbilityRequest getAbilityRequest()
	{
		return abilityRequest;
	}
	
	public AbilityCommand(AbilityRequest abilityRequest)
	{
		this.abilityRequest = abilityRequest;
	}
	
	@Override
	public void accept(CommandVisitor commandVisitor)
	{
		commandVisitor.visit(this);
	}
}
