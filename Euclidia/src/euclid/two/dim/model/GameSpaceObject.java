package euclid.two.dim.model;

import java.awt.Color;
import java.util.Random;
import java.util.UUID;

import euclid.two.dim.Configuration;
import euclid.two.dim.behavior.SteeringBehavior;
import euclid.two.dim.path.Path;
import euclid.two.dim.render.RenderComponent;
import euclid.two.dim.team.Team;
import euclid.two.dim.updater.Updatable;

public abstract class GameSpaceObject implements Updatable {
	protected double radius;
	protected EuVector position;
	protected EuVector futurePosition;
	protected EuVector velocity;
	protected EuVector futureVelocity;
	protected double maxSpeed = Configuration.maxSpeed;
	protected double mass;
	protected Color color;
	protected Path path;
	protected boolean isSelected;
	protected UUID id;
	protected double theta;
	protected boolean isAtRest;
	protected RenderComponent renderComponent;
	protected SteeringBehavior steeringBehavior;
	protected Team team;

	public SteeringBehavior getSteeringBehavior() {
		return steeringBehavior;
	}

	public void setSteeringBehavior(SteeringBehavior steeringBehavior) {
		this.steeringBehavior = steeringBehavior;
	}

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public void setTheta(double theta) {
		this.theta = theta;
	}

	/**
	 * @return the isSelected
	 */
	public boolean isSelected() {
		return isSelected;
	}

	public void setFutureVelocity(EuVector euVector) {
		futureVelocity = euVector;
	}

	public abstract boolean hasExpired();

	/**
	 * @param isSelected the isSelected to set
	 */
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public GameSpaceObject() {
		Random rand = new Random();
		this.color = new Color(rand.nextInt(250), rand.nextInt(250), rand.nextInt(250));
		this.id = UUID.randomUUID();
		this.theta = 0;
		this.isAtRest = true;
		this.steeringBehavior = SteeringBehavior.Flock;
		this.team = Team.Neutral;
	}

	public double getRadius() {
		return radius;
	}

	public Color getColor() {
		return color;
	}

	public UUID getId() {
		return id;
	}

	public GameSpaceObject(GameSpaceObject copy) {
		this.position = new EuVector(copy.getPosition());
		this.color = copy.getColor();
		this.radius = copy.getRadius();
		this.velocity = new EuVector(copy.getVelocity());
		this.futurePosition = new EuVector(copy.getFuturePosition());
		this.id = copy.getId();
		this.theta = copy.getTheta();
		this.renderComponent = copy.getRenderComponent().deepCopy();
		this.team = copy.getTeam();
		this.steeringBehavior = copy.getSteeringBehavior();
		this.path = copy.getPath().deepCopy();
	}

	public Team getTeam() {
		return team;
	}

	public RenderComponent getRenderComponent() {
		return renderComponent;
	}

	public boolean isAtRest() {
		return isAtRest;
	}

	public double getTheta() {
		return theta;
	}

	public double getMaxSpeed() {
		return maxSpeed;
	}

	public EuVector getPosition() {
		return position;
	}

	public void setPosition(EuVector position) {
		this.position = position;
	}

	public void setVelocity(EuVector velocity) {
		this.velocity = velocity;
	}

	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public EuVector getVelocity() {
		return velocity;
	}

	public void update(double timeStep) {

	}

	public void travelToTheFuture() {
		position = new EuVector(futurePosition);
		velocity = new EuVector(futureVelocity);
	}

	public EuVector getFuturePosition() {
		return futurePosition;
	}

	public void setFuturePosition(EuVector futurePosition) {
		this.futurePosition = futurePosition;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof GameSpaceObject) {
			return ((GameSpaceObject) o).getId() == id;
		}
		else
			return false;
	}

	@Override
	public String toString() {
		return "[" + position + futurePosition + velocity + "]";
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public Path getPath() {
		return path;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
}
