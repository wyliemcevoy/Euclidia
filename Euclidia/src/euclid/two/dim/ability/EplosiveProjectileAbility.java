package euclid.two.dim.ability;

import euclid.two.dim.ability.internal.Ability;
import euclid.two.dim.ability.internal.LocationAbility;
import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.etherial.Explosion;
import euclid.two.dim.world.WorldState;

public class EplosiveProjectileAbility extends LocationAbility
{
	protected int range;
	protected int damage;
	protected int radius;
	
	public int getRange()
	{
		return range;
	}
	
	public int getDamage()
	{
		return damage;
	}
	
	public void attack()
	{
		this.currentTime = 0;
	}
	
	public EplosiveProjectileAbility(EplosiveProjectileAbility grenadeAbility)
	{
		initialize();
		this.currentTime = grenadeAbility.getCurrentTime();
	}
	
	public EplosiveProjectileAbility()
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
		return new EplosiveProjectileAbility(this);
	}
	
	@Override
	public void processRequest(AbilityRequest abilityRequest, WorldState worldState)
	{
		// Sanity check to prevent a client sending invalid requests
		if (isValidRequest(abilityRequest))
		{
			
			worldState.addEtherial(new Explosion(request.getLocation()));
			
			closeRequest();
		}
	}
	
}
