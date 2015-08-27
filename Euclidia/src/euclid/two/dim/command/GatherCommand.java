package euclid.two.dim.command;

import java.util.ArrayList;
import java.util.UUID;

public class GatherCommand extends Command {

	private UUID targetResource;
	private ArrayList<UUID> workers;

	public GatherCommand(ArrayList<UUID> workers, UUID targetResource) {
		this.workers = workers;
		this.targetResource = targetResource;
	}

	public GatherCommand(UUID id, UUID targetResource) {
		this.workers = new ArrayList<UUID>();
		this.targetResource = targetResource;
		workers.add(id);
	}

	@Override
	public void accept(CommandVisitor commandVisitor) {
		commandVisitor.visit(this);
	}

	public UUID getTargetResource() {
		return targetResource;
	}

	public ArrayList<UUID> getWorkers() {
		return workers;
	}

}
