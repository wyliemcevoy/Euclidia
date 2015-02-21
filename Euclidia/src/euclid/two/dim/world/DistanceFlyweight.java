package euclid.two.dim.world;

import java.util.HashMap;

import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.optimizer.GsoPair;

public class DistanceFlyweight
{
	private WorldState worldState;
	private HashMap<GsoPair, Double> distanceCache;
	
	public DistanceFlyweight(WorldState worldState)
	{
		this.worldState = worldState;
		
		this.distanceCache = new HashMap<GsoPair, Double>();
	}
	
	public double getDistance(GameSpaceObject one, GameSpaceObject two)
	{
		GsoPair gsoPair = new GsoPair(one.getId(), two.getId());
		
		if (distanceCache.containsKey(gsoPair))
		{
			return distanceCache.get(gsoPair);
		} else
		{
			EuVector distVect = one.getPosition().subtract(two.getPosition());
			
			Double distance = distVect.getMagnitudeSquared();
			
			distanceCache.put(gsoPair, distance);
			return distance;
			
		}
		
	}
	
}
