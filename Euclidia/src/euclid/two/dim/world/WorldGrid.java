package euclid.two.dim.world;

import java.util.ArrayList;
import java.util.List;

import euclid.two.dim.Configuration;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;

public class WorldGrid
{
	private WorldGridCell[][] grid;
	private int rows;
	private int cols;
	private int gridSize = Configuration.gridSize;

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

	public List<GameSpaceObject> get(double worldSpaceX, double worldSpaceY)
	{
		int gridSpaceX = (int) worldSpaceX / Configuration.gridSize;
		int gridSpaceY = (int) worldSpaceY / Configuration.gridSize;
		return grid[gridSpaceY][gridSpaceX].getContents();
	}

	public List<GameSpaceObject> get(double radius, EuVector center)
	{
		ArrayList<GameSpaceObject> results = new ArrayList<GameSpaceObject>();
		int minGridX = (int) Math.max(Math.floor(center.getX() - radius), 0);
		int maxGridX = (int) Math.min(Math.ceil(center.getX() + radius), rows);
		int minGridY = (int) Math.max(Math.floor(center.getX() - radius), 0);
		;
		int maxGridY = (int) Math.min(Math.ceil(center.getY() + radius), rows);

		for (int x = minGridX; x < maxGridX; x++)
		{
			for (int y = minGridY; y < maxGridY; y++)
			{
				ArrayList<GameSpaceObject> contents = grid[y][x].getContents();
				for (GameSpaceObject gso : contents)
				{
					if (gso.getPosition().subtract(center).getMagnitude() < radius)
					{
						results.add(gso);
					}
				}
			}
		}

		return results;
	}

	public void add(GameSpaceObject gso)
	{
		if (gso.getRadius() < Configuration.gridSize)
		{

			int gridSpaceX = (int) Math.floor(gso.getPosition().getX() / Configuration.gridSize);
			int gridSpaceY = (int) Math.floor(gso.getPosition().getY() / Configuration.gridSize);
			if (gridSpaceX < 0 || gridSpaceY < 0)
			{
				// System.out.println(gso);
			}

			grid[gridSpaceY][gridSpaceX].add(gso);
		} else
		{

			for (int x = 0; x < rows; x++)
			{
				for (int y = 0; y < cols; y++)
				{
					EuVector pointer = new EuVector(x * Configuration.gridSize, y * Configuration.gridSize);
					EuVector distance = pointer.subtract(gso.getPosition());

				}

			}
		}
	}

	public int getRows()
	{
		return rows;
	}

	public int getCols()
	{
		return cols;
	}

	public int getGridStep()
	{
		return Configuration.gridSize;
	}
}
