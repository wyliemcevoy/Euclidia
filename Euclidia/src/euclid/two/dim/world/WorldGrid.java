package euclid.two.dim.world;

import java.util.List;

import euclid.two.dim.Configuration;
import euclid.two.dim.model.GameSpaceObject;

public class WorldGrid
{
	private WorldGridCell[][] grid;
	private int rows;
	private int cols;
	
	public WorldGrid()
	{
		rows = Configuration.width / Configuration.gridSize;
		cols = Configuration.height / Configuration.gridSize;
		grid = new WorldGridCell[cols][rows];
		intialize();
	}
	
	private void intialize()
	{
		for (int x = 0; x < rows; x++)
		{
			for (int y = 0; y < cols; y++)
			{
				grid[y][x] = new WorldGridCell();
			}
		}
	}
	
	public List<GameSpaceObject> get(int worldSpaceX, int worldSpaceY)
	{
		int gridSpaceX = worldSpaceX / Configuration.gridSize;
		int gridSpaceY = worldSpaceY / Configuration.gridSize;
		return grid[gridSpaceY][gridSpaceX].getContents();
	}
	
	public void add(GameSpaceObject gso)
	{
		int gridSpaceX = (int) Math.floor(gso.getPosition().getX() / Configuration.gridSize);
		int gridSpaceY = (int) Math.floor(gso.getPosition().getY() / Configuration.gridSize);
		grid[gridSpaceY][gridSpaceX].add(gso);
	}
}
