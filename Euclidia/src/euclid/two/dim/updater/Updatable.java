package euclid.two.dim.updater;

import euclid.two.dim.render.Renderable;

public interface Updatable
{
	public void acceptUpdateVisitor(UpdateVisitor updatevisitor);
	
	public Updatable deepCopy();
	
	public Renderable toRenderable();
}
