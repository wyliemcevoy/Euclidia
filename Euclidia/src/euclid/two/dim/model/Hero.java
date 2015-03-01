package euclid.two.dim.model;

import java.util.ArrayList;

import euclid.two.dim.ability.internal.Ability;
import euclid.two.dim.team.Team;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;

public class Hero extends Unit
{
	private ArrayList<Ability> abilities;
	
	public Hero(Team team, EuVector location)
	{
		super(team, location);
		this.setAbilities(new ArrayList<Ability>());
		
	}
	
	public Hero(Hero hero)
	{
		super(hero);
		this.setAbilities(new ArrayList<Ability>());
		for (Ability ability : hero.getAbilities())
		{
			abilities.add(ability.deepCopy());
		}
	}
	
	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor)
	{
		updateVisitor.visit(this);
	}
	
	@Override
	public Updatable deepCopy()
	{
		return new Hero(this);
	}
	
	public ArrayList<Ability> getAbilities()
	{
		return abilities;
	}
	
	private void setAbilities(ArrayList<Ability> abilities)
	{
		this.abilities = abilities;
	}
	
	public void addAbility(Ability ability)
	{
		this.abilities.add(ability);
	}
	
}
