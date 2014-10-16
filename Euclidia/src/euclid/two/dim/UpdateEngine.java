package euclid.two.dim;

import java.util.concurrent.ArrayBlockingQueue;

import euclid.two.dim.input.InputCommand;
import euclid.two.dim.input.InputManager;
import euclid.two.dim.world.WorldState;

public class UpdateEngine extends Thread
{
	private ArrayBlockingQueue<WorldState> rendererQueue;
	private WorldState worldStateN;
	private long now, then;
	private InputManager inputManager;
	private boolean stopRequested;

	public UpdateEngine(ArrayBlockingQueue<WorldState> rendererQueue, InputManager inputManager)
	{
		this.rendererQueue = rendererQueue;
		this.inputManager = inputManager;
		this.stopRequested = false;
	}

	public void setWorldState(WorldState worldState)
	{
		this.worldStateN = worldState;
	}

	public void run()
	{
		now = System.currentTimeMillis();
		then = System.currentTimeMillis();
		while (!stopRequested)
		{
			// Update time step
			then = now;
			now = System.currentTimeMillis();

			// Read through queue of input events and process them
			if (inputManager.hasUnprocessedEvents())
			{

				for (InputCommand inputCommand : inputManager.getInputCommands())
				{
					inputCommand.execute();
				}

				// 
				////ArrayList<ClickEvent> inputEvents = inputManager.getInputEvents();

				// Currently only care about the last click
				//ClickEvent target = inputEvents.get(inputEvents.size() - 1);
				//System.out.println("Handling event " + target.getX() + " " + target.getY());

				// Handle user input events

			}

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

	public void requestStop()
	{
		stopRequested = true;
	}

	public WorldState getCurrentWorldState()
	{
		return worldStateN;
	}

	public double getZoom()
	{
		// TODO Auto-generated method stub
		return this.getCurrentWorldState().getCamera().getZoom();
	}

	public void setZoom(double zoom)
	{
		this.getCurrentWorldState().getCamera().setZoom(zoom);

	}
}
