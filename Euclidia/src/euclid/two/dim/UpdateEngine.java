package euclid.two.dim;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.RoomPath;
import euclid.two.dim.path.PathCalculator;
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
			// Update time step
			then = now;
			now = System.currentTimeMillis();

			// Read through queue of input events and process them
			if (inputManager.hasUnprocessedEvents())
			{

				// 
				ArrayList<ClickInputEvent> inputEvents = inputManager.getInputEvents();

				// Currently only care about the last click
				ClickInputEvent target = inputEvents.get(inputEvents.size() - 1);
				System.out.println("Handling event " + target.getX() + " " + target.getY());

				// Handle user input events
				for (GameSpaceObject fish : worldStateN.getFish())
				{
					RoomPath roomPath = PathCalculator.calculateRoomPath(worldStateN, fish.getPosition(), new EuVector(target.getX(), target.getY()));
					fish.setPath(roomPath.toPath());
				}
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
}
