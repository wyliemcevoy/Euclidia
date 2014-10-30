package euclid.two.dim.updater;

import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Fish;
import euclid.two.dim.model.Obstacle;

public interface UpdateVisitor
{
	public void visit(Fish gso);

	public void visit(Boid boid);

	public void visit(Obstacle obstacle);
}
