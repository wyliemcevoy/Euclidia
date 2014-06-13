package euclid.two.dim;

import java.util.LinkedList;
import java.util.Queue;

import euclid.two.dim.model.EuVector;

public class Path
{
	private Queue<EuVector> targets;

	public void addTarget(EuVector target)
	{
		targets.add(target);
	}

	public Path(EuVector firstTarget)
	{
		targets = new LinkedList<EuVector>();
		targets.add(firstTarget);
	}

	public EuVector getTarget()
	{
		if (targets.isEmpty())
		{
			return null;
		} else
		{
			return targets.peek();
		}
	}

	public boolean haveArrived(EuVector location)
	{
		if (location.subtract(targets.peek()).getMagnitude() < 30)
		{
			if (targets.size() > 1)
			{
				EuVector arived = targets.poll();
				System.out.println(targets.size());
			}
			return true;
		} else
		{
			return false;
		}
	}

	public int size()
	{
		return targets.size();
	}
}
