package euclid.two.dim.ability.internal;

import java.util.UUID;

import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.ability.request.BasicAbilityRequest;
import euclid.two.dim.ability.request.LocationAbilityRequest;
import euclid.two.dim.ability.request.TargetedAbilityRequest;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.world.WorldState;

public class BuildUnitAbility extends Ability {

	@Override
	public void visit(BasicAbilityRequest basicAbilityRequest) {
		// Do nothing
	}

	@Override
	public void visit(TargetedAbilityRequest targetedAbilityRequest) {
		// Do nothing
	}

	@Override
	public void visit(LocationAbilityRequest locationAbilityRequest) {
		// Do nothing
	}

	@Override
	public AbilityRequest toRequest(UUID heroId, WorldState worldState, EuVector location) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void processRequest(AbilityRequest abilityRequest, WorldState worldState) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isValidRequest(AbilityRequest abilityRequest) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void closeRequest() {
		// TODO Auto-generated method stub

	}

	@Override
	public Ability deepCopy() {
		// TODO Auto-generated method stub
		return null;
	}

}
