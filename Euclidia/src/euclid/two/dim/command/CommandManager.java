package euclid.two.dim.command;

import java.util.ArrayList;

public abstract interface CommandManager {
	public void put(Command command);

	public ArrayList<Command> getAllCommands();
}
