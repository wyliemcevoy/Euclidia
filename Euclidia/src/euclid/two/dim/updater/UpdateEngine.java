package euclid.two.dim.updater;

import java.util.ArrayList;
import java.util.UUID;

import euclid.two.dim.VectorMath;
import euclid.two.dim.ability.internal.Ability;
import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.behavior.CombatBehavior;
import euclid.two.dim.command.AbilityCommand;
import euclid.two.dim.command.AttackCommand;
import euclid.two.dim.command.Command;
import euclid.two.dim.command.CommandVisitor;
import euclid.two.dim.command.GatherCommand;
import euclid.two.dim.command.MoveCommand;
import euclid.two.dim.command.UseLocationAbilityCommand;
import euclid.two.dim.command.UseTargetedAbilityCommand;
import euclid.two.dim.etherial.CircleGraphic;
import euclid.two.dim.etherial.Etherial;
import euclid.two.dim.etherial.Explosion;
import euclid.two.dim.etherial.ExplosiveProjectile;
import euclid.two.dim.etherial.Projectile;
import euclid.two.dim.etherial.Slash;
import euclid.two.dim.etherial.ZergDeath;
import euclid.two.dim.model.Building;
import euclid.two.dim.model.CasterUnit;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.ResourcePatch;
import euclid.two.dim.model.Unit;
import euclid.two.dim.model.Worker;
import euclid.two.dim.path.Path;
import euclid.two.dim.path.PathCalculator;
import euclid.two.dim.visitor.EndStep;
import euclid.two.dim.visitor.EtherialVisitor;
import euclid.two.dim.visitor.PhysicsStep;
import euclid.two.dim.visitor.SingleTypeSelection;
import euclid.two.dim.visitor.SteeringStep;
import euclid.two.dim.visitor.UpdateStep;
import euclid.two.dim.world.WorldState;

public class UpdateEngine implements UpdateVisitor, EtherialVisitor, CommandVisitor {
	private WorldState worldStateN;
	private long timeStep;

	private ArrayList<UpdateStep> updateSteps;

	public UpdateEngine(WorldState worldState) {
		this.worldStateN = worldState;
		initializeSteps();
	}

	private void initializeSteps() {
		this.updateSteps = new ArrayList<UpdateStep>();
		this.updateSteps.add(new SteeringStep());
		this.updateSteps.add(new PhysicsStep());
		this.updateSteps.add(new EndStep());
	}

	public void setWorldState(WorldState worldState) {
		this.worldStateN = worldState;
	}

	public WorldState update(long timeStep, ArrayList<Command> commands) {
		this.timeStep = timeStep;

		for (Command command : commands) {
			command.accept(this);
		}

		for (UpdateStep updateStep : updateSteps) {
			updateStep.runStep(worldStateN, timeStep);
		}

		ArrayList<GameSpaceObject> gsos = worldStateN.getGsos();
		for (GameSpaceObject gso : gsos) {
			gso.acceptUpdateVisitor(this);
		}

		for (Etherial etherial : worldStateN.getEtherials()) {
			etherial.accept(this);
		}

		return this.worldStateN.deepCopy();
	}

	public WorldState getCurrentWorldState() {
		return worldStateN;
	}

	public void visit(Unit unit) {
		unit.getAttack().update(timeStep);

		Unit target = worldStateN.getUnit(unit.getTarget());
		if (unit.getCombatBehavior() == CombatBehavior.AttackIfInRange) {

			// Check to see if the units target still is alive / exists
			if (target == null || unit.getPosition() == null || target.getPosition() == null) {

				GameSpaceObject nextTarget = worldStateN.getClosestUnfriendly(unit);

				if (nextTarget != null) {
					unit.setTarget(nextTarget.getId());
				}
				else {
					return;
				}
			}
		}

		if (target == null) {
			return;
		}

		double distSqrd = VectorMath.getDistanceSquared(target.getPosition(), unit.getPosition());
		double rangeSqrd = unit.getAttack().getRange() * unit.getAttack().getRange();

		// Check and see if the unit is capable of attacking (basic attack off cool-down)
		if (unit.getAttack().isReloaded()) {
			if (distSqrd <= rangeSqrd) {
				// Unit is alive and within range
				target.getHealth().add(-1 * unit.getAttack().getDamage());
				unit.getAttack().attack();

				worldStateN.addEtherial(new Slash(target.getPosition(), unit.getPosition()));
			}
		}

		if (distSqrd > rangeSqrd) {
			Path path = PathCalculator.calculatePath(worldStateN, unit.getPosition(), target.getPosition());
			unit.setPath(path);
		}

	}

	@Override
	public void visit(Minion unit) {
		visit((Unit) unit);
	}

	@Override
	public void visit(ResourcePatch resourcePatch) {

		if (resourcePatch.getCurrentlyGathering() != null) {
			GameSpaceObject gso = worldStateN.getGso(resourcePatch.getCurrentlyGathering());
			if (gso != null) {
				Worker worker = (new SingleTypeSelection(gso)).getWorker();
				if (worker == null || worker.getPosition().subtract(resourcePatch.getPosition()).getMagnitudeSquared() > 1000 || !worker.isCollecting()) {
					resourcePatch.freePatch();
				}
			}
			else {
				resourcePatch.freePatch();
			}
		}
	}

