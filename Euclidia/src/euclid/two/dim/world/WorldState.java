package euclid.two.dim.world;

import java.util.ArrayList;

import euclid.two.dim.exception.OutOfBoundsException;
import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Door;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Fish;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Room;

public class WorldState
{
	private ArrayList<GameSpaceObject> fish;
	private WorldGrid worldGrid;
	private ArrayList<Room> rooms;
	private ArrayList<Door> doors;
	private ArrayList<GameSpaceObject> selectedObjects;

	//private NavMesh navMesh;

	/**
	 * @param rooms
	 *            the rooms to set
	 */
	public void setRooms(ArrayList<Room> rooms)
	{
		this.rooms = rooms;
	}

	public WorldGrid getWorldGrid()
	{
		return worldGrid;
	}

	public void setWorldGrid(WorldGrid worldGrid)
	{
		this.worldGrid = worldGrid;
	}

	public WorldState()
	{
		this.setFish(new ArrayList<GameSpaceObject>());
		this.worldGrid = new WorldGrid();
		this.rooms = new ArrayList<Room>();
		this.doors = new ArrayList<Door>();
		this.selectedObjects = new ArrayList<GameSpaceObject>();
	}

	public void addObject(GameSpaceObject gso)
	{
		fish.add(gso);
		worldGrid.add(gso);
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

	public ArrayList<GameSpaceObject> getSelected()
	{
		ArrayList<GameSpaceObject> build = new ArrayList<GameSpaceObject>();
		for (GameSpaceObject gso : this.fish)
		{
			if (gso.isSelected())
			{
				build.add(gso);
			}
		}
		return build;
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

		for (GameSpaceObject gso : fish)
		{
			if (gso instanceof Fish)
			{
				copy.addObject(new Fish(gso));
			}
			if (gso instanceof Boid)
			{
				copy.addObject(new Boid(gso));
			}
		}

		/*
		ArrayList<Room> newRooms = new ArrayList<Room>();
		for (Room room : rooms)
		{
			newRooms.add(new Room(room));
		} */
		copy.setDoors(doors);
		copy.setRooms(rooms);
		return copy;
	}

	public void addDoor(Door door)
	{
		this.doors.add(door);
	}

	public void setDoors(ArrayList<Door> doors)
	{
		this.doors = doors;
	}

	public ArrayList<Room> getRooms()
	{

		return rooms;
	}

	public ArrayList<Door> getDoors()
	{
		// TODO Auto-generated method stub
		return doors;
	}

	public Room getRoom(EuVector point) throws OutOfBoundsException
	{
		// Bad implementation (fix with a grid and then store rooms inside)
		for (Room room : rooms)
		{
			if (room.contains(point))
				return room;
		}
		throw new OutOfBoundsException();
	}

	/*
	public NavMesh getNavMesh()
	{
		return navMesh;
	}

	public void setNavMesh(NavMesh navMesh)
	{
		this.navMesh = navMesh;

	}
	*/
}
