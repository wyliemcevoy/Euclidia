package euclid.two.dim.behavior;

import euclid.two.dim.Path;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;

public abstract class SteeringBehavior
{
	private static final double maxSteeringForce = 50;
	protected GameSpaceObject self;
	private Path path;

	public abstract EuVector calculate();

	public void setPath(Path path)
	{
		this.path = path;
	}

	public Path getPath()
	{
		return path;
	}
}
