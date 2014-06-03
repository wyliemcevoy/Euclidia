package euclid.two.dim;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class ConsoleFrame extends JFrame
{
	/** uid */
	private static final long serialVersionUID = 1L;
	
	public ConsoleFrame(int width, int height)
	{
		super();
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setSize(width, height);
		this.setVisible(true);
	}
	
}
