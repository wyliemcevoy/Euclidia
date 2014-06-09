package euclid.two.dim.path;

import euclid.two.dim.model.EuVector;

public class PathNode
{
	private PathNode nextTarget;
	private EuVector location;

	public PathNode(EuVector location)
	{
		this.location = location;
	}

	public EuVector getLocation()
	{
		return location;
	}

	public void setNextPathNode(PathNode nextTarget)
	{
		this.nextTarget = nextTarget;
	}

}
