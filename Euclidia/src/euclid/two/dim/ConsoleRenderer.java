package euclid.two.dim;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.concurrent.ArrayBlockingQueue;

import euclid.two.dim.world.WorldState;

public class ConsoleRenderer extends Thread
{
	private GraphicsConfiguration config;
	private boolean isRunning = true;
	private Canvas canvas;
	private BufferStrategy strategy;
	private BufferedImage background;
	private Graphics2D backgroundGraphics;
	private Graphics2D graphics;
	private ConsoleFrame consoleFrame;
	private int width = 500;
	private int height = 500;
	private int scale = 1;
	private ArrayBlockingQueue<WorldState> rendererQueue;
	private WorldState currentState;
	
	public ConsoleRenderer(ArrayBlockingQueue<WorldState> rendererQueue)
	{
		this.rendererQueue = rendererQueue;
		config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
		consoleFrame = new ConsoleFrame(width, height);
		consoleFrame.addWindowListener(new FrameClose());
		
		// Canvas
		canvas = new Canvas(config);
		canvas.setSize(width * scale, height * scale);
		consoleFrame.add(canvas, 0);
		
		// Background & Buffer
		background = create(width, height, false);
		canvas.createBufferStrategy(2);
		do
		{
			strategy = canvas.getBufferStrategy();
		} while (strategy == null);
		
	}
	
	// create a hardware accelerated image
	public final BufferedImage create(final int width, final int height, final boolean alpha) {
		return config.createCompatibleImage(width, height, alpha ? Transparency.TRANSLUCENT : Transparency.OPAQUE);
	}
	
	private class FrameClose extends WindowAdapter
	{
		@Override
		public void windowClosing(final WindowEvent e) {
			isRunning = false;
		}
	}
	
	// Screen and buffer stuff
	private Graphics2D getBuffer() {
		if (graphics == null)
		{
			try
			{
				graphics = (Graphics2D) strategy.getDrawGraphics();
			} catch (IllegalStateException e)
			{
				return null;
			}
		}
		return graphics;
	}
	
	private boolean updateScreen() {
		graphics.dispose();
		graphics = null;
		try
		{
			strategy.show();
			Toolkit.getDefaultToolkit().sync();
			return (!strategy.contentsLost());
			
		} catch (NullPointerException e)
		{
			return true;
			
		} catch (IllegalStateException e)
		{
			return true;
		}
	}
	
	public void run() {
		backgroundGraphics = (Graphics2D) background.getGraphics();
		long fpsWait = (long) (1.0 / 30 * 1000);
		
		main: while (isRunning)
		{
			long renderStart = System.nanoTime();
			updateGame();
			
			// Update Graphics
			do
			{
				Graphics2D bg = getBuffer();
				if (!isRunning)
				{
					break main;
				}
				renderGame(backgroundGraphics); // this calls your draw method
				// thingy
				if (scale != 1)
				{
					bg.drawImage(background, 0, 0, width * scale, height * scale, 0, 0, width, height, null);
				} else
				{
					bg.drawImage(background, 0, 0, null);
				}
				bg.dispose();
			} while (!updateScreen());
			
			// Better do some FPS limiting here
			long renderTime = (System.nanoTime() - renderStart) / 1000000;
			try
			{
				Thread.sleep(Math.max(0, fpsWait - renderTime));
			} catch (InterruptedException e)
			{
				Thread.interrupted();
				break;
			}
			renderTime = (System.nanoTime() - renderStart) / 1000000;
			
		}
		consoleFrame.dispose();
	}
	
	public void updateGame() {
		// update game logic here
	}
	
	public void renderGame(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width, height);
	}
	
}
