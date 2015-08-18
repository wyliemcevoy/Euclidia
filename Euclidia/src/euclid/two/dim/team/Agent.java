package euclid.two.dim.team;

import java.util.Random;

import euclid.two.dim.Player;
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
}
