package euclid.two.dim.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;

public class MoveCommand extends Command {
	private List<UUID> ids;
	private EuVector location;

	public MoveCommand(ArrayList<GameSpaceObject> gsos, EuVector location) {

		this.ids = new ArrayList<UUID>();
		for (GameSpaceObject gso : gsos) {
			ids.add(gso.getId());
		}

		this.location = location.deepCopy();
	}

	public MoveCommand(List<UUID> ids, EuVector location) {
		this.ids = ids;
		this.location = location.deepCopy();
	}

	public MoveCommand(UUID id, EuVector location) {
		this.ids = new ArrayList<UUID>();
		ids.add(id);
		this.location = location.deepCopy();
	}

	public EuVector getLocation() {
		return location;
	}

	@Override
	public void accept(CommandVisitor commandVisitor) {
		commandVisitor.visit(this);
	}

	public List<UUID> getIds() {
		return ids;
	}

}
