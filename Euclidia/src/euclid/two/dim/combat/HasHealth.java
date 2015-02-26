package euclid.two.dim.combat;

public interface HasHealth
{
	public void setCurrentHealth(int currentHealth);
	
	public int getCurrentHealth();
	
	public int getMaxHealth();
	
	public void setMaxHealth(int maxHealth);
	
	public void acceptDamage(int damage);
	
	public void acceptHeal(int heal);
	
	public double getCurrenHealthPercentage();
	
	public boolean isDead();
}
