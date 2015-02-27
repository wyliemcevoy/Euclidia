package euclid.two.dim.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import euclid.two.dim.Path;
import euclid.two.dim.Player;
import euclid.two.dim.ability.Ability;
import euclid.two.dim.attack.Attack;
import euclid.two.dim.attack.SwordAttack;
import euclid.two.dim.behavior.CombatBehavior;
import euclid.two.dim.behavior.SteeringBehavior;
import euclid.two.dim.combat.Health;
import euclid.two.dim.render.ZerglingRenderComponent;
import euclid.two.dim.updater.Updatable;
import euclid.two.dim.updater.UpdateVisitor;

public class Minion extends GameSpaceObject
{
	protected UUID enemyTarget;
	
	protected int actionIndex;
	protected Health health;
	protected Attack attack;
	protected int detectionRange;
	protected CombatBehavior combatBehavior;
	protected boolean hasTarget;
	protected ArrayList<Ability> abilities;
	
	public boolean hasTarget()
	{
		return hasTarget;
	}
	
	public void setHasTarget(boolean hasTarget)
	{
		this.hasTarget = hasTarget;
	}
	
	public Minion(Path path, EuVector position, Player player)
	{
		this.position = position;
		this.futurePosition = new EuVector(position);
		this.future = new EuVector(position);
		this.velocity = new EuVector(0, 0);
		this.steeringBehavior = SteeringBehavior.Flock;
		this.player = player;
		this.health = new Health(100);
		this.attack = new SwordAttack();
		this.path = path;
		initialize();
	}
	
	private void initialize()
	{
		this.abilities = new ArrayList<Ability>();
		this.radius = 8;
		this.isSelected = true;
		this.mass = 10;
		this.detectionRange = 50;
		this.combatBehavior = CombatBehavior.AttackIfInRange;
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
		this.abilities = new ArrayList<Ability>();
		
		for (Ability ability : copy.getAbilities())
		{
			abilities.add(ability.deepCopy());
		}
	}
	
	private List<Ability> getAbilities()
	{
		return abilities;
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
	public void acceptUpdateVisitor(UpdateVisitor updateVisitor)
	{
		updateVisitor.visit(this);
	}
	
	@Override
	public Updatable deepCopy()
	{
		return new Minion(this);
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
	
	public void addAbility(Ability ability)
	{
		this.abilities.add(ability);
	}
	
}
