package euclid.two.dim.world;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.UUID;

import euclid.two.dim.exception.OutOfBoundsException;
import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Door;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Room;
import euclid.two.dim.model.Unit;
import euclid.two.dim.render.Camera;

public class WorldState
{
	private ArrayList<GameSpaceObject> gsos;
	private WorldGrid worldGrid;
	private ArrayList<Room> rooms;
	private ArrayList<Door> doors;
	private Camera camera;
	
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
		gsos.add(gso);
		worldGrid.add(gso);
	}
	
	public void update(long timeStep)
	{
		for (GameSpaceObject fishi : gsos)
		{
			fishi.update(timeStep);
		}
		
		for (GameSpaceObject fishi : gsos)
		
		{
			fishi.travelToTheFuture();
		}
		
		for (GameSpaceObject fishi : gsos)
		
		{
			fishi.separate();
		}
		
		for (GameSpaceObject fishi : gsos)
		
		{
			fishi.travelToTheFuture();
		}
		
	}
	
	public ArrayList<GameSpaceObject> getSelected()
	{
		ArrayList<GameSpaceObject> build = new ArrayList<GameSpaceObject>();
		for (GameSpaceObject gso : this.gsos)
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
		return gsos;
	}
	
	/**
	 * @param fish
	 *            the fish to set
	 */
	public void setFish(ArrayList<GameSpaceObject> fish)
	{
		this.gsos = fish;
	}
	
	public WorldState deepCopy()
	{
		WorldState copy = new WorldState();
		
		for (GameSpaceObject gso : gsos)
		
		{
			if (gso instanceof Unit)
			{
				copy.addObject(new Unit(gso));
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
	
	public ArrayList<GameSpaceObject> getGameSpaceObjects()
	{
		return gsos;
	}
	
	public Unit getUnit(UUID id)
	{
		// HOrrible implementation (change to map)
		for (GameSpaceObject gso : gsos)
		{
			if (gso.getId().equals(id))
			{
				return (Unit) gso;
			}
		}
		return null;
	}
}
