package euclid.two.dim.updater;

public interface Updatable
{
	public void acceptUpdateVisitor(UpdateVisitor updatevisitor);
	
	public Updatable deepCopy();
}
