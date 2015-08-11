package euclid.two.dim.visitor;

import java.util.ArrayList;

import euclid.two.dim.model.Building;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.Unit;

public class PhysicsStep extends UpdateStep {

	public PhysicsStep() {

	}

	@Override
	public void visit(Minion unit) {

		visit((Unit) unit);
	}

	private void visit(Unit unit) {
		ArrayList<GameSpaceObject> gsos = worldState.getGsos();

		EuVector position = new EuVector(unit.getPosition());

		EuVector futurePosition = new EuVector(position);
		EuVector update = new EuVector(0, 0);
		for (GameSpaceObject gso : gsos) {
			EuVector distTo = position.subtract(gso.getPosition());
			double mag = distTo.getMagnitude(); // - gso.getRadius() - unit.getRadius();
			if (!unit.equals(gso) && mag < 15) {
				EuVector plus = distTo.normalize().dividedBy(mag * mag / (gso.getRadius() * 10));
				update = update.add(plus);
			}
		}

		if (update.getMagnitude() > 2) {
			update = update.normalize().multipliedBy(2);
		}
		if (update.getMagnitude() < .15) {
			return;
		}
		futurePosition = futurePosition.add(update);

		unit.setFuturePosition(futurePosition);
	}

	@Override
	public void visit(Obstacle obstacle) {
		// Do nothing
	}

	@Override
	public void visit(Hero hero) {
		visit((Unit) hero);
	}

	@Override
	public void accept(Building unit) {
		// do nothing
	}
}
