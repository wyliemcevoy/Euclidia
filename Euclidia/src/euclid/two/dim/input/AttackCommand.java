package euclid.two.dim.input;

import java.util.UUID;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.RoomPath;
import euclid.two.dim.model.Minion;
import euclid.two.dim.path.PathCalculator;
import euclid.two.dim.updater.UpdateEngine;
import euclid.two.dim.world.WorldState;

public class AttackCommand implements InputCommand
{
	private UpdateEngine updateEngine;
	private EuVector target;
	private UUID id;

	public AttackCommand(UpdateEngine updateEngine, EuVector target, UUID id)
	{
		this.updateEngine = updateEngine;
		this.target = target;
		this.id = id;
	}

	@Override
	public void execute()
	{
		WorldState worldState = updateEngine.getCurrentWorldState();
		Minion unit = updateEngine.getCurrentWorldState().getUnit(id);

		// if unit died between planning time step and execution of this command
		// the unit will be null (hence the null test)
		if (unit != null)
		{
			RoomPath roomPath = PathCalculator.calculateRoomPath(worldState, unit.getPosition(), target);
			unit.setPath(roomPath.toPath());
		}
	}
}
