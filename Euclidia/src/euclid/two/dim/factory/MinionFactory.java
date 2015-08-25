package euclid.two.dim.factory;

import euclid.two.dim.ability.internal.AbilityType;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.Unit;
import euclid.two.dim.team.Team;

public class MinionFactory implements UnitFactory {

	@Override
	public Unit build(Team team, EuVector position) {
		return new Minion(team, position);
	}

	@Override
	public AbilityType getAbilityType() {
		// TODO Auto-generated method stub
		return AbilityType.buildMinion;
	}

}
