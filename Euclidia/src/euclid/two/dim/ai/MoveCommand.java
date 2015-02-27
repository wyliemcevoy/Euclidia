package euclid.two.dim.ai;

public class MoveCommand extends Command
{
	
	@Override
	public void accept(CommandVisitor commandVisitor)
	{
		commandVisitor.visit(this);
		
	}
	
}