	@Override
	public void visit(Explosion explosion) {
		explosion.update(timeStep);
		if (explosion.hasExpired()) {
			worldStateN.registerAsExpired(explosion);
		}
	}

	@Override
	public void visit(Projectile projectile) {
		projectile.update(timeStep);

		Unit target = worldStateN.getUnit(projectile.getTarget());

		if (projectile.hasExpired() || target == null) {
			projectile.setAsExpired();
		}
		else {
			EuVector targetLocation = target.getPosition();

			EuVector dT = targetLocation.subtract(projectile.getLocation()).normalize().multipliedBy(2);
			projectile.setLocation(projectile.getLocation().add(dT));

			if (projectile.getLocation().subtract(targetLocation).getMagnitude() < 3) {
				target.getHealth().add(-projectile.getDamage());
				projectile.setAsExpired();
			}
		}
	}

	@Override
	public void visit(Slash slash) {
		slash.update(timeStep);
		if (slash.hasExpired()) {
			worldStateN.registerAsExpired(slash);
		}
	}

	@Override
	public void visit(ZergDeath zergDeath) {
		zergDeath.update(timeStep);
		if (zergDeath.hasExpired()) {
			worldStateN.registerAsExpired(zergDeath);
		}
	}

	@Override
	public void visit(Hero hero) {

		visit((Unit) hero);

	}

	@Override
	public void visit(MoveCommand moveCommand) {

		for (UUID id : moveCommand.getIds()) {
			Unit unit = worldStateN.getUnit(id);
			if (unit != null && moveCommand.getLocation() != null) {
				if (moveCommand.isAttackWhileMoving()) {
					unit.setCombatBehavior(CombatBehavior.AttackIfInRange);
				}
				else {
					unit.setCombatBehavior(CombatBehavior.NoAttack);
					unit.setTarget(null);
				}
				unit.setPath(PathCalculator.calculatePath(worldStateN, unit.getPosition(), moveCommand.getLocation()));
			}
		}
	}

	@Override
	public void visit(UseLocationAbilityCommand command) {
		command.getHeroId();
		command.getAbilityIndex();

		worldStateN.addEtherial(new Explosion(command.getLocation()));

	}

	@Override
	public void visit(UseTargetedAbilityCommand useTargetedAbilityCommand) {

	}

	@Override
	public void visit(AttackCommand attackCommand) {
		if (attackCommand.getIds().size() > 0) {
			for (UUID id : attackCommand.getIds()) {
				Unit unit = worldStateN.getUnit(id);
				Unit target = worldStateN.getUnit(attackCommand.getTargetId());
				if (unit != null && target != null && unit.getTeam() != target.getTeam()) {
					unit.setTarget(attackCommand.getTargetId());
				}
			}
		}
	}

	@Override
	public void visit(AbilityCommand abilityCommand) {

		AbilityRequest abilityRequest = abilityCommand.getAbilityRequest();

		CasterUnit hero = worldStateN.getCaster(abilityRequest.getHeroId());

		// Verify hero is still in existence
		if (hero != null) {
			Ability ability = hero.getAbility(abilityRequest.getAbilityType());

			// Verify ability is valid
			if (ability != null) {
				ability.processRequest(abilityRequest, worldStateN);
			}
		}
	}

	@Override
	public void visit(ExplosiveProjectile projectile) {
		projectile.update(timeStep);

		if (projectile.hasExpired()) {
			projectile.setAsExpired();
		}
		else {
			EuVector targetLocation = projectile.getDestination();

			EuVector dT = targetLocation.subtract(projectile.getLocation()).normalize().multipliedBy(2);
			projectile.setLocation(projectile.getLocation().add(dT));

			if (projectile.getLocation().subtract(targetLocation).getMagnitude() < 3) {
				for (Unit unit : worldStateN.getUnitsInRange(projectile.getLocation(), projectile.getExplosionRadius())) {
					unit.getHealth().add(-60);
				}

				projectile.setAsExpired();
			}
		}
	}

	@Override
	public void visit(CircleGraphic circleGraphic) {
		circleGraphic.update(timeStep);
	}

	@Override
	public void visit(Building building) {
	}

	@Override
	public void visit(Worker worker) {
		visit((Unit) worker);

		worker.update(worldStateN);

	}

	@Override
	public void visit(GatherCommand gatherCommand) {

		GameSpaceObject resource = worldStateN.getGso(gatherCommand.getTargetResource());
		if (resource != null) {
			// Deal with gathering
			for (UUID id : gatherCommand.getWorkers()) {
				Worker worker = (new SingleTypeSelection(worldStateN.getUnit(id))).getWorker();

				// worker could have died between command request and execution
				if (worker != null) {
					worker.setResourcePatch(gatherCommand.getTargetResource());
					worker.setPath(PathCalculator.calculatePath(worldStateN, worker.getPosition(), resource.getPosition()));
					worker.gather(gatherCommand.getTargetResource());
				}
			}
		}
	}
}
