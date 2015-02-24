package eucild.two.dim.combat;

public class Health
{
	private int maxHealth;
	private int currentHealth;
	
	public Health(int maxHealth)
	{
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
	}
	
	public Health(Health copy)
	{
		this.currentHealth = copy.getCurrentHealth();
		this.maxHealth = copy.getMaxHealth();
	}
	
	private int getCurrentHealth()
	{
		return currentHealth;
	}
	
	public void add(int modifier)
	{
		this.currentHealth = currentHealth + modifier;
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
	
	public Health deepCopy()
	{
		return new Health(this);
	}
	
	public void setMaxHealth(int maxHealth)
	{
		this.maxHealth = maxHealth;
		this.currentHealth = maxHealth;
	}
	
}
