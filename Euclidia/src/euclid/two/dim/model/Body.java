package euclid.two.dim.model;

public class Body
{
	private EuVector position;
	private EuVector velocity;
	private double mass;
	private double radius;
	private double maxSpeed;
	
	/** Spring constant F = -k*X (where X is displacement from rest) */
	private double k;
	
	/** distance away from body when system is at rest */
	private double relaxedPosition;
	
	private double coeficientOfKineticFriction;
	
	/** Force of damping F = -c*v (where v is velocity) */
	private double dampingCoefficient;
	
	private void initialize()
	{
		
	}
	
	public Body(EuVector position)
	{
		this.position = new EuVector(position);
		initialize();
	}
}
