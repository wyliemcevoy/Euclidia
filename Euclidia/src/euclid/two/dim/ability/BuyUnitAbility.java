package euclid.two.dim.ability;

import java.util.Random;
import java.util.UUID;

import euclid.two.dim.Player;
import euclid.two.dim.ability.internal.Ability;
import euclid.two.dim.ability.internal.BuildUnitAbility;
import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.ability.request.BuildUnitAbilityRequest;
import euclid.two.dim.factory.UnitFactory;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Unit;
import euclid.two.dim.team.Team;
import euclid.two.dim.world.WorldState;

public class BuyUnitAbility extends BuildUnitAbility {

	public BuyUnitAbility(UnitFactory unitFactory, int gasCost, int mineralCost) {
		super(unitFactory, gasCost, mineralCost);
	}

	public BuyUnitAbility(BuyUnitAbility clone) {
		super(clone.getUnitFactory(), clone.getGasCost(), clone.getMineralCost());
	}

	private int getGasCost() {
		return gasCost;
	}

	private int getMineralCost() {
		return mineralCost;
	}

	@Override
	public void processRequest(AbilityRequest abilityRequest, WorldState worldState) {
		if (isValidRequest(abilityRequest, worldState)) {

			Unit caster = worldState.getUnit(request.getHeroId());
			EuVector casterLocation = caster.getPosition();
			Team team = worldState.getUnit(request.getHeroId()).getTeam();
			Random rand = new Random();

			Player player = worldState.getPlayer(team);
			player.setGas(player.getGas() - gasCost);
			player.setMinerals(player.getMinerals() - mineralCost);

			EuVector position = new EuVector(casterLocation.getX() + rand.nextInt(100), casterLocation.getY() + rand.nextInt(100));

			worldState.addObject(unitFactory.build(team, position));

		}
		closeRequest();
	}

	@Override
	public AbilityRequest toRequest(UUID heroId, WorldState worldState, EuVector location) {

		BuildUnitAbilityRequest request = new BuildUnitAbilityRequest();
		request.setHeroId(heroId);
		request.setAbilityType(unitFactory.getAbilityType());
		return request;
	}

	@Override
	public Ability deepCopy() {
		return new BuyUnitAbility(this);
	}

}
