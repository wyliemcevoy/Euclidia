package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.ability.internal.Ability;
import euclid.two.dim.ability.internal.AbilityType;
import euclid.two.dim.team.Team;

public abstract class CasterUnit extends Unit {
	protected ArrayList<Ability> abilities;

	public CasterUnit(Team team, EuVector location) {
		super(team, location);
		abilities = new ArrayList<Ability>();

	}

	public CasterUnit(CasterUnit copy) {
		super(copy);
		abilities = new ArrayList<Ability>();
		for (Ability ability : copy.getAbilities()) {
			abilities.add(ability.deepCopy());
		}
	}

	public ArrayList<Ability> getAbilities() {
		return abilities;
	}

	public void addAbility(Ability ability) {
		this.abilities.add(ability);
	}

	public Ability getAbility(AbilityType abilityType) {
		for (Ability ability : abilities) {
			if (abilityType == ability.getAbilityType()) {
				return ability;
			}
		}
		return null;
	}
}
