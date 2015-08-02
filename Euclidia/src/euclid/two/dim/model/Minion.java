package euclid.two.dim.model;

import euclid.two.dim.team.Team;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;

public class Minion extends Unit
{
	
	public Minion(Team team, EuVector position)
	{
		super(team, position);
	}
	
	public Minion(Minion copy)
	{
		super(copy);
	}
	
	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor)
	{
		updateVisitor.visit(this);
	}
	
	@Override
	public Updatable deepCopy()
	{
		return new Minion(this);
	}
	
}
