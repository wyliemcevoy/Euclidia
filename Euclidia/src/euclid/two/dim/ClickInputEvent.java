package euclid.two.dim;

public class ClickInputEvent extends UserInputEvent
{
	private int x, y;

	public ClickInputEvent(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public ClickInputEvent(ClickInputEvent clone)
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
}
