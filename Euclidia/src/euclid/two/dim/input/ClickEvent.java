package euclid.two.dim.input;

import euclid.two.dim.UpdateEngine;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.RoomPath;
import euclid.two.dim.path.PathCalculator;
import euclid.two.dim.world.WorldState;

public class ClickEvent implements InputCommand
{
	private int x, y;
	private UpdateEngine updateEngine;

	public ClickEvent(int x, int y, UpdateEngine updateEngine)
	{
		this.x = x;
		this.y = y;
		this.updateEngine = updateEngine;
	}

	public ClickEvent(ClickEvent clone)
	{
		this.x = clone.getX();
		this.y = clone.getY();
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}

	@Override
	public void execute()
	{
		WorldState worldState = updateEngine.getCurrentWorldState();
		for (GameSpaceObject fish : worldState.getSelected())
		{
			RoomPath roomPath = PathCalculator.calculateRoomPath(worldState, fish.getPosition(), new EuVector(x, y));
			fish.setPath(roomPath.toPath());
		}

	}
}
