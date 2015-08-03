package euclid.two.dim.model;

import java.util.ArrayList;

public class NavMesh {
	private ArrayList<ConvexPoly> polys;

	public NavMesh() {
		this.polys = new ArrayList<ConvexPoly>();
	}

	public void addPoly(ConvexPoly poly) {
		polys.add(poly);
	}

	public void preCalculatePaths() {
		// TODO run A* on every possible path and store it in memory?
	}

	public ConvexPoly getPoly(EuVector point) {
		// Bad implementation (fix with a grid and then store rooms inside)
		for (ConvexPoly poly : polys) {
			if (poly.contains(point))
				return poly;
		}
		return null;
	}

	public ArrayList<ConvexPoly> getAllPolygons() {
		return polys;
	}
}
