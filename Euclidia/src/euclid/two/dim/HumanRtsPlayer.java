package euclid.two.dim;

import java.util.ArrayList;
import java.util.UUID;

import euclid.two.dim.command.Command;
import euclid.two.dim.team.Team;

public class HumanRtsPlayer extends HumanPlayer {
	private ArrayList<UUID> selectedUnits;
	private static final Object lock = new Object();
	private int selectedAbility;

	public HumanRtsPlayer(Team team) {
		this.team = team;
		this.selectedUnits = new ArrayList<UUID>();
		this.selectedAbility = -1;
		this.commands = new ArrayList<Command>();
	}

	public Team getTeam() {
		return team;
	}

}
