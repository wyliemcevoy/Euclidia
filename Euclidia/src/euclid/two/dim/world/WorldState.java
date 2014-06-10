package euclid.two.dim.world;

import java.util.ArrayList;

import euclid.two.dim.model.Fish;
import euclid.two.dim.model.GameSpaceObject;

public class WorldState
{
	private ArrayList<GameSpaceObject> fish;

	public WorldState()
	{
		this.setFish(new ArrayList<GameSpaceObject>());
	}

	public void addObject(GameSpaceObject gso)
	{
		fish.add(gso);
	}

	public void update(long timeStep)
	{
		for (GameSpaceObject fishi : fish)
		{
			fishi.update(timeStep);
		}

		for (GameSpaceObject fishi : fish)
		{
			fishi.travelToTheFuture();
		}

		for (GameSpaceObject fishi : fish)
		{
			fishi.separate();
		}

		for (GameSpaceObject fishi : fish)
		{
			fishi.travelToTheFuture();
		}
	}

	/**
	 * @return the fish
	 */
	public ArrayList<GameSpaceObject> getFish()
	{
		return fish;
	}

	/**
	 * @param fish
	 *            the fish to set
	 */
	public void setFish(ArrayList<GameSpaceObject> fish)
	{
		this.fish = fish;
	}

	public WorldState deepCopy()
	{
		WorldState copy = new WorldState();
		ArrayList<GameSpaceObject> fishes = new ArrayList<GameSpaceObject>();

		for (GameSpaceObject fishi : fish)
		{
			fishes.add(new Fish(fishi));
		}

		copy.setFish(fishes);

		return copy;
	}
}
