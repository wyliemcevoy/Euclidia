package euclid.two.dim.model;

import java.util.UUID;

public abstract class Unit extends DamageableGso
{
	private UUID target;
	
	public UUID getTarget()
	{
		return target;
	}
	
	public void setTarget(UUID target)
	{
		this.target = target;
	}
	
}
