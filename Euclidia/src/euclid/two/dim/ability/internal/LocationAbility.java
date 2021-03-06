package euclid.two.dim.ability.internal;

import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.ability.request.BasicAbilityRequest;
import euclid.two.dim.ability.request.BuildUnitAbilityRequest;
import euclid.two.dim.ability.request.LocationAbilityRequest;
import euclid.two.dim.ability.request.TargetedAbilityRequest;
import euclid.two.dim.world.WorldState;

public abstract class LocationAbility extends Ability {
	protected LocationAbilityRequest request;

	@Override
	public Ability deepCopy() {
		return null;
	}

	@Override
	public void visit(BasicAbilityRequest basicAbilityRequest) {
		// DO NOTHING
	}

	@Override
	public void visit(TargetedAbilityRequest targetedAbilityRequest) {
		// DO NOTHING
	}

	@Override
	public void visit(BuildUnitAbilityRequest targetedAbilityRequest) {
		// DO NOTHING
	}

	@Override
	public void visit(LocationAbilityRequest request) {
		this.request = request;
	}

	@Override
	public boolean isValidRequest(AbilityRequest abilityRequest, WorldState worldState) {
		abilityRequest.accept(this);
		return (this.abilityType == abilityRequest.getAbilityType() && request != null && this.isOffCooldown());
	}

	@Override
	public void closeRequest() {
		this.request = null;
	}

}
