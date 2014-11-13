package euclid.two.dim.updater;

import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Unit;
import euclid.two.dim.model.Obstacle;

public interface UpdateVisitor
{
	public void visit(Unit gso);

	public void visit(Boid boid);

	public void visit(Obstacle obstacle);
}
