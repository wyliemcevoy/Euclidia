package euclid.two.dim.model;

public abstract class GameSpaceObject
{
	protected double radius;
	protected EuVector position;
	protected EuVector futurePosition;
	protected EuVector velocity;
	protected EuVector futureVelocity;
	protected static final double maxSpeed = 30;
	protected SteeringBehavior sb;
	protected double mass;

	public GameSpaceObject()
	{

	}

	public GameSpaceObject(GameSpaceObject copy)
	{
		this.position = new EuVector(copy.getPosition());
		//this.sb = new SteeringBehavior(copy.getSteeringBehavior());
	}

	public SteeringBehavior getSteeringBehavior()
	{
		return sb;
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
		toroidify(500, 500);
	}

}
