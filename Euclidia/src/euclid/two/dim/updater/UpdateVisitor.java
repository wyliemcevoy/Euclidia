package euclid.two.dim.updater;

import euclid.two.dim.model.Fish;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.Obstacle;

public interface UpdateVisitor
{
	public void visit(Minion gso);
	
	public void visit(Fish fish);
	
	public void visit(Hero hero);
	
	public void visit(Obstacle obstacle);
	
}
