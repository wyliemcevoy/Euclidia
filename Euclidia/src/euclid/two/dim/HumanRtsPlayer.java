package euclid.two.dim;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import euclid.two.dim.command.AttackCommand;
import euclid.two.dim.command.Command;
import euclid.two.dim.command.MoveCommand;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.team.Team;
import euclid.two.dim.world.WorldState;

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

	// @Override
	public void click(EuVector location) {
		double x = location.getX();
		double y = location.getY();

		location = new EuVector(x, y);

		for (GameSpaceObject gso : worldState.getGameSpaceObjects()) {
			if (location.subtract(gso.getPosition()).getMagnitudeSquared() < gso.getRadius() * gso.getRadius()) {
				commands.add(new AttackCommand(selectedUnits, gso.getId()));
			}
		}

		if (selectedAbility != -1) {

		}
		else {
			commands.add(new MoveCommand(selectedUnits, location));
		}
	}

	// @Override
	public void keyPressed(char c) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Command> getCommands() {
		synchronized (lock) {
			ArrayList<Command> result = commands;

			commands = new ArrayList<Command>();

			return result;
		}
	}

	@Override
	public void acceptWorldState(WorldState worldState) {
		synchronized (lock) {
			this.worldState = worldState;
		}
	}

	// @Override
	public void selectUnitsIn(EuVector mouseDown, EuVector mouseCurrent) {
		synchronized (lock) {
			System.out.println(worldState + " " + mouseDown + " " + mouseCurrent);
			this.selectedUnits = this.worldState.getUnitsInsideRect(this.team, mouseDown, mouseCurrent);

			for (UUID id : selectedUnits) {
				System.out.println("Unit selected " + id);
			}

		}
	}

	@Override
	public void addCommand(Command command) {
		synchronized (lock) {
			this.commands.add(command);
		}
	}

}
