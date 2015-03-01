package euclid.two.dim.updater;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;

import euclid.two.dim.Path;
import euclid.two.dim.VectorMath;
import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.command.AbilityCommand;
import euclid.two.dim.command.AttackCommand;
import euclid.two.dim.command.Command;
import euclid.two.dim.command.CommandVisitor;
import euclid.two.dim.command.MoveCommand;
import euclid.two.dim.command.UseLocationAbilityCommand;
import euclid.two.dim.command.UseTargetedAbilityCommand;
import euclid.two.dim.etherial.Etherial;
import euclid.two.dim.etherial.Explosion;
import euclid.two.dim.etherial.Projectile;
import euclid.two.dim.etherial.Slash;
import euclid.two.dim.etherial.ZergDeath;
import euclid.two.dim.input.InputCommand;
import euclid.two.dim.input.InputManager;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Fish;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.RoomPath;
import euclid.two.dim.model.Unit;
import euclid.two.dim.path.PathCalculator;
import euclid.two.dim.team.Agent;
import euclid.two.dim.team.Game;
import euclid.two.dim.team.Team;
import euclid.two.dim.visitor.EndStepManager;
import euclid.two.dim.visitor.EtherialVisitor;
import euclid.two.dim.visitor.PhysicsStep;
import euclid.two.dim.visitor.SteeringStep;
import euclid.two.dim.world.WorldState;

public class UpdateEngine extends Thread implements UpdateVisitor, EtherialVisitor, CommandVisitor
{
	private ArrayBlockingQueue<WorldState> rendererQueue;
	private WorldState worldStateN;
	private long now, then, timeStep;
	private InputManager inputManager;
	private boolean stopRequested;
	private ArrayList<Agent> agents;
	private Game game;
	
	public UpdateEngine(ArrayBlockingQueue<WorldState> rendererQueue, InputManager inputManager, Game game)
	{
		this.rendererQueue = rendererQueue;
		this.inputManager = inputManager;
		this.stopRequested = false;
		this.agents = new ArrayList<Agent>();
		this.game = game;
	}
	
	public void addAgent(Agent agent)
	{
		this.agents.add(agent);
	}
	
	public void setWorldState(WorldState worldState)
	{
		this.worldStateN = worldState;
	}
	
	private void processInput()
	{
		// Read through queue of input events and process them
		
		for (Command command : game.getCommands())
		{
			command.accept(this);
		}
		
		if (inputManager.hasUnprocessedEvents())
		{
			
			for (InputCommand inputCommand : inputManager.getInputCommands())
			{
				//inputCommand.execute();
			}
		}
	}
	
