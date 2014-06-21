package euclid.two.dim;

import java.util.concurrent.ArrayBlockingQueue;

import euclid.two.dim.world.WorldState;

public class UpdateEngine extends Thread
{
	private ArrayBlockingQueue<WorldState> rendererQueue;
	private WorldState worldStateN;
	private long now, then;
	private InputManager inputManager;
	
	public UpdateEngine(ArrayBlockingQueue<WorldState> rendererQueue, InputManager inputManager)
	{
		this.rendererQueue = rendererQueue;
		this.inputManager = inputManager;
	}
	
	public void setWorldState(WorldState worldState)
	{
		this.worldStateN = worldState;
	}
	
	public void run()
	{
		now = System.currentTimeMillis();
		then = System.currentTimeMillis();
		while (true)
		{
			
			then = now;
			now = System.currentTimeMillis();
			inputManager.updatePaths();
			long timeStep = now - then;
			worldStateN.update(timeStep);
			WorldState nPlusOne = worldStateN.deepCopy();
			
			try
			{
				rendererQueue.put(nPlusOne);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
