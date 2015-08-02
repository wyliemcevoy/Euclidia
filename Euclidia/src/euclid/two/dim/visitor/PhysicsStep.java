package euclid.two.dim.visitor;

import java.util.ArrayList;

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
		ArrayList<GameSpaceObject> fishes = worldState.getGsos();

		EuVector position = new EuVector(unit.getPosition());

		EuVector futurePosition = new EuVector(position);
		EuVector update = new EuVector(0, 0);
		for (GameSpaceObject fish : fishes) {
			EuVector distTo = position.subtract(fish.getPosition());
			double mag = distTo.getMagnitude();
			if (!unit.equals(fish) && mag < 15) {
				EuVector plus = distTo.normalize().dividedBy(mag * mag / (fish.getRadius() * 10));
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

}
