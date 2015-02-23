package euclid.two.dim.ai;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import euclid.two.dim.Player;
import euclid.two.dim.input.AttackCommand;
import euclid.two.dim.input.InputCommand;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.updater.UpdateEngine;
import euclid.two.dim.world.WorldState;

public class Agent extends Player
{
	private WorldState worldState;
	private UpdateEngine updateEngine;
	private ArrayList<UUID> freindlies;
	
	public Agent(UpdateEngine updateEngine, ArrayList<UUID> freindlies)
	{
		super(Color.BLUE);
		this.updateEngine = updateEngine;
		this.freindlies = freindlies;
	}
	
	public void setWorldState(WorldState worldState)
	{
		this.worldState = worldState;
	}
	
	public void regesterControl(UUID id)
	{
		this.freindlies.add(id);
	}
	
	public List<InputCommand> getCommands()
	{
		ArrayList<InputCommand> commands = new ArrayList<InputCommand>();
		
		Random rand = new Random();
		EuVector target = new EuVector(100 + rand.nextInt(400), 100 + rand.nextInt(400));
		
		for (UUID id : freindlies)
		{
			AttackCommand command = new AttackCommand(updateEngine, target, id);
			commands.add(command);
		}
		
		return commands;
	}
}
