package euclid.two.dim.team;

import java.util.ArrayList;
import java.util.List;

import euclid.two.dim.Player;
import euclid.two.dim.command.Command;
import euclid.two.dim.world.WorldState;

public class Game
{
	private GameState gameState;
	private ArrayList<Player> players;
	
	public Game()
	{
		this.players = new ArrayList<Player>();
	}
	
	public void addPlayer(Player player)
	{
		this.players.add(player);
	}
	
	public List<Command> getCommands()
	{
		ArrayList<Command> commands = new ArrayList<Command>();
		
		for (Player player : players)
		{
			commands.addAll(player.getCommands());
		}
		
		return commands;
	}
	
	public void updatePlayers(WorldState worldState)
	{
		for (Player player : players)
		{
			player.acceptWorldState(worldState.deepCopy());
		}
	}
	
}
