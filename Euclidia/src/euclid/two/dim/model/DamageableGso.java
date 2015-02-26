package euclid.two.dim.model;

public abstract class DamageableGso extends GameSpaceObject
{
	private int maxHealth;
	private int currentHealth;
	
	public DamageableGso()
	{
		super();
		this.maxHealth = 100;
		this.currentHealth = 100;
	}
	
	public DamageableGso(DamageableGso copy)
	{
		super(copy);
		this.currentHealth = copy.getCurrentHealth();
		this.maxHealth = copy.getMaxHealth();
	}
	
	private int getCurrentHealth()
	{
		return currentHealth;
	}
	
	public void acceptDamage(int damage)
	{
		this.currentHealth -= damage;
	}
	
	public void acceptHeal(int heal)
	{
		this.currentHealth = Math.min(maxHealth, currentHealth + heal);
	}
	
	public int getMaxHealth()
	{
		return maxHealth;
	}
	
	public double getHealthPercentage()
	{
		return (1.0 * currentHealth) / maxHealth;
	}
	
	public boolean isDead()
	{
		return currentHealth <= 0;
	}
	
	public void setMaxHealth(int maxHealth)
	{
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
	}
	
}
