package euclid.two.dim;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class InputManager implements MouseListener
{

	private ArrayList<ClickInputEvent> inputEvents;
	private static Object lock = new Object();

	public InputManager()
	{
		this.inputEvents = new ArrayList<ClickInputEvent>();
	}

	public ArrayList<ClickInputEvent> getInputEvents()
	{

		ArrayList<ClickInputEvent> build = new ArrayList<ClickInputEvent>();

		synchronized (lock)
		{
			for (ClickInputEvent clone : inputEvents)
			{
				build.add(new ClickInputEvent(clone));
			}

			inputEvents = new ArrayList<ClickInputEvent>();
		}

		return build;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		synchronized (lock)
		{
			inputEvents.add(new ClickInputEvent(e.getX(), e.getY()));
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
		return inputEvents.size() > 0;
	}

}
