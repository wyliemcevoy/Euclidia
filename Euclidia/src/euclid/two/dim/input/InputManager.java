package euclid.two.dim.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import euclid.two.dim.UpdateEngine;

public class InputManager implements MouseListener, MouseWheelListener
{

	private ArrayList<InputCommand> inputCommands;
	private static Object lock = new Object();
	private UpdateEngine updateEngine;

	public InputManager()
	{
		this.inputCommands = new ArrayList<InputCommand>();
		//inputEvents = new ArrayList<ClickEvent>();
	}

	/*
	public ArrayList<ClickEvent> getInputEvents()
	{

		ArrayList<ClickEvent> build = new ArrayList<ClickEvent>();

		synchronized (lock)
		{
			for (ClickEvent clone : inputEvents)
			{
				build.add(new ClickEvent(clone));
			}

			inputEvents = new ArrayList<ClickEvent>();
		}

		return build;
	}
	*/

	@Override
	public void mouseClicked(MouseEvent e)
	{
		synchronized (lock)
		{
			inputCommands.add(new ClickEvent(e.getX(), e.getY(), updateEngine));
		}

		System.out.println(e.getX() + " " + e.getY() + " mouse ");
	}

	@Override
	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		this.mouseClicked(arg0);
	}

	public boolean hasUnprocessedEvents()
	{
		return inputCommands.size() > 0;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e)
	{
		ZoomCommand zoomCommand = new ZoomCommand(this.updateEngine);
		inputCommands.add(zoomCommand);
	}

	public ArrayList<InputCommand> getInputCommands()
	{
		ArrayList<InputCommand> build = new ArrayList<InputCommand>();
		synchronized (lock)
		{
			for (InputCommand inputCommand : inputCommands)
			{
				build.add(inputCommand);
			}
			inputCommands = new ArrayList<InputCommand>();
		}
		return build;
	}

	public void setUpdateEngine(UpdateEngine updateEngine)
	{
		this.updateEngine = updateEngine;
	}

}
