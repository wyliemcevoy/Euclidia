package euclid.two.dim.visitor;

import java.util.ArrayList;

import euclid.two.dim.etherial.Etherial;
import euclid.two.dim.etherial.Explosion;
import euclid.two.dim.etherial.Projectile;
import euclid.two.dim.etherial.Slash;
import euclid.two.dim.etherial.ZergDeath;
import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Fish;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.Minion;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.world.WorldState;

public class EndStepManager implements UpdateVisitor, EtherialVisitor
{
	
	private WorldState worldState;
	private ArrayList<GameSpaceObject> dead;
	private ArrayList<Etherial> expired;
	
	public EndStepManager(WorldState worldState)
	{
		this.worldState = worldState;
		this.dead = new ArrayList<GameSpaceObject>();
		this.expired = new ArrayList<Etherial>();
	}
	
	public void endPhase()
	{
		// Utilize visitor pattern to have specific behavior on expire/death of game objects
		
		// Collect all dead gsos in dead list
		for (GameSpaceObject gso : worldState.getGsos())
		{
			gso.acceptUpdateVisitor(this);
		}
		
		// Collect all expired ethereals in expired list
		for (Etherial etherial : worldState.getEtherials())
		{
			etherial.accept(this);
		}
		
		worldState.getGsos().removeAll(dead);
		worldState.getEtherials().removeAll(expired);
		
	}
	
	@Override
	public void visit(Projectile projectile)
	{
		if (projectile.hasExpired())
		{
			expired.add(projectile);
		}
	}
	
	@Override
	public void visit(Explosion explosion)
	{
		if (explosion.hasExpired())
		{
			expired.add(explosion);
		}
	}
	
	@Override
	public void visit(Slash slash)
	{
		if (slash.hasExpired())
		{
			expired.add(slash);
		}
	}
	
	@Override
	public void visit(ZergDeath zergDeath)
	{
		if (zergDeath.hasExpired())
		{
			expired.add(zergDeath);
		}
	}
	
	@Override
	public void visit(Minion unit)
	{
		if (unit.hasExpired())
		{
			dead.add(unit);
			worldState.addEtherial(new ZergDeath(unit.getPosition(), (int) unit.getRadius()));
		}
		
	}
	
	@Override
	public void visit(Fish fish)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visit(Boid boid)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visit(Obstacle obstacle)
	{
		// TODO Auto-generated method stub
		
	}
	
}
