package euclid.two.dim.map;

import java.util.ArrayList;

import euclid.two.dim.model.ConvexPoly;
import euclid.two.dim.model.NavMesh;

public class GameMap {
	protected NavMesh navMesh;

	public GameMap(NavMesh navMesh) {
		this.navMesh = navMesh;
	}

	public ArrayList<ConvexPoly> getAllPolygons() {
		return navMesh.getAllPolygons();
	}

	public NavMesh getNavMesh() {
		return navMesh;
	}

}
