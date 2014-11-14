package euclid.two.dim.model;

import java.awt.Color;
import java.util.Random;
import java.util.UUID;

import euclid.two.dim.Configuration;
import euclid.two.dim.Path;
import euclid.two.dim.behavior.SteeringBehavior;
import euclid.two.dim.updater.Updatable;

public abstract class GameSpaceObject implements Updatable
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
	protected EuVector future;
	protected Path path;
	protected boolean isSelected;
	protected UUID id;

	/**
	 * @return the isSelected
	 */
	public boolean isSelected()
	{
		return isSelected;
	}

	/**
	 * @param isSelected
	 *            the isSelected to set
	 */
	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}

	public GameSpaceObject()
	{
		Random rand = new Random();
		color = new Color(rand.nextInt(250), rand.nextInt(250), rand.nextInt(250));
		this.id = UUID.randomUUID();
	}

	public double getRadius()
	{
		return radius;
	}

	public Color getColor()
	{
		return color;
	}

	public UUID getId()
	{
		return id;
	}

	public GameSpaceObject(GameSpaceObject copy)
	{
		this.position = new EuVector(copy.getPosition());
		this.color = copy.getColor();
		this.radius = copy.getRadius();
		this.velocity = new EuVector(copy.getVelocity());
		this.futurePosition = new EuVector(copy.getFuturePosition());
		this.future = new EuVector(copy.getFuture());
		this.id = copy.getId();
		specificConstructor(copy);
	}

	protected abstract void specificConstructor(GameSpaceObject copy);

	public EuVector getFuture()
	{
		return future;
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

		this.future = position.add(futureVelocity.multipliedBy(timeStep / 20));
		/*
		if (sb instanceof Flock)
		{
			ArrayList<GameSpaceObject> objects = new ArrayList<GameSpaceObject>();
			
			for (GameSpaceObject gso : ((Flock) sb).getWorldState().getFish())
			{
				if (gso.radius > 5)
				{
					objects.add(gso);
				}
			}
			
			for (GameSpaceObject object : objects)
			{
				EuVector obDist = future.subtract(object.getPosition());
				
				if (obDist.getMagnitude() < object.getRadius())
				{
					
				}
			}
			
			WorldGrid grid = ((Flock) sb).getWorldState().getWorldGrid();
			
			EuVector adustedFuture = grid.getForceAt(future.getX(), future.getY()).dividedBy(2);
			adustedFuture = adustedFuture.add(future);
			
			futurePosition = position.add(adustedFuture.subtract(position).truncate(7));
			
		}
		
		 */
		specificUpdate(futureVelocity.multipliedBy(timeStep / 100));
	}

	public EuVector getUpdate()
	{
		EuVector steeringForce;

		if (sb == null)
		{
			steeringForce = new EuVector(1, 1);
		} else
		{
			steeringForce = sb.calculate();
		}
		EuVector acceleration = steeringForce.dividedBy(mass);
		EuVector temp = velocity.add(acceleration.multipliedBy(330));
		temp.truncate(maxSpeed);

		return position.add(temp.multipliedBy(330 / 25));
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

	@Override
	public String toString()
	{
		return "[" + position + futurePosition + velocity + "]";
	}

	public abstract void specificUpdate(EuVector displacement);

	public void setPath(Path path)
	{
		this.path = path;
	}

}
