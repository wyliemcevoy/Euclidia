package euclid.two.dim.datastructure;

import java.util.ArrayList;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;

public class GridNode {
	private EuVector topLeft, bottomRight;
	private Cell[][] grid;
	private static final int CELL_SIZE = 15;
	private int rows, cols;

	public GridNode(EuVector one, EuVector two) {
		// figure out if one or two is the top left
		double minX = two.getX();
		double maxX = one.getX();
		if (one.getX() < two.getX()) {
			minX = one.getX();
			maxX = two.getX();
		}

		double minY = two.getY();
		double maxY = one.getY();

		if (one.getY() < two.getY()) {
			minY = one.getY();
			maxY = two.getY();
		}

		this.topLeft = new EuVector(minX, minY);
		this.bottomRight = new EuVector(maxX, maxY);

		rows = (int) Math.ceil((maxY - minY) / CELL_SIZE);
		cols = (int) Math.ceil((maxX - minX) / CELL_SIZE);
		grid = new Cell[rows][cols];
	}

	public void add(GameSpaceObject gso) {
		EuVector gridSpace = gso.getPosition().subtract(topLeft);
		int c = (int) Math.floor(gridSpace.getX() / CELL_SIZE);
		int r = (int) Math.floor(gridSpace.getY() / CELL_SIZE);

		// Lazy initialize
		if (grid[r][c] == null) {
			grid[r][c] = new Cell();
			grid[r][c].add(gso);
		}
	}

	public ArrayList<GameSpaceObject> getGsosInRange(EuVector location, double range) {
		ArrayList<GameSpaceObject> build = new ArrayList<GameSpaceObject>();

		int startRow = Math.max(Math.min(getRow(location.getY() - range), rows - 1), 0);
		int endRow = Math.max(Math.min(getRow(location.getY() + range), rows - 1), 0);
		int startCol = Math.max(Math.min(getCol(location.getX() - range), cols - 1), 0);
		int endCol = Math.max(Math.min(getCol(location.getX() + range), cols - 1), 0);

		for (int r = startRow; r <= endRow; r++) {
			for (int c = startCol; c <= endCol; c++) {
				if (!(grid[r][c] == null)) {
					for (GameSpaceObject gso : grid[r][c].getGsos()) {
						// actual distance check
						if (gso.getPosition().subtract(location).getMagnitudeSquared() <= range * range) {
							build.add(gso);
						}
					}
				}
			}
		}

		return build;
	}

	private int getRow(double y) {
		return (int) Math.floor((y - topLeft.getY()) / CELL_SIZE);
	}

	private int getCol(double x) {
		return (int) Math.floor((x - topLeft.getX()) / CELL_SIZE);
	}

	private class Cell {
		private ArrayList<GameSpaceObject> gsos;

		Cell() {
			gsos = new ArrayList<GameSpaceObject>();
		}

		void add(GameSpaceObject gso) {
			gsos.add(gso);
		}

		ArrayList<GameSpaceObject> getGsos() {
			return gsos;
		}
	}
}
