package test;

import java.util.UUID;

import euclid.two.dim.datastructure.HasUUID;

public class IntegerBox implements HasUUID {

	private UUID id;
	private int value;

	public IntegerBox(int value) {
		this.value = value;
		this.id = UUID.randomUUID();
	}

	public int getValue() {
		return value;
	}

	@Override
	public UUID getUUID() {
		return id;
	}

	@Override
	public String toString() {
		return "" + value;
	}
}
