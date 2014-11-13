package euclid.two.dim.input;

import java.util.ArrayList;

import euclid.two.dim.updater.UpdateEngine;

public class CommandManager
{
	private UpdateEngine updateEngine;
	private ArrayList<InputCommand> commands;

	public CommandManager(UpdateEngine updateEngine)
	{
		this.updateEngine = updateEngine;
		this.commands = new ArrayList<InputCommand>();
	}

	public void setUpdateEngine(UpdateEngine updateEngine)
	{
		this.updateEngine = updateEngine;
	}
}
