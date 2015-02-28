package euclid.two.dim.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AttackCommand extends Command
{
	private List<UUID> ids;
	private UUID target;
	
	public AttackCommand(List<UUID> ids, UUID target)
	{
		this.ids = ids;
		this.target = target;
	}
	
	public AttackCommand(UUID id, UUID target)
	{
		this.ids = new ArrayList<UUID>();
		ids.add(id);
		this.target = target;
	}
	
	@Override
	public void accept(CommandVisitor commandVisitor)
	{
		commandVisitor.visit(this);
	}
	
	public List<UUID> getIds()
	{
		return ids;
	}
	
	public UUID getTargetId()
	{
		return target;
	}
	
}
