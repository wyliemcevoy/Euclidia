package euclid.two.dim;

import euclid.two.dim.model.EuVector;

public abstract class HumanPlayer extends Player {
	public abstract void click(EuVector vector);

	/*
	 * public abstract void mouseDown(EuVector vector); public abstract void mouseReleased(EuVector vector);
	 */
	public abstract void keyPressed(char c);

	public abstract void selectUnitsIn(EuVector mouseDown, EuVector mouseCurrent);

}