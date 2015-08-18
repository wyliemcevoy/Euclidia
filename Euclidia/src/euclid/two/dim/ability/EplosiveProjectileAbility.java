package euclid.two.dim.ability;

import java.util.UUID;

import euclid.two.dim.ability.internal.Ability;
import euclid.two.dim.ability.internal.AbilityType;
import euclid.two.dim.ability.internal.LocationAbility;
import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.ability.request.LocationAbilityRequest;
import euclid.two.dim.etherial.ExplosiveProjectile;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.world.WorldState;

public class EplosiveProjectileAbility extends LocationAbility {
	protected int range;
	protected int damage;
	protected int radius;

	public int getRange() {
		return range;
	}

	public int getDamage() {
		return damage;
	}

	public void attack() {
		this.currentTime = 0;
	}

	public EplosiveProjectileAbility(EplosiveProjectileAbility grenadeAbility) {
		initialize();
		this.currentTime = grenadeAbility.getCurrentTime();
	}

	public EplosiveProjectileAbility() {
		initialize();
	}

	private void initialize() {
		this.damage = 50;
		this.radius = 10;
		this.reloadTime = 15000;
		this.currentTime = reloadTime + 1;
		this.range = 100;
		this.abilityType = AbilityType.grenade;
	}

	@Override
	public Ability deepCopy() {
		return new EplosiveProjectileAbility(this);
	}

	@Override
	public void processRequest(AbilityRequest abilityRequest, WorldState worldState) {
		// Sanity check to prevent a client sending invalid requests
		if (isValidRequest(abilityRequest, worldState)) {
			worldState.addEtherial(new ExplosiveProjectile(request.getLocation(), worldState.getUnit(abilityRequest.getHeroId())));
			closeRequest();
		}
	}

	@Override
	public AbilityRequest toRequest(UUID heroId, WorldState worldState, EuVector location) {
		LocationAbilityRequest request = new LocationAbilityRequest();
		request.setHeroId(heroId);
		request.setAbilityType(AbilityType.grenade);
		request.setLocation(location);
		return request;
	}

	@Override
	public boolean isImediate() {
		return false;
	}

}
