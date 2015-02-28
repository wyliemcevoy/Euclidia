package euclid.two.dim;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import euclid.two.dim.command.Command;
import euclid.two.dim.team.Team;
import euclid.two.dim.world.WorldState;

public abstract class Player
{
	protected Team team;
	protected WorldState worldState;
	protected Color color;
	protected ArrayList<Command> commands;
	
	public abstract List<Command> getCommands();
	
	public abstract void acceptWorldState(WorldState worldState);
	
}
