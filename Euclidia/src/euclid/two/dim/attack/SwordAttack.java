package euclid.two.dim.attack;

public class SwordAttack extends Attack
{
	
	public SwordAttack()
	{
		initialize();
	}
	
	public SwordAttack(SwordAttack swordAttack)
	{
		initialize();
		this.currentTime = swordAttack.getCurrentTime();
	}
	
	private void initialize()
	{
		this.damage = 40;
		this.reloadTime = 1000;
		this.currentTime = 1001;
		this.range = 20;
	}
	
	@Override
	public Attack deepCopy()
	{
		return new SwordAttack(this);
	}
	
}
