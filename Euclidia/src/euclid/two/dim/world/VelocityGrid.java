package euclid.two.dim.world;

import euclid.two.dim.Configuration;
import euclid.two.dim.model.EuVector;

public class VelocityGrid
{
	private static final int gridStep = 5;
	private int width;
	private int height;
	private EuVector[][] grid;

	public VelocityGrid()
	{
		this.width = Configuration.width;
		this.height = Configuration.height;
		grid = new EuVector[height][width];
		for (int y = 0; y < height; y++)
		{
			for (int x = 0; x < width; x++)
			{
				grid[y][x] = new EuVector(0, 0);
			}
		}
	}

}
