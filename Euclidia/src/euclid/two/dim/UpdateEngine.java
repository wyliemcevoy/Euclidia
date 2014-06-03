package euclid.two.dim;

import java.util.concurrent.ArrayBlockingQueue;

import euclid.two.dim.world.WorldState;

public class UpdateEngine extends Thread
{
	private ArrayBlockingQueue<WorldState> rendererQueue;
	private WorldState worldStateN;
	private long now, then;

	public UpdateEngine(ArrayBlockingQueue<WorldState> rendererQueue)
	{
		this.rendererQueue = rendererQueue;
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
			try
			{
				Thread.sleep(100);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}

			System.out.println("Update thread : ");
			then = now;
			now = System.currentTimeMillis();
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
