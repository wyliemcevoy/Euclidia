package euclid.two.dim.world;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import euclid.two.dim.exception.OutOfBoundsException;
import euclid.two.dim.model.Boid;
import euclid.two.dim.model.Door;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Explosion;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Room;
import euclid.two.dim.model.Unit;
import euclid.two.dim.render.Camera;
import euclid.two.dim.render.Renderable;
import euclid.two.dim.updater.Updatable;

public class WorldState
{
	private ArrayList<GameSpaceObject> gsos;
	private WorldGrid worldGrids;
	private ArrayList<Room> rooms;
	private ArrayList<Door> doors;
	private Camera camera;
	private ArrayList<Explosion> explosions;
	private ArrayList<Updatable> updatables;
	private ArrayList<Updatable> expired;
	
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
		return worldGrids;
	}
	
	public void setWorldGrid(WorldGrid worldGrid)
	{
		this.worldGrids = worldGrid;
	}
	
	public WorldState()
	{
		this.setFish(new ArrayList<GameSpaceObject>());
		this.worldGrids = new WorldGrid();
		this.rooms = new ArrayList<Room>();
		this.doors = new ArrayList<Door>();
		this.explosions = new ArrayList<Explosion>();
		this.updatables = new ArrayList<Updatable>();
		this.expired = new ArrayList<Updatable>();
	}
	
	public void addObject(GameSpaceObject gso)
	{
		gsos.add(gso);
		worldGrids.add(gso);
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
		
		Iterator<Explosion> it = explosions.iterator();
		while (it.hasNext())
		{
			Explosion explosion = it.next();
			if (explosion.hasExpired(timeStep))
			{
				it.remove();
			}
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
		copy.setCamera(this.camera);
		
		for (Updatable updatable : updatables)
		{
			copy.addUpdatable(updatable);
		}
		
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
		aTransform.setToTranslation(000, 000);
		aTransform.rotate(camera.getRotation());
		aTransform.scale(camera.getZoom(), camera.getZoom());
		
		return aTransform;
	}
	
	public ArrayList<GameSpaceObject> getGameSpaceObjects()
	{
		return gsos;
	}
	
	public Unit getUnit(UUID id)
	{
		// Horrible implementation (change to map)
		for (GameSpaceObject gso : gsos)
		{
			if (gso.getId().equals(id))
			{
				return (Unit) gso;
			}
		}
		return null;
	}
	
	public ArrayList<Explosion> getExplosions()
	{
		explosions = new ArrayList<Explosion>();
		
		for (Updatable updatable : updatables)
		{
			if (updatable instanceof Explosion)
			{
				explosions.add((Explosion) updatable);
			}
		}
		
		return explosions;
	}
	
	public void addUpdatable(Updatable updatable)
	{
		this.updatables.add(updatable);
	}
	
	public ArrayList<Updatable> getUpdatable()
	{
		return updatables;
	}
	
	public void registerAsExpired(Updatable updatable)
	{
		this.expired.add(updatable);
	}
	
	public void purgeExpired()
	{
		updatables.removeAll(expired);
		expired = new ArrayList<Updatable>();
	}
	
	public ArrayList<Renderable> getRenderables()
	{
		ArrayList<Renderable> renderables = new ArrayList<Renderable>();
		
		for (GameSpaceObject gso : this.gsos)
		{
			renderables.add(gso.toRenderable());
		}
		
		for (Updatable updatable : updatables)
		{
			renderables.add(updatable.toRenderable());
		}
		return renderables;
	}
}
