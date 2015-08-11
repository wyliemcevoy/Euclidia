package euclid.two.dim.updater;

import euclid.two.dim.model.Building;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.Obstacle;

public interface UpdateVisitor {
	public void visit(Minion gso);

	public void visit(Hero hero);

	public void visit(Obstacle obstacle);

	public void accept(Building building);

}