	public void run()
	{
		now = System.currentTimeMillis();
		then = System.currentTimeMillis();
		while (!stopRequested)
		{
			// Update time step
			then = now;
			now = System.currentTimeMillis();
			timeStep = now - then;
			randomCommand();
			
			processInput();
			
			update(worldStateN, timeStep / 2);
			
			for (Etherial etherial : worldStateN.getEtherials())
			{
				etherial.accept(this);
			}
			
			//worldStateN.purgeExpired();
			
			EndStepManager endStep = new EndStepManager(worldStateN);
			endStep.endPhase();
			//purgeExpired();
			
			WorldState nPlusOne = worldStateN.deepCopy();
			WorldState playerCopy = worldStateN.deepCopy();
			
			game.updatePlayers(playerCopy);
			
			try
			{
				rendererQueue.put(nPlusOne);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void update(WorldState worldState, long timeStep)
	{
		
		SteeringStep steeringStep = new SteeringStep(worldState, timeStep);
		steeringStep.runStep();
		
		PhysicsStep physicsStep = new PhysicsStep(worldState);
		physicsStep.runStep();
		
		List<GameSpaceObject> gsos = worldState.getGameSpaceObjects();
		for (GameSpaceObject gso : gsos)
		{
			gso.acceptUpdateVisitor(this);
		}
		
		/*
		for (GameSpaceObject fishi : gsos)
		{
			fishi.update(timeStep);
		}
		
		for (GameSpaceObject fishi : gsos)
		
		{
			fishi.travelToTheFuture();
		}
		
		for (GameSpaceObject fishi : gsos)
		
		{
			fishi.separate();
		}
		
		for (GameSpaceObject fishi : gsos)
		
		{
			fishi.travelToTheFuture();
		}
		
		Iterator<Explosion> it = explosions.iterator();
		while (it.hasNext())
		{
			Explosion explosion = it.next();
			if (explosion.hasExpired())
			{
				it.remove();
			}
		}
		*/
		
		/*
		for (GameSpaceObject gso : worldState.getGsos())
		{
			gso.acceptUpdateVisitor(this);
		}
		*/
	}
	
	public void requestStop()
	{
		stopRequested = true;
	}
	
	public WorldState getCurrentWorldState()
	{
		return worldStateN;
	}
	
	public double getZoom()
	{
		return this.getCurrentWorldState().getCamera().getZoom();
	}
	
	public void setZoom(double zoom)
	{
		this.getCurrentWorldState().getCamera().setZoom(zoom);
	}
	
	public void visit(Unit unit)
	{
		unit.getAttack().update(timeStep);
		
		Unit target = worldStateN.getUnit(unit.getTarget());
		
		// Check to see if the units target still is alive / exists
		if (target == null || unit.getPosition() == null || target.getPosition() == null)
		{
			//pickNewTarget(unit);
			
			return;
		}
		
		double distSqrd = VectorMath.getDistanceSquared(target.getPosition(), unit.getPosition());
		double rangeSqrd = unit.getAttack().getRange() * unit.getAttack().getRange();
		
		// Check and see if the unit is capable of attacking (basic attack off cooldown)
		if (unit.getAttack().isReloaded())
		{
			if (distSqrd <= rangeSqrd)
			{
				// Unit is alive and within range
				target.getHealth().add(-1 * unit.getAttack().getDamage());
				unit.getAttack().attack();
				
				worldStateN.addEtherial(new Slash(target.getPosition(), unit.getPosition()));
			}
		}
		
		if (distSqrd > rangeSqrd)
		{
			RoomPath roomPath = PathCalculator.calculateRoomPath(worldStateN, unit.getPosition(), target.getPosition());
			unit.setPath(roomPath.toPath());
		}
		
	}
	
	@Override
	public void visit(Minion unit)
	{
		visit((Unit) unit);
		/*
		
		unit.getAttack().update(timeStep);
		
		Unit target = worldStateN.getUnit(unit.getTarget());
		
		// Check to see if the units target still is alive / exists
		if (target == null || unit.getPosition() == null || target.getPosition() == null)
		{
			pickNewTarget(unit);
			
			return;
		}
		
		double distSqrd = VectorMath.getDistanceSquared(target.getPosition(), unit.getPosition());
		double rangeSqrd = unit.getAttack().getRange() * unit.getAttack().getRange();
		
		// Check and see if the unit is capable of attacking (basic attack off cooldown)
		if (unit.getAttack().isReloaded())
		{
			if (distSqrd <= rangeSqrd)
			{
				// Unit is alive and within range
				target.getHealth().add(-1 * unit.getAttack().getDamage());
				unit.getAttack().attack();
				
				worldStateN.addEtherial(new Slash(target.getPosition(), unit.getPosition()));
			}
		}
		
		if (distSqrd > rangeSqrd)
		{
			RoomPath roomPath = PathCalculator.calculateRoomPath(worldStateN, unit.getPosition(), target.getPosition());
			unit.setPath(roomPath.toPath());
		}
		
		/*
		ArrayList<GameSpaceObject> fishes = worldState.getGsos();
		futurePosition = new EuVector(position);
		EuVector update = new EuVector(0, 0);
		for (GameSpaceObject fish : fishes)
		{
			EuVector distTo = position.subtract(fish.getPosition());
			double mag = distTo.getMagnitude();
			if (!this.equals(fish) && mag < 15)
			{
				EuVector plus = distTo.normalize().dividedBy(mag * mag / (fish.getRadius() * 10));
				update = update.add(plus);
			}
		}
		
		if (update.getMagnitude() > 2)
		{
			update = update.normalize().multipliedBy(2);
		}
		if (update.getMagnitude() < .15)
		{
			return;
		}
		//futureVelocity = futureVelocity.add(update);
		futurePosition = futurePosition.add(update);
		
		*/
	}
	
	private void pickNewTarget(Minion unit)
	{
		for (GameSpaceObject gso : worldStateN.getGameSpaceObjects())
		{
			if ((gso instanceof Minion) && gso != unit)
			{
				Minion minion = (Minion) gso;
				if (unit.getTeam() != Team.Blue)
				{
					unit.setTarget(minion.getId());
				}
			}
		}
		
	}
	
	@Override
	public void visit(Obstacle obstacle)
	{
		
	}
	
	public void update(long timeStep)
	{
		for (GameSpaceObject gso : worldStateN.getGameSpaceObjects())
		{
			gso.acceptUpdateVisitor(this);
		}
	}
	
	public void randomCommand()
	{
		Random rand = new Random();
		if (rand.nextDouble() > .99)
		{
			
			for (Agent agent : agents)
			{
				/*
				for (InputCommand command : agent.getCommands())
				{
					command.execute();
				}
				*/
			}
		}
	}
	
	@Override
	public void visit(Explosion explosion)
	{
		explosion.update(timeStep);
		if (explosion.hasExpired())
		{
			worldStateN.registerAsExpired(explosion);
		}
	}
	
	@Override
	public void visit(Projectile projectile)
	{
		projectile.update(timeStep);
		
		Unit target = worldStateN.getUnit(projectile.getTarget());
		
		if (projectile.hasExpired() || target == null)
		{
			projectile.setAsExpired();
		} else
		{
			EuVector targetLocation = target.getPosition();
			
			EuVector dT = targetLocation.subtract(projectile.getLocation()).normalize().multipliedBy(2);
			projectile.setLocation(projectile.getLocation().add(dT));
			
			if (projectile.getLocation().subtract(targetLocation).getMagnitude() < 3)
			{
				target.getHealth().add(-projectile.getDamage());
				projectile.setAsExpired();
			}
		}
	}
	
	@Override
	public void visit(Fish fish)
	{
		
	}
	
	@Override
	public void visit(Slash slash)
	{
		slash.update(timeStep);
		if (slash.hasExpired())
		{
			worldStateN.registerAsExpired(slash);
		}
	}
	
	@Override
	public void visit(ZergDeath zergDeath)
	{
		zergDeath.update(timeStep);
		if (zergDeath.hasExpired())
		{
			worldStateN.registerAsExpired(zergDeath);
		}
	}
	
	private void purgeExpired()
	{
		Iterator<GameSpaceObject> it = worldStateN.getGsos().iterator();
		DeathVisitor deathVisitor = new DeathVisitor();
		
		while (it.hasNext())
		{
			GameSpaceObject gso = it.next();
			
			gso.acceptUpdateVisitor(deathVisitor);
			if (deathVisitor.isDead())
			{
				it.remove();
				worldStateN.addEtherial(new ZergDeath(gso.getPosition(), (int) gso.getRadius()));
				
			}
		}
	}
	
	@Override
	public void visit(Hero hero)
	{
		
		visit((Unit) hero);
		
	}
	
	@Override
	public void visit(MoveCommand moveCommand)
	{
		
		for (UUID id : moveCommand.getIds())
		{
			Unit unit = worldStateN.getUnit(id);
			
			if (unit != null)
			{
				unit.setPath(new Path(new EuVector(moveCommand.getLocation())));
			}
			
		}
	}
	
	@Override
	public void visit(UseLocationAbilityCommand command)
	{
		command.getHeroId();
		command.getAbilityIndex();
		
		worldStateN.addEtherial(new Explosion(command.getLocation()));
		
	}
	
	@Override
	public void visit(UseTargetedAbilityCommand useTargetedAbilityCommand)
	{
		
	}
	
	@Override
	public void visit(AttackCommand attackCommand)
	{
		if (attackCommand.getIds().size() > 0)
		{
			for (UUID id : attackCommand.getIds())
			{
				Unit unit = worldStateN.getUnit(id);
				Unit target = worldStateN.getUnit(attackCommand.getTargetId());
				if (unit != null && unit.getTeam() != target.getTeam())
				{
					unit.setTarget(attackCommand.getTargetId());
				}
			}
		}
	}
	
	@Override
	public void visit(AbilityCommand abilityCommand)
	{
		AbilityRequest abilityRequest = abilityCommand.getAbilityRequest();
		
		Hero hero = worldStateN.getHero(abilityRequest.getHeroId());
		
		hero.getAbilities();
		
	}
}
