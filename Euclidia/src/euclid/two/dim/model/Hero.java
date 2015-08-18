package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.ability.internal.Ability;
import euclid.two.dim.team.Team;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;

public class Hero extends CasterUnit {
	private ArrayList<Ability> abilities;

	public Hero(Team team, EuVector location) {
		super(team, location);

	}

	public Hero(Hero copy) {
		super(copy);
	}

	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor) {
		updateVisitor.visit(this);
	}

	@Override
	public Updatable deepCopy() {
		return new Hero(this);
	}

}
