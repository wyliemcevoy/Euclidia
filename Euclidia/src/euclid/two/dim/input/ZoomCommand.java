package euclid.two.dim.input;

import euclid.two.dim.UpdateEngine;

public class ZoomCommand implements InputCommand
{
	private UpdateEngine updateEngine;

	public ZoomCommand(UpdateEngine updateEngine)
	{
		this.updateEngine = updateEngine;
	}

	@Override
	public void execute()
	{
		updateEngine.setZoom(updateEngine.getZoom());

	}

}
