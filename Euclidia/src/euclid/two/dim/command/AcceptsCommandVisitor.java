package euclid.two.dim.command;

public interface AcceptsCommandVisitor
{
	public void accept(CommandVisitor commandVisitor);
}
