package euclid.two.dim.map;

import euclid.two.dim.model.NavMesh;
import euclid.two.dim.model.Room;

public class SpacePlatformMap extends GameMap
{
	
	public SpacePlatformMap()
	{
		initialize();
	}
	
	private void initialize()
	{
		
		Room room = new Room(0, 0, 0, 0);
		
		this.navMesh = new NavMesh();
		this.navMesh.addRoom(null);
		
	}
	
}
