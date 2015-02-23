package euclid.two.dim.visitor;

import euclid.two.dim.behavior.SteeringBehavior;
import euclid.two.dim.model.Boid;
import euclid.two.dim.model.EuVector;
import euclid.two.dim.model.Fish;
import euclid.two.dim.model.GameSpaceObject;
import euclid.two.dim.model.Obstacle;
import euclid.two.dim.model.Unit;
import euclid.two.dim.updater.UpdateVisitor;
import euclid.two.dim.world.WorldState;

public class SteeringStep implements UpdateVisitor
{
	private double timeStep;
	private WorldState worldState;
	
	public SteeringStep(WorldState worldState, double timeStep)
	{
		this.timeStep = timeStep;
		this.worldState = worldState;
	}
	
	public void runStep()
	{
		for (GameSpaceObject gso : worldState.getGameSpaceObjects())
		{
			gso.acceptUpdateVisitor(this);
		}
		
		for (GameSpaceObject gso : worldState.getGameSpaceObjects())
		{
			gso.travelToTheFuture();
		}
	}
	
	@Override
	public void visit(Unit unit)
	{
		double mass = unit.getMass();
		SteeringBehavior sb = unit.getSteeringBehavior();
		EuVector steeringForce = sb.calculate();
		EuVector acceleration = steeringForce.dividedBy(mass);
		
		EuVector velocity = unit.getVelocity();
		EuVector futureVelocity = new EuVector(unit.getVelocity());
		EuVector futurePosition;
		EuVector position = new EuVector(unit.getPosition());
		
		futureVelocity = velocity.add(acceleration.multipliedBy(timeStep));
		futureVelocity.truncate(unit.getMaxSpeed());
		
		futurePosition = position.add(futureVelocity.multipliedBy(timeStep / 100));
		
		unit.setFuturePosition(futurePosition);
		unit.setVelocity(velocity);
		unit.setFutureVelocity(futureVelocity);
		
		if (velocity.getMagnitude() > 1)
		{
			unit.setTheta(Math.atan2(velocity.getX(), -1 * velocity.getY()));
		}
		//
		unit.getRenderComponent().acceptUpdate(unit, timeStep);
	}
	
	@Override
	public void visit(Fish fish)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visit(Boid boid)
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visit(Obstacle obstacle)
	{
		// TODO Auto-generated method stub
		
	}
	
}
