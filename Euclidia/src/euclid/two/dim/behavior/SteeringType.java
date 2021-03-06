package euclid.two.dim.behavior;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.path.Path;

public abstract class SteeringType
{
	private static final double maxSteeringForce = 50;
	protected GameSpaceObject self;
	protected Path path;
	
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
