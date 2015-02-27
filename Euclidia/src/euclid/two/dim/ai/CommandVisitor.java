package euclid.two.dim.ai;

public interface CommandVisitor
{
	void visit(MoveCommand moveCommand);
	
}
