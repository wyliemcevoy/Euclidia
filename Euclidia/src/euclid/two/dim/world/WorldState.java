package euclid.two.dim.world;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import euclid.two.dim.VectorMath;
import euclid.two.dim.etherial.Etherial;
import euclid.two.dim.etherial.Explosion;
import euclid.two.dim.map.GameMap;
import euclid.two.dim.model.Door;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Hero;
import euclid.two.dim.model.Minion;
import euclid.two.dim.model.Room;
import euclid.two.dim.model.Unit;
import euclid.two.dim.render.Camera;
import euclid.two.dim.render.RenderCreator;
import euclid.two.dim.render.Renderable;

public class WorldState
{
	private ArrayList<GameSpaceObject> gsos;
	private WorldGrid worldGrids;
	private ArrayList<Room> rooms;
	private ArrayList<Door> doors;
	private Camera camera;
	private ArrayList<Explosion> explosions;
	private ArrayList<Etherial> etherials;
	private ArrayList<Etherial> expired;
	private GameMap gameMap;
	
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
		this.gsos = new ArrayList<GameSpaceObject>();
		this.worldGrids = new WorldGrid();
		this.rooms = new ArrayList<Room>();
		this.doors = new ArrayList<Door>();
		this.explosions = new ArrayList<Explosion>();
		this.etherials = new ArrayList<Etherial>();
		this.expired = new ArrayList<Etherial>();
	}
	
	public List<GameSpaceObject> getUnfriendliesInRage(Minion unit)
	{
		ArrayList<GameSpaceObject> targets = new ArrayList<GameSpaceObject>();
		
		EuVector unitLocation = new EuVector(unit.getPosition());
		
		for (GameSpaceObject gso : gsos)
		{
			EuVector dist = VectorMath.subtract(gso.getPosition(), unitLocation);
			
			if (dist.getMagnitude() < unit.getDetectionRange())
			{
				targets.add(gso);
			}
		}
		
		return targets;
	}
	
	public double getDistanceBetween(GameSpaceObject one, GameSpaceObject two)
	{
		return VectorMath.subtract(one.getPosition(), two.getPosition()).getMagnitude() - (one.getRadius() + two.getRadius());
	}
	
	public GameSpaceObject getClosestUnfriendly(Unit unit)
	{
		GameSpaceObject target = null;
		double minDist = Double.MAX_VALUE;
		
		for (GameSpaceObject gso : gsos)
		{
			if (gso.getTeam() != unit.getTeam())
			{
				double dist = getDistanceBetween(gso, unit);
				if (dist < unit.getDetectionRange() && dist < minDist)
				{
					target = gso;
					minDist = dist;
				}
				
			}
			
		}
		
		return target;
	}
	
	public void addObject(GameSpaceObject gso)
	{
		gsos.add(gso);
		worldGrids.add(gso);
	}
	
	public void update(long timeStep)
	{/*
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
		*/
		Iterator<Explosion> it = explosions.iterator();
		while (it.hasNext())
		{
			Explosion explosion = it.next();
			if (explosion.hasExpired())
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
	public ArrayList<GameSpaceObject> getGsos()
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
			
			if (gso instanceof Minion)
			{
				copy.addObject(new Minion((Minion) gso));
			}
			if (gso instanceof Hero)
			{
				copy.addObject(new Hero((Hero) gso));
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
		copy.setGameMap(gameMap);
		for (Etherial updatable : etherials)
		{
			copy.addEtherial(updatable);
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
	
	public Room getRoom(EuVector point)
	{
		// Bad implementation (fix with a grid and then store rooms inside)
		for (Room room : rooms)
		{
			if (room.contains(point))
				return room;
		}
		return null;
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
		
		for (Etherial updatable : etherials)
		{
			if (updatable instanceof Explosion)
			{
				explosions.add((Explosion) updatable);
			}
		}
		
		return explosions;
	}
	
	public void addEtherial(Etherial etherial)
	{
		this.etherials.add(etherial);
	}
	
	public ArrayList<Etherial> getUpdatable()
	{
		return etherials;
	}
	
	public void registerAsExpired(Etherial etherial)
	{
		this.expired.add(etherial);
	}
	
	public void purgeExpired()
	{
		etherials.removeAll(expired);
		expired = new ArrayList<Etherial>();
	}
	
	public List<Renderable> getRenderables()
	{
		
		RenderCreator renderCreator = new RenderCreator(this);
		
		return renderCreator.getRenderables();
	}
	
	public List<Etherial> getEtherials()
	{
		return etherials;
	}
	
	public Hero getHero(UUID id)
	{
		// Horrible implementation (change to map)
		for (GameSpaceObject gso : gsos)
		{
			if (gso.getId().equals(id))
			{
				return (Hero) gso;
			}
		}
		return null;
	}
	
	public ArrayList<Unit> getUnitsInRange(EuVector location, int explosionRadius)
	{
		ArrayList<Unit> result = new ArrayList<Unit>();
		
		for (GameSpaceObject gso : gsos)
		{
			EuVector distance = VectorMath.subtract(gso.getPosition(), location);
			
			if (gso instanceof Unit && distance.getMagnitude() - gso.getRadius() < explosionRadius)
			{
				result.add((Unit) gso);
			}
		}
		
		return result;
	}
	
	public GameMap getGameMap()
	{
		return gameMap;
	}
	
	public void setGameMap(GameMap gameMap)
	{
		this.gameMap = gameMap;
	}
	
}
