package euclid.two.dim.command;

import java.util.UUID;

public abstract class UseAbilityCommand extends Command
{
	protected int abilityIndex;
	protected UUID heroId;
	
	public int getAbilityIndex()
	{
		return abilityIndex;
	}
	
	public UUID getHeroId()
	{
		return heroId;
	}
}
