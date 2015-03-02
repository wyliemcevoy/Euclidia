package euclid.two.dim;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import euclid.two.dim.ability.internal.Ability;
import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.command.AbilityCommand;
import euclid.two.dim.command.AttackCommand;
import euclid.two.dim.command.Command;
import euclid.two.dim.command.MoveCommand;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.team.Team;
import euclid.two.dim.world.WorldState;

public class HumanMobaPlayer extends HumanPlayer
{
	private UUID heroId;
	private int selectedAbility;
	private static final Object lock = new Object();
	
	public HumanMobaPlayer(Team team, UUID heroId)
	{
		this.team = team;
		this.heroId = heroId;
		this.selectedAbility = -1;
		this.commands = new ArrayList<Command>();
	}
	
	public void click(EuVector location)
	{
		
		double x = location.getX();
		double y = location.getY();
		
		location = new EuVector(x / 2, y / 2);
		//EuVector adjustedTarget = new EuVector(x / worldState.getCamera().getZoom(), y / worldState.getCamera().getZoom());
		
		for (GameSpaceObject gso : worldState.getGameSpaceObjects())
		{
			if (location.subtract(gso.getPosition()).getMagnitudeSquared() < gso.getRadius() * gso.getRadius())
			{
				commands.add(new AttackCommand(heroId, gso.getId()));
			}
		}
		
		if (selectedAbility != -1)
		{
			
			Hero hero = worldState.getHero(heroId);
			List<Ability> abilities = hero.getAbilities();
			if (abilities.size() > selectedAbility)
			{
				Ability ability = hero.getAbilities().get(selectedAbility);
				AbilityRequest request = ability.toRequest(heroId, worldState, location);
				
				commands.add(new AbilityCommand(request));
			}
			
			selectedAbility = -1;
		} else
		{
			
			commands.add(new MoveCommand(heroId, location));
		}
		
	}
	
	@Override
	public List<Command> getCommands()
	{
		synchronized (lock)
		{
			ArrayList<Command> result = commands;
			
			commands = new ArrayList<Command>();
			
			return result;
		}
		
	}
	
	@Override
	public void acceptWorldState(WorldState worldState)
	{
		synchronized (lock)
		{
			this.worldState = worldState.deepCopy();
		}
	}
	
	@Override
	public void keyPressed(char c)
	{
		switch (c)
		{
		case 'q':
			selectedAbility = 0;
			break;
		case 'w':
			selectedAbility = 1;
			break;
		case 'e':
			selectedAbility = 2;
			break;
		case 'r':
			selectedAbility = 3;
			break;
		
		default:
			selectedAbility = -1;
		}
		
	}
	
}
