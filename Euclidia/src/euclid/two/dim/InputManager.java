package euclid.two.dim;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import euclid.two.dim.model.EuVector;

public class InputManager implements MouseListener
{
	private ArrayList<Path> paths;
	private Queue<EuVector> unAddedPaths;

	public InputManager()
	{
		paths = new ArrayList<Path>();
		unAddedPaths = new LinkedList<EuVector>();
	}

	public void addPath(Path path)
	{
		paths.add(path);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		unAddedPaths.add(new EuVector(e.getX(), e.getY()));
		System.out.println(e.getX() + " " + e.getY() + " mouse ");
	}

	public void updatePaths()
	{
		if (unAddedPaths.size() > 0)
		{
			EuVector target = unAddedPaths.poll();
			System.out.println("adding target " + target);
			for (Path path : paths)
			{

				path.addTarget(target);
			}
		}
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
		System.out.println("MOUSE DOWN!!!!");// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}
}
