package euclid.two.dim.updater;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;

import euclid.two.dim.ai.Agent;
import euclid.two.dim.input.InputCommand;
import euclid.two.dim.input.InputManager;
import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Etherial;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Explosion;
import euclid.two.dim.model.Fish;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.Projectile;
import euclid.two.dim.model.Unit;
import euclid.two.dim.visitor.EtherialVisitor;
import euclid.two.dim.world.WorldState;

public class UpdateEngine extends Thread implements UpdateVisitor, EtherialVisitor
{
	private ArrayBlockingQueue<WorldState> rendererQueue;
	private WorldState worldStateN;
	private long now, then, timeStep;
	private InputManager inputManager;
	private boolean stopRequested;
	private ArrayList<Agent> agents;
	
	public UpdateEngine(ArrayBlockingQueue<WorldState> rendererQueue, InputManager inputManager)
	{
		this.rendererQueue = rendererQueue;
		this.inputManager = inputManager;
		this.stopRequested = false;
		this.agents = new ArrayList<Agent>();
	}
	
	public void addAgent(Agent agent)
	{
		this.agents.add(agent);
	}
	
	public void setWorldState(WorldState worldState)
	{
		this.worldStateN = worldState;
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
			
			// Read through queue of input events and process them
			if (inputManager.hasUnprocessedEvents())
			{
				
				for (InputCommand inputCommand : inputManager.getInputCommands())
				{
					inputCommand.execute();
				}
			}
			
			worldStateN.update(timeStep / 2);
			
			//update(worldStateN, timeStep / 2);
			
			for (Etherial etherial : worldStateN.getEtherials())
			{
				etherial.accept(this);
			}
			
			worldStateN.purgeExpired();
			
			WorldState nPlusOne = worldStateN.deepCopy();
			
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
		worldState.update(timeStep);
		
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
	
	@Override
	public void visit(Unit gso)
	{
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
	
	@Override
	public void visit(Obstacle obstacle)
	{
		
	}
	
	@Override
	public void visit(Boid boid)
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
				
				for (InputCommand command : agent.getCommands())
				{
					command.execute();
				}
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
		EuVector targetLocation = worldStateN.getUnit(projectile.getTarget()).getPosition();
		
		EuVector dT = targetLocation.subtract(projectile.getLocation()).normalize().multipliedBy(2);
		projectile.setLocation(projectile.getLocation().add(dT));
		
		if (projectile.hasExpired(timeStep) || projectile.getLocation().subtract(targetLocation).getMagnitude() < 3)
		{
			worldStateN.registerAsExpired(projectile);
		}
	}
	
	@Override
	public void visit(Fish fish)
	{
		
	}
	
}
