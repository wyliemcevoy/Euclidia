package euclid.two.dim;

import euclid.two.dim.model.EuVector;

public abstract class HumanPlayer extends Player
{
	
	public abstract void click(EuVector vector);
	
	public abstract void keyPressed(char c);
	
}