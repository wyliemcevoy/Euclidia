package euclid.two.dim.factory;

import euclid.two.dim.ability.internal.AbilityType;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Unit;
import euclid.two.dim.team.Team;

public abstract interface UnitFactory {
	public abstract AbilityType getAbilityType();

	public abstract Unit build(Team team, EuVector position);
}
