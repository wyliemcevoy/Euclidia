package euclid.two.dim.input;

import euclid.two.dim.UpdateEngine;

public class QuitCommand implements InputCommand
{
	private UpdateEngine updateEngine;

	public QuitCommand(UpdateEngine updateEngine)
	{
		this.updateEngine = updateEngine;
	}

	@Override
	public void execute()
	{
		updateEngine.requestStop();
	}

}
