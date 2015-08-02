package euclid.two.dim.updater;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import euclid.two.dim.VectorMath;
import euclid.two.dim.ability.internal.Ability;
import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.command.AbilityCommand;
import euclid.two.dim.command.AttackCommand;
import euclid.two.dim.command.Command;
import euclid.two.dim.command.CommandVisitor;
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
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.RoomPath;
import euclid.two.dim.model.Unit;
import euclid.two.dim.path.Path;
import euclid.two.dim.path.PathCalculator;
import euclid.two.dim.team.Agent;
import euclid.two.dim.team.Game;
import euclid.two.dim.team.Team;
import euclid.two.dim.visitor.EndStepManager;
import euclid.two.dim.visitor.EtherialVisitor;
import euclid.two.dim.visitor.PhysicsStep;
import euclid.two.dim.visitor.SteeringStep;
import euclid.two.dim.world.WorldState;

public class UpdateEngine implements UpdateVisitor, EtherialVisitor, CommandVisitor {
	private WorldState worldStateN;
	private long timeStep;
	private ArrayList<Agent> agents;
	private Game game;

	public UpdateEngine(Game game) {
		this.agents = new ArrayList<Agent>();
		this.game = game;
	}

	public void addAgent(Agent agent) {
		this.agents.add(agent);
	}

	public void setWorldState(WorldState worldState) {
		this.worldStateN = worldState;
	}

	private void processInput() {
		// Read through queue of commands from players and process them
		for (Command command : game.getCommands()) {
			command.accept(this);
		}
	}

	public WorldState update(long timeStep) {
		this.timeStep = timeStep;
		processInput();

		SteeringStep steeringStep = new SteeringStep(worldStateN, timeStep);
		steeringStep.runStep();

		PhysicsStep physicsStep = new PhysicsStep(worldStateN);
		physicsStep.runStep();

		List<GameSpaceObject> gsos = worldStateN.getGameSpaceObjects();
		for (GameSpaceObject gso : gsos) {
			gso.acceptUpdateVisitor(this);
		}

		for (Etherial etherial : worldStateN.getEtherials()) {
			etherial.accept(this);
		}

		EndStepManager endStep = new EndStepManager(worldStateN);
		endStep.endPhase();

		return this.worldStateN.deepCopy();
	}

	public WorldState getCurrentWorldState() {
		return worldStateN;
	}

	public double getZoom() {
		return this.getCurrentWorldState().getCamera().getZoom();
	}

	public void setZoom(double zoom) {
		this.getCurrentWorldState().getCamera().setZoom(zoom);
	}

	public void visit(Unit unit) {
		unit.getAttack().update(timeStep);

		Unit target = worldStateN.getUnit(unit.getTarget());

		// Check to see if the units target still is alive / exists
		if (target == null || unit.getPosition() == null || target.getPosition() == null) {
			// pickNewTarget(unit);

			return;
		}

		double distSqrd = VectorMath.getDistanceSquared(target.getPosition(), unit.getPosition());
		double rangeSqrd = unit.getAttack().getRange() * unit.getAttack().getRange();

		// Check and see if the unit is capable of attacking (basic attack off
		// cooldown)
		if (unit.getAttack().isReloaded()) {
			if (distSqrd <= rangeSqrd) {
				// Unit is alive and within range
				target.getHealth().add(-1 * unit.getAttack().getDamage());
				unit.getAttack().attack();

				worldStateN.addEtherial(new Slash(target.getPosition(), unit.getPosition()));
			}
		}

		if (distSqrd > rangeSqrd) {
			RoomPath roomPath = PathCalculator.calculateRoomPath(worldStateN, unit.getPosition(), target.getPosition());
			unit.setPath(roomPath.toPath());
		}

	}

	@Override
	public void visit(Minion unit) {
		visit((Unit) unit);
	}

	private void pickNewTarget(Minion unit) {
		for (GameSpaceObject gso : worldStateN.getGameSpaceObjects()) {
			if ((gso instanceof Minion) && gso != unit) {
				Minion minion = (Minion) gso;
				if (unit.getTeam() != Team.Blue) {
					unit.setTarget(minion.getId());
				}
			}
		}

	}

	@Override
	public void visit(Obstacle obstacle) {
		// Do nothing
	}

	public void randomCommand() {
		Random rand = new Random();
		if (rand.nextDouble() > .99) {

			for (Agent agent : agents) {
				/*
				 * for (InputCommand command : agent.getCommands()) {
				 * command.execute(); }
				 */
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

	private void purgeExpired() {
		Iterator<GameSpaceObject> it = worldStateN.getGsos().iterator();
		DeathVisitor deathVisitor = new DeathVisitor();

		while (it.hasNext()) {
			GameSpaceObject gso = it.next();

			gso.acceptUpdateVisitor(deathVisitor);
			if (deathVisitor.isDead()) {
				it.remove();
				worldStateN.addEtherial(new ZergDeath(gso.getPosition(), (int) gso.getRadius()));

			}
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

			if (unit != null) {
				unit.setPath(new Path(new EuVector(moveCommand.getLocation())));
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
				if (unit != null && unit.getTeam() != target.getTeam()) {
					unit.setTarget(attackCommand.getTargetId());
				}
			}
		}
	}

	@Override
	public void visit(AbilityCommand abilityCommand) {

		AbilityRequest abilityRequest = abilityCommand.getAbilityRequest();

		Hero hero = worldStateN.getHero(abilityRequest.getHeroId());

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
}
