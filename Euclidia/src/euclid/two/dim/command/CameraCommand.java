package euclid.two.dim.command;

import euclid.two.dim.model.EuVector;

public class CameraCommand extends Command
{
	private double x, y;
	
	public CameraCommand(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
	
	public CameraCommand(EuVector vect)
	{
		this.x = vect.getX();
		this.y = vect.getY();
	}
	
	@Override
	public void accept(CommandVisitor commandVisitor)
	{
		//commandVisitor.visit(this);
	}
	
	/**
	 * @return the x
	 */
	public double getX()
	{
		return x;
	}
	
	/**
	 * @return the y
	 */
	public double getY()
	{
		return y;
	}
}
