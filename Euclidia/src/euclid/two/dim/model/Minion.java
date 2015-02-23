package euclid.two.dim.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.UUID;

import eucild.two.dim.combat.Health;
import euclid.two.dim.Path;
import euclid.two.dim.Player;
import euclid.two.dim.attack.Attack;
import euclid.two.dim.attack.SwordAttack;
import euclid.two.dim.behavior.Flock;
import euclid.two.dim.render.Renderable;
import euclid.two.dim.render.UnitRender;
import euclid.two.dim.render.ZerglingRenderComponent;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.world.WorldState;

public class Minion extends GameSpaceObject
{
	protected WorldState worldState;
	protected UUID enemyTarget;
	protected Player player;
	protected int actionIndex;
	protected Health health;
	protected Attack attack;
	
	public Minion(WorldState worldState, Path path, EuVector position, Player player)
	{
		this.position = position;
		this.futurePosition = new EuVector(position);
		this.future = new EuVector(position);
		this.velocity = new EuVector(0, 0);
		this.worldState = worldState;
		this.sb = new Flock(worldState, path, this);
		this.player = player;
		this.health = new Health(100);
		this.attack = new SwordAttack();
		initialize();
	}
	
	private void initialize()
	{
		this.radius = 8;
		this.isSelected = true;
		this.mass = 10;
		this.renderComponent = new ZerglingRenderComponent();
	}
	
	public Color getColor()
	{
		return player.getColor();
	}
	
	public Minion(Minion copy)
	{
		super(copy);
		
		this.enemyTarget = copy.getTarget();
		this.player = copy.getPlayer();
		this.actionIndex = copy.getActionIndex();
		this.health = copy.getHealth().deepCopy();
		this.attack = copy.getAttack().deepCopy();
	}
	
	public Attack getAttack()
	{
		return attack;
	}
	
	public Health getHealth()
	{
		return this.health;
	}
	
	public int getActionIndex()
	{
		return actionIndex;
	}
	
	public UUID getTarget()
	{
		// TODO Auto-generated method stub
		return enemyTarget;
	}
	
	@Override
	public void separate()
	{
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
	}
	
	@Override
	public void setPath(Path path)
	{
		this.sb.setPath(path);
	}
	
	@Override
	public void separate2()
	{
		
	}
	
	@Override
	public void specificUpdate(EuVector displacement)
	{
		// TODO Auto-generated method stub
	}
	
	@Override
	protected void specificConstructor(GameSpaceObject gso)
	{
		this.player = ((Minion) gso).getPlayer();// TODO Auto-generated method stub
		
	}
	
	public Player getPlayer()
	{
		return this.player;
	}
	
	@Override
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor)
	{
		updateVisitor.visit(this);
	}
	
	@Override
	public Updatable deepCopy()
	{
		return new Minion(this);
	}
	
	@Override
	public Renderable toRenderable()
	{
		return new UnitRender(this);
	}
	
	public void setTarget(UUID uuid)
	{
		this.enemyTarget = uuid;
	}
	
	@Override
	public boolean hasExpired()
	{
		return health.isDead();
	}
}
