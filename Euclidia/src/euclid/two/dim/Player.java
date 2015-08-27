package euclid.two.dim;

import euclid.two.dim.model.Resource;
import euclid.two.dim.team.Team;

public class Player {
	protected Team team;

	private int gas, minerals;

	public Player(Team team) {
		this.team = team;
		this.gas = 0;
		this.minerals = 50;
	}

	public int getGas() {
		return gas;
	}

	public void setGas(int gas) {
		this.gas = gas;
	}

	public int getMinerals() {
		return minerals;
	}

	public void setMinerals(int minerals) {
		this.minerals = minerals;
	}

	public Team getTeam() {
		return team;
	}

	public void addResource(Resource type, int amount) {
		switch (type) {
		case GAS:
			this.gas += amount;
			break;
		case MINERALS:
			this.minerals += amount;
			break;
		default:
			break;
		}
	}
}
