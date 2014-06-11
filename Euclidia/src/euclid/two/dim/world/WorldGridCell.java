package euclid.two.dim.world;

import java.util.ArrayList;

import euclid.two.dim.model.GameSpaceObject;

public class WorldGridCell
{
	private ArrayList<GameSpaceObject> contents;
	
	public WorldGridCell()
	{
		contents = new ArrayList<GameSpaceObject>();
	}
	
	public void add(GameSpaceObject gso) {
		contents.add(gso);
	}
	
	public ArrayList<GameSpaceObject> getContents() {
		return contents;
	}
	
}
