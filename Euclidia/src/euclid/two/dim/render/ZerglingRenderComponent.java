package euclid.two.dim.render;

import euclid.two.dim.model.GameSpaceObject;

public class ZerglingRenderComponent implements RenderComponent
{
	private int renderIndex;
	private int action;
	private int maxRenderIndex = 5;
	private double timeToUpdateFrame = 50;
	private double currentCountDown;
	
	public ZerglingRenderComponent()
	{
		this.action = 0;
		this.renderIndex = 0;
		this.currentCountDown = timeToUpdateFrame;
	}
	
	public ZerglingRenderComponent(ZerglingRenderComponent copy)
	{
		this.action = copy.getRenderAction();
		this.renderIndex = copy.getRenderIndex();
		this.currentCountDown = copy.getCurrentCountDown();
	}
	
	private double getCurrentCountDown()
	{
		return currentCountDown;
	}
	
	@Override
	public void acceptUpdate(GameSpaceObject gso, double timeStep)
	{
		double result = currentCountDown - timeStep;
		
		if (result <= 0)
		{
			currentCountDown = result + timeToUpdateFrame;
			renderIndex = (renderIndex + 1) % maxRenderIndex;
		} else
		{
			currentCountDown = result;
		}
	}
	
	@Override
	public int getRenderIndex()
	{
		return renderIndex;
	}
	
	@Override
	public int getRenderAction()
	{
		// Always return non attack
		return action;
	}
	
	@Override
	public RenderComponent deepCopy()
	{
		return new ZerglingRenderComponent(this);
	}
}
