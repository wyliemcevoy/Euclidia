package euclid.two.dim.model;

import java.util.UUID;

import euclid.two.dim.Path;
import euclid.two.dim.attack.Attack;
import euclid.two.dim.attack.SwordAttack;
import euclid.two.dim.behavior.CombatBehavior;
import euclid.two.dim.behavior.SteeringBehavior;
import euclid.two.dim.combat.Health;
import euclid.two.dim.render.ZerglingRenderComponent;
import euclid.two.dim.team.Team;

public abstract class Unit extends GameSpaceObject
{
	protected UUID enemyTarget;
	
	protected int actionIndex;
	protected Health health;
	protected Attack attack;
	protected int detectionRange;
	protected CombatBehavior combatBehavior;
	protected boolean hasTarget;
	
	public boolean hasTarget()
	{
		return hasTarget;
	}
	
	public void setHasTarget(boolean hasTarget)
	{
		this.hasTarget = hasTarget;
	}
	
	public Unit(Team team, EuVector position)
	{
		this.position = position;
		this.futurePosition = new EuVector(position);
		this.future = new EuVector(position);
		this.velocity = new EuVector(0, 0);
		this.steeringBehavior = SteeringBehavior.Flock;
		this.team = team;
		this.health = new Health(100);
		this.attack = new SwordAttack();
		this.path = new Path(new EuVector(position));
		initialize();
	}
	
	private void initialize()
	{
		this.radius = 8;
		this.isSelected = true;
		this.mass = 10;
		this.detectionRange = 50;
		this.combatBehavior = CombatBehavior.AttackIfInRange;
		this.renderComponent = new ZerglingRenderComponent();
	}
	
	public Unit(Unit copy)
	{
		super(copy);
		
		this.enemyTarget = copy.getTarget();
		this.team = copy.getTeam();
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
	
	public void setTarget(UUID uuid)
	{
		this.enemyTarget = uuid;
	}
	
	@Override
	public boolean hasExpired()
	{
		return health.isDead();
	}
	
	public int getDetectionRange()
	{
		return detectionRange;
	}
	
	public void setDetectionRange(int detectionRange)
	{
		this.detectionRange = detectionRange;
	}
	
	public CombatBehavior getCombatBehavior()
	{
		return combatBehavior;
	}
	
	public void setCombatBehavior(CombatBehavior combatBehavior)
	{
		this.combatBehavior = combatBehavior;
	}
	
}
