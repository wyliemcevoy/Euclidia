package euclid.two.dim.input;

import java.awt.Color;

import euclid.two.dim.etherial.Explosion;
import euclid.two.dim.etherial.Projectile;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.RoomPath;
import euclid.two.dim.model.Minion;
import euclid.two.dim.path.PathCalculator;
import euclid.two.dim.updater.UpdateEngine;
import euclid.two.dim.world.WorldState;

public class ClickEvent implements InputCommand
{
	private int x, y;
	private UpdateEngine updateEngine;
	
	public ClickEvent(int x, int y, UpdateEngine updateEngine)
	{
		this.x = x;
		this.y = y;
		this.updateEngine = updateEngine;
	}
	
	public ClickEvent(ClickEvent clone)
	{
		this.x = clone.getX();
		this.y = clone.getY();
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	@Override
	public void execute()
	{
		WorldState worldState = updateEngine.getCurrentWorldState();
		EuVector adjustedTarget = new EuVector(x / worldState.getCamera().getZoom(), y / worldState.getCamera().getZoom());
		worldState.addEtherial(new Explosion(new EuVector(adjustedTarget)));
		
		Minion unit = null;
		Minion target = null;
		
		for (GameSpaceObject fish : worldState.getSelected())
		{
			if (fish instanceof Minion && ((Minion) fish).getPlayer().getColor().equals(Color.RED))
			{
				
				RoomPath roomPath = PathCalculator.calculateRoomPath(worldState, fish.getPosition(), adjustedTarget);
				fish.setPath(roomPath.toPath());
				
				unit = (Minion) fish;
			}
			if (fish instanceof Minion && ((Minion) fish).getPlayer().getColor().equals(Color.BLUE) && unit != null)
			{
				
				target = (Minion) fish;
				Projectile projectile = new Projectile(unit, target);
				worldState.addEtherial(projectile);
			}
		}
	}
}
