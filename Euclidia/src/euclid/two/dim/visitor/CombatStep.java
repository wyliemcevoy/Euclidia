package euclid.two.dim.visitor;

import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Fish;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.world.WorldState;

public class CombatStep implements UpdateVisitor
{
	private WorldState worldState;
	
	public CombatStep(WorldState worldState)
	{
		this.worldState = worldState;
		
		for (GameSpaceObject gso : worldState.getGameSpaceObjects())
		{
			gso.acceptUpdateVisitor(this);
		}
		
	}
	
	@Override
	public void visit(Minion unit)
	{
		switch (unit.getCombatBehavior())
		{
		case AttackIfInRange:
			
			// Unit has target
			// 		- Unit's target no longer exists
			//		- Unit's target is out of range
			//			- There is a new target in range
			//			- There is no no target in range
			// Unit does not have target
			
			Minion target = worldState.getUnit(unit.getTarget());
			if (target != null)
			{
				if (worldState.getDistanceBetween(target, unit) < unit.getAttack().getRange())
				{
					// target is still alive and within range
				} else
				{
					GameSpaceObject newTarget = worldState.getClosestUnfriendly(unit);
					if (newTarget != null)
					{
						unit.setTarget(newTarget.getId());
					}
					
				}
			}
			
			break;
		case PersueIfInRange:
			
			break;
		case NoAttack:
			// DO NOTHING
			break;
		default:
			break;
		
		}
		
	}
	
	@Override
	public void visit(Fish fish)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visit(Boid boid)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visit(Obstacle obstacle)
	{
		// TODO Auto-generated method stub
		
	}
	
}
