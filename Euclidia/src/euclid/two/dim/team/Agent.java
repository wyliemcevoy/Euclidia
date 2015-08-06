package euclid.two.dim.team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import euclid.two.dim.Player;
import euclid.two.dim.command.AttackCommand;
import euclid.two.dim.command.Command;
import euclid.two.dim.command.MoveCommand;
import euclid.two.dim.model.ConvexPoly;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Unit;
import euclid.two.dim.world.WorldState;

public class Agent extends Player {
	protected WorldState worldState;
	protected Random rand;
	protected long lastUpdate;
	protected long countDown;

	public Agent(Team team) {
		this.team = team;
		this.rand = new Random();
		this.countDown = 0;
		this.lastUpdate = System.currentTimeMillis();
	}

	public List<Command> getCommands() {
		ArrayList<Command> result = new ArrayList<Command>();

		if (worldState != null) {
			long now = System.currentTimeMillis();

			countDown -= (now - lastUpdate);

			lastUpdate = now;

			if (countDown <= 0) {

				countDown = 2000;

				ArrayList<UUID> ids = new ArrayList<UUID>();
				for (GameSpaceObject gso : worldState.getGameSpaceObjects()) {
					if (gso.getTeam() == team) {
						ids.add(gso.getId());
					}
				}
				if (rand.nextBoolean()) {
					ArrayList<ConvexPoly> polys = worldState.getGameMap().getNavMesh().getAllPolygons();
					ConvexPoly target = polys.get(rand.nextInt(polys.size()));
					result.add(new MoveCommand(ids, target.getCenter()));
				}
				else {
					if (ids.size() > 0) {
						UUID id = ids.get(0);

						Unit target = (Unit) worldState.getClosestUnfriendly(worldState.getUnit(id));
						if (target != null) {
							result.add(new AttackCommand(ids, target.getId()));
						}
					}
				}

			}
		}
		return result;
	}

	@Override
	public void acceptWorldState(WorldState worldState) {
		this.worldState = worldState;
	}
}
