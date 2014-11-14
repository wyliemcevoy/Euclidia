package euclid.two.dim;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

import euclid.two.dim.ai.Agent;
import euclid.two.dim.input.ClickEvent;
import euclid.two.dim.input.InputManager;
import euclid.two.dim.render.ConsoleRenderer;
import euclid.two.dim.updater.UpdateEngine;
import euclid.two.dim.world.WorldState;
import euclid.two.dim.world.WorldStateFactory;

public class ConsoleEuclidia
{
	private ConsoleRenderer consoleRenderer;
	private ArrayBlockingQueue<WorldState> rendererQueue;
	private ArrayBlockingQueue<ClickEvent> clickQueue;
	private UpdateEngine updateEngine;

	ConsoleEuclidia()
	{

		rendererQueue = new ArrayBlockingQueue<WorldState>(5);
		InputManager inputManager = new InputManager();
		updateEngine = new UpdateEngine(rendererQueue, inputManager);
		inputManager.setUpdateEngine(updateEngine);

		ArrayList<UUID> ids = new ArrayList<UUID>();
		Agent agent = new Agent(updateEngine, ids);
		updateEngine.addAgent(agent);

		WorldStateFactory f = new WorldStateFactory(inputManager);
		WorldState state = f.createVsWorldState(agent); //f.createRandomWorldState();

		updateEngine.setWorldState(state);
		rendererQueue.add(state);

		consoleRenderer = new ConsoleRenderer(rendererQueue, inputManager);
		consoleRenderer.start();

		try
		{
			Thread.sleep(500);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}

		updateEngine.start();
	}
}
