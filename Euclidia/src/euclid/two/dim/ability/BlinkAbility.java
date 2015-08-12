package euclid.two.dim.ability;

import java.util.UUID;

import euclid.two.dim.ability.internal.Ability;
import euclid.two.dim.ability.internal.AbilityType;
import euclid.two.dim.ability.internal.LocationAbility;
import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.ability.request.LocationAbilityRequest;
import euclid.two.dim.etherial.Explosion;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Hero;
import euclid.two.dim.world.WorldState;

public class BlinkAbility extends LocationAbility {
	protected int radius;

	public BlinkAbility(BlinkAbility copy) {
		initialize();
	}

	public BlinkAbility() {
		initialize();
	}

	private void initialize() {

		this.radius = 40;
		this.reloadTime = 1000;
		this.currentTime = reloadTime + 1;
		this.abilityType = AbilityType.blink;

	}

	@Override
	public void processRequest(AbilityRequest abilityRequest, WorldState worldState) {
		// Sanity check to prevent a client sending invalid requests
		if (isValidRequest(abilityRequest)) {
			EuVector destination = request.getLocation();
			Hero hero = worldState.getHero(request.getHeroId());
			worldState.addEtherial(new Explosion(hero.getPosition()));

			hero.setPosition(destination.deepCopy());
			hero.setFuturePosition(destination.deepCopy());
			worldState.addEtherial(new Explosion(hero.getPosition()));
			closeRequest();
		}

	}

	@Override
	public AbilityRequest toRequest(UUID heroId, WorldState worldState, EuVector location) {
		LocationAbilityRequest request = new LocationAbilityRequest();
		request.setHeroId(heroId);
		request.setAbilityType(AbilityType.blink);
		request.setLocation(location);
		return request;
	}

	@Override
	public Ability deepCopy() {
		return new BlinkAbility(this);
	}

}
