package euclid.two.dim.visitor;

import euclid.two.dim.model.Building;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.ResourcePatch;
import euclid.two.dim.model.Unit;
import euclid.two.dim.model.Worker;

public class CombatStep extends UpdateStep {
	public CombatStep() {
	}

	@Override
	public void visit(Minion unit) {
		visit((Unit) unit);
	}

	private void visit(Unit unit) {

		switch (unit.getCombatBehavior()) {
		case AttackIfInRange:

			// Unit has target
			// - Unit's target no longer exists
			// - Unit's target is out of range
			// - There is a new target in range
			// - There is no no target in range
			// Unit does not have target

			Unit target = worldState.getUnit(unit.getTarget());
			if (target != null) {
				if (worldState.getDistanceBetween(target, unit) < unit.getAttack().getRange()) {
					// target is still alive and within range
				}
				else {
					GameSpaceObject newTarget = worldState.getClosestUnfriendly(unit);
					if (newTarget != null) {
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
	public void visit(ResourcePatch resourcePatch) {
		// Do nothing
	}

	@Override
	public void visit(Hero hero) {
		visit((Unit) hero);

	}

	@Override
	public void visit(Building building) {
		// do nothing
	}

	@Override
	public void visit(Worker worker) {
		visit((Unit) worker);

	}

}
