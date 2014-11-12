package euclid.two.dim.model;

import euclid.two.dim.Player;
import euclid.two.dim.behavior.SteeringBehavior;

public class Tank
{
	private EuVector location;
	private SteeringBehavior steeringBehavior;
	private static final double weaponCooldown = 1.5;
	private long lastShotTime;
	private Tank targetTank;
	private Player player;

	public Tank(EuVector location)
	{
		this.location = location;
		this.targetTank = null;

	}

	private boolean isReloaded()
	{
		return true;
	}

}
