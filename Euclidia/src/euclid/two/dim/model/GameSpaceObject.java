package euclid.two.dim.model;

public abstract class GameSpaceObject
{
	protected double radius;
	protected EuVector position;
	protected EuVector futurePosition;
	protected EuVector velocity;
	protected static final double maxSpeed = 10;
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
		System.out.println(steeringForce);
		EuVector acceleration = steeringForce.dividedBy(mass);
		velocity = velocity.add(acceleration.multipliedBy(timeStep));
		velocity.truncate(maxSpeed);
		position = position.add(velocity.multipliedBy(timeStep / 100));
		toroidify(500, 500);
	}

	private void toroidify(int width, int height)
	{
		position.setX(position.getX() % width);
		position.setY(position.getY() % height);
	}

}
