package euclid.two.dim.updater;

import euclid.two.dim.model.Building;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.ResourcePatch;
import euclid.two.dim.model.Worker;

public interface UpdateVisitor {
	public void visit(Minion gso);

	public void visit(Hero hero);

	public void visit(ResourcePatch resourcePatch);

	public void visit(Building building);

	public void visit(Worker worker);
}
