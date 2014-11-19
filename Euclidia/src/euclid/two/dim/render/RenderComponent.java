package euclid.two.dim.render;

import euclid.two.dim.model.GameSpaceObject;

public interface RenderComponent
{
	public void acceptUpdate(GameSpaceObject gso, double timeStep);
	
	public int getRenderIndex();
	
	public int getRenderAction();
	
	public RenderComponent deepCopy();
}
