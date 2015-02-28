package euclid.two.dim.command;

import euclid.two.dim.team.Team;

public abstract class Command implements AcceptsCommandVisitor
{
	private Team team;
	
	public Team getTeam()
	{
		return team;
	}
	
}
