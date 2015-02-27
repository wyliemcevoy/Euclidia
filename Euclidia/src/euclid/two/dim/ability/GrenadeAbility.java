package euclid.two.dim.ability;

public class GrenadeAbility extends Ability
{
	protected int range;
	protected int damage;
	protected int radius;
	protected long reloadTime;
	protected long currentTime;
	
	public int getRange()
	{
		return range;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	protected long getCurrentTime()
	{
		return currentTime;
	}
	
	public long getReloadTime()
	{
		return reloadTime;
	}
	
	public void update(long timeStep)
	{
		if (!isReloaded())
		{
			currentTime = currentTime + timeStep;
		}
	}
	
	public boolean isReloaded()
	{
		return currentTime > reloadTime;
	}
	
	public void attack()
	{
		this.currentTime = 0;
	}
	
	public GrenadeAbility(GrenadeAbility grenadeAbility)
	{
		initialize();
		this.currentTime = grenadeAbility.getCurrentTime();
	}
	
	public GrenadeAbility()
	{
		initialize();
	}
	
	private void initialize()
	{
		this.damage = 100;
		this.radius = 40;
		this.reloadTime = 1000;
		this.currentTime = 1001;
		this.range = 100;
	}
	
	@Override
	public Ability deepCopy()
	{
		return new GrenadeAbility(this);
	}
	
}
