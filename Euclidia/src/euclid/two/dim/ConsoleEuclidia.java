package euclid.two.dim;

import java.util.concurrent.ArrayBlockingQueue;

import euclid.two.dim.world.WorldState;

public class ConsoleEuclidia
{
	private ConsoleRenderer consoleRenderer;
	private ArrayBlockingQueue<WorldState> rendererQueue;
	
	ConsoleEuclidia()
	{
		rendererQueue = new ArrayBlockingQueue<WorldState>(5);
		
		consoleRenderer = new ConsoleRenderer(rendererQueue);
		consoleRenderer.start();
	}
}
