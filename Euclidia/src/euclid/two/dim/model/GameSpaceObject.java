package euclid.two.dim.model;

import java.awt.Color;
import java.util.Random;

import euclid.two.dim.Configuration;
import euclid.two.dim.behavior.SteeringBehavior;

public abstract class GameSpaceObject
{
	protected double radius;
	protected EuVector position;
	protected EuVector futurePosition;
	protected EuVector velocity;
	protected EuVector futureVelocity;
	protected static final double maxSpeed = Configuration.maxSpeed;
	protected SteeringBehavior sb;
	protected double mass;
	protected Color color;

	public GameSpaceObject()
	{
		Random rand = new Random();
		color = new Color(rand.nextInt(250), rand.nextInt(250), rand.nextInt(250));
	}

	public double getRadius()
	{
		return radius;
	}

	public Color getColor()
	{
		return color;
	}

	public GameSpaceObject(GameSpaceObject copy)
	{
		this.position = new EuVector(copy.getPosition());
		this.color = copy.getColor();
		this.radius = copy.getRadius();
		//this.sb = new SteeringBehavior(copy.getSteeringBehavior());
	}

	public SteeringBehavior getSteeringBehavior()
	{
		return sb;
	}

	public void setSteeringBehavior(SteeringBehavior steeringBehavior)
	{
		this.sb = steeringBehavior;
	}

	public double getMaxSpeed()
	{
		return maxSpeed;
	}

	public EuVector getPosition()
	{
		return position;
	}

	public void setPosition(EuVector position)
	{
		this.position = position;
	}

	public void setVelocity(EuVector velocity)
	{
		this.velocity = velocity;
	}

	public EuVector getVelocity()
	{
		return velocity;
	}

	public void update(double timeStep)
	{
		EuVector steeringForce = sb.calculate();
		EuVector acceleration = steeringForce.dividedBy(mass);
		futureVelocity = velocity.add(acceleration.multipliedBy(timeStep));
		futureVelocity.truncate(maxSpeed);
		futurePosition = position.add(futureVelocity.multipliedBy(timeStep / 100));
	}

	private void toroidify(int width, int height)
	{
		futurePosition.setX(futurePosition.getX() % width);
		futurePosition.setY(futurePosition.getY() % height);
	}

	public void travelToTheFuture()
	{
		position = new EuVector(futurePosition);
		velocity = new EuVector(futureVelocity);
		toroidify(Configuration.width, Configuration.height);
	}

	public void separate()
	{
		// TODO Auto-generated method stub
	}

	public EuVector getFuturePosition()
	{
		return futurePosition;
	}

	public void setFuturePosition(EuVector futurePosition)
	{
		this.futurePosition = futurePosition;
	}

	public void separate2()
	{

	}

}
