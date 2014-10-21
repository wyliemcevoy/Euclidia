package euclid.two.dim.world;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import euclid.two.dim.exception.OutOfBoundsException;
import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Door;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Fish;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Room;
import euclid.two.dim.render.Camera;

public class WorldState
{
	private ArrayList<GameSpaceObject> fish;
	private WorldGrid worldGrid;
	private ArrayList<Room> rooms;
	private ArrayList<Door> doors;
	private Camera camera;
	
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
		copy.setCamera(new Camera(camera));
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
	
	public Camera getCamera()
	{
		return camera;
	}
	
	public void setCamera(Camera camera)
	{
		this.camera = camera;
	}
	
	public AffineTransform buildTransform()
	{
		AffineTransform aTransform = new AffineTransform();
		aTransform.setToTranslation(500, 000);
		aTransform.rotate(Math.toRadians(45));
		aTransform.scale(camera.getZoom(), camera.getZoom());
		
		return aTransform;
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
