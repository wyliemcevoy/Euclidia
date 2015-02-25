package euclid.two.dim;

import java.util.ArrayList;
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
	
	public Path(Path copy)
	{
		targets = new LinkedList<EuVector>();
		
		for (EuVector target : copy.getTargets())
		{
			targets.add(target);
		}
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
		if (targets.size() == 0)
		{
			return true;
		}
		if (location.subtract(targets.peek()).getMagnitude() < 15)
		{
			if (targets.size() >= 1)
			{
				targets.poll();
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
	
	@Override
	public boolean equals(Object o)
	{
		if (o instanceof Path)
		{
			return (this.equals((Path) o));
		} else
		{
			return false;
			
		}
	}
	
	private boolean equals(Path path)
	{
		ArrayList<EuVector> clonesTargets = path.getTargets();
		
		if (clonesTargets.size() != targets.size())
		{
			return false;
		}
		
		int i = 0;
		for (EuVector target : targets)
		{
			EuVector clonesTarget = clonesTargets.get(i);
			
			if (!target.equals(clonesTarget))
			{
				return false;
			}
			i++;
		}
		
		return true;
	}
	
	public ArrayList<EuVector> getTargets()
	{
		ArrayList<EuVector> result = new ArrayList<EuVector>();
		
		for (EuVector target : targets)
		{
			result.add(target.deepCopy());
		}
		
		return result;
		
	}
	
	public Path deepCopy()
	{
		return new Path(this);
	}
	
}
