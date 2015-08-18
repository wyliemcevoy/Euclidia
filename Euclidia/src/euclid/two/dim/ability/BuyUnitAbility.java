package euclid.two.dim.ability;

import java.util.Random;
import java.util.UUID;

import euclid.two.dim.Player;
import euclid.two.dim.ability.internal.Ability;
import euclid.two.dim.ability.internal.AbilityType;
import euclid.two.dim.ability.internal.BuildUnitAbility;
import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.ability.request.BuildUnitAbilityRequest;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Unit;
import euclid.two.dim.path.Path;
import euclid.two.dim.team.Team;
import euclid.two.dim.world.WorldState;

public class BuyUnitAbility extends BuildUnitAbility {

	public BuyUnitAbility(Unit unit, int gasCost, int mineralCost) {
		super(unit, gasCost, mineralCost);
	}

	public BuyUnitAbility(BuyUnitAbility clone) {
		super(clone.getUnit(), clone.getGasCost(), clone.getMineralCost());
	}

	private int getGasCost() {
		return gasCost;
	}

	private Unit getUnit() {
		return (Unit) unit.deepCopy();
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
			// Unit clone = (Unit) unit.deepCopy();
			Hero clone = new Hero(team, position.deepCopy());
			clone.setTeam(team);
			clone.setPosition(position);
			clone.setFuturePosition(position);
			clone.setPath(new Path(new EuVector(0, 0)));
			System.out.println(team + " adding uint " + clone);
			worldState.addObject(clone);
		}
		closeRequest();
	}

	@Override
	public AbilityRequest toRequest(UUID heroId, WorldState worldState, EuVector location) {

		BuildUnitAbilityRequest request = new BuildUnitAbilityRequest();
		request.setHeroId(heroId);
		request.setAbilityType(AbilityType.build);
		return request;
	}

	@Override
	public Ability deepCopy() {
		return new BuyUnitAbility(this);
	}

}
