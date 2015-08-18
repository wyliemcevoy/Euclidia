package euclid.two.dim.ability.internal;

import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.ability.request.BasicAbilityRequest;
import euclid.two.dim.ability.request.BuildUnitAbilityRequest;
import euclid.two.dim.ability.request.LocationAbilityRequest;
import euclid.two.dim.ability.request.TargetedAbilityRequest;
import euclid.two.dim.world.WorldState;

public abstract class BasicAbility extends Ability {
	protected BasicAbilityRequest request;

	public abstract Ability deepCopy();

	@Override
	public void visit(BasicAbilityRequest request) {
		this.request = request;
	}

	@Override
	public void visit(TargetedAbilityRequest request) {
	}

	@Override
	public void visit(LocationAbilityRequest request) {

	}

	@Override
	public void visit(BuildUnitAbilityRequest request) {
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
