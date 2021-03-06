package euclid.two.dim.ability.internal;

import java.util.UUID;

import euclid.two.dim.ability.request.AbilityRequest;
import euclid.two.dim.ability.request.AbilityRequestVisitor;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.world.WorldState;

public abstract class Ability implements AbilityRequestVisitor {
	protected long reloadTime;
	protected long currentTime;
	protected AbilityType abilityType;

	/**
	 * @return the abilityType
	 */
	public AbilityType getAbilityType() {
		return abilityType;
	}

	/**
	 * @param abilityType the abilityType to set
	 */
	public void setAbilityType(AbilityType abilityType) {
		this.abilityType = abilityType;
	}

	public abstract AbilityRequest toRequest(UUID heroId, WorldState worldState, EuVector location);

	public abstract void processRequest(AbilityRequest abilityRequest, WorldState worldState);

	public abstract boolean isValidRequest(AbilityRequest abilityRequest, WorldState worldState);

	public abstract void closeRequest();

	public long getCurrentTime() {
		return currentTime;
	}

	public long getReloadTime() {
		return reloadTime;
	}

	public void update(long timeStep) {
		if (!isOffCooldown()) {
			currentTime = currentTime + timeStep;
		}
	}

	public abstract boolean isImediate();

	public boolean isOffCooldown() {
		return currentTime > reloadTime;
	}

	public abstract Ability deepCopy();

}
