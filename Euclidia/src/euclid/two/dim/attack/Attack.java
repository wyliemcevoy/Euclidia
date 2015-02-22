package euclid.two.dim.attack;

public abstract class Attack
{
	protected int range;
	protected int damage;
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
	
	public abstract Attack deepCopy();
}
