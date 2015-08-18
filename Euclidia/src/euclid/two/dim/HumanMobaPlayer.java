package euclid.two.dim;

import java.util.ArrayList;
import java.util.UUID;

import euclid.two.dim.command.Command;
import euclid.two.dim.team.Team;

public class HumanMobaPlayer extends HumanPlayer {
	private UUID heroId;
	private int selectedAbility;
	private static final Object lock = new Object();

	public HumanMobaPlayer(Team team, UUID heroId) {
		this.team = team;
		this.heroId = heroId;
		this.selectedAbility = -1;
		this.commands = new ArrayList<Command>();
	}
}
