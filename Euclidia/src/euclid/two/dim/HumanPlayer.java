package euclid.two.dim;

import euclid.two.dim.command.Command;

public abstract class HumanPlayer extends Player {
	// public abstract void click(EuVector vector);

	// public abstract void keyPressed(char c);

	// public abstract void selectUnitsIn(EuVector mouseDown, EuVector mouseCurrent);

	public abstract void addCommand(Command command);
}