package euclid.two.dim.command;

import java.util.UUID;

import euclid.two.dim.model.EuVector;

public class UseLocationAbilityCommand extends UseAbilityCommand
{
	private EuVector location;
	
	public UseLocationAbilityCommand(UUID heroId, EuVector location, int i)
	{
		this.heroId = heroId;
		this.location = location;
		this.abilityIndex = i;
	}
	
	@Override
	public void accept(CommandVisitor commandVisitor)
	{
		commandVisitor.visit(this);
	}
	
	public EuVector getLocation()
	{
		return location;
	}
	
}
