package euclid.two.dim.ability.internal;

import java.util.UUID;

import euclid.two.dim.Player;
import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.ability.request.BasicAbilityRequest;
import euclid.two.dim.ability.request.BuildUnitAbilityRequest;
import euclid.two.dim.ability.request.LocationAbilityRequest;
import euclid.two.dim.ability.request.TargetedAbilityRequest;
import euclid.two.dim.model.CasterUnit;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Unit;
import euclid.two.dim.world.WorldState;

public abstract class BuildUnitAbility extends Ability {
	protected BuildUnitAbilityRequest request;
	protected int gasCost;
	protected int mineralCost;
	protected Unit unit;

	public BuildUnitAbility(Unit unit, int gasCost, int mineralCost) {
		this.unit = unit;

		this.gasCost = gasCost;
		this.mineralCost = mineralCost;
		this.abilityType = AbilityType.build;
	}

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
	public void visit(BuildUnitAbilityRequest request) {
		this.request = request;
	}

	@Override
	public AbilityRequest toRequest(UUID heroId, WorldState worldState, EuVector location) {
		BuildUnitAbilityRequest request = new BuildUnitAbilityRequest();
		return request;
	}

	@Override
	public boolean isValidRequest(AbilityRequest abilityRequest, WorldState worldState) {
		abilityRequest.accept(this);
		CasterUnit caster = worldState.getCaster(abilityRequest.getHeroId());

		if (caster == null || abilityRequest.getAbilityType() != this.abilityType) {
			return false;
		}
		Player player = worldState.getPlayer(worldState.getGso(abilityRequest.getHeroId()).getTeam());
		return ((player.getMinerals() >= mineralCost) && (player.getGas() >= gasCost));
	}

	@Override
	public void closeRequest() {
		this.request = null;
	}

	@Override
	public boolean isImediate() {
		return true;
	}
}
