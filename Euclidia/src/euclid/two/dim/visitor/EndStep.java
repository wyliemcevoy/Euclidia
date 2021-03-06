package euclid.two.dim.visitor;

import java.util.ArrayList;

import euclid.two.dim.etherial.CircleGraphic;
import euclid.two.dim.etherial.Etherial;
import euclid.two.dim.etherial.Explosion;
import euclid.two.dim.etherial.ExplosiveProjectile;
import euclid.two.dim.etherial.Projectile;
import euclid.two.dim.etherial.Slash;
import euclid.two.dim.etherial.ZergDeath;
import euclid.two.dim.model.Building;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.ResourcePatch;
import euclid.two.dim.model.Worker;
import euclid.two.dim.world.WorldState;

public class EndStep extends UpdateStep implements EtherialVisitor {

	private WorldState worldState;
	private ArrayList<GameSpaceObject> dead;
	private ArrayList<Etherial> expired;
	private ArrayList<Etherial> created;

	public EndStep() {
		this.dead = new ArrayList<GameSpaceObject>();
		this.expired = new ArrayList<Etherial>();
		this.created = new ArrayList<Etherial>();
	}

	public void endPhase() {
		// Utilize visitor pattern to have specific behavior on expire/death of
		// game objects

		// Collect all dead gsos in dead list
		for (GameSpaceObject gso : worldState.getGsos()) {
			gso.acceptUpdateVisitor(this);
		}

		// Collect all expired ethereals in expired list
		for (Etherial etherial : worldState.getEtherials()) {
			etherial.accept(this);
		}

		worldState.getGsos().removeAll(dead);
		worldState.getEtherials().removeAll(expired);
		for (Etherial etherial : created) {
			worldState.addEtherial(etherial);
		}
	}

	@Override
	public void visit(Projectile projectile) {
		if (projectile.hasExpired()) {
			expired.add(projectile);
		}
	}

	@Override
	public void visit(Explosion explosion) {
		if (explosion.hasExpired()) {
			expired.add(explosion);
		}
	}

	@Override
	public void visit(Slash slash) {
		if (slash.hasExpired()) {
			expired.add(slash);
		}
	}

	@Override
	public void visit(ZergDeath zergDeath) {
		if (zergDeath.hasExpired()) {
			expired.add(zergDeath);
		}
	}

	@Override
	public void visit(Minion unit) {
		if (unit.hasExpired()) {
			dead.add(unit);
			worldState.addEtherial(new ZergDeath(unit.getPosition(), (int) unit.getRadius()));
		}

	}

	@Override
	public void visit(ResourcePatch resourcePatch) {
		if (resourcePatch.hasExpired()) {
			dead.add(resourcePatch);
		}
	}

	@Override
	public void visit(Hero hero) {
		if (hero.hasExpired()) {
			dead.add(hero);
			worldState.addEtherial(new ZergDeath(hero.getPosition(), (int) hero.getRadius()));
		}
	}

	@Override
	public void visit(ExplosiveProjectile explosiveProjectile) {
		if (explosiveProjectile.hasExpired()) {
			expired.add(explosiveProjectile);
			created.add(new Explosion(explosiveProjectile.getLocation()));
			// created.add(new CircleGraphic(explosiveProjectile.getLocation(), explosiveProjectile.getExplosionRadius()));
		}
	}

	@Override
	public void visit(CircleGraphic circleGraphic) {
		if (circleGraphic.hasExpired()) {
			expired.add(circleGraphic);
		}

	}

	@Override
	public void runStep(WorldState worldState, double timeStep) {
		this.worldState = worldState;
		this.dead = new ArrayList<GameSpaceObject>();
		this.expired = new ArrayList<Etherial>();
		this.created = new ArrayList<Etherial>();

		for (GameSpaceObject gso : worldState.getGsos()) {
			gso.acceptUpdateVisitor(this);
		}

		for (GameSpaceObject gso : worldState.getGsos()) {
			gso.travelToTheFuture();
		}

		for (Etherial etherial : worldState.getEtherials()) {
			etherial.accept(this);
		}

		endPhase();
	}

	@Override
	public void visit(Building building) {
		if (building.hasExpired()) {
			dead.add(building);
		}
	}

	@Override
	public void visit(Worker unit) {
		if (unit.hasExpired()) {
			dead.add(unit);
			worldState.addEtherial(new ZergDeath(unit.getPosition(), (int) unit.getRadius()));
		}
	}
}
