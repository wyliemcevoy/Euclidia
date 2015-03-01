package euclid.two.dim.team;

import java.util.ArrayList;
import java.util.UUID;

import euclid.two.dim.ability.internal.Ability;

public class TeamState
{
	private Team team;
	private ArrayList<UUID> selectedUnits;
	private Ability selectedAbility;
	
	public TeamState(Team team)
	{
		this.team = team;
	}
	
}
