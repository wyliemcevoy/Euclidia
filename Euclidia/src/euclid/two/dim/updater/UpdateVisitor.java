package euclid.two.dim.updater;

import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Fish;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.Minion;

public interface UpdateVisitor
{
	public void visit(Minion gso);
	
	public void visit(Fish fish);
	
	public void visit(Boid boid);
	
	public void visit(Obstacle obstacle);
	
}
